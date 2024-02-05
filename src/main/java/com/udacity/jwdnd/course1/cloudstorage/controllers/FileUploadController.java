package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file-upload")
public class FileUploadController {
    private FileUploadService fileUploadService;
    private UserService userService;
    public FileUploadController(FileUploadService fileUploadService, UserService userService) {
        this.fileUploadService = fileUploadService;
        this.userService = userService;
    }
    @PostMapping
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model){
        if (fileUpload.isEmpty()) {
            model.addAttribute("error", "Can not upload an empty file, please try again.");
            return "result";
        }
        String userName = authentication.getName();
        Integer userId = userService.getUserId(userName);
        String fileName = fileUpload.getOriginalFilename();
        if(fileUploadService.isFileNameExisted(userId, fileName)) {
            model.addAttribute("error", "File name is already existed, please try again.");
            return "result";
        }
        fileUploadService.saveFile(fileUpload, userId);
        model.addAttribute("isSuccess", true);
        return "result";
    }
}
