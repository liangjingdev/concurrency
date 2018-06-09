package cn.jing.concurrency.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * function:使用场景1:程序执行需要等待某个条件完成后，才能继续进行后面的操作。比如父任务等待所有的子任务都完成的时候，再继续往下进行
 * 
 * @author liangjing
 *
 */
@Slf4j
public class CountDownLatchExample1 {
	private final static int threadCount = 200;

	public static void main(String[] args) throws Exception {

		ExecutorService exec = Executors.newCachedThreadPool();

		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					test(threadNum);
				} catch (Exception e) {
					log.error("exception", e);
				} finally {
					// 为防止出现异常，放在finally更保险一些
					countDownLatch.countDown();
				}
			});
		}
		// 不含参数的CountDownLatch的await()方法使得需要等待计数器的值为0时，位于await()方法下面的代码才得以执行
		countDownLatch.await();
		// 只有当上面定义的所有线程都执行完之后，才打印出'finish'
		log.info("finish");
		// 注意：该方法并不是第一时间内销毁掉所有线程，而是先让正在执行的线程执行完，再将线程池关闭
		exec.shutdown();
	}

	private static void test(int threadNum) throws Exception {
		Thread.sleep(100);
		log.info("{}", threadNum);
		Thread.sleep(100);
	}
}
