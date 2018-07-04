package cn.jing.concurrency.example.threadLocal;

/**
 * function:利用ThreadLocal实例保存登录用户信息（ThreadLocal--线程封闭）
 * 
 * @author liangjing
 *
 */
public class RequestHolder {

	private final static ThreadLocal<Long> requestHolder = new ThreadLocal<Long>();

	/**
	 * function:添加数据 在filter里将登录用户信息存入ThreadLocal 如果不使用ThreadLocal，我们就需要将request一直透传（会使得我们的代码很冗余）
	 * 
	 * @param id
	 */
	public static void add(Long id) {
		// ThreadLocal
		// 内部维护一个ThreadLocalMap，key为当前的ThreadLocal实例，value为当前set的变量值（每一个线程都有自己独有的一个ThreadLocalMap实例对象）
		requestHolder.set(id);
	}

	/**
	 * function:获取数据
	 * 
	 * @return
	 */
	public static Long getId() {
		return requestHolder.get();
	}

	/**
	 * function:从ThreadLocalMap中移除掉当前的键值对信息 如果不移除，那么变量不会释放掉，会造成内存泄漏
	 * 在接口处理完以后进行处理（利用interceptor拦截器进行处理）
	 */
	public static void remove() {
		requestHolder.remove();
	}
}
