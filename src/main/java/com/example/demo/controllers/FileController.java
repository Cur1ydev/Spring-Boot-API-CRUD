package com.example.demo.controllers;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/api/v1/file")
public class FileController {
    @Autowired
    public ServletContext app;

        @GetMapping("")
    public String homeFile(){
        return "file";
    }
    @PostMapping("/post")
    public String save(Model model,@RequestParam(value = "file",required = true) MultipartFile file) {
        String uploadRootPath = "src/main/resources/static/images";
        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        try {
            String fileName = file.getOriginalFilename();
            File serverFile = new File(uploadRootDir.getAbsoluteFile() + File.separator + fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();
            model.addAttribute("size", file.getSize());
            model.addAttribute("path",uploadRootPath);
            model.addAttribute("type",file.getContentType());
            model.addAttribute("name",fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "result";
    }

}
