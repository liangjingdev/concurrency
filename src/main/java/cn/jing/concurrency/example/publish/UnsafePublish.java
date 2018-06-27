package cn.jing.concurrency.example.publish;

import java.util.Arrays;

import cn.jing.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function:不安全的发布对象
 * 
 * @author liangjing
 *
 */
@Slf4j
@NotThreadSafe
public class UnsafePublish {

	private String[] states = { "a", "b", "c" };

	/**
	 * function:通过public访问级别发布了类的域，在类的外部，任何线程都可以访问这个域
	 * 这样是不安全的，因为我们无法检查其他线程是否会去修改这个域从而导致状态的错误
	 * 
	 * @return
	 */
	public String[] getStates() {
		return states;
	}

	public static void main(String[] args) {
		UnsafePublish unsafePublish = new UnsafePublish();
		log.info("{}", Arrays.toString(unsafePublish.getStates()));

		unsafePublish.getStates()[0] = "d";
		log.info("{}", Arrays.toString(unsafePublish.getStates()));

	}
}
