package com.whut.yt.core.mq.listener;

import com.rabbitmq.client.Channel;
import com.whut.yt.core.constant.YTransactionType;
import com.whut.yt.core.entity.YTransactionEntity;
import com.whut.yt.core.manager.YTransactionManager;
import com.whut.yt.core.store.RedisStore.RedisService;
import com.whut.yt.core.vo.YTransactionMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 一大岐
 * @Date: 2021/4/19 8:06
 * @Description: 监听队列，解锁任务
 */
@RabbitListener(queues = "ytransaction.message.queue")
@Service
public class YTMessageListener {

    @Autowired
    RedisService redisService;

    @RabbitHandler
    public void listener(YTransactionMessage yTransactionMessage, Channel channel, Message message) throws IOException {
        if (yTransactionMessage != null){
            YTransactionType yTransactionType = yTransactionMessage.getYTransactionType();
            try{
                YTransactionEntity ytTransaction = YTransactionManager.getYTTransaction(yTransactionMessage.getGroupId());
                if (yTransactionMessage.getYTransactionId().equals(ytTransaction.getYTransactionId())){
                    ytTransaction.getTask().signalTask();
                    ytTransaction.setYTransactionType(yTransactionType);
                    redisService.remove(ytTransaction);
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                }
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            }catch (Exception e){
                e.printStackTrace();
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            }
        }
    }
}
