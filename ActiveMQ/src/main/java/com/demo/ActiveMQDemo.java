package com.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQDemo {

    public static final String ACTIVEMQ_URL="tcp://123.56.67.50:61616";
    public static final String QUEUE_NAME="queue02";

//向自己的云服务器的消息队列发送消息message

    public void send(String message) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection=activeMQConnectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue=session.createQueue(QUEUE_NAME);

        MessageProducer messageProducer=session.createProducer(queue);

        TextMessage textMessage=session.createTextMessage(message);
        messageProducer.send(textMessage);

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发布到MQ成功！！！");
    }

//从云服务器的消息队列读取消息，并打印输出到控制台

    public void read() throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection=activeMQConnectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue=session.createQueue(QUEUE_NAME);

        MessageConsumer messageConsumer=session.createConsumer(queue);

        while (true){
            TextMessage textMessage= (TextMessage) messageConsumer.receive();
            if(textMessage!=null){
                System.out.println("消费者收到的消息："+textMessage.getText());
            }else {
                break;
            }
        }

        messageConsumer.close();
        session.close();
        connection.close();
    }

}