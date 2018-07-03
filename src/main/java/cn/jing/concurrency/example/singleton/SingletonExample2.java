package cn.jing.concurrency.example.singleton;

import static org.mockito.Mockito.RETURNS_MOCKS;

import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.ThreadSafe;

/**
 * function:饿汉模式 单例的实例在类加载的时候创建 
 * 缺点 
 * 1.如果创建过程中进行很多的运算，会导致类加载的时候特别的慢
 * 2.如果创建出来的实例要很久以后才被调用，那么会导致资源的浪费 
 * 3.如果初始化本身依赖于一些其他数据，那么也就很难保证其他数据会在它初始化之前准备好
 * 
 * @author liangjing
 */
@ThreadSafe
public class SingletonExample2 {

	// 私有构造函数
	private SingletonExample2() {

	}

	// 单例对象
	private static SingletonExample2 instance = new SingletonExample2();

	// 静态的工厂方法
	public static SingletonExample2 getInstance() {
		return instance;
	}
}
