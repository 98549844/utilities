package com.util.redisUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    static private final Log log = LogFactory.getLog(RedisUtil.class);

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
        RedisUtil connRedis = new RedisUtil();
        connRedis.test1();
        connRedis.test2();
    }

    public void test1() {
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();
        // 2. 保存数据
        jedis.set("name", "garlam");
        // 3. 获取数据
        String value = jedis.get("name");
        System.out.println(value);
        // 4.释放资源
        jedis.close();
    }

    public void test2() {
        RedisUtil redisUtil = new RedisUtil();
        JedisPool jedisPool = redisUtil.getJedisPool();

        // 获得核心对象：jedis
        Jedis jedis = null;
        try {
            // 通过连接池来获得连接
            jedis = jedisPool.getResource();
            // 设置数据
            jedis.set("name", "张三");
            // 获取数据
            String value = jedis.get("name");
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (jedis != null) {
                jedis.close();
            }
            // 释放连接池
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }
}
