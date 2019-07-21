/**
 * Created by soygrow on 2019-07-18.
 * 这里主要说明ReentrantLock锁有公平和非公平之分，参数为true时，表示公平锁
 * 从打印的日志可以看出，两个线程是交替获得锁的，如果是false，可能会出现有一
 * 个线程连续两次获得锁
 *
 * 这里整理重入锁ReetrantLock的几个重要方法：
 * 1. lock(): 获得锁，如果所已经被占用，则等待
 * 2. lockInterruptibly(): 获得锁，但是优先响应中断
 * 3. tryLock(): 尝试获得锁，如果成功返回true，如果失败返回false。该方法不等待，立即返回
 * 4. tryLock(long time, TimeUnit unit): 在给定的时间内尝试获得锁
 * 5. unlock(): 释放锁
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1, "Thread_t1");
        Thread t2 = new Thread(r1, "Thread_t2");

        t1.start();
        t2.start();
    }
}

