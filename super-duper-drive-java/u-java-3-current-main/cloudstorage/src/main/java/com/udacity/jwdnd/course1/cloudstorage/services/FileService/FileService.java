package com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;

import java.util.List;


public interface FileService {
    List<File> getAllUserIdFiles(Integer userId);

    File getSingleFileFileName(String fileName);

    int createFile(String filename, Long fileSize, String contentType,byte[] file,Integer userId);

    byte[] downloadFile(String filename);

     void deleteFile(Long fileId);

}
