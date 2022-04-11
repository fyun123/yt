# yt (基于RabbitMq+Redis的分布式事务框架)
## 1. 涉及技术
1. AOP思想
2. Redis
3. RabbitMQ：延迟队列、死信队列
## 2. 主要流程
1. 事务发起者执行前，创建根事务，执行原逻辑
2. 调用远程服务，在feign请求头添加事务上下文（事务组id，事务执行阶段，事务参与的角色），传播事务上下文到消费者。
3. 消费者获取事务组id，创建分支事务（事务组id，事务id，事务类型【回滚、提交】，阻塞任务）。保存子事务到Redis。
4. 事务发起者发送【事务组id，事务id，提交 Or 回滚】消息到消息队列。
5. 子事务阻塞等待消息队列通知，根据消息提交或者回滚。
## 3. 实例运行
yt-core为该分布式事务框架的核心。例程是基于spring cloud，展示了如何取在项目中整合这个分布式事务框架
1. 修改数据库连接
2. 创建数据库ytcc-server1和ytcc-server2，并各创建一个名为count的表，各添加一条记录
```sql
drop table if exists count;
/*==============================================================*/
/* Table: count                                                 */
/*==============================================================*/
create table count
(
   id                   bigint not null auto_increment comment '账户id',
   money                float comment '余额',
   primary key (id)
);
alter table count comment '账户';
```
3. 修改redis、RabbitMQ、nacos地址，启动服务即可
4. 可在服务1com.whut.yt.test.server1.service.impl.AccountServiceImpl.transfer方法中加入错误，模拟交易失败，观察数据库是否回滚。
## 4. 注意
该框架设计存在很多缺陷，只适用于学习，并不能用于实际应用。