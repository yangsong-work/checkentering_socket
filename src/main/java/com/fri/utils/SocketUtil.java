package com.fri.utils;

import com.fri.socket.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
public class SocketUtil {
    @Autowired
    MyHandler myHandler;

    //推送内容
    public boolean sendMessage(String padId, String message) {
        System.out.println("准备推送数据："+ message);
        boolean hasSend = false;
        TextMessage textMessage = new TextMessage(message);
        hasSend = myHandler.sendMessageToUser(padId,textMessage);
        System.out.println(hasSend);
        return hasSend;

    }
}
