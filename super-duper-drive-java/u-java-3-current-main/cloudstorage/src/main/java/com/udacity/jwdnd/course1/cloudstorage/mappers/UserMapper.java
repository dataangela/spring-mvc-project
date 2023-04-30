package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, password, firstname, lastname, salt) VALUES(#{username}, #{password}, #{firstName}, #{lastName}, #{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}
