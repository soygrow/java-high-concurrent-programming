public class BadSuspend {
    public static Object u = new Object();

    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());

                /**
                 * Suspends this thread.
                 * <p>
                 * First, the <code>checkAccess</code> method of this thread is called
                 * with no arguments. This may result in throwing a
                 * <code>SecurityException </code>(in the current thread).
                 * <p>
                 * If the thread is alive, it is suspended and makes no further
                 * progress unless and until it is resumed.
                 *
                 * @exception  SecurityException  if the current thread cannot modify
                 *               this thread.
                 * @see #checkAccess
                 * @deprecated   This method has been deprecated, as it is
                 *   inherently deadlock-prone.  If the target thread holds a lock on the
                 *   monitor protecting a critical system resource when it is suspended, no
                 *   thread can access this resource until the target thread is resumed. If
                 *   the thread that would resume the target thread attempts to lock this
                 *   monitor prior to calling <code>resume</code>, deadlock results.  Such
                 *   deadlocks typically manifest themselves as "frozen" processes.
                 *   For more information, see
                 *   <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
                 *   are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
                 */
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();

        t1.resume();
        t2.resume();

        t1.join();
        t2.join();
    }
}