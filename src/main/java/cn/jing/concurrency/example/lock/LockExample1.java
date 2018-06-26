package cn.jing.concurrency.example.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:锁-synchronized
 * 
 * @author liangjing
 */
@Slf4j
@ThreadSafe
public class lockExample1 {

	// 日志工具类
	private static Logger log = (Logger) LoggerFactory.getLogger(lockExample1.class);

	// 请求总数
	public static int clientTotal = 5000;
	// 同时并发执行的线程数
	public static int threadTotal = 50;
	// 计数值
	public static int count = 0;

	public static void main(String[] args) throws InterruptedException {
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 信号量（定义同时并发执行的线程数）
		final Semaphore semaphore = new Semaphore(threadTotal);
		// 闭锁
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		// 模拟并发请求
		for (int i = 0; i < clientTotal; i++) {
			executorService.execute(() -> {
				try {
					// 请求一个信号，如果信号量小于clientTotal，则阻塞
					semaphore.acquire();
					add();
					// 释放一个信号
					semaphore.release();
				} catch (InterruptedException e) {
					log.error("exception", e);
				}
				// countDown：减一
				countDownLatch.countDown();
			});
		}
		// 阻塞直到countDown的次数为threadTotal
		countDownLatch.await();
		// 关闭线程池
		executorService.shutdown();
		log.info("count:{}", count);
	}

	/**
	 * function:计数方法,该方法是线程安全的
	 */
	private synchronized static void add() {
		count++;
	}
}
