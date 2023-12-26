package com.tensa.quickerioc;

import com.tensa.quickerioc.annotation.Dependency;
import lombok.NonNull;

public interface DependencyInjector {
	
	/**
	 * Inject dependency to specified object's {@link Dependency} annotated fields.
	 *
	 * @param object Object to inject dependencies. Cannot be null.
	 */
	void inject(@NonNull Object object);
	
	/**
	 * Returns instance's {@link DependencyPool} object.
	 *
	 * @return Instance's {@link DependencyPool} object.
	 */
	@NonNull
	DependencyPool getDependencyPool();
	
}