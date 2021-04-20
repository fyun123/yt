package com.whut.yt.core.mediator;

import com.google.gson.Gson;
import com.whut.yt.common.constant.CommonConstant;
import com.whut.yt.core.context.YTransactionContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 17:08
 * @Description:
 */
public class RpcMediator {

    private static final RpcMediator RPC_MEDIATOR = new RpcMediator();

    private final Gson GSON = new Gson();

    /**
     * 获取实例
     *
     * @return RpcMediator实例
     */
    public static RpcMediator getInstance() {
        return RPC_MEDIATOR;
    }

    /**
     * Transmit.
     *
     * @param rpcTransmit rpc transmit
     * @param context 事务上下文
     */
    public void transmit(final RpcTransmit rpcTransmit, final YTransactionContext context) {
        if (Objects.nonNull(context)) {
            rpcTransmit.transmit(CommonConstant.Y_TRANSACTION_CONTEXT, GSON.toJson(context));
        }
    }

    /**
     * 获取事务上下文
     *
     * @param rpcAcquire the rpc acquire
     * @return 事务上下文
     */
    public YTransactionContext acquire(final RpcAcquire rpcAcquire) {
        YTransactionContext yTransactionContext = null;
        final String context = rpcAcquire.acquire(CommonConstant.Y_TRANSACTION_CONTEXT);
        if (StringUtils.isNoneBlank(context)) {
            yTransactionContext = GSON.fromJson(context, YTransactionContext.class);
        }
        return yTransactionContext;
    }
}
