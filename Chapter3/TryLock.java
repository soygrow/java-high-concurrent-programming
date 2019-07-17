/**
 * Created by soygrow on 2019-07-17.
 *
 * 演示tryLock方法不带时间参数的时候很可能死锁，但是tryLock是尝试获取资源，
 * 代码中会循环tryLock，只要时间足够常总会得到lock1和lock2两个资源的
 */
public class TryLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public TryLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == 1) {
            while (true) {
                if (lock1.tryLock()) {
                    try {
                        try {
                            System.out.println("sleep 5000");
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {

                        }

                        if (lock2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getId() + ":My Job done");
                                return;
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
            }
        } else {
            while (true) {
                if (lock2.tryLock()) {
                    try {
                        try {
                            System.out.println("sleep 500");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {

                        }

                        if (lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread() + ":My Job done");
                                return;
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLock r1 = new TryLock(1);
        TryLock r2 = new TryLock(2);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
    }
}
