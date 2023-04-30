package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping(value={"/home",""})
public class HomeController {

    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;

    @GetMapping
    public String HomePage(Model model,Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("allFiles", fileService.getAllUserIdFiles(user.getUserId()));
        model.addAttribute("allNotes", noteService.getAllUserIdNotes(user.getUserId()));
        model.addAttribute("allCredentials", credentialService.getAllUserIdCredentials(user.getUserId()));
        return "home";
    }

}
