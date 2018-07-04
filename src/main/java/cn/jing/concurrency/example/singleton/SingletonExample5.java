package cn.jing.concurrency.example.singleton;

import static org.mockito.Mockito.RETURNS_MOCKS;

import cn.jing.concurrency.annotations.NotRecommend;
import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.Recommend;
import cn.jing.concurrency.annotations.ThreadSafe;

/**
 * function:懒汉模式(双重同步锁单例模式) 单例的实例在第一次使用的时候进行创建
 * 
 * @author liangjing
 *
 */
@ThreadSafe
@Recommend
public class SingletonExample5 {

	// 私有构造函数
	private SingletonExample5() {

	}

	// 单例对象 volatile + 双重检测机制->禁止指令重排
	private static volatile SingletonExample5 instance = null;

	// 静态的工厂方法
	public static SingletonExample5 getInstance() {
		// 双重检测机制
		if (instance == null) {
			// 同步锁
			synchronized (SingletonExample5.class) {
				if (instance == null) {
					instance = new SingletonExample5();
				}
			}
		}
		return instance;
	}
}
