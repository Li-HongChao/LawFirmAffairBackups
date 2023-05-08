package com.example.entity;

import lombok.Data;

@Data
public class Orders {
    private Long id;
    private String userName;
    private String lawyerName;
    private Integer status;
}
