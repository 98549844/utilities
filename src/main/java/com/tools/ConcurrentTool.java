package com.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname: ConcurrentTool
 * @Date: 2023/8/14 下午 12:15
 * @Author: kalam_au
 * @Description:
 */


public class ConcurrentTool {
    private static final Logger log = LogManager.getLogger(ConcurrentTool.class.getName());

    private static int count = 0;

    public static void main(String[] args) {
        // 创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(100);
        // 模拟100个并发请求
        for (int i = 0; i < 100; i++) {
            executor.execute(new staticTask());
        }
        // 关闭线程池
        executor.shutdown();
    }

    static class staticTask implements Runnable {
        @Override
        public void run() {
            // 这里编写需要并发执行的代码逻辑
            // 例如发送HTTP请求、数据库查询等
            // ...
            // 模拟业务处理耗时
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 打印当前线程名和执行结果
            count = count + 1;
            System.out.println(count + " " + Thread.currentThread().getName() + " 执行完成");
        }
    }

    class task implements Runnable {
        @Override
        public void run() {
            // 这里编写需要并发执行的代码逻辑
            // 例如发送HTTP请求、数据库查询等
            // ...
            // 模拟业务处理耗时
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 打印当前线程名和执行结果
            count = count + 1;
            System.out.println(count + " " + Thread.currentThread().getName() + " 执行完成");
        }
    }


}

