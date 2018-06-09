package cn.jing.concurrency.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * function:使用场景2:比如有多个线程一起完成某个任务，但是这个任务只想给他一个指定的时间，超过这个时间就不继续等待了。完成多少算多少
 * 
 * @author liangjing
 *
 */
@Slf4j
public class CountDownLatchExample2 {
	// 线程数
	private final static int threadCount = 200;

	public static void main(String[] args) throws Exception {

		ExecutorService exec = Executors.newCachedThreadPool();

		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			// 放在这里是没有用的，因为这时候还是在主线程中阻塞，阻塞完以后才开始执行下面的await
			// Thread.sleep(100);
			exec.execute(() -> {
				try {
					test(threadNum);
				} catch (Exception e) {
					log.error("exception", e);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		// 含参数的CountDownLatch的await()方法使得即使计数器中的值没有0但是达到指定的时间后，位于await()方法下面的代码也得以执行，含参数的await()方法还有一个好处就是：比如我在某些情况下忘记调用countDown()方法时，不至于出现死等待的情况，即代码无法继续往下执行的情况
		// 等待指定的时间 参数1：等待时间 参数2：时间单位
		countDownLatch.await(10, TimeUnit.MILLISECONDS);
		log.info("finish");
		// 注意：该方法并不是第一时间内销毁掉所有线程，而是先让正在执行的线程执行完，再将线程池关闭
		exec.shutdown();
	}

	private static void test(int threadNum) throws Exception {
		Thread.sleep(100);
		log.info("{}", threadNum);
	}
}
