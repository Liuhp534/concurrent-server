package cn.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CustomLock implements Lock {

    private boolean lockStatus = Boolean.FALSE;

    @Override
    public synchronized void lock() {
        while (lockStatus) {//这里需要用到while，？？？
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lockStatus = Boolean.TRUE;
    }

    @Override
    public synchronized void unlock() {
        this.notify();
        lockStatus = Boolean.FALSE;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
