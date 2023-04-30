package com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;

import java.util.List;
@AllArgsConstructor
@Service
public class FileServiceImpl implements FileService{
    private final FileMapper fileMapper;

    public List<File> getAllUserIdFiles(Integer userId) {
        return fileMapper.getAllUserIdFiles(userId);
    }

    public File getSingleFileFileName(String fileName){
        return fileMapper.getSingleFileFileName(fileName);
    }
    public int createFile(String filename, Long fileSize, String contentType,byte[] file,Integer userId){
        return fileMapper.insertFile(filename, fileSize,contentType, file, userId);
    }

    public byte[] downloadFile(String fileName) {
        File file = fileMapper.getSingleFileFileName(fileName);
        return file.getFileData();
    }

    public void deleteFile(Long fileId) {
        fileMapper.deleteFile(fileId);
    }
}