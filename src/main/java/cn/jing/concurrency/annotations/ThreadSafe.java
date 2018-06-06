package cn.jing.concurrency.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * function:为了更好的更方便的理解项目中的代码，可以定义一些自己的注解。
 * 
 * @author liangjing
 *
 */
// 用来标记线程安全的类或者写法
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadSafe {
	String value() default "";
}
