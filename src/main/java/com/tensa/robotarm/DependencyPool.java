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
	 * Returning dependency for specific class and empty name.
	 *
	 * @param clazz Class of dependency. Cannot be null.
	 * @param <T>   Type of dependency.
	 * @return A dependency of specific name and class. Can be not present.
	 */
	@NonNull <T> Optional<T> getDependency(@NonNull Class<T> clazz);
	
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
	Set<Dependency> getAllDependencies();
	
	/**
	 * Register object as dependency with specific name and class
	 *
	 * @param object Object to register dependency.
	 * @param name   Dependency's name.
	 * @param clazz  Dependency's class.
	 */
	void register(@NonNull Object object, String name, Class<?> clazz);
	
	/**
	 * Register object as dependency with specific name and object's class.
	 *
	 * @param object Object to register dependency.
	 * @param name   Dependency's name.
	 */
	void register(@NonNull Object object, String name);
	
	/**
	 * Register object as dependency with empty name and object's class.
	 *
	 * @param object Object to register dependency.
	 */
	void register(@NonNull Object object);
	
	/**
	 * Remove dependency from pool.
	 *
	 * @param name  Dependency's name.
	 * @param clazz Dependency's class.
	 */
	void remove(@NonNull String name, @NonNull Class<?> clazz);
	
	/**
	 * Remove dependency named empty string ("") from pool.
	 *
	 * @param clazz Dependency's class.
	 */
	void remove(@NonNull Class<?> clazz);
	
}
