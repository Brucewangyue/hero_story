package org.tinygame.herostory.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class RedisUtil {
    static private JedisPool jedisPool;

    private RedisUtil() {
    }

    static public void init() {
        jedisPool = new JedisPool("192.168.177.101", 6379);
    }

    static public Jedis getClient() {
        if (null == jedisPool) throw new RuntimeException("jedis尚未初始化");

        Jedis jedis = jedisPool.getResource();
//        jedis.auth("");
        return jedis;
    }
}
