package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    private UserService userService;

    @GetMapping
    public String homePage(Model model,Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("allFiles", fileService.getAllUserIdFiles(user.getUserId()));
        return "home";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multiFile, Model model,Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Integer userId = user.getUserId();
        if (multiFile.getSize()==0) {
            model.addAttribute("result", "error");
            model.addAttribute("message", "upload error");
            return "result";
        }
        boolean isFileNameInUse = fileService.getAllUserIdFiles(userId).stream()
                .anyMatch(file -> file.getFileName().equals(multiFile.getOriginalFilename()));

        if (isFileNameInUse) {
            model.addAttribute("result", "error");
            model.addAttribute("message", "FileName is in use");
            return "result";
        }
        try {
            String fileName = multiFile.getOriginalFilename();
            String contentType = multiFile.getContentType();
            Long fileSize = multiFile.getSize();
            byte[] fileData = multiFile.getBytes();

            fileService.createFile(fileName, fileSize,contentType, fileData,userId);
            model.addAttribute("result", "success");
        } catch (IOException ex) {
            model.addAttribute("result", "error");
            model.addAttribute("message", "exception occurred");
        }
        model.addAttribute("allFiles",fileService.getAllUserIdFiles(user.getUserId()));
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName, Model model) throws Exception{

        byte[] fileResource = fileService.downloadFile(fileName);

        ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(fileName)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(fileResource);
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam Long fileId, Model model){
        fileService.deleteFile(fileId);
        model.addAttribute("result", "success");
        return "result";
    }
}