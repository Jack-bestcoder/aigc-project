package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
public class BiInitMain {

    public static void main(String[] args) {
        try {
            //创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            //设置连接地址(rabbitmq服务器所在的系统的IP地址)
            connectionFactory.setHost("120.76.47.158");
            //设置连接端口号，默认为5672
            connectionFactory.setPort(5672);
            //设置虚拟主机名称，默认为/
            connectionFactory.setVirtualHost("my_vhost");
            //设置rabbitMQ用户名
            connectionFactory.setUsername("admin");
            //设置rabbitMQ密码
            connectionFactory.setPassword("123456");
            //获取连接对象
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //创建交换机名称
            String EXCHANGE_NAME =  BiMqConstant.BI_EXCHANGE_NAME;
            //交换机路由模式
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 创建队列，随机分配一个队列名称
            String queueName = BiMqConstant.BI_QUEUE_NAME;
            // 将队列持久化
            channel.queueDeclare(queueName, true, false, false, null);
            // 将队列绑定到交换机上
            channel.queueBind(queueName, EXCHANGE_NAME,  BiMqConstant.BI_ROUTING_KEY);
        } catch (Exception e) {

        }

    }
}
