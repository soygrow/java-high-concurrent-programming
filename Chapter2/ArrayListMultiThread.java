public class ArrayListMultiThread {
    static ArrayList<Integer> al = new ArrayList<>(10);

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000000; i ++) {
                al.add(i);
            }
        }
    }

    // 多线程下ArrayList是不安全的
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(al.size());
    }
}
