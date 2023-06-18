package com.samsung.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String email;
    private boolean status;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String gender;
}
