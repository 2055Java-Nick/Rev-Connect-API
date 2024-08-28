package com.rev_connect_api.services;

import com.rev_connect_api.exceptions.ResourceNotFoundException;
import com.rev_connect_api.models.PartnerChannel;
import com.rev_connect_api.repositories.PartnerChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PartnerChannelServiceTest {

    @Mock
    private PartnerChannelRepository partnerChannelRepository;

    @InjectMocks
    private PartnerChannelService partnerChannelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePartnerChannel() {
        PartnerChannel channel = new PartnerChannel(111L, "Test Channel", "https://test.com");
        when(partnerChannelRepository.save(any(PartnerChannel.class))).thenReturn(channel);

        PartnerChannel created = partnerChannelService.createPartnerChannel(111L, "Test Channel", "https://test.com");

        assertEquals("Test Channel", created.getName());
        assertEquals("https://test.com", created.getUrl());
        verify(partnerChannelRepository, times(1)).save(any(PartnerChannel.class));
    }

    @Test
    public void testUpdatePartnerChannel() {
        PartnerChannel existingChannel = new PartnerChannel(111L, "Old Channel", "https://old.com");
        when(partnerChannelRepository.findById(anyLong())).thenReturn(Optional.of(existingChannel));
        when(partnerChannelRepository.save(any(PartnerChannel.class))).thenReturn(existingChannel);

        PartnerChannel updated = partnerChannelService.updatePartnerChannel(1L, 111L, "New Channel", "https://new.com");

        assertEquals("New Channel", updated.getName());
        assertEquals("https://new.com", updated.getUrl());
        verify(partnerChannelRepository, times(1)).findById(1L);
        verify(partnerChannelRepository, times(1)).save(any(PartnerChannel.class));
    }

    @Test
    public void testDeletePartnerChannel() {
        doNothing().when(partnerChannelRepository).deleteById(anyLong());

        partnerChannelService.deletePartnerChannel(1L);

        verify(partnerChannelRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetPartnerChannelById_Success() {
        PartnerChannel channel = new PartnerChannel(111L, "Test Channel", "https://test.com");
        channel.setId(4L);

        when(partnerChannelRepository.findById(4L)).thenReturn(Optional.of(channel));

        PartnerChannel foundChannel = partnerChannelService.getPartnerChannelById(4L);

        assertEquals("Test Channel", foundChannel.getName());
        assertEquals("https://test.com", foundChannel.getUrl());
    }

    @Test
    public void testGetPartnerChannelById_NotFound() {
        when(partnerChannelRepository.findById(4L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            partnerChannelService.getPartnerChannelById(4L);
        });
    }
}
