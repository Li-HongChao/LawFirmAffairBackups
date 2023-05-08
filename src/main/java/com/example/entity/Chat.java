package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("chat_records")
public class Chat {
    private Long id;
    private String fromName;
    private String toName;
    private String msg;
    private String role;
    private String time;
}
