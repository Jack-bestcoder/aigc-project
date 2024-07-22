package com.yupi.springbootinit.bizmq;



import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.rabbitmq.client.Channel;
import java.io.IOException;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private RabbitTemplate rabbitTemplate;

//    @RabbitListener(queues = {"simple_queue3"}, ackMode = "MANUAL")
//    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
//        log.info("receiveMessage message = {}", message);
//        channel.basicAck(deliveryTag, false);
//    }
}
