package com.whut.yt.core.threadlocal;

import com.whut.yt.core.context.YTransactionContext;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 15:33
 * @Description: 本地事务上下文
 */
public final class YTransactionThreadLocal {

    private static final YTransactionThreadLocal Y_TRANSACTION_THREAD_LOCAL = new YTransactionThreadLocal();

    private static final ThreadLocal<YTransactionContext> CURRENT_TRANSACTION_CONTEXT = new ThreadLocal<>();

    public YTransactionThreadLocal() {
    }

    public static YTransactionThreadLocal getInstance() {
        return Y_TRANSACTION_THREAD_LOCAL;
    }

    public void setCurrentTransactionContext(final YTransactionContext yTransactionContext) {
        CURRENT_TRANSACTION_CONTEXT.set(yTransactionContext);
    }

    public YTransactionContext getCurrentTransactionContext() {
        return CURRENT_TRANSACTION_CONTEXT.get();
    }

    public void removeCurrentTransactionContext() {
        CURRENT_TRANSACTION_CONTEXT.remove();
    }

}
