package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/credentials")
@AllArgsConstructor
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    @GetMapping
    public String getAllUserIdCredentials(Model model, Authentication authentication){
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("allCredentials", credentialService.getAllUserIdCredentials(user.getUserId()));
        return "home";

    }

    @PostMapping
    public String createUpdateCredential(Credential credential, Model model, Authentication authentication){
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        if(credential.getUserId() ==null){
            credential.setUserId(user.getUserId());
        }
        if(credential.getCredentialId()==null){
            credentialService.createCredential(credential);
            model.addAttribute("result","success");

        }else{

            credentialService.updateCredential(credential);
            model.addAttribute("result","success");

        }
        return "result";
    }


    @GetMapping("/delete")
    public String deleteCredential(@RequestParam Long credentialId, Model model){
        credentialService.deleteCredential(credentialId);
        model.addAttribute("result","success");
        return "result";
    }
}
