package com.tensa.quickerioc;

import java.util.List;
import java.util.Optional;

/**
 * A dependency management pool.
 *
 * @author Kim Jae Hwan
 * @version 0.1.0
 * @since 0.1.0
 */
public interface DependencyPool {
	
	/**
	 * Initialize dependency pool.
	 */
	void initialize();
	
	/**
	 * Inject dependency to specific object.
	 *
	 * @param object Target for inject dependencies.
	 */
	void injectDependencies(Object object);
	
	/**
	 * Returning dependency for specific name and class.
	 *
	 * @param name  Name of dependency.
	 * @param clazz Class of dependency.
	 * @param <T>   Type of dependency.
	 * @return A dependency of specific name and class. Can be not present.
	 */
	<T> Optional<T> getDependency(String name, Class<T> clazz);
	
	/**
	 * Returning all dependencies for specific class.
	 *
	 * @param clazz Class of dependencies.
	 * @param <T>   Type of dependencies.
	 * @return A dependencies of specific class. Cannot be null.
	 */
	<T> List<T> getDependencies(Class<T> clazz);
	
	/**
	 * Returning all dependencies of this pool.
	 *
	 * @return All dependencies of this pool.
	 */
	List<Object> getAllDependencies();
	
}
