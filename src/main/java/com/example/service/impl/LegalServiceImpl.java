package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Legal;
import com.example.entity.User;
import com.example.mapper.LegalMapper;
import com.example.mapper.UserMapper;
import com.example.service.ILegalService;
import com.example.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class LegalServiceImpl extends ServiceImpl<LegalMapper, Legal>implements ILegalService {
}
