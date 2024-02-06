package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/note")
public class NoteController {
    public NoteService noteService;
    public UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
    @PostMapping
    public String createNote(@ModelAttribute("newNote") Note newNote, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        newNote.setUserId(userId);
        noteService.createNote(newNote);
        model.addAttribute("navNumber", 1);
        redirectAttributes.addFlashAttribute("navNumber", 1);
        redirectAttributes.addFlashAttribute("message", "Your note is successfully created!");
        return "redirect:/home";
    }
}
