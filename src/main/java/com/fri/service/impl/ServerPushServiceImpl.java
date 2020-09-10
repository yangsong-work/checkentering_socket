package com.fri.service.impl;

import com.alibaba.fastjson.JSON;
import com.fri.contants.CommonContants;
import com.fri.service.ServerPushService;
import com.fri.utils.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fri.contants.CommonContants.*;

@Service
public class ServerPushServiceImpl implements ServerPushService {
    public static final Logger logger = LoggerFactory.getLogger(ServerPushServiceImpl.class);
    @Autowired
    SocketUtil socketUtil;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public boolean verifyOcr(String padId, String json) {
        return socketUtil.sendMessage(padId, json);
    }

    @Override
    public boolean verifyIDCard(String padId, String json) {
        return socketUtil.sendMessage(padId, json);
    }

    @Override
    public boolean verifyImage(String padId, String json) {
        return socketUtil.sendMessage(padId, json);
    }

    /**
     * pad离线通知核录桩
     *
     * @param padId
     */
    @Override
    public void logout(String padId) {
        Map pushMap = new HashMap();
        pushMap.put("padId",padId);
        boolean flag = pushMessage("offLine", pushMap, CommonContants.OFFLINE_METHOD,padId);
            logger.info("pad离线通知结果:{}",flag);
    }

    /**
     * 推送至三类区
     * @param method
     * @param mapData
     * @param FFBS
     * @return
     */
    public boolean pushMessage(String method, Map mapData, String FFBS,String padId) {
        //TODO 测试代码
//        String url = socketUrl+method;

        String url = socketUrl;

        Map dataMap = new HashMap();
        dataMap.put("padId", padId);
        dataMap.put("json", JSON.toJSONString(mapData));
        //拼装总线参数

        Map sendMap = new HashMap();

        Date date = new Date();

        //TODO 针对二类区服务器时间不对
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE,-1);
//        date = calendar.getTime();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

        String time = sdf1.format(date);

        int i = (int) (Math.random() * 9000 + 1000);
        String FWQQ_BWBH = "SR" + SERVICE_IP + time + i;
        sendMap.put("FWQQ_BWBH", FWQQ_BWBH);
        sendMap.put("BWLYIPDZ", SERVICE_IP);
        sendMap.put("BWLYDKH", port);
        sendMap.put("FWQQZ_ZCXX", FWQQZ_ZCXX);
        sendMap.put("FWBS", FWBS);
        sendMap.put("FFBS", FFBS);
        sendMap.put("FWQQ_RQSJ", sdf2.format(date));
        //方法及内容字段包装
        Map jsonMap = new HashMap();
        jsonMap.put("method", method);
        jsonMap.put("params", dataMap);

        sendMap.put("FWQQ_NR", jsonMap);


        sendMap.put("XXCZRY_XM", "白志斌");
        sendMap.put("XXCZRY_GMSFHM", "140603199011271011");
        sendMap.put("XXCZRY_GAJGJGDM", "110102900000");
        sendMap.put("FWQQSB_BH", SERVICE_IP);
        sendMap.put("FWQQ_SJSJLX", "service_request");


        Map returnMap = new HashMap<>();
        try {
//            HttpHeaders requestHeaders = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            requestHeaders.setContentType(type);
//            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
//            HttpEntity<Map> requestEntity = new HttpEntity<Map>(dataMap, requestHeaders);
            logger.info("总线发送报文：{}", sendMap);
            String data = restTemplate.postForObject(url,sendMap, String.class);
            logger.info("总线返回报文：{}", data);
            returnMap = JSON.parseObject(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("三类区服务返回数据：{}", returnMap);
        String str = (String) returnMap.get("FWTGZTDM");
        if("200".equals(str)) {
            Map subMap = (Map) returnMap.get("FWTG_NR");
            if (subMap == null) {
                return false;
            } else {
                return subMap.get("code").equals("0");
            }
        }
        return false;
    }
}