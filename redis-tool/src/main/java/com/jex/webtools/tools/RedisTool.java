package com.jex.webtools.tools;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 **/

public class RedisTool {


    /**
     * 设置字符串
     *
     * @param redisTemplate
     * @param key
     * @param value
     */
    public static void setStr(RedisTemplate redisTemplate, String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串
     *
     * @param redisTemplate
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public static void setStr(RedisTemplate redisTemplate, String key, String value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * 获取字符串
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static String getStr(RedisTemplate redisTemplate, String key) {
        Object o = get(redisTemplate, key);
        return o == null ? null : o.toString();
    }
    /**
     * 获取数字
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static Long getLong(RedisTemplate redisTemplate, String key) {
        Object o = get(redisTemplate, key);
        return o == null ? 0 : Long.parseLong(o.toString());
    }

    /**
     * 获取值
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static Object get(RedisTemplate redisTemplate, String key) {
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.get();
    }

    /**
     * 删除key
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static boolean del(RedisTemplate redisTemplate, String key) {
        if (exists(redisTemplate, key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 判断key是否存在
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static boolean exists(RedisTemplate redisTemplate, final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 从左往右
     *
     * @param redisTemplate
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void setLeftList(RedisTemplate redisTemplate, String key, List<T> value) {
        BoundListOperations boundListOperations = redisTemplate.boundListOps(key);
        if (value != null) {
            boundListOperations.leftPushAll(value.toArray());
        }
    }

    /**
     * 获取左边第一个并删除
     *
     * @param redisTemplate
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T popLeftList(RedisTemplate redisTemplate, String key) {
        BoundListOperations<String, T> boundListOperations = redisTemplate.boundListOps(key);
        return boundListOperations.leftPop();
    }
    /**
     * 从右往左
     *
     * @param redisTemplate
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void setRightList(RedisTemplate redisTemplate, String key, List<T> value) {
        BoundListOperations boundListOperations = redisTemplate.boundListOps(key);
        if (value != null) {
            boundListOperations.rightPushAll(value.toArray());
        }
    }

    /**
     * 获取右边第一个并删除
     *
     * @param redisTemplate
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T popRightList(RedisTemplate redisTemplate, String key) {
        BoundListOperations<String, T> boundListOperations = redisTemplate.boundListOps(key);
        return boundListOperations.rightPop();
    }

    /**
     * 对key设置newValue，返回原来的旧值，如果key不存在则返回null
     *
     * @param redisTemplate
     * @param key
     * @param value
     * @return
     */
    public static String getAndSet(RedisTemplate redisTemplate, String key, String value) {
        BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.getAndSet(value);
    }

    /**
     * 占位，如果key不存在则返回true，反之返回false
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static Boolean setNX(RedisTemplate redisTemplate, String key, String value) {
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.setIfAbsent(value);
    }

}
