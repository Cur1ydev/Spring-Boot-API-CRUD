package com.example.demo.controllers;

import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class CrudController {
    private ProductRepository productRepository;

    public CrudController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("name", "John Doe");
        return "á shi ba";
    }

}
