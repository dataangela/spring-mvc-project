package com.udacity.jwdnd.course1.cloudstorage.mappers;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getAllUserIdFiles(Integer userId);

    //prevent reupload same filename file
    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    File getSingleFileFileName(String fileName);
    @Insert("INSERT INTO FILES (fileName, fileSize, contentType, fileData, userId) VALUES (#{fileName}, #{fileSize}, #{contentType}, #{fileData}, #{userId})")
    int insertFile(String fileName, Long fileSize,String contentType, byte[] fileData, Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Long fileId);
}

