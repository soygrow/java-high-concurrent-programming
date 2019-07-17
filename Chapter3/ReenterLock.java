public class ReenterLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j ++) {
            lock.lock();

            try {
                i ++;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 重入锁完全可以替代synchronized关键字，而且性能要远远好于synchronized
     * 重入锁使用ReentrantLock实现
     * 在一个线程中，重入锁可以多次获得锁，但是在所得释放上次数要和加锁次数相同
     *
     * synchronized关键字只有两种可能：1.继续执行，2.保持等待
     * 而ReentrantLock可以被中断，这种对死锁有一定的帮助
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ReenterLock r1 = new ReenterLock();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(i);

    }
}
