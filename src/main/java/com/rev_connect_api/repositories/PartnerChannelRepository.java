package com.rev_connect_api.repositories;

import com.rev_connect_api.models.PartnerChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartnerChannelRepository extends JpaRepository<PartnerChannel, Long> {
  List<PartnerChannel> findByBusinessUserId(Long businessUserId);
}
