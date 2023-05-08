package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Lawyer;
import com.example.entity.User;
import com.example.mapper.LawyerMapper;
import com.example.mapper.UserMapper;
import com.example.service.ILawyerService;
import com.example.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class LawyerServiceImpl extends ServiceImpl<LawyerMapper, Lawyer>implements ILawyerService {
}
