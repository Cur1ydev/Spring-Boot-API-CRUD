package com.example.demo.api;


import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.response.ResponseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("api/v1/product")
public class CrudApi{

    private final ProductRepository productRepository;

    CrudApi(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Boolean authenticate() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName)
                .filter(name -> !"anonymousUser".equals(name))
                .isPresent();
    }

    @GetMapping({"", "/"})
    ResponseEntity<ResponseAPI> getList() {
        try {
            if (!authenticate()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseAPI(true, "Not User Login, Please Try Again!", ""));
            }
            List<Product> product = productRepository.findAll();
            return !product.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Truy vấn thành công", product)) : ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Không có bản ghi nào", product));
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(false, "đã có lỗi xảy ra", throwable.getMessage()));
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseAPI> findById(@PathVariable Integer id) {
        try {
            Optional<Product> statusProduct = productRepository.findById(id);
            return statusProduct.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Truy vấn thành công ", statusProduct)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(true, "Không tìm thấy dữ liệu có id la " + id, ""));
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(false, "đã có lỗi xảy ra", throwable.getMessage()));
        }
    }

    @PostMapping("/store")
    @Validated
    ResponseEntity<ResponseAPI> store(@Validated @RequestBody Product newProduct, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                List<Product> findProduct = productRepository.findByName(newProduct.getName().trim());
                return !findProduct.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseAPI(true, "Đã trùng bản ghi", "")) : ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Them thanh công", productRepository.save(newProduct)));
            }
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(false, "loi validate", errors));
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(false, "đã có lỗi xảy ra", throwable.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseAPI> update(@RequestBody Product product, @PathVariable Integer id) {
        try {
            List<Product> findProduct = productRepository.findByName(product.getName().trim());
            return productRepository.findById(id).map(productMap -> {
                productMap.setName(product.getName());
                productMap.setDescription(product.getDescription());
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Cập nhật thành công", productRepository.save(productMap)));

            }).orElseGet(() -> !findProduct.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseAPI(true, "Đã trùng bản ghi", "")) : ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Them thanh công", productRepository.save(product))));
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(false, "đã có lỗi xảy ra", throwable.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseAPI> delete(@PathVariable Integer id) {
        try {
            boolean status = productRepository.existsById(id);
            if (status) {
                productRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(true, "Xoá thành công", ""));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(true, "Không tìm thấy bản ghi nào", ""));
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(false, "đã có lỗi xảy ra", throwable.getMessage()));
        }
    }

    @GetMapping("/detail")
    String detail() {
        return "123";
//        return ResponseEntity.ok(new ResponseAPI(true, "query user success",""));
    }


}
