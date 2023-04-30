package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Long fileId;
    private String fileName;
    private Long fileSize;
    private String contentType;
    private byte[] fileData;
    private Integer userId;

}
