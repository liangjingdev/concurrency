package cn.jing.concurrency.example.singleton;

import static org.mockito.Mockito.RETURNS_MOCKS;

import cn.jing.concurrency.annotations.NotRecommend;
import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.ThreadSafe;

/**
 * function:懒汉模式 单例的实例在第一次使用的时候进行创建
 * 
 * @author liangjing
 *
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {

	// 私有构造函数
	private SingletonExample3() {

	}

	// 单例对象
	private static SingletonExample3 instance = null;

	// 静态的且同步的工厂方法（保证了同一时间内只能有一个线程能够访问该方法，所以是线程安全的）
	public static synchronized SingletonExample3 getInstance() {
		if (instance == null) {
			instance = new SingletonExample3();
		}
		return instance;
	}
}
