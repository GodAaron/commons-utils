package com.wf2311.commons.id;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wf2311
 * @date 2016/01/14 9:23
 */
@Deprecated
public class Sequence {
    private static final long ONE_STEP = 10;
    private static final Lock LOCK = new ReentrantLock();
    private static long lastTime = System.currentTimeMillis();
    private static short lastCount = 0;
    private static int count = 0;

    @SuppressWarnings("finally")
    public static String nextId()
    {
        LOCK.lock();
        try {
            if (lastCount == ONE_STEP) {
                boolean done = false;
                while (!done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                        }
                        continue;
                    } else {
                        lastTime = now;
                        lastCount = 0;
                        done = true;
                    }
                }
            }
            count = lastCount++;
        }
        finally
        {
            LOCK.unlock();
            return lastTime+""+String.format("%03d",count);
        }
    }
    public static void main(String[] args)
    {
        //测试
        for(int i=0;i<1000;i++)
        {
            System.out.println(nextId());
        }
    }
}
