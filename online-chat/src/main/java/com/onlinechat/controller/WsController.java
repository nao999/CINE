package com.onlinechat.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.onlinechat.entity.Message;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.vo.MessageVO;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.service.TGroupMsgService;
import com.onlinechat.service.TUserRecordsService;
import com.onlinechat.service.TUserService;
import com.onlinechat.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName : WsController
 * @Description :
 * @Author : y
 * @Date: 2021-10-25 10:04
 */

@ServerEndpoint("/websocket")
@Controller
public class WsController {

    private static int onlineCount=0;
    private static CopyOnWriteArrayList<WsController> webSocketSet=new CopyOnWriteArrayList<WsController>();
    private Session session;
    EmojiConverter emojiConverter = EmojiConverter.getInstance();


    private static TUserRecordsService userRecordsService;

    private static TGroupMsgService groupMsgService;

    @Autowired
    public void setTUserRecordsService(TUserRecordsService userRecordsService){
        WsController.userRecordsService = userRecordsService;
    }

    @Autowired
    public void setGroupMsgService(TGroupMsgService groupMsgService){
        WsController.groupMsgService = groupMsgService;
    }


    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);//加入set中
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("来自客户端的消息："+message);
        String username = "";
        String friendName = "";
        String chatGroupId = "";
        Integer messageType = 0;
        Map<String, String> pathParameters = session.getPathParameters();
        String rawQuery = session.getRequestURI().getRawQuery();
        String[] splitList = rawQuery.split("&");
        for(String str : splitList){
            String[] param = str.split("=");
            if(param[0].equals("username")){
                username = param[1];
            }else if(param[0].equals("friendName")){
                friendName = param[1];
            }else if(param[0].equals("chatGroup")){
                chatGroupId = param[1];
            }
        }

        String messageEncode = emojiConverter.toHtml(message);
        String[] splitResult = messageEncode.split("::");
        if(!splitResult[0].equals("image")){
            messageType = 0;
        }else {
            messageType = 1;
            messageEncode = splitResult[1];
            message = splitResult[1];
        }

        // 保存消息
        MessageVO messageVO = new MessageVO();
        if(!friendName.equals("")){
            messageVO = userRecordsService.saveMessage(messageEncode,username,friendName,messageType);
        }else{
            messageVO = groupMsgService.saveMessage(messageEncode,chatGroupId,username,messageType);
        }

        messageVO.setMessageContent(message);
//        群发消息
        System.out.println("发消息了");
        for (WsController item:webSocketSet){
            try {
                item.sendMessage(JSON.toJSONString(messageVO));
                System.out.println("message::"+message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }
    //   下面是自定义的一些方法
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        WsController.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WsController.onlineCount--;
    }

}
