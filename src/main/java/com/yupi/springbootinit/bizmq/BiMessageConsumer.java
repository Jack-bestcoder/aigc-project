package com.yupi.springbootinit.bizmq;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.model.dto.interaction.UpdateRequest;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.service.InteractionService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class BiMessageConsumer {

    @Resource
    private InteractionService interactionService;


    // 监听update_sql消息队列中的任务
    @SneakyThrows
    @RabbitListener(queues = {UpdateMqConstant.BI_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        // 打印获取的消息
        log.info("receiveMessage message = {}", message);

        // 检测消息是否为空
        if (StringUtils.isBlank(message)) {
            // 拒绝消费这条消息
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "消息为空");
        }

        // 接收消息转换为对象
        UpdateRequest updateRequest = JSONUtil.toBean(message, UpdateRequest.class);

        System.out.println(updateRequest.getId());
        System.out.println(updateRequest.getGenerateSql());
        // 更新数据库
        interactionService.updateUserQuery(updateRequest);

        // 消息确认
        channel.basicAck(deliveryTag, false);
    }
}
