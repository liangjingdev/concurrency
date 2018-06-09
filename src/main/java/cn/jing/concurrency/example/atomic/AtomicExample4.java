package cn.jing.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:并发测试
 * 
 * @author liangjing
 */
@Slf4j
@ThreadSafe
public class AtomicExample4 {

	private static Logger log = (Logger) LoggerFactory.getLogger(AtomicExample4.class);

	private static AtomicReference<Integer> count = new AtomicReference<Integer>(0);

	public static void main(String[] args) {
		count.compareAndSet(0, 2); // 2
		count.compareAndSet(0, 1); // no
		count.compareAndSet(1, 3); // no
		count.compareAndSet(2, 4); // 4
		count.compareAndSet(3, 5); // no
		log.info("count:{}", count.get());
	}
}
