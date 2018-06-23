package cn.jing.concurrency.example.immutable;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.jing.concurrency.annotations.NotThreadSafe;
import cn.jing.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * function：不可变对象
 * 
 * @author liangjing
 *
 */
@ThreadSafe
public class ImmutableExample2 {

	private static Map<Integer, Integer> map = Maps.newHashMap();

	static {
		map.put(1, 2);
		// Collections.unmodifiableMap 创建完以后不允许被修改
		// （源码）初始化的时候将传进来的map赋值给一个final类型的map，然后将所有会修改的方法直接抛出UnsupportedOperationException异常
		map = Collections.unmodifiableMap(map);
	}

	public static void main(String[] args) {
		// Exception in thread "main" java.lang.UnsupportedOperationException
		// at java.util.Collections$UnmodifiableMap.put(Collections.java:1457)
		// at
		// com.gwf.concurrency.example.immutable.ImmutableExample1.main(ImmutableExample1.java:21)
		map.put(1, 3);
	}
}
