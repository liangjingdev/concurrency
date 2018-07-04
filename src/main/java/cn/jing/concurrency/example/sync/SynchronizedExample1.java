package cn.jing.concurrency.example.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedExample1 {

	/**
	 * function：修饰一个代码块，作用范围为大括号括起来的
	 */
	public void test1(int j) {
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				log.info("test1 {} - {}", j, i);
			}
		}
	}

	/**
	 * function:修饰方法，作用范围是整个方法，作用对象为调用这个方法的对象
	 * 若子类继承父类调用父类的synchronized方法，是带不上synchronized关键字的 原因：synchronized
	 * 不属于方法声明的一部分,如果子类也想使用synchronized同步的话就需要在方法上进行声明
	 */
	public synchronized void test2(int j) {
		for (int i = 0; i < 10; i++) {
			log.info("test2 {} - {}", j, i);
		}
	}

	public static void main(String[] args) {
		SynchronizedExample1 example1 = new SynchronizedExample1();
		SynchronizedExample1 example2 = new SynchronizedExample1();

		// 使用线程池模拟两个线程同时调用作用对象的同步代码块中的代码的执行过程
		ExecutorService executorService = Executors.newCachedThreadPool();

		// 注意：并不确定是哪个线程首先调用方法的，顺序是由系统来决定的
		// 线程pool-1-thread-1,pool-1-thread-2
		// 交叉输出（注意：此处两个线程的作用对象不相同，故获取到的对象锁是不一样的，所以synchronized关键字是不会对这两个线程产生互斥影响的，因此是交叉输出的）
		executorService.execute(() -> example1.test1(1));
		executorService.execute(() -> example2.test1(2));

		// 线程pool-1-thread-1 先从0-9输出，然后pool-1-thread-2 从0到9顺序输出（注意：此处两个线程的作用对象相同)
		executorService.execute(() -> example1.test1(1));
		executorService.execute(() -> example1.test1(1));

	}
}
