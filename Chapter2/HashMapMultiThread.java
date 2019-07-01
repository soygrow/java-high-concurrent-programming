public class HashMapMultiThread {
    static Map<String, String> map = new HashMap<>();

    public static class AddThread implements Runnable {
        int start = 0;
        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100000; i += 2) {
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    /**
     * 可能出现三种结果：
     * 1. 程序正常结束，结果符合预期
     * 2. 程序正常结束，结果不符合预期
     * 3. 程序永远无法结束（可能会占满仅有的两个CPU，使用率达到100%，类似死循环的样子）
     *
     * 我在我的电脑上跑的结果是正常结束，但是结果不符合预期
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(map.size());
    }
}