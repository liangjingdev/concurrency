package cn.jing.concurrency.example.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * function:ReentrantLock锁与Condition类(Condition可以分组唤醒需要唤醒的线程)
 * 
 * @author liangjing
 *
 */
@Slf4j
public class LockExample6 {
	public static void main(String[] args) {
		ReentrantLock reentrantLock = new ReentrantLock();
		// 从reentrantLock实例里获取了condition
		Condition condition = reentrantLock.newCondition();
		// 线程1
		new Thread(() -> {
			try {
				// 线程1调用了lock方法，此时线程就加入到了AQS的同步队列里面去
				reentrantLock.lock();
				log.info("wait signal"); // 1 等待信号
				// 调用了Condition的await方法后，该线程就从AQS队列里移除了(对应的是释放锁的操作)，然后进入到了condition队列(条件队列)里面去，等待一个信号
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("get signal"); // 4 得到信号
			// 线程1释放锁
			reentrantLock.unlock();
		}).start();
		// 线程2
		new Thread(() -> {
			// 线程1通过condition.await()操作释放锁之后，这里就获取了锁，加入到了AQS同步队列中
			reentrantLock.lock();
			log.info("get lock"); // 2 获取锁
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 调用signalAll发送信号的方法,此时Condition条件队列的所有节点元素被取出，放在了AQS同步队列里（注意此时线程并没有被唤醒，只是放到了同步队列中）
			condition.signalAll();
			log.info("send signal ~ "); // 3 发送信号
			// 线程2释放锁，这时候AQS同步队列中只剩下线程1，那么线程1被唤醒并继续执行，即从’4'这个这个位置开始继续执行
			reentrantLock.unlock();
		}).start();
	}
}
