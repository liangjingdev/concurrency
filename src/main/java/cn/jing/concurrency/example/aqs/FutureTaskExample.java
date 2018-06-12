package cn.jing.concurrency.example.aqs;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import lombok.extern.slf4j.Slf4j;

/**
 * function:使用Callable+FutureTask获取执行结果
 * 
 * @author liangjing
 *
 */
@Slf4j
public class FutureTaskExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.info("do something in callable");
				Thread.sleep(5000);
				return "Done";
			}
		});

		new Thread(futureTask).start();
		log.info("do something in main");
		Thread.sleep(1000);
		String result = futureTask.get();
		log.info("result:{}", result);
	}
}
