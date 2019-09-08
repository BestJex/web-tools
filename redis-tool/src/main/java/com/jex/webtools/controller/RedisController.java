package com.jex.webtools.controller;

import com.jex.webtools.tools.RedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@RestController
public class RedisController {

    private final String key = "redis.key";
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("setStr")
    public String setStr(@RequestParam String value) {
        RedisTool.setStr(redisTemplate, key, value);
        return "0";
    }

    @GetMapping("getStr")
    public String getStr() {
        return RedisTool.getStr(redisTemplate, key);
    }

    @GetMapping("setList")
    public String setList() {
        //消息队列
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        RedisTool.setLeftList(redisTemplate, key, list);
        //消息队列
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String result = getList();
                if (result != null) {
                    System.out.println(result);
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
        return "0";
    }

    @GetMapping("getAndSet/{value}")
    public void getAndSet(@PathVariable("value") String value) {
        System.out.println(RedisTool.getAndSet(redisTemplate, key, value));
    }

    @GetMapping("setNX/{value}")
    public void setNX(@PathVariable("value") String value) {
        System.out.println(RedisTool.setNX(redisTemplate, key, value));
    }

    public String getList() {
        return RedisTool.popRightList(redisTemplate, key);
    }


}
