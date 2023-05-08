package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.entity.Chat;
import com.example.entity.Orders;
import com.example.service.impl.ChatServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@Slf4j
//预约管理类
public class ChatController {
    @Autowired
    private ChatServiceImpl chatService;

    @GetMapping
    public R<List<Chat>> getByUserOrder(String username){
        List<Chat> chats = chatService.getBaseMapper().selectList(null);
        //过滤出未解决的订单
        chats = chats.stream().filter(s-> Objects.equals(s.getToName(), username) || Objects.equals(s.getFromName(), username)).collect(Collectors.toList());
        log.info(username);
        log.info("此人聊天记录："+chats);
        return R.success(chats);
    }
}
