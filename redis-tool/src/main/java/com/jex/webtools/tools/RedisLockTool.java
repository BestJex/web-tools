package com.jex.webtools.tools;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis锁
 **/

public class RedisLockTool {


    private RedisLockTool() {

    }


    /**
     * 获得锁
     *
     * @param redisTemplate
     * @param key
     * @param expire
     * @return
     */
    public static boolean lock(StringRedisTemplate redisTemplate, String key, long expire) {
        long value = System.currentTimeMillis() + expire;
        //如果设置key成功则获得了锁
        if (redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(value))) {
            return true;
        }
        //如果失败说明存在该key则获取key的原时间
        long oldExpireTime = Long.parseLong(redisTemplate.opsForValue().get(key));
        //如果原时间小于当前时间则超时
        if (oldExpireTime < System.currentTimeMillis()) {
            //设置新的时间，并返回原时间
            long newExpireTime = System.currentTimeMillis() + expire;
            long currentExpireTime = Long.parseLong(redisTemplate.opsForValue().getAndSet(key, String.valueOf(newExpireTime)));
            //如果返回的时间与原时间一样，说明设置成功，则获得了锁
            if (currentExpireTime == oldExpireTime) {
                return true;
            }
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param redisTemplate
     * @param key
     */
    public static void unLock(StringRedisTemplate redisTemplate, String key) {
        long oldExpireTime = Long.parseLong(redisTemplate.opsForValue().get(key));
        //如果原时间大于当前时间，则手动删除key，反之不做操作
        if (oldExpireTime > System.currentTimeMillis()) {
            redisTemplate.delete(key);
        }
    }
}
