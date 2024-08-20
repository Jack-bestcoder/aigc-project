package com.yupi.springbootinit;

import com.yupi.springbootinit.mapper.InteractionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.PriorityQueue;

@RunWith(SpringRunner.class)    // 固定写法
@SpringBootTest(classes = MainApplication.class)    // SpringBoot启动类（自定义的）
public class MainApplicationTests {
    @Resource
    InteractionMapper interactionMapper;

    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void mapperTest() {
        System.out.println(interactionMapper.selectUserQueryByUserIdWithCursor(1, 0, 1));
    }
}