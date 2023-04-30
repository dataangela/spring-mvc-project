package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String salt;
}