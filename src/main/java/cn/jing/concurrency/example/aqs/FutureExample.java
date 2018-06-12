package cn.jing.concurrency.example.aqs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import lombok.extern.slf4j.Slf4j;

/**
 * function:使用Callable+Future获取执行结果
 * 
 * @author liangjing
 *
 */
@Slf4j
public class FutureExample {

	static class MyCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			log.info("do something in callable");
			// 阻塞线程5秒钟
			Thread.sleep(5000);
			return "Done";
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> future = executorService.submit(new MyCallable());
		log.info("do something in main");
		Thread.sleep(1000);
		String result = future.get();
		log.info("result:{}", result);
	}
}
