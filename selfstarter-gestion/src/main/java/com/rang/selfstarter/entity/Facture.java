package com.rang.selfstarter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facture_id", nullable = false)
    private Long factureId;


    @Basic
    @Column(name = "facture_name",nullable = false, length = 64)
    private String factureName;

    @Basic
    @Column(name = "facture_description",nullable = false, length = 64)
    private String factureDescription;


    @Basic
    @Column(name = "total", nullable = false, length = 64)
    private Long total;

    @ManyToOne
    @JoinColumn(name = "entrepreneur_id", referencedColumnName = "entrepreneur_id", nullable = false)
    private Entrepreneur entrepreneur;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private Client client;

    public Facture(String factureName, String factureDescription, Long total) {
        this.factureName = factureName;
        this.factureDescription = factureDescription;
        this.total = total;
    }

    public Facture() {

    }
}
