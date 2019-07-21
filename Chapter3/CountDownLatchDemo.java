/**
 * Created by soygrow on 2019-07-21.
 * CountDownLatch 是一个非常实用的多线程控制工具类，它可以让某一个线程等待直到
 * 倒计时结束，再开始执行
 */
public class CountDownLatchDemo implements Runnable {
    // 定义倒计时总数
    static final CountDownLatch end = new CountDownLatch(10);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            // 模拟检查任务
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println("check complete");
            end.countDown(); // 倒计时减一
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.submit(demo);
        }

        // 等待倒计时结束
        end.await();
        // 发射火箭
        System.out.println("Fire!");
        exec.shutdown();
    }
}
