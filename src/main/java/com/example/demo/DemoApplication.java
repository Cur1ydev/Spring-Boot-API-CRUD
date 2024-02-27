package com.example.demo;

import com.example.demo.api.CrudApi;
import com.example.demo.controllers.CrudController;
import com.example.demo.controllers.HomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Import({
        CrudApi.class,
        CrudController.class,
        HomeController.class
})
@SpringBootApplication
@EnableWebMvc
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
