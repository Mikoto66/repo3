package com.demo;

import redis.clients.jedis.Jedis;

public class TestRedis{

    public void show(){

//此处编写代码，通过客户端访问redis，在控制台显示学校和姓名的值。
        Jedis jedis = new Jedis("123.56.67.50",6379);
        jedis.auth("redis354");
        if(jedis.ping().equals("PONG")){
            System.out.println("连接Redis服务器成功");
        }else{
            System.out.println("连接Redis服务器失败");
            System.exit(0);
        }
        System.out.println("学校："+jedis.get("university"));
        System.out.println("姓名："+jedis.get("stu_name"));
    }

    public static void main(String[] args) {
        new TestRedis().show();
    }

}
