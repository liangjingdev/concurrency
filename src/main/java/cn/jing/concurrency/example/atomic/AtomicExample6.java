package cn.jing.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * function:并发测试
 * 
 * @author liangjing
 */
@Slf4j
@ThreadSafe
public class AtomicExample6 {

	public static AtomicBoolean isHappened = new AtomicBoolean(false);
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
					test();
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
		// 调用AtomicInteger的get方法来获取当前的值
		log.info("isHappened:{}", isHappened.get());
	}

	private static void test() {
		// 可以保证(if语句中包含的代码)只会执行一次,并不会重复
		if (isHappened.compareAndSet(false, true)) {
			log.info("execute");
		}
	}
}
