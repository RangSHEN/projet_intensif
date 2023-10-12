package com.rang.selfstarter.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="entrepreneurs")
public class Entrepreneur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="entrepreneur_id", nullable = false)
    private Long entrepreneurId;

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Basic
    @Column(name = "company_name", nullable = false, length = 64)
    private String companyName;

    @OneToMany(mappedBy = "entrepreneur", fetch = FetchType.LAZY)
    private Set<Devis> devis;

    @OneToMany(mappedBy = "entrepreneur", fetch = FetchType.LAZY)
    private Set<Facture> factures;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public Entrepreneur(String firstName, String lastName, String companyName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
    }

    public Entrepreneur() {

    }
}
