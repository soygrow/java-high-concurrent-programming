/**
 * Created by zhanghao on 2019-07-21.
 * 信号量：允许多个线程访问。下面演示了信号量为多线程协作提供了更为强大的
 * 控制方法。
 */
public class SemapDemo implements Runnable {
    // 定义5个信号量
    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire(); // 尝试获得一个准入许可，最多允许5个线程同时访问下面代码

            // 模拟耗时操作
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ":done!");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemapDemo demo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            exec.submit(demo);
        }
    }
}
