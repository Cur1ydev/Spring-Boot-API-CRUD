package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("")
@Validated
public class HomeController implements WebMvcConfigurer {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public String home(ModelMap model, Product product) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("message", "Hello World");
        model.addAttribute("products", products);
        model.addAttribute("product", product);
        model.addAttribute("title", "Trang chu");
        return "index";
    }

    @PostMapping("/")
    public  String checkPersonInfo(@Validated @ModelAttribute Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/";
        }
//        redirectAttributes("product")
        return "redirect:/api/v1/product/store";

    }

    @GetMapping("/abc")
    String abc() {
        return "testform";
    }


}
