package com.wucfu.helo.redis;

import java.util.concurrent.*;

/**
 * @author wucfu
 * @description
 * @date 2021-03-23
 */
public class RedisMain {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 订阅
        RedisPubSub redisPubSub = new RedisPubSub();
        executorService.submit(() -> {
            redisPubSub.subscribe("verify");
        });
        // 发布
        RedisPubSub publish = new RedisPubSub();
        executorService.submit(() -> {
            for (int i = 1; i <= 200; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publish.publish("CHANNELB", "hello,channel a" + System.currentTimeMillis());
            }
        });

        String key = "ABC";
        // 模拟请求
        System.out.println("接受到请求：" + key);

        // 模拟异步MQ
        AsyncMsgProcessor.MsgCallable msgCallable = new AsyncMsgProcessor.MsgCallable(key);

        // 模拟异步转同步响应（由redis发布触发）
        AsyncMsgProcessor.MsgFutureTask<String> msgFutureTask = AsyncMsgProcessor.put(key, msgCallable);

        System.out.println("等待人行MQ异步消息中...");
        String msg = "";
        try {
            msg = msgFutureTask.get(6000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("等待超时请重新发起请求！");
            return;
        }

        System.out.println("验证返回结果：" + msg);
    }
}
