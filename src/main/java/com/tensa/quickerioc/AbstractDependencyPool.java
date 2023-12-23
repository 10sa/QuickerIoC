package com.tensa.quickerioc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The abstract implementation of {@link DependencyPool} interface.
 */
public abstract class AbstractDependencyPool implements DependencyPool {
	
	protected final Set<Dependency> dependencies;
	
	protected AbstractDependencyPool(@NonNull final Set<Dependency> dependencySet) {
		this.dependencies = dependencySet;
	}
	
	protected AbstractDependencyPool(final int initSize) {
		this.dependencies = new HashSet<>(initSize);
	}
	
	protected AbstractDependencyPool() {
		this.dependencies = new HashSet<>();
	}
	
	@Override
	public @NonNull <T> Optional<T> getDependency(@NonNull final String name, @NonNull final Class<T> clazz) {
		return dependencies.stream()
			.filter(v -> Objects.equals(name, v.getName()))
			.filter(v -> Objects.equals(clazz, v.getClazz()))
			.map(v -> clazz.cast(v.getObject()))
			.findFirst();
	}
	
	@Override
	public @NonNull <T> List<T> getDependencies(@NonNull final Class<T> clazz) {
		return dependencies.stream()
			.filter(v -> Objects.equals(clazz, v.getClazz()))
			.map(v -> clazz.cast(v.getObject()))
			.collect(Collectors.toList());
	}
	
	@Override
	public @NonNull Set<Object> getAllDependencies() {
		return Collections.unmodifiableSet(dependencies);
	}
	
	@Data
	@AllArgsConstructor
	protected static class Dependency {
		
		private String name;
		
		private Class<?> clazz;
		
		private Object object;
		
	}
	
}
