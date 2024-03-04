package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Table(name = "product")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Tên phaỉ được nhập")
    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @NotEmpty(message = "Mô tả phải được nhập")
    @Column(name = "description")
    private String description;

}
