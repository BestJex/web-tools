package com.jex.webtools.controller;

import com.jex.webtools.tools.RedisLockTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.Set;


@RestController
@RequestMapping("ReceiveRedPacket")
public class ReceiveRedPacket {

    @Autowired
    StringRedisTemplate redisTemplate;

    static Double dou = 0d;


    @RequestMapping(value = "/receive/{userId}", method = RequestMethod.GET)
    public void receive(@PathVariable("userId") String userId) {
        String key = "receive.userId:" + userId;
        boolean lock = RedisLockTool.lock(redisTemplate, key, 10000L);
        if (lock) {
            //领取操作
            System.out.println("领取成功！");
            //释放锁
            RedisLockTool.unLock(redisTemplate, key);
        } else {
            System.out.println("重复领取！");
        }
    }

    /**
     * 实现分数排行榜功能
     */
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public void set() {
        String key = "redis:set:";
        String[] strings = new String[]{"a", "b", "c", "d", "e", "f", "g"};
        String str = strings[new Random().nextInt(strings.length)];

        //自增
        redisTemplate.opsForZSet().incrementScore(key, str, 1);
        Long size = redisTemplate.opsForZSet().size(key);
        //如果记录大于5条则删除最小一条
//        if (size != null) {
//            if (size > 5) {
//                //从小到大排序，获取最小一条，并删除
//                Set<String> mins = redisTemplate.opsForZSet().range(key, 0, 0);
//                if (mins != null) {
//                    for (String min : mins) {
//                        redisTemplate.opsForZSet().remove(key, min);
//                    }
//                }
//            }
//        }
        //获取所有记录，从大到小
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
        if (set != null) {
            for (ZSetOperations.TypedTuple<String> s : set) {
                System.out.println(s.getValue() + "-" + s.getScore());
            }
            System.out.println("----------------------------------------------");
        }

    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(@RequestBody Integer... id) {
        for (Integer i : id) {
            System.out.println(i);
        }
    }
}
