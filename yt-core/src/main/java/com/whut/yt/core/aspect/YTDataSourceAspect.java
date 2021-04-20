package com.whut.yt.core.aspect;

import com.whut.yt.core.manager.YTransactionManager;
import com.whut.yt.core.utils.YTConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @Author: 一大岐
 * @Date: 2021/4/18 10:18
 * @Description: 数据源切面
 */
@Aspect
@Component
public class YTDataSourceAspect {

    /**
     * 每次创建数据源连接时，都会经过该切面
     * 如果本地已经存在事务，说明先被@YTransaction注解切入了
     *
     * @param point 切入点
     * @return 连接
     * @throws Throwable Throwable
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable {
        if (YTransactionManager.getCurrentTransaction() != null) {
            Connection connection = (Connection) point.proceed();
            connection.setAutoCommit(false);
            return new YTConnection(connection, YTransactionManager.getCurrentTransaction());
        } else {
            return (Connection) point.proceed();
        }
    }
}
