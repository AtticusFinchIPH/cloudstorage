package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dataForms.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/home")
public class HomeController {
    private FileUploadService fileUploadService;
    private NoteService noteService;
    private UserService userService;
    public HomeController(FileUploadService fileUploadService, NoteService noteService, UserService userService) {
        this.fileUploadService = fileUploadService;
        this.noteService = noteService;
        this.userService = userService;
    }
    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        Integer userId = userService.getUserId(authentication);
        String[] files = fileUploadService.getFileNameList(userId);
        model.addAttribute("files", files);
        model.addAttribute("newNote", new Note());
        Note[] notes = noteService.getNoteList(userId);
        model.addAttribute("notes", notes);
        return "home";
    }
}
