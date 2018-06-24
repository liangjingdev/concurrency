package cn.jing.concurrency.example.immutable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
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
public class ImmutableExample3 {

	private final static List<Integer> list = ImmutableList.of(1, 2, 3);
	// private final static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
	private final static ImmutableSet set = ImmutableSet.copyOf(list);
	// 注意：奇数位参数为key，偶数位参数为value
	private final static ImmutableMap map1 = ImmutableMap.of(1, 2, 3, 5);

	private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder().put(1, 2)
			.put(3, 4).build();

	public static void main(String[] args) {
		// 执行以下代码都会抛出UnsupportedOperationException异常(即表示不允许被修改)
		// 使用ImmutableXXX声明的对象的话会直接在编译的时候就告诉你这个方法已经被废弃
		list.add(5);
		set.add(6);
		map1.put(1, 2);
		map2.put(3, 4);
	}
}
