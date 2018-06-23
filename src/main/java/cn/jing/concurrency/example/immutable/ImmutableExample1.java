package cn.jing.concurrency.example.immutable;

import java.util.Map;

import com.google.common.collect.Maps;

import cn.jing.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function：不可变对象
 * 
 * @author liangjing
 *
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {

	private final static Integer a = 1;
	private final static String b = "2";
	// final关键字修饰的引用类型变量（初始化之后就不能再修改其引用，但引用的对象是可以修改的）
	private final static Map<Integer, Integer> map = Maps.newHashMap();

	static {
		map.put(1, 2);
		map.put(3, 4);
		map.put(5, 6);
	}

	public static void main(String[] args) {
		// a = 2; ❌
		// b = "3"; ❌
		// map = Maps.newHashMap(); ❌
		map.put(1, 3);
		log.info("{}", map.get(1));
	}
}
