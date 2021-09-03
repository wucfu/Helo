package com.wucfu.helo.redis;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * @author wucfu
 * @description
 * @date 2021-03-23
 */
public class AsyncMsgProcessor {
    private static final ConcurrentHashMap<String, MsgFutureTask<String>> TASK_MAP = new ConcurrentHashMap<>();

    public static MsgFutureTask<String> put(String key, MsgCallable msgCallable) {
        MsgFutureTask<String> futureTask = new MsgFutureTask<>(msgCallable);
        futureTask.setMsgCallable(msgCallable);
        TASK_MAP.put(key, futureTask);
        return futureTask;
    }

    public static void exec(String key, String msg) {
        // 存在任务，则执行
        if (TASK_MAP.containsKey(key)) {
            try {
                System.out.println("匹配到任务，现在唤醒执行任务...");
                MsgFutureTask<String> futureTask = TASK_MAP.remove(key);
                futureTask.getMsgCallable().setName(key);
                futureTask.getMsgCallable().setMsg(msg);
                new Thread(futureTask).start();
                System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("没有找到关联任务");
        }
    }

    static class MsgCallable implements Callable<String> {

        private String name;

        private String msg;

        public MsgCallable(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String call() throws Exception {
            System.out.println(name + "任务处理消息中...");
            for (int i = 1; i <= 5; i++) {
                System.out.println("处理进度:" + (i / 5d * 100) + "%");
                Thread.sleep(600);
            }
            System.out.println(name + "任务处理完毕消息：" + msg);
            return msg;
        }
    }

    static class MsgFutureTask<T> extends FutureTask<T> {

        private MsgCallable msgCallable;

        public MsgFutureTask(Callable<T> callable) {
            super(callable);
        }

        public MsgCallable getMsgCallable() {
            return msgCallable;
        }

        public void setMsgCallable(MsgCallable msgCallable) {
            this.msgCallable = msgCallable;
        }
    }
}
