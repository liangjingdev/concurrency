package cn.jing.concurrency.example.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedExample2 {

	/**
	 * function:修饰类，括号包起来的代码 作用对象为这个类的所有对象
	 */
	public static void test1(int j) {
		synchronized (SynchronizedExample2.class) {
			for (int i = 0; i < 10; i++) {
				log.info("test1 {} - {}", j, i);
			}
		}
	}

	/**
	 * function:修饰一个静态方法，作用对象为这个类的所有对象
	 */
	public static synchronized void test2(int j) {
		for (int i = 0; i < 10; i++) {
			log.info("test2 {} - {}", j, i);
		}
	}

	public static void main(String[] args) {
		SynchronizedExample2 example1 = new SynchronizedExample2();
		SynchronizedExample2 example2 = new SynchronizedExample2();

		// 注意：并不确定是哪个线程首先调用方法的，顺序是由系统来决定的
		// 使用线程池模拟一个对象的两个线程同时调用一段sync代码的执行过程
		ExecutorService executorService = Executors.newCachedThreadPool();

		// 线程pool-1-thread-1 先从0-9输出，然后pool-1-thread-2 从0到9顺序输出
		executorService.execute(() -> example1.test1(1));
		executorService.execute(() -> example1.test1(1));

		// 线程pool-1-thread-1 先从0-9输出，然后pool-1-thread-2 从0到9顺序输出
		executorService.execute(() -> example1.test2(1));
		executorService.execute(() -> example2.test2(2));
	}
}
