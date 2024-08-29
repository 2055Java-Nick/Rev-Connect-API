package com.rev_connect_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="connections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "connection_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_A", nullable = false)
    private User userA;

    @ManyToOne
    @JoinColumn(name = "user_B", nullable = false)
    private User userB;

    public Connection(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
    }
}
