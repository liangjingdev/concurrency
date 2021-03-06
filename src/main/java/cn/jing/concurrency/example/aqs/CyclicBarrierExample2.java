package cn.jing.concurrency.example.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
public class CyclicBarrierExample2 {

	// 1.给定一个值，说明有多少个线程同步等待
	private static CyclicBarrier barrier = new CyclicBarrier(5);

	public static void main(String[] args) throws Exception {

		ExecutorService executor = Executors.newCachedThreadPool();

		// 向线程池中放入一些线程
		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			// 延迟1秒，方便观察
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
		try {
			// 2.调用await方法进行等待(如果等待的时间超出指定的时间，那么就继续执行下去，不等待了)
			 // 由于状态可能会改变，所以会抛出BarrierException异常，如果想继续往下执行，需要加上try-catch
			barrier.await(2000, TimeUnit.MILLISECONDS);
		} catch (BrokenBarrierException | TimeoutException e) {
			// 因为含参数的await()方法执行可能会抛出BrokenBarrierException异常以及TimeoutException从而导致程序执行失败，所以要想程序继续执行下去的话，那么就需要捕捉该异常
			log.warn("BarrierException", e);
		}
		// 等待的线程数达到指定的数目之后，那么以下的代码便可以执行了
		log.info("{} continue", threadNum);
	}
}
