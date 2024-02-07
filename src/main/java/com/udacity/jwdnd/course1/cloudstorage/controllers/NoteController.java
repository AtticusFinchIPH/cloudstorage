package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String saveNote(@ModelAttribute("newNote") Note newNote, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        if(newNote.getNoteId() == null) {
            newNote.setUserId(userId);
            noteService.createNote(newNote);
            redirectAttributes.addFlashAttribute("message", "Your note is successfully created!");
        } else if(noteService.getNoteById(userId, newNote.getNoteId()) == null){
            redirectAttributes.addFlashAttribute("message", "Unauthorized!");
        } else {
            newNote.setUserId(userId);
            noteService.updateNote(newNote);
            redirectAttributes.addFlashAttribute("message", "Your note is successfully edited!");
        }
        redirectAttributes.addFlashAttribute("navNumber", 1);
        return "redirect:/home";
    }
    @GetMapping("/{noteId}")
    public String getNote(@PathVariable("noteId") Integer noteId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        Note noteForm = noteService.getNoteById(userId, noteId);
        if (noteForm == null) {
            redirectAttributes.addFlashAttribute("message", "We can not find your note. Please try again.");
        } else {
            redirectAttributes.addFlashAttribute("noteForm", noteForm);
        }
        redirectAttributes.addFlashAttribute("navNumber", 1);
        return "redirect:/home";
    }
    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        noteService.deleteNoteById(userId, noteId);
        redirectAttributes.addFlashAttribute("navNumber", 1);
        redirectAttributes.addFlashAttribute("message", "Delete note successfully!");
        return "redirect:/home";
    }
}
