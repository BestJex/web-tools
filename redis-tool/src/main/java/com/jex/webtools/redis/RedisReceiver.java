package com.jex.webtools.redis;

import com.jex.webtools.tools.RedisLockTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 订阅
 */
@Slf4j
@Component
public class RedisReceiver {

    @Autowired
    StringRedisTemplate redisTemplate;

    public void pv(String message) {
        String key = "receive.userId:" + message;
        boolean lock = RedisLockTool.lock(redisTemplate, key, 10000L);
        if (lock) {
            //领取操作
            System.out.println("消费成功！" + message);
            //释放锁
            RedisLockTool.unLock(redisTemplate, key);
        }
//        else {
//            System.out.println("重复消费！");
//        }
    }


    public void uv(String message) {

    }


    public void nu(String message) {

    }
}
