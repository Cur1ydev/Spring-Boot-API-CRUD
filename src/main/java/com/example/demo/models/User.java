package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    @NotEmpty(message = "Name must be entered")
    private String name;

    @Column(name = "email",unique = true)
    @NotEmpty(message = "Email must be entered")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password must be entered")
    private String password;
}
