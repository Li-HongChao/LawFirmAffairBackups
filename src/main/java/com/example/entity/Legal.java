package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("legal_advice")
public class Legal {
    private Long id;
    private String author;
    private String title;
    @TableField(value = "publish_time")
    @JsonFormat(pattern="yyyy 年 MM 月 dd 日")
    private Timestamp time;
    private String content;
    @TableField(value = "image_url")
    private String imageUrl;
}
