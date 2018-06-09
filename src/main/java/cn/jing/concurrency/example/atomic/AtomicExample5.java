package cn.jing.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * function:并发测试
 * 
 * @author liangjing
 */
@Slf4j
@ThreadSafe
public class AtomicExample5 {

	@Getter
	private volatile int count = 100;

	/**
	 * function:AtomicIntegerFieldUpdater 核心是原子性的去更新某一个类的实例的指定的某一个字段
	 * 构造函数第一个参数为类定义，第二个参数为指定字段的属性名，必须是volatile修饰并且非static的字段
	 */
	private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater
			.newUpdater(AtomicExample5.class, "count");

	public static void main(String[] args) throws InterruptedException {
		AtomicExample5 example5 = new AtomicExample5();

		// 第一次 count=100 -> count->120 返回True
		if (updater.compareAndSet(example5, 100, 120)) {
			log.info("update success 1:{}", example5.getCount());
		}

		// count=120 -> 返回False
		if (updater.compareAndSet(example5, 100, 120)) {
			log.info("update success 2:{}", example5.getCount());
		} else {
			log.info("update field:{}", example5.getCount());

		}
	}

}
