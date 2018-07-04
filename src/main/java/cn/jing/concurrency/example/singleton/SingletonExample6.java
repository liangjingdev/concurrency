package cn.jing.concurrency.example.singleton;

import cn.jing.concurrency.annotations.ThreadSafe;

/**
 * function:饿汉模式 单例的实例在类加载的时候创建 缺点 1.如果创建过程中进行很多的运算，会导致类加载的时候特别的慢
 * 2.如果创建出来的实例要很久以后才被调用，那么会导致资源的浪费 3.如果初始化本身依赖于一些其他数据，那么也就很难保证其他数据会在它初始化之前准备好
 * 
 * 注意：在写静态域与静态代码块的时候，需要注意它们的顺序，顺序不一样，产生的结果可能会不一样
 * @author liangjing
 */
@ThreadSafe
public class SingletonExample6 {

	// 单例对象
	private static SingletonExample6 instance = null;

	// 私有构造函数
	private SingletonExample6() {

	}

	// 静态代码块
	static {
		instance = new SingletonExample6();
	}

	// 静态的工厂方法
	public static SingletonExample6 getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		System.out.println(getInstance().hashCode());
		System.out.println(getInstance().hashCode());
	}
}
