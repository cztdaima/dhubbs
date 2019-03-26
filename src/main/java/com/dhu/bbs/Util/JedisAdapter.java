package com.dhu.bbs.Util;


import redis.clients.jedis.Jedis;

public class JedisAdapter {
    public static void print(int index, Object obj){
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    public static void main(String[] argv){
        Jedis jedis = new Jedis();
        jedis.flushAll();
    }
}
