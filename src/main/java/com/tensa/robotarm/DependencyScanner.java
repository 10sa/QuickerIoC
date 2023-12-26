package com.tensa.robotarm;

import java.util.List;

/**
 * Interface of dependency scanner.
 *
 * @author Kim Jae Hwan
 * @version 0.1.0
 * @since 0.1.0
 */
public interface DependencyScanner {
	
	/**
	 * Do scanning dependencies.
	 *
	 * @return Dependencies classes.
	 */
	List<Class<?>> scan();
	
}
