package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Table(name = "product")

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product() {

    }


    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", description=" + description + '}';
    }
}
