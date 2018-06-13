package cn.jing.concurrency.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * function:信号量
 * 使用场景1：仅能提供有限访问的资源：比如数据库的连接数最大只有20，而上层的并发数远远大于20，这时候如果不做限制，可能会由于无法获取连接而导致并发异常，这时候可以使用Semaphore来进行并发访问控制，当信号量设置为1的时候，就和单线程运行很相似了
 * 
 * @author liangjing
 *
 */
@Slf4j
public class SemaphoreExample1 {
	private final static int threadCount = 20;

	public static void main(String[] args) throws Exception {

		ExecutorService exec = Executors.newCachedThreadPool();

		// 做并发访问控制
		final Semaphore semaphore = new Semaphore(3);

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					semaphore.acquire(); // 获取一个许可
					test(threadNum);
					semaphore.release(); // 释放一个许可
				} catch (Exception e) {
					log.error("exception", e);
				}
			});
		}
		exec.shutdown();
	}

	private static void test(int threadNum) throws Exception {
		// 运行时注意看时间，即可看到线程池中每一秒只能放行三个线程
		log.info("{}", threadNum);
		Thread.sleep(1000);
	}
}
