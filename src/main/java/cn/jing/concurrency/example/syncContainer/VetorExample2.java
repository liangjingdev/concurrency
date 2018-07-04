package cn.jing.concurrency.example.syncContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:同步容器 注意：同步容器并不一定线程安全（即同步容器并一定能够做到真正的线程安全）
 * (这个例子演示了同步容器里面的两个同步方法因为操作顺序的差异，不同的线程里面可能会触发线程不安全的问题，因此为了保证线程安全，我们必须在方法调用端
 * 做一些额外的措施才可以)
 * 
 * @author liangjing
 *
 */
@Slf4j
@NotThreadSafe
public class VetorExample2 {

	/** 请求总数 */
	public static int clientTotal = 5000;
	/** 同时并发执行的线程数 */
	public static int threadTotal = 50;

	public static List<Integer> list = new Vector<>();

	public static void main(String[] args) throws InterruptedException {
		while (true) {

			for (int i = 0; i < 10; i++) {
				list.add(i);
			}
			Thread thread1 = new Thread(() -> {
				for (int i = 0; i < list.size(); i++) {
					list.remove(i);
				}
			});

			Thread thread2 = new Thread(() -> {
				// thread2想获取i=9的元素的时候，thread1刚好将i=9的元素移除了，这时就会导致数组越界
				for (int i = 0; i < list.size(); i++) {
					list.get(i);
				}
			});
			thread1.start();
			thread2.start();
		}
	}

}
