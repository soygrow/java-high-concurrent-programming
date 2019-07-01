public class StopThreadUnsafe {
    public static User u = new User();

    public static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }
    }

    public static class ChangeObjectThread extends Thread {
        //volatile boolean stopme = false;

        @Override
        public void run() {
            while (true) {
                //if (stopme) {
                //    System.out.println("exit by stop me.");
                //    break;
                //}

                synchronized (u) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    u.setId(v);

                    // Oh, do something. else
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    u.setName(String.valueOf(v));
                }

                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    String nameNum = u.getName();
                    if (u.getId() != Integer.parseInt(nameNum)) {
                        System.out.println(u.toString());
                    }
                }

                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();

        while (true) {
            Thread t = new ChangeObjectThread();

            t.start();
            Thread.sleep(150);
            t.stop();
        }
    }
}