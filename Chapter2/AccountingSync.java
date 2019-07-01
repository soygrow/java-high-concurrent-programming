public class AccountingSync implements Runnable {
    static AccountingSync instance = new AccountingSync();
    static int i = 0;
    
    @Override
    public void run() {
        for (int j = 0; j < 10000000; j ++) {
            // synchronized 指定加锁对象，进入同步代码前需要获取对象的锁
            synchronized (instance) {
                i ++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println(i);
    }
}
