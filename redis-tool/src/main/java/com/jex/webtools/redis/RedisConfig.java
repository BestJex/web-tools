package com.jex.webtools.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * redis配置文件
 **/
@Slf4j
@Component
public class RedisConfig {

    /**
     * 初始化监听器
     *
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerPvAdapter,
                                            MessageListenerAdapter listenerUvAdapter,
                                            MessageListenerAdapter listenerNuAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 监听pv
        container.addMessageListener(listenerPvAdapter, new PatternTopic(Enum.PV.getTopic()));
        // 监听uv
        container.addMessageListener(listenerUvAdapter, new PatternTopic(Enum.UV.getTopic()));
        // 监听nu
        container.addMessageListener(listenerNuAdapter, new PatternTopic(Enum.NU.getTopic()));
        return container;
    }

    /**
     * 监听保存路由消息后处理 pv
     *
     * @return
     */
    @Bean
    MessageListenerAdapter listenerPvAdapter(RedisReceiver redisReceiverPv) {
        return new MessageListenerAdapter(redisReceiverPv, "pv");
    }

    /**
     * 监听更新路由消息后处理 uv
     *
     * @return
     */
    @Bean
    MessageListenerAdapter listenerUvAdapter(RedisReceiver redisReceiverUv) {
        return new MessageListenerAdapter(redisReceiverUv, "uv");
    }

    /**
     * 监听删除路由消息后处理 nu
     *
     * @return
     */
    @Bean
    MessageListenerAdapter listenerNuAdapter(RedisReceiver redisReceiverNu) {
        return new MessageListenerAdapter(redisReceiverNu, "nu");
    }

}
