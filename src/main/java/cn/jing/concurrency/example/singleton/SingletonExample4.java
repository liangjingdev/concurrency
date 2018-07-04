package cn.jing.concurrency.example.singleton;

import static org.mockito.Mockito.RETURNS_MOCKS;

import cn.jing.concurrency.annotations.NotRecommend;
import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.Recommend;
import cn.jing.concurrency.annotations.ThreadSafe;

/**
 * function:懒汉模式(双重同步锁单例模式)
 * 单例的实例在第一次使用的时候进行创建
 * 注意：该单例构造模式是非线程安全的，因为构造一个对象其实是需要执行三个步骤的(1、memory=allocate 分配对象的内存空间 2、ctorInstance() 初始化对象
 * 3、instance=memory 设置instance指向刚分配的内存)，执行完这三步之后，才算真正的完成该对象的构建(instance不为null)，在单线程的情况下，以下这种写法
 * 是没有问题的，但是在多线程的情况下，需要考虑到指令重排序的问题(JVM和cpu优化，发生了指令重排)，如果发生了指令重排的话，那么上面的三个步骤就不一定是按照
 * 顺序来执行的了，比如变成了是1、3、2这种顺序的话，那么假如此时线程A正在执行3这个步骤，而线程B正在执行第一个if语句的判断，此时的判断是instance不为空的
 * (实际中instance还是为空的，由于重排序才导致了这样的假象出现)，那么就会立即return instance(此时的对象是并没有初始化完成的额，即线程B拿到的是还没有
 * 进行初始化的对象,若被调用的话就会出现问题)。
 * 
 * @author liangjing
 *
 */
@NotThreadSafe
@NotRecommend
public class SingletonExample4 {

	// 私有构造函数
	private SingletonExample4() {

	}

	// 单例对象
	private static SingletonExample4 instance = null;

	// 静态的工厂方法
	public static SingletonExample4 getInstance() {
		// 双重检测机制
		if (instance == null) { // B
			// 同步锁
			synchronized (SingletonExample4.class) {
				if (instance == null) {
					instance = new SingletonExample4(); // A -3
				}
			}
		}
		return instance;
	}
}
