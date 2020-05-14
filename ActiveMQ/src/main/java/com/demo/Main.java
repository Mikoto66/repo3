package com.demo;

import javax.jms.JMSException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws JMSException {
        new ActiveMQDemo().send("呵呵");
        new ActiveMQDemo().read();
//        Scanner sc=new Scanner(System.in);
//        while (true){
//            System.out.println("请输入支付金额：");
//            new Payment().pay(Double.parseDouble(sc.nextLine()));
//        }
    }
}
