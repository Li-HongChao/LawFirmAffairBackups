package com.example.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String phone;
    private String name;
    private String email;
    private String password;
}
