package cn.jing.concurrency.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * function:信号量
 * 使用场景2：一次性获取多个许可
 * 
 * @author liangjing
 *
 */
@Slf4j
public class SemaphoreExample2 {
	private final static int threadCount = 20;

	public static void main(String[] args) throws Exception {

		ExecutorService exec = Executors.newCachedThreadPool();

		// 做并发访问控制
		final Semaphore semaphore = new Semaphore(3);

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					semaphore.acquire(3); // 一次性获取三个许可
					test(threadNum);
					semaphore.release(3); // 一次性释放三个许可
				} catch (Exception e) {
					log.error("exception", e);
				}
			});
		}
		exec.shutdown();
	}

	private static void test(int threadNum) throws Exception {
		// 运行时注意看时间，即可看到线程池中每一秒只能放行一个线程（这时就跟单线程执行很相似了）
		log.info("{}", threadNum);
		Thread.sleep(1000);
	}
}
