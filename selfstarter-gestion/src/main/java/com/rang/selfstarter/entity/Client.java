package com.rang.selfstarter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name="clients")
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id", nullable = false)
    private Long clientId;

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Basic
    @Column(name = "is_premium", nullable = false, length = 64)
    private String isPremium;

    @OneToMany(mappedBy = "entrepreneur", fetch = FetchType.LAZY)
    private Set<Devis> devis;

    @OneToMany(mappedBy = "entrepreneur", fetch = FetchType.LAZY)
    private Set<Facture> factures;


    @OneToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public Client(String firstName, String lastName, String isPremium) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPremium = isPremium;
    }

    public Client() {

    }
}
