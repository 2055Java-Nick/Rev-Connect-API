package com.rev_connect_api.services;

import com.rev_connect_api.enums.MediaType;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.repositories.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class MediaService {

    // File url to upload media, starts at root of project (rev-connect-api)
    private static final String attachmentsDirectory = "src/main/resources/public/attachments";

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    // The method to save the actual Media entity to database
    @Transactional
    public Media saveMedia(MultipartFile file, Long postId, LocalDateTime createdAt) {
        String path;
        try {
            path = saveFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MediaType mediaType = file.getContentType().startsWith("image") ? MediaType.IMAGE : MediaType.VIDEO;
        Media media = Media.builder()
                .postId(postId)
                .createdAt(createdAt)
                .mediaType(mediaType)
                .mediaUrl(path)
                .build();
        return mediaRepository.save(media);
    }

    public List<Media> getMediaByPostId(Long postId) {
        return mediaRepository.findAllByPostId(postId);
    }

    @Transactional
    public boolean deleteMediaByPostId(Long postId) {
    List<Media> mediaList = getMediaByPostId(postId);
    if (mediaList == null || mediaList.isEmpty()) {
        return false;
    }
    // Iterate through the list of media and delete each one
    for (Media media : mediaList) {
        try {
            File file = new File(attachmentsDirectory + "/" + media.getMediaUrl());
            if (!file.delete()) {
                throw new RuntimeException("Failed to delete file: " + media.getMediaUrl());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    mediaRepository.deleteMediaByPostId(postId);
    return true;
    }

    // Saves attachments locally, should probably switch out implementation in production
    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Path destination = Paths.get(attachmentsDirectory)
                .resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
