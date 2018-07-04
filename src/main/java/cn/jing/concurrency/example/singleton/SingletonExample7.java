package cn.jing.concurrency.example.singleton;

import cn.jing.concurrency.annotations.Recommend;
import cn.jing.concurrency.annotations.ThreadSafe;

/**
 * function:改造后的饿汉模式 枚举类型的单例，最安全最简单(使用枚举类来构建单例，最安全最简单)
 * 
 * @author liangjing
 */
@ThreadSafe
// 为什么比较推荐呢？是因为相比于懒汉模式，在安全性方面更加有保障，其次相比于之前的饿汉模式，可以是在实际调用的时候才进行对应的初始化操作，不会造成资源的浪费
@Recommend
public class SingletonExample7 {

	// 私有构造函数
	private SingletonExample7() {

	}

	// 静态的工厂方法
	public static SingletonExample7 getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	public static void main(String[] args) {
		System.out.println(getInstance().hashCode());
		System.out.println(getInstance().hashCode());
	}

	private enum Singleton {
		INSTANCE;

		private SingletonExample7 singleton;

		// 该构造函数是由JVM来保证绝对只会调用一次
		private Singleton() {
			singleton = new SingletonExample7();
		}

		public SingletonExample7 getInstance() {
			return singleton;
		}
	}
}
