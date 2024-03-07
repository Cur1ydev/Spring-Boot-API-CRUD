package com.example.demo.api;

import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.response.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserApi {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserApi(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/store")
    ResponseEntity<ResponseAPI> save(@Validated @RequestBody User user, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                List<User> findUser = userRepository.findByName(user.getName().trim());
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
                return !findUser.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseAPI(true, "Đã trùng bản ghi", "")) : ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Them thanh công", userRepository.save(user)));
            }
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(false, "loi validate", errors));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(false, "Some thing went wrong", e.getMessage()));
        }
    }

    @GetMapping("/search")
    ResponseEntity<ResponseAPI> search(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(
                new ResponseAPI(true, "success", userRepository.findByNameLike("%"+name+"%") )
        );
    }
}
