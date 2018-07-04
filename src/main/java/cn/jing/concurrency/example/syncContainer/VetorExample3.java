package cn.jing.concurrency.example.syncContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:同步容器
 * 注意：在foreach或迭代器遍历的过程中不要做（增加）删除操作，应该先标记，然后最后（在循环结束之后）再统一进行（增加）删除操作
 * （同步容器主要就是利用了synchronized关键字进行同步）
 * 
 * @author liangjing
 *
 */
public class VetorExample3 {

	// java.util.ConcurrentModificationException
	// 在遍历的同时进行了(增加)删除的操作，导致抛出了并发修改的异常
	private static void test1(Vector<Integer> v1) { // foreach
		for (Integer i : v1) {
			if (i.equals(3)) {
				v1.remove(i);
			}
		}
	}

	// java.util.ConcurrentModificationException
	// 在遍历的同时进行了(增加)删除的操作，导致抛出了并发修改的异常
	private static void test2(Vector<Integer> v1) { // iterator
		Iterator<Integer> iterator = v1.iterator();
		while (iterator.hasNext()) {
			Integer i = iterator.next();
			if (i.equals(3)) {
				v1.remove(i);
			}
		}
	}

	// success（推荐使用for循环）
	private static void test3(Vector<Integer> v1) { // for
		for (int i = 0; i < v1.size(); i++) {
			if (v1.get(i).equals(3)) {
				v1.remove(i);
			}
		}
	}

	public static void main(String[] args) {

		Vector<Integer> vector = new Vector<>();
		vector.add(1);
		vector.add(2);
		vector.add(3);
		test1(vector);
		// test2(vector);
		// test3(vector);
	}

}
