package com.udacity.jwdnd.course1.cloudstorage.mappers;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getAllUserIdCredentials(Integer userId);

    @Select("SELECT password FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    String getPlainPassword(Long credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential findByCredentialId(Long credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username,password,key,userId) VALUES (#{url},#{username},#{password},#{key},#{userId})")
    Integer createCredential(String url, String username, String password, String key,Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username},password = #{password}, key=#{key},userId=#{userId} WHERE credentialId=#{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    void deleteCredential(Long credentialId);
}
