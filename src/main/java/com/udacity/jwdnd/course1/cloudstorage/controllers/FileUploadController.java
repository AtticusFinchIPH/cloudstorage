package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, RedirectAttributes redirectAttributes){
        if (fileUpload.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Can not upload an empty file, please try again.");
            return "redirect:/home";
        }
        Integer userId = userService.getUserId(authentication);
        String fileName = fileUpload.getOriginalFilename();
        if(fileUploadService.isFileNameExisted(userId, fileName)) {
            redirectAttributes.addFlashAttribute("message", "File name is already existed, please try again.");
            return "redirect:/home";
        }
        fileUploadService.saveFile(fileUpload, userId);
        redirectAttributes.addFlashAttribute("message", "Upload file successfully!");
        return "redirect:/home";
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName, Authentication authentication){
        Integer userId = userService.getUserId(authentication);
        File file = fileUploadService.getFile(fileName, userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDispositionFormData(fileName, fileName);
        byte[] body = file.getFileData();
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(body, headers, HttpStatus.ACCEPTED);
        return responseEntity;
    }
    @GetMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        fileUploadService.deleteFile(fileName, userId);
        redirectAttributes.addFlashAttribute("message", "Delete file successfully!");
        return "redirect:/home";
    }
}
