package com.rev_connect_api.repositories;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.recipient.accountId = :userId AND cr.status = 'PENDING'")
    List<ConnectionRequest> findPendingRequestsForUser(@Param("userId") Long userId);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.recipient.accountId = :userId AND cr.status = 'ACCEPTED'")
    List<ConnectionRequest> findAcceptedConnectionsForUser(@Param("userId") Long userId);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE (cr.requester.accountId = :userId OR cr.recipient.accountId = :userId) AND cr.status = 'ACCEPTED'")
    List<ConnectionRequest> findConnectionsByUserId(@Param("userId") Long userId);

    boolean existsByRequesterAccountIdAndRecipientAccountIdAndStatus(Long requesterId, Long recipientId, RequestStatus status);
}
