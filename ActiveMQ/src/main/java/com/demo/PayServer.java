package com.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PayServer {

    public static final String ACTIVEMQ_URL="tcp://123.56.67.50:61616";
    public static final String PAY_QUEUE_NAME="queue03";
    public static final String RETURN_QUEUE_NAME="queue04";
    public static double balance=100.00;

    public static void main(String[] args) throws JMSException {

        while (true){
            System.out.println("server:waiting...");
            double money=read();
            balance-=money;
            send(balance);
        }
    }

    public static void send(double money) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection=activeMQConnectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue=session.createQueue(RETURN_QUEUE_NAME);

        MessageProducer messageProducer=session.createProducer(queue);

        TextMessage textMessage=session.createTextMessage(String.valueOf(money));
        messageProducer.send(textMessage);

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("server:消息发布到return queue成功！！！");
    }

    public static double read() throws JMSException {

        Double result;

        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection=activeMQConnectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue=session.createQueue(PAY_QUEUE_NAME);

        MessageConsumer messageConsumer=session.createConsumer(queue);

        TextMessage textMessage= (TextMessage) messageConsumer.receive();
        result=Double.parseDouble(textMessage.getText());

        messageConsumer.close();
        session.close();
        connection.close();

        return result;
    }
}
