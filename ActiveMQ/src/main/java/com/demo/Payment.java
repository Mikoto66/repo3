package com.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Payment {

    /**

     * 向服务器发送银行支付的消息，服务器返回付款结果，包括成功与否，账户余额。

     *

     * @param amount 支付金额

     * @return

     */

    public static final String ACTIVEMQ_URL="tcp://123.56.67.50:61616";
    public static final String PAY_QUEUE_NAME="queue03";
    public static final String RETURN_QUEUE_NAME="queue04";

    public void pay(double amount) throws JMSException {

//此处编写代码，可以向自己的云服务器以消息队列的机制发送异步支付请求。

//以消息队列的机制获取服务器支付结果消息，并在控制台打印输出结果。

        send(amount);
        System.out.println("支付成功，余额："+read());

    }

    public void send(double money) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection=activeMQConnectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue=session.createQueue(PAY_QUEUE_NAME);

        MessageProducer messageProducer=session.createProducer(queue);

        TextMessage textMessage=session.createTextMessage(String.valueOf(money));
        messageProducer.send(textMessage);

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发布到MQ成功！！！");
    }

    public double read() throws JMSException {

        Double result = null;
        
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection=activeMQConnectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue=session.createQueue(RETURN_QUEUE_NAME);

        MessageConsumer messageConsumer=session.createConsumer(queue);

        TextMessage textMessage= (TextMessage) messageConsumer.receive();

        result=Double.parseDouble(textMessage.getText());

        messageConsumer.close();
        session.close();
        connection.close();
        
        return result;
    }

}


