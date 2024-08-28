package com.rev_connect_api.controllers;

import com.rev_connect_api.models.PartnerChannel;
import com.rev_connect_api.services.PartnerChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/partner_channels")
public class PartnerChannelController {

  @Autowired
  private PartnerChannelService partnerChannelService;

  @PostMapping
  public ResponseEntity<PartnerChannel> createPartnerChannel(@RequestBody PartnerChannel partnerChannel) {
    PartnerChannel createdChannel = partnerChannelService.createPartnerChannel(
        partnerChannel.getBusinessUserId(),
        partnerChannel.getName(),
        partnerChannel.getUrl());

    // Building the URI of the newly created resource
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdChannel.getId())
        .toUri();

    // Returning the created status with the location URI and the body
    return ResponseEntity.created(location).body(createdChannel);
  }

  @GetMapping
  public ResponseEntity<List<PartnerChannel>> getPartnerChannelsByBusinessUserId(@RequestParam Long businessUserId) {
    List<PartnerChannel> channels = partnerChannelService.getPartnerChannelsByBusinessUserId(businessUserId);
    return ResponseEntity.ok(channels);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PartnerChannel> getPartnerChannelById(@PathVariable Long id) {
    PartnerChannel partnerChannel = partnerChannelService.getPartnerChannelById(id);
    return ResponseEntity.ok(partnerChannel);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<PartnerChannel> updatePartnerChannel(
      @PathVariable Long id,
      @RequestBody PartnerChannel partnerChannel) {
    PartnerChannel updatedChannel = partnerChannelService.updatePartnerChannel(
        id,
        partnerChannel.getBusinessUserId(),
        partnerChannel.getName(),
        partnerChannel.getUrl());
    return ResponseEntity.ok(updatedChannel);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePartnerChannel(@PathVariable Long id) {
    partnerChannelService.deletePartnerChannel(id);
    return ResponseEntity.noContent().build();
  }
}
