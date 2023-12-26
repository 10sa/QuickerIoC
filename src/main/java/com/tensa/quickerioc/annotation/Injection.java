package com.tensa.quickerioc.annotation;

import com.tensa.quickerioc.DependencyUnsatisfiedException;

import java.lang.annotation.*;

/**
 * Annotation for dependency injection to specific field, constructor or method parameter.
 *
 * @author Kim Jae Hwan
 * @version 0.1.0
 * @since 0.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.TYPE,
	ElementType.PARAMETER,
	ElementType.FIELD
})
public @interface Injection {
	
	/**
	 * Name of the dependency to inject.
	 *
	 * @return Name of the dependency to inject. Default is empty string.
	 */
	String value() default "";
	
	/**
	 * Whether to injected if dependency type is not exact equals to specific type, but can be cast.
	 *
	 * @return Whether to injected if dependency type is not specific's type but can be cast. Default is true.
	 */
	boolean allowCasting() default true;
	
	/**
	 * Whether to throw {@link DependencyUnsatisfiedException} if dependency cannot be satisfied.
	 *
	 * @return Whether to throw {@link DependencyUnsatisfiedException} if dependency cannot be satisfied. Default is true.
	 */
	boolean required() default true;
	
	/**
	 * Type of dependency. If not specified or {@link Void} class presented, following target's declared type.
	 *
	 * @return Type of dependency. If not specified, returns {@link Void} class.
	 */
	Class<?> clazz() default Void.class;
	
}
