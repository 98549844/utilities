package com.ace.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTool {
    static private final Log log = LogFactory.getLog(RedisTool.class);

    public JedisPool getJedisPool() {
        // 获取连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(30);
        // 设置最大的空闲连接数
        // config.setMaxIdel(10);
        // 获得连接池: JedisPool jedisPool = new JedisPool(poolConfig,host,port);
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
        return jedisPool;
    }

    public Jedis getJedis() {
        // 1. 设置IP地址和端口
        Jedis jedis = new Jedis("localhost", 6379);
        return jedis;
    }

    public static void main(String[] args) {
        RedisTool connRedis = new RedisTool();
        connRedis.test1();
        connRedis.test2();
    }

    public void test1() {
        RedisTool redisUtil = new RedisTool();
        Jedis jedis = redisUtil.getJedis();
        // 2. 保存数据
        jedis.set("name", "garlam");
        // 3. 获取数据
        String value = jedis.get("name");
        System.out.println("1. " + value);
        // 4.释放资源
        jedis.close();
    }

    public void test2() {
        RedisTool redisUtil = new RedisTool();

        // 获得核心对象：jedis
        try (JedisPool jedisPool = redisUtil.getJedisPool(); Jedis jedis = jedisPool.getResource()) {
            // 通过连接池来获得连接
            // 设置数据
            jedis.set("name", "张三");
            // 获取数据
            String value = jedis.get("name");
            System.out.println("2. " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 释放资源
        // 释放连接池
    }
}
