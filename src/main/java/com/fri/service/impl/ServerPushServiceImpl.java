package com.fri.service.impl;

import com.fri.service.ServerPushService;
import com.fri.utils.SocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerPushServiceImpl implements ServerPushService {
    @Autowired
    SocketUtil socketUtil;
    @Override
    public boolean verifyOcr(String padId, String json) {
        return socketUtil.sendMessage(padId, json);
    }

    @Override
    public boolean verifyIDCard(String padId, String json) {
        return socketUtil.sendMessage(padId, json);
    }
}
