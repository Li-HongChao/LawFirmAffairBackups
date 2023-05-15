package com.example.until;


import com.example.entity.Chat;
import com.example.service.impl.ChatServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * websocket服务
 */
@ServerEndpoint("/websocket/{username}")
@Component
@Slf4j
public class WebSocketServer {


    //记录当前在线连接数
    public static final Map<String, Session> sessionMap = new HashMap<>();

    ChatServiceImpl chatService = GetBeanUtils.getBean(ChatServiceImpl.class);
    private String[] split;

    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessionMap.put(username, session);
        log.info("当前连接用户:{},用户名:{}", session.toString(), username);
    }

    //onMessage 是一个消息的中转站 message 客户端发送过来的消息
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) throws IOException {
        log.info("服务端收到:{}的消息:{}", username, message);
        String[] msg = message.split("\\$");
        Chat chat = new Chat();
        chat.setFromName(msg[0]);
        chat.setToName(msg[1]);
        chat.setMsg(msg[2]);
        chat.setRole(msg[3]);
        chat.setTime(msg[4]);

        if (Integer.parseInt(chat.getRole()) > 1 && sessionMap.get(chat.getToName()) != null) {
            //如果对象存在且角色为图片
            split = chat.getMsg().split("\\*\\*\\*");
            //编辑发送内容
            message = chat.getFromName() + "$" + chat.getToName() + "$" + "http://localhost:8082/image/" + split[1] + "$" + chat.getRole() + "$" + chat.getTime();
            //用于保存
            chat.setMsg(split[0] + split[1]);
            chatService.save(chat);
            sessionMap.get(chat.getToName()).getBasicRemote().sendText(message);
        } else if(sessionMap.get(chat.getToName()) == null){
            //如果对象不存在
            session.getBasicRemote().sendText("*");
        }else {
            //如果对象存在但角色为非图片
            chatService.save(chat);
            sessionMap.get(chat.getToName()).getBasicRemote().sendText(message);
        }
    }

    //连接关闭调用的方法
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

}
