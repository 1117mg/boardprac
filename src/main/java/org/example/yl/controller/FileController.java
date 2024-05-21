package org.example.yl.controller;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("imageFiles") MultipartFile[] files) {
        return "File uploaded successfully!";
    }
}