public class InterruptedThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    //if (Thread.currentThread().isInterrupted()) {
                    //    System.out.println("Interrupted!");
                    //}

                    System.out.println("1");
                    Thread.yield();
                    System.out.println("2");
                }
            }
        };

        t1.start();
        Thread.sleep(2000);

        /**
         * 这里中断线程后，t1并不会停止，可以看到"1""2"还在不断打印
         * 如果希望t1在中断后必须退出，那可以加上已注释的代码
         */
        t1.interrupt();
    }
}