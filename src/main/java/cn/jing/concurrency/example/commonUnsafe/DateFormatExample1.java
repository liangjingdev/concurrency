package cn.jing.concurrency.example.commonUnsafe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import cn.jing.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:线程不安全的写法
 * 
 * @author liangjing
 *
 */
@Slf4j
@NotThreadSafe
public class DateFormatExample1 {

	// 注意 ：SimpleDateFormat
	// 在多线程共享使用的时候会抛出转换异常，应该采用堆栈封闭方式在每次调用方法的时候在方法里创建一个SimpleDateFormat
	// 另一种方式是使用joda-time的DateTimeFormatter(推荐使用)
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	// 请求总数
	public static int clientTotal = 5000;
	// 同时并发执行的线程数
	public static int threadTotal = 50;

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
					update();
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
	}

	private static void update() {
		try {
			simpleDateFormat.parse("20180427");
		} catch (ParseException e) {
			log.error("parse exception", e);
		}
	}
}
