package com.whut.yt.core.context;

import com.whut.yt.common.enums.YTccActionEnum;
import com.whut.yt.common.enums.YTccRoleEnum;
import lombok.Data;
import java.io.Serializable;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 16:16
 * @Description: 事务上下文
 */
@Data
public class YTransactionContext implements Serializable {

    private static final long serialVersionUID = 5534536283743576437L;

    /**
     * 事务组Id.
     */
    private String transGroupId;

    /**
     * 事务执行阶段 {@linkplain YTccActionEnum}
     */
    private int action;

    /**
     * 事务参与的角色 {@linkplain YTccRoleEnum}
     */
    private int role;
}
