package com.example.demo.dao.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    private String name;
    private String email;
    private String password;

    @Column(name = "token", nullable = true) // Nueva columna token
    private String token;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active = true;
}
