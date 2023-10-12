package com.rang.selfstarter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Basic
    @Column(name = "name", nullable = false, length = 45, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name);
    }
}
