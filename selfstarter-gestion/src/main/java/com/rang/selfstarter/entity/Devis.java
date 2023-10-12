package com.rang.selfstarter.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "devis")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devis_id", nullable = false)
    private Long devisId;


    @Basic
    @Column(name = "devis_name",nullable = false, length = 64)
    private String devisName;

    @Basic
    @Column(name = "devis_description",nullable = false, length = 64)
    private String devisDescription;


    @Basic
    @Column(name = "total", nullable = false, length = 64)
    private Long total;

    @ManyToOne
    @JoinColumn(name = "entrepreneur_id", referencedColumnName = "entrepreneur_id", nullable = false)
    private Entrepreneur entrepreneur;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private Client client;

    public Devis(String devisName, String devisDescription, Long total) {
        this.devisName = devisName;
        this.devisDescription = devisDescription;
        this.total = total;
    }

    public Devis() {

    }
}
