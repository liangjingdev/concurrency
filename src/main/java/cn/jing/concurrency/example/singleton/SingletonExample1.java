package cn.jing.concurrency.example.singleton;

import static org.mockito.Mockito.RETURNS_MOCKS;

import cn.jing.concurrency.annotations.NotThreadSafe;

/**
 * function:懒汉模式 单例的实例在第一次使用的时候进行创建
 * 
 * @author liangjing
 *
 */
@NotThreadSafe
public class SingletonExample1 {

	// 私有构造函数
	private SingletonExample1() {

	}

	// 单例对象
	private static SingletonExample1 instance = null;

	// 静态的工厂方法
	public static SingletonExample1 getInstance() {
		if (instance == null) {
			instance = new SingletonExample1();
		}
		return instance;
	}
}
