package com.rang.selfstarter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Client client;

    @OneToOne(mappedBy = "user")
    private Entrepreneur entrepreneur;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void assignRoleToUser(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRoleFromUser(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public User() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password);
    }
}
