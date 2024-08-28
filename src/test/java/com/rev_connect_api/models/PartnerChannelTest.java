package com.rev_connect_api.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartnerChannelTest {

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidPartnerChannel() {
    PartnerChannel partnerChannel = new PartnerChannel(111L, "Valid Channel", "https://validurl.com");

    Set<ConstraintViolation<PartnerChannel>> violations = validator.validate(partnerChannel);
    assertTrue(violations.isEmpty(), "No violations expected for a valid partner channel");
  }

  @Test
  public void testInvalidUrl() {
    PartnerChannel partnerChannel = new PartnerChannel(111L, "Invalid Channel", "invalid-url");

    Set<ConstraintViolation<PartnerChannel>> violations = validator.validate(partnerChannel);
    assertEquals(1, violations.size(), "One violation expected for an invalid URL");

    ConstraintViolation<PartnerChannel> violation = violations.iterator().next();
    assertEquals("Invalid URL format for partner channel: invalid-url", violation.getMessage());
    assertEquals("url", violation.getPropertyPath().toString());
  }
}
