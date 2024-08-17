package com.rev_connect_api.services;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.entity.User;
import com.rev_connect_api.entity.RequestStatus;
import com.rev_connect_api.repositories.ConnectionRequestRepository;
import com.rev_connect_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionRequestService {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionRequestService.class);

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserRepository userRepository;

    public ConnectionRequestService(ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository) {
        this.connectionRequestRepository = connectionRequestRepository;
        this.userRepository = userRepository;
    }

    public ConnectionRequest sendRequest(Long requesterId, Long recipientId) {
        logger.info("Attempting to send connection request from user {} to user {}", requesterId, recipientId);

        Optional<User> requester = userRepository.findById(requesterId);
        Optional<User> recipient = userRepository.findById(recipientId);

        if (!requester.isPresent()) {
            logger.error("Requester with ID {} not found", requesterId);
            throw new IllegalArgumentException("Requester not found");
        }

        if (!recipient.isPresent()) {
            logger.error("Recipient with ID {} not found", recipientId);
            throw new IllegalArgumentException("Recipient not found");
        }

        ConnectionRequest request = new ConnectionRequest();
        request.setRequester(requester.get());
        request.setRecipient(recipient.get());
        request.setStatus(RequestStatus.PENDING);

        ConnectionRequest savedRequest = connectionRequestRepository.save(request);
        logger.info("Connection request from user {} to user {} has been successfully sent", requesterId, recipientId);

        return savedRequest;
    }

    public List<ConnectionRequest> getPendingRequestsForUser(Long userId) {
        logger.info("Fetching pending connection requests for user {}", userId);
        return connectionRequestRepository.findPendingRequestsForUser(userId);
    }

    public void acceptRequest(Long requestId) {
        logger.info("Accepting connection request with ID {}", requestId);

        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (request.getStatus() == RequestStatus.ACCEPTED) {
            logger.warn("Connection request with ID {} has already been accepted", requestId);
            throw new IllegalStateException("Request already accepted");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        connectionRequestRepository.save(request);

        logger.info("Connection request with ID {} has been accepted", requestId);
    }

    public void rejectRequest(Long requestId) {
        logger.info("Rejecting connection request with ID {}", requestId);

        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (request.getStatus() == RequestStatus.REJECTED) {
            logger.warn("Connection request with ID {} has already been rejected", requestId);
            throw new IllegalStateException("Request already rejected");
        }

        request.setStatus(RequestStatus.REJECTED);
        connectionRequestRepository.save(request);

        logger.info("Connection request with ID {} has been rejected", requestId);
    }

    public List<ConnectionRequest> getAllConnections() {
        logger.info("Fetching all connection requests");
        return connectionRequestRepository.findAll();
    }

    public List<ConnectionRequest> getAcceptedConnectionsForUser(Long userId) {
        logger.info("Fetching accepted connection requests for user {}", userId);
        return connectionRequestRepository.findAcceptedConnectionsForUser(userId);
    }

    public List<ConnectionRequest> findConnectionsByUserId(Long userId) {
        logger.info("Fetching all connections for user {}", userId);
        return connectionRequestRepository.findConnectionsByUserId(userId);
    }
}
