package com.tensa.robotarm.annotation;

import java.lang.annotation.*;

/**
 * Annotation for designate class as dependency.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.TYPE
})
public @interface Dependency {
	
	/**
	 * Name of dependency.
	 *
	 * @return Name of dependency. Default is empty string.
	 */
	String value() default "";
	
	/**
	 * Priority of dependency.
	 *
	 * @return Priority of dependency.
	 */
	int priority() default 0;
	
	/**
	 * Type of dependency. If not specified or {@link Void} class presented, following annotated class's type.
	 *
	 * @return Type of dependency. If not specified, returns {@link Void} class.
	 */
	Class<?> clazz() default Void.class;
	
}
