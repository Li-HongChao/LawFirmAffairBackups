package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Admin;
import com.example.entity.User;

import java.util.List;

public interface IAdminService extends IService<Admin> {
    List<Admin> selectAll();
}
