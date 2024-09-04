package com.rev_connect_api;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.FieldErrorResponse;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PersonalProfileRepository;
import com.rev_connect_api.repositories.ProfileRepository;
import com.rev_connect_api.security.JwtUtil;
import com.rev_connect_api.utils.UserUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/profileTestInit.sql")
public class NameAndBioTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    PersonalProfileRepository personalProfileRepository;
    @Autowired
    JwtUtil jwtUtil;

    @LocalServerPort
    private int port;

    private String serviceLocation;
    private ObjectMapper mapper;

    private final int maxNameLength = 50;
    private final int maxBioLength = 255;

    private final User initialUser, validUser;
    private final PersonalProfile initialProfile, validProfile, profileBioTooLong;
    private String token;

    public NameAndBioTest() {
        //Profile prior to client request
        initialProfile = new PersonalProfile("Initial bio");
        initialProfile.setId(999L);
        //User prior to client request
        initialUser = new User(111L,"user", "","", "Test", "User", false,LocalDateTime.now(),LocalDateTime.now(),Set.of(Role.ROLE_USER),initialProfile);

        //Valid change to initialProfile
        validProfile = new PersonalProfile("Valid bio");
        //Valid change to initialUser
        validUser = new User("user", "","", "John", "Doe", false,validProfile);

        //Invalid changes to initialProfile
        profileBioTooLong = new PersonalProfile("A".repeat(maxBioLength + 1));
        
    }

    @BeforeEach
    public void beforeEach() {

        serviceLocation = "http://localhost:" + port + "/api/profile";
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        token = jwtUtil.generateToken(initialUser.getUsername(), Set.of("ROLE_USER"));
    }

    @Test
    public void retrieveProfile() throws JsonMappingException, JsonProcessingException  {
        
        //Generate HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);

        //Send HTTP request
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/" + initialUser.getUserId(), HttpMethod.GET, requestEntity,String.class);
        
        //Verify response status code
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

        //Verify response content
        PersonalProfile profile = mapper.readValue(response.getBody().toString(), PersonalProfile.class);
        Assertions.assertEquals(initialProfile.getBio(), profile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual: " + profile.getBio());
    }

    @Test
    public void updateProfileSuccessful() throws JsonMappingException, JsonProcessingException {
        //Generate HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(validProfile, headers);
        
        //Send HTTP request
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/" + initialUser.getUserId(), HttpMethod.PUT, requestEntity,String.class);

        //Verify response status code
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

        //Verify response content
        PersonalProfile responseProfile = mapper.readValue(response.getBody().toString(), PersonalProfile.class);
        Assertions.assertEquals(validProfile.getBio(), responseProfile.getBio(), "Expected: " + validProfile.getBio() + "\nActual result was " + responseProfile.getBio());

        //Verify database content
        PersonalProfile dbProfile = (PersonalProfile)personalProfileRepository.findById(initialProfile.getId()).get();
        Assertions.assertEquals(validProfile.getBio(), dbProfile.getBio(), "Expected: " + validProfile.getBio() + "\nActual result was " + dbProfile.getBio());
    }

//     @Test
//     public void updateProfileEmptyFirstName() throws JsonMappingException, JsonProcessingException {
//         //Generate HTTP request
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.set("Authorization", "Bearer " + token);
//         HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileEmptyFirstName, headers);

//         //Send HTTP request
//         ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

//         //Verify response status code
//         HttpStatusCode statusCode = response.getStatusCode();
//         Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

//         //Verify response content
//         FieldErrorResponse responseProfile = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
//         Assertions.assertEquals(responseProfile, new FieldErrorResponse("firstName", "First name must not be empty."), "Expected first name field error.");

//         //Verify database content
//         PersonalProfile dbProfile = profileRepository.findByUser_UserId(initialUser.getUserId()).get();
//         Assertions.assertEquals(initialProfile.getBio(), dbProfile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual result was " + dbProfile.getBio());
//         Assertions.assertEquals(UserUtils.getFullName(initialUser), UserUtils.getFullName(dbProfile.getUser()), "Expected: " + UserUtils.getFullName(initialUser) + "\nActual result was " + UserUtils.getFullName(dbProfile.getUser()));
//     }
    
//     @Test
//     public void updateProfileEmptyLastName() throws JsonMappingException, JsonProcessingException {
//         //Generate HTTP request
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.set("Authorization", "Bearer " + token);
//         HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileEmptyLastName, headers);

//         //Send HTTP request
//         ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

//         //Verify response status code
//         HttpStatusCode statusCode = response.getStatusCode();
//         Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

//         //Verify response content
//         FieldErrorResponse responseProfile = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
//         Assertions.assertEquals(responseProfile, new FieldErrorResponse("lastName", "Last name must not be empty."), "Expected last name field error.");

//         //Verify database content
//         PersonalProfile dbProfile = profileRepository.findByUser_UserId(initialUser.getUserId()).get();
//         Assertions.assertEquals(initialProfile.getBio(), dbProfile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual result was " + dbProfile.getBio());
//         Assertions.assertEquals(UserUtils.getFullName(initialUser), UserUtils.getFullName(dbProfile.getUser()), "Expected: " + UserUtils.getFullName(initialUser) + "\nActual result was " + UserUtils.getFullName(dbProfile.getUser()));
//     }

//     @Test
//     public void updateProfileFirstNameTooLong() throws JsonMappingException, JsonProcessingException {
//         //Generate HTTP request
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.set("Authorization", "Bearer " + token);
//         HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileFirstNameTooLong, headers);

//         //Send HTTP request
//         ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

//         //Verify response status code
//         HttpStatusCode statusCode = response.getStatusCode();
//         Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

//         //Verify response content
//         FieldErrorResponse responseProfile = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
//         Assertions.assertEquals(responseProfile, new FieldErrorResponse("firstName", "First name is too long."), "Expected first name field error.");

//         //Verify database content
//         PersonalProfile dbProfile = profileRepository.findByUser_UserId(initialUser.getUserId()).get();
//         Assertions.assertEquals(initialProfile.getBio(), dbProfile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual result was " + dbProfile.getBio());
//         Assertions.assertEquals(UserUtils.getFullName(initialUser), UserUtils.getFullName(dbProfile.getUser()), "Expected: " + UserUtils.getFullName(initialUser) + "\nActual result was " + UserUtils.getFullName(dbProfile.getUser()));
//     }

//     @Test
//     public void updateProfileLastNameTooLong() throws JsonMappingException, JsonProcessingException {
//         //Generate HTTP request
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.set("Authorization", "Bearer " + token);
//         HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileLastNameTooLong, headers);

//         //Send HTTP request
//         ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.PUT, requestEntity, String.class);

//         //Verify response status code
//         HttpStatusCode statusCode = response.getStatusCode();
//         Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

//         //Verify response content
//         FieldErrorResponse responseProfile = mapper.readValue(response.getBody().toString(), FieldErrorResponse.class);
//         Assertions.assertEquals(responseProfile, new FieldErrorResponse("lastName", "Last name is too long."), "Expected last name field error.");

//         //Verify database content
//         PersonalProfile dbProfile = profileRepository.findByUser_UserId(initialUser.getUserId()).get();
//         Assertions.assertEquals(initialProfile.getBio(), dbProfile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual result was " + dbProfile.getBio());
//         Assertions.assertEquals(UserUtils.getFullName(initialUser), UserUtils.getFullName(dbProfile.getUser()), "Expected: " + UserUtils.getFullName(initialUser) + "\nActual result was " + UserUtils.getFullName(dbProfile.getUser()));
//     }

    @Test
    public void updateProfileBioTooLong() throws JsonMappingException, JsonProcessingException {
        //Generate HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(profileBioTooLong, headers);

        //Send HTTP request
        ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/" + initialUser.getUserId(), HttpMethod.PUT, requestEntity, String.class);

        //Verify response status code
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode, "Expected status code 400. Actual result was " + statusCode.value());

        //Verify database content
        PersonalProfile dbProfile = (PersonalProfile)personalProfileRepository.findById(initialProfile.getId()).get();
        Assertions.assertEquals(initialProfile.getBio(), dbProfile.getBio(), "Expected: " + initialProfile.getBio() + "\nActual result was " + dbProfile.getBio());
    }
}
