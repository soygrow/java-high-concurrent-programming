/**
 * Created by soygrow on 2019-07-19.
 * 重入锁ReenterLock和Condition配合使用。await会是当前线程等待，同时会释放当前锁，
 * signal会唤醒一个等待线程，一旦形成被唤醒后，它会重新尝试获得与之绑定的重入锁，一
 * 旦成功获取，就可以继续执行了。
 *
 * 这里的Condition是和重入锁绑定的，Condition condition = lock.newCondition()
 */
public class ReenterLockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            condition.await();

            System.out.println("Thread is going on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition tl = new ReenterLockCondition();
        Thread t1 = new Thread(tl);

        t1.start();
        Thread.sleep(2000);

        // 通知线程t1继续执行
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
