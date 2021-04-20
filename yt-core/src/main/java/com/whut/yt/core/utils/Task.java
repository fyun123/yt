package com.whut.yt.core.utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: 一大岐
 * @Date: 2021/4/18 22:28
 * @Description: 等待任务
 */
public class Task {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void waitTask() {
        try {
            lock.lock();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalTask() {
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
