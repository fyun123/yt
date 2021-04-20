package com.whut.yt.core.vo;

import com.whut.yt.core.constant.YTransactionType;
import lombok.Data;

/**
 * @Author: 一大岐
 * @Date: 2021/4/18 18:06
 * @Description: RabbitMQ消息实体类
 */
@Data
public class YTransactionMessage {

    private String groupId;

    private String yTransactionId;

    private YTransactionType yTransactionType;
}
