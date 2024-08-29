package com.rev_connect_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.Connection;
import com.rev_connect_api.repositories.ConnectionRepository;

@Service
public class ConnectionService {
    
    private ConnectionRepository connectionRepository;
    
    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public List<Connection> getConnectionsForUser(Long userId) {
        return connectionRepository.findByUserA_UserId(userId);
    }

    public List<Long> getConnectedUserIds(Long userId) {
        List<Connection> connections = getConnectionsForUser(userId);
        if(connections == null) return new ArrayList<Long>();

        return connections.stream().map(connection -> { 
                                            return connection.getUserB().getUserId(); 
                                        }).collect(Collectors.toList());
    }
}
