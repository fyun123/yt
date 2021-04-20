package com.whut.yt.core.entity;



import com.whut.yt.core.constant.YTransactionType;
import com.whut.yt.core.utils.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 22:03
 * @Description: 事务实体类
 */

public class YTransactionEntity implements Serializable {

    private static final long serialVersionUID = 2347241847141213547L;

    /**
     * 事务组id
     */
    @Getter
    @Setter
    private String groupId;

    /**
     * 事务id
     */
    @Getter
    @Setter
    private String yTransactionId;

    /**
     * 事务类型（提交or回滚）
     */
    @Getter
    @Setter
    private YTransactionType yTransactionType;

    /**
     * 任务
     */
    @Getter
    private Task task;

    public YTransactionEntity(String groupId, String yTransactionId) {
        this.groupId = groupId;
        this.yTransactionId = yTransactionId;
        this.task = new Task();
    }

}