package com.example.controller;

import com.example.until.R;
import com.example.entity.Chat;
import com.example.service.impl.ChatServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

    //聊天记录全部导出
    @GetMapping
    public R<List<Chat>> getByUserOrder(String username){
        List<Chat> chats = chatService.getBaseMapper().selectList(null);
        //过滤出未解决的订单
        chats = chats.stream().filter(s-> Objects.equals(s.getToName(), username) || Objects.equals(s.getFromName(), username)).collect(Collectors.toList());
        log.info(username);
        log.info("此人聊天记录："+chats);
        return R.success(chats);
    }

    //聊天记录部分导出
    @GetMapping("/user")
    public R<List<Chat>> getByLawyerOrder(String username,String lawyerName){
        List<Chat> chats = chatService.getBaseMapper().selectList(null);
        //过滤出未解决的订单
        chats = chats.stream().
                filter(s-> Objects.equals(s.getToName(), username) || Objects.equals(s.getFromName(), username)).
                collect(Collectors.toList());
        chats =chats.stream().
                filter(s-> Objects.equals(s.getToName(), lawyerName) || Objects.equals(s.getFromName(), lawyerName)).
                collect(Collectors.toList());

        log.info(chats.toString());
        return R.success(chats);
    }

    @Value("${myPath}")
    private String path;

    //图片上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        long time = new Date().getTime();
        String url =path +"chat\\"+ time + ".jpg";
        File dest = new File(url);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            log.info("当前文件已经存储:"+url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(path+"***"+"chat\\"+ time + ".jpg");
    }
}
