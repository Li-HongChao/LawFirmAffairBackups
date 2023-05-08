package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Admin;
import com.example.entity.User;
import com.example.mapper.AdminMapper;
import com.example.mapper.UserMapper;
import com.example.service.IAdminService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>implements IAdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectList(null);
    }
}
