package com.tensa.robotarm.annotation;

import java.lang.annotation.*;

/**
 * Annotation for designate class as dependency.
 *
 * @author Kim Jae Hwan
 * @version 0.1.0
 * @since 0.1.0
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
	
}
