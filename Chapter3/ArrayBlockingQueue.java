/**
 * Created by soygrow on 2019-07-20.
 * 在JDK内部，重入锁和Condition对象被广泛应用，下面是以ArrayBlockingQueue为例
 */
// 在ArrayBlockingQueue中的一些定义
private final ReentrantLock lock;
private final Condition notEmpty;
private final Condition notFull;

lock = new ReentrantLock(fair);
notEmpty = lock.newCondition();
notFull = lock.newCondition();

// put()方法的实现
public void put(E e) throws InterruptedException {
    if (e == null) throw new NullPointerException();

    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly(); // 对put()方法做同步

    try {
        try {
            // 如果当前队列已满
            while (count == items.length) {
                notFull.await(); // 等待队列有足够的空间
            }
        } catch (InterruptedException ie) {
            notFull.signal();
            throw ie;
        }
        
        insert(e); // 当notFull被通知时，说明有足够的空间
    } finally {
        lock.unlock();
    }
    
}

private void insert(E x) {
    items[putIndex] = x;
    putIndex = inc(putIndex);
    ++ count;
    notEmpty.signal(); // 通知需要take()的线程，队列已经有足够的数据
}

public E take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly(); // 对take()方法做同步
    
    try {
        try {
            // 如果队列为空
            while (count == 0) {
                notEmpty.await(); // 则消费者队列要等待一个非空的信号
            }
        } catch (InterruptedException ie) {
            notEmpty.signal();
            throw ie;
        }
        
        E x = extract();
        return x;
    } finally {
        lock.unlock();
    }
}

private E extract() {
    final E[] items = this.items;
    E x = items[takeIndex];
    items[takeIndex] = null;
    takeIndex = inc(takeIndex);
    -- count;
    notFull.signal(); // 通知put()线程队列已经有两个空闲空间
    return x;
}