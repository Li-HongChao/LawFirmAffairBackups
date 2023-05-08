package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Chat;
import com.example.entity.User;
import com.example.mapper.ChatMapper;
import com.example.mapper.UserMapper;
import com.example.service.IChatService;
import com.example.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat>implements IChatService {
}
