package cn.jing.concurrency.example.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * function:同步辅助类，允许一组线程相互等待，直到所有线程都准备就绪后，才能继续操作，当某个线程调用了await方法之后，就会进入等待状态，并将计数器-1，直到所有线程调用await方法使计数器为0，才可以继续执行，由于计数器可以重复使用，所以我们又叫他循环屏障
 * 
 * 使用场景：可以用于多线程计算数据，最后合并计算结果的应用场景，比如用Excel保存了用户的银行流水，每一页保存了一个用户近一年的每一笔银行流水，现在需要统计用户的日均银行流水，这时候我们就可以用多线程处理每一页里的银行流水，都执行完以后，
 * 得到每一个页的日均银行流水，之后通过CyclicBarrier的action，利用这些线程的计算结果，计算出整个excel的日均流水
 * 
 * @author liangjing
 *
 */
@Slf4j
public class CyclicBarrierExample3 {

	private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
		// 当指定的线程数（此处是5个）全部都到达屏障时，优先执行这里的runnable
		log.info("callback is running");
	});

	public static void main(String[] args) throws Exception {

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			Thread.sleep(1000);
			executor.execute(() -> {
				try {
					race(threadNum);
				} catch (Exception e) {
					log.error("exception", e);
				}
			});
		}
		executor.shutdown();
	}

	private static void race(int threadNum) throws Exception {
		Thread.sleep(1000);
		log.info("{} is ready", threadNum);
		barrier.await();
		log.info("{} continue", threadNum);
	}
}
