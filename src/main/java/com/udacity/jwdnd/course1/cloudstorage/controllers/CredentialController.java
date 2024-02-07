package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("credential")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }
    @PostMapping
    public String saveCredential(@ModelAttribute("newCredential") Credential newCredential, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        if (newCredential.getCredentialId() == null) {
            newCredential.setUserId(userId);
            credentialService.saveCredential(newCredential, true);
            redirectAttributes.addFlashAttribute("message", "Your note is successfully created!");
        } else if (credentialService.getCredentialById(userId, newCredential.getCredentialId()) != null) {
            newCredential.setUserId(userId);
            credentialService.saveCredential(newCredential, false);
            redirectAttributes.addFlashAttribute("message", "Your note is successfully edited!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Unauthorized!");
        }
        redirectAttributes.addFlashAttribute("navNumber", 2);
        return "redirect:/home";
    }
    @GetMapping("/{credentialId}")
    public String getNote(@PathVariable("credentialId") Integer credentialId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        Credential credentialForm = credentialService.getCredentialById(userId, credentialId);
        if (credentialForm == null) {
            redirectAttributes.addFlashAttribute("message", "We can not find your credential. Please try again.");
        } else {
            redirectAttributes.addFlashAttribute("credentialForm", credentialForm);
        }
        redirectAttributes.addFlashAttribute("navNumber", 2);
        return "redirect:/home";
    }
    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable("credentialId") Integer credentialId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication);
        credentialService.deleteCredentialById(userId, credentialId);
        redirectAttributes.addFlashAttribute("navNumber", 2);
        redirectAttributes.addFlashAttribute("message", "Delete note successfully!");
        return "redirect:/home";
    }
}
