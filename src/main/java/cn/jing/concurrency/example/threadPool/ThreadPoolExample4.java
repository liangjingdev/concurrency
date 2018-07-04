package cn.jing.concurrency.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExample4 {

	public static void main(String[] args) {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

		// 指定任务执行时间
		executorService.schedule(new Runnable() {

			@Override
			public void run() {

			}
		}, 3, TimeUnit.SECONDS);

		executorService.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				log.warn("schedule run");
			}
		}, 1, 3, TimeUnit.SECONDS);

		executorService.shutdown();
	}
}
