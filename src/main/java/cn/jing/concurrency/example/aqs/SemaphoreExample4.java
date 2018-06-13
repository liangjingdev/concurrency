package cn.jing.concurrency.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * function:信号量
 * 使用场景3：在并发量很高的情况下，想要实现一旦超过其允许的并发数就将该线程抛弃的情景
 * 
 * @author liangjing
 *
 */
@Slf4j
public class SemaphoreExample4 {
	private final static int threadCount = 20;

	public static void main(String[] args) throws Exception {

		ExecutorService exec = Executors.newCachedThreadPool();

		final Semaphore semaphore = new Semaphore(3);

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					if (semaphore.tryAcquire(2000, TimeUnit.MILLISECONDS)) { // 尝试在指定时间内获取一个许可,获取不到则不执行，抛弃掉
						// 本例中只有六个线程可以执行该test方法，其它都被丢弃了
						test(threadNum);
						semaphore.release(); // 释放一个许可
					}
				} catch (Exception e) {
					log.error("exception", e);
				}
			});
		}
		exec.shutdown();
	}

	private static void test(int threadNum) throws Exception {
		log.info("{}", threadNum);
		Thread.sleep(1000);
	}
}
