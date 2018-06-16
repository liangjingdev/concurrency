package cn.jing.concurrency.example.commonUnsafe;

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
 * function:线程安全的类
 * 
 * @author liangjing
 *
 */
@Slf4j
@ThreadSafe
public class StringExample2 {

	// 日志工具类
	private static Logger log = (Logger) LoggerFactory.getLogger(StringExample2.class);

	// 请求总数
	public static int clientTotal = 5000;
	// 同时并发执行的线程数
	public static int threadTotal = 50;
	// StringBuffer线程安全 (原因:StringBuffer几乎所有的方法都加了synchronized关键字)
	// 由于StringBuffer 加了 synchronized 所以性能会下降很多，所以在堆栈封闭等线程安全的环境下应该首先选用StringBuilder
	public static StringBuffer stringBuffer = new StringBuffer();

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
		// 如果是线程安全的话，那么此处打印出来的长度应该是5000
		log.info("size:{}", stringBuffer.length());
	}

	/**
	 * function:计数方法,该方法是线程不安全的
	 */
	private static void update() {
		stringBuffer.append("1");
	}
}
