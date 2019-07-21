/**
 * Created by soygrow on 2019-07-21.
 * 循环栅栏: CyclicBarrier. CyclicBarrier是一种多线程并发控制实用工具，和CountDownLatch
 * 非常类似，也可以实现线程间的计数等待，但是它的功能比CountDownLatch更加复杂和强大。
 *
 * Cyclic意为循环，也就是说这个计数器可以反复使用，比如，下面的代码我们将计数器设置为10，那么
 * 凑齐第一批10个线程后，计数器就会归零，然后接着凑齐下一批10个线程，这个就是循环栅栏内在的含义。
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic, String soldierName) {
            this.cyclic = cyclic;
            this.soldier = soldierName;
        }

        @Override
        public void run() {
            try {
                // 等待所有士兵到齐，也就是等待10个线程到这里，然后才能执行doWork
                cyclic.await();
                doWork();
                // 等待所有士兵完成工作，也就是循环栅栏要等待10个线程都完成了doWork才可以
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(soldier + ":任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令:[士兵" + N + "个，任务完成！]");
            } else {
                System.out.println("司令:[士兵" + N + "个，集合完毕！]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;

        // 创建了CyclicBarrier实例，并将计数器设置为10，并要求在计数器达到指标时，
        // 执行BarrierRun中run方法
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));

        // 设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍!");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵 " + i + " 报道！");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵 " + i));
            allSoldier[i].start();
        }
    }
}
