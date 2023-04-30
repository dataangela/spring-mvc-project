package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    private Long credentialId;
    private String url;
    private String username;
    private String password;
    private String key;
    private Integer userId;

}
