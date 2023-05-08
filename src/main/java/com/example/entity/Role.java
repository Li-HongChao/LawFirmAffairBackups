package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role")
public class Role {
    private Long id;
    private String username;
    private String password;
    private String category;
    public Role(String username, String password, String category) {
    }

    public Role() {

    }
}
