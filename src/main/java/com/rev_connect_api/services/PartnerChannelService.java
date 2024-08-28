package com.rev_connect_api.services;

import com.rev_connect_api.exceptions.ResourceNotFoundException;
import com.rev_connect_api.models.PartnerChannel;
import com.rev_connect_api.repositories.PartnerChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PartnerChannelService {

  @Autowired
  private PartnerChannelRepository partnerChannelRepository;

  public PartnerChannel createPartnerChannel(Long businessUserId, String name, String url) {
    PartnerChannel partnerChannel = new PartnerChannel(businessUserId, name, url);
    return partnerChannelRepository.save(partnerChannel);
  }

  public List<PartnerChannel> getPartnerChannelsByBusinessUserId(Long businessUserId) {
    return partnerChannelRepository.findByBusinessUserId(businessUserId);
  }

  public PartnerChannel getPartnerChannelById(Long id) {
    return partnerChannelRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("PartnerChannel not found with id: " + id));
  }

  public PartnerChannel updatePartnerChannel(Long id, Long businessUserId, String name, String url) {
    PartnerChannel partnerChannel = partnerChannelRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Partner channel not found: " + id));
    partnerChannel.setBusinessUserId(businessUserId);
    partnerChannel.setName(name);
    partnerChannel.setUrl(url);
    return partnerChannelRepository.save(partnerChannel);
  }

  public void deletePartnerChannel(Long id) {
    partnerChannelRepository.deleteById(id);
  }
}
