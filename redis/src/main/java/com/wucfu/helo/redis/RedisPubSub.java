package com.wucfu.helo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * @author wucfu
 * @description redis发布&订阅
 * @date 2021-03-23
 */
public class RedisPubSub {
    public static final JedisPool jedispool = new JedisPool("127.0.0.1", 6379);
    private Jedis jedis;

    public RedisPubSub() {
        this.jedis = jedispool.getResource();
    }

    /**
     * 订阅
     *
     * @param channel
     */
    public void subscribe(String channel) {
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                System.out.println("接收到消息：" + message);
                String[] msgArr = message.split("-");
                AsyncMsgProcessor.exec(msgArr[0], msgArr[1]);
            }
        };
        jedis.subscribe(jedisPubSub, channel);
    }

    /**
     * 发布消息
     *
     * @param channel
     * @param msg
     */
    public void publish(String channel, String msg) {
        Long reply = jedis.publish(channel, msg);
        System.out.println("发布消息：" + msg + ",通道为：" + channel + ",订阅数：" + reply);
    }

}
