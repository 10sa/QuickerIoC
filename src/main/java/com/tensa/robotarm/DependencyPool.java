package com.tensa.robotarm;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A dependency management pool.
 *
 * @author Kim Jae Hwan
 * @version 0.1.0
 * @since 0.1.0
 */
public interface DependencyPool {
	
	/**
	 * Returning dependency for specific name and class.
	 *
	 * @param name  Name of dependency.
	 * @param clazz Class of dependency. Cannot be null.
	 * @param <T>   Type of dependency.
	 * @return A dependency of specific name and class. Can be not present.
	 */
	@NonNull <T> Optional<T> getDependency(@NonNull String name, @NonNull Class<T> clazz);
	
	/**
	 * Returning all dependencies for specific class.
	 *
	 * @param clazz Class of dependencies.
	 * @param <T>   Type of dependencies.
	 * @return A dependencies of specific class. Cannot be null.
	 */
	@NonNull <T> List<T> getDependencies(@NonNull Class<T> clazz);
	
	/**
	 * Returning all dependencies of this pool.
	 *
	 * @return All dependencies of this pool.
	 */
	@NonNull
	Set<Object> getAllDependencies();
	
	/**
	 * Register object as dependency with specific name and class
	 *
	 * @param object Object to register dependency.
	 * @param name   Dependency's name.
	 * @param clazz  Dependency's class.
	 */
	void register(@NonNull Object object, @NonNull String name, @NonNull Class<?> clazz);
	
}
