package com.ybase.bas.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 实体库表<列-字段> 映射注解<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	String name();

	boolean key() default false;

	String type();
}
