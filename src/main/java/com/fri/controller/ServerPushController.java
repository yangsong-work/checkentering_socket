package com.fri.controller;

import com.alibaba.fastjson.JSONObject;
import com.fri.service.ServerPushService;
import com.fri.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class ServerPushController {
    public static final Logger log = LoggerFactory.getLogger(ServerPushController.class);
    @Autowired
    ServerPushService serverPushService;

    /**
     * 推送ocr证件查询
     */

    @PostMapping("/ocr")
    public String verifyOcr(@RequestBody String data) {
        log.info("接受数据时间：{}", new Date());
        Map map = JSONObject.parseObject(data,Map.class);
        String padId = (String)map.get("padId");
        String json = JSONObject.toJSONString(map.get("json"));
        boolean res = serverPushService.verifyOcr(padId, json);
        if (res) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail();
        }
    }

    /**
     * 推送身份证信息
     *
     * @param
     * @return
     */
    @PostMapping("/idcard")
    public String verifyIDCard(@RequestBody String data) {
        Map map = JSONObject.parseObject(data,Map.class);
        String padId = (String)map.get("padId");
        String json = JSONObject.toJSONString(map.get("json"));
        log.info("接受数据时间：{}", new Date());
        boolean res = serverPushService.verifyIDCard(padId, json);
        if (res) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail();
        }
    }


    /**
     * 推送人像列表
     *
     * @param
     * @return
     */
    @PostMapping("/face")
    public String verifyImage(@RequestBody String data) {
        log.info("接受数据时间：{}", new Date());
        Map map = JSONObject.parseObject(data,Map.class);
        String padId = (String)map.get("padId");
        String json = JSONObject.toJSONString(map.get("json"));
        boolean res = serverPushService.verifyImage(padId, json);
        if (res) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail();
        }
    }

}
