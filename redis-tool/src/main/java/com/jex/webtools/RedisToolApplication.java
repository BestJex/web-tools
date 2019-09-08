package com.jex.webtools;

import com.jex.webtools.tools.RedisLockTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class RedisToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisToolApplication.class, args);
    }

    @Autowired
    StringRedisTemplate redisTemplate;


    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        String key = "receive.userId:123456";
        boolean lock = RedisLockTool.lock(redisTemplate, key, 1000L);
        if (lock) {
            //获得锁操作
            System.out.println("获得锁成功！");
            //释放锁
            RedisLockTool.unLock(redisTemplate, key);
        } else {
            System.out.println("获得锁失败！");
        }
    }

}
