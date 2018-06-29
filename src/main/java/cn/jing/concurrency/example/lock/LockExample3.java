package cn.jing.concurrency.example.lock;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:锁--ReentrantReadWriteLock
 * 
 * @author liangjing
 *
 */
@Slf4j
@ThreadSafe
public class LockExample3 {
	private final Map<String, Data> map = new TreeMap<>();

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private final Lock readLock = lock.readLock();

	private final Lock writeLock = lock.writeLock();

	public Data get(String key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}

	public Set<String> getAllKeys() {
		readLock.lock();
		try {
			return map.keySet();
		} finally {
			readLock.unlock();
		}
	}

	// 在没有任何读写锁的时候才可以进行写入操作
	public Data put(String key, Data value) {
		writeLock.lock();
		try {
			return map.put(key, value);
		} finally {
			readLock.unlock();
		}
	}

	class Data {

	}
}
