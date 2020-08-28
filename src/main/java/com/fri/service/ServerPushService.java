package com.fri.service;

public interface ServerPushService {
    public boolean verifyOcr(String padId, String json);

    public boolean verifyIDCard(String padId, String json);
}
