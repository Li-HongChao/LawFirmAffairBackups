package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Lawyer {
    private Long id;
    @TableField(value = "area_of_expertise")
    private String area;
    @TableField(value = "phone_number")
    private String phone;
    private String name;
    private String password;
    @TableField(value = "law_firm")
    private String lawFirm;
    @TableField(value = "fee_rate")
    private Float feeRate;
    @TableField(value = "working_hours")
    private String working;
    private Integer rating;
    @TableField(value = "imgUrl")
    private String imgUrl;
    private Integer status;
}
