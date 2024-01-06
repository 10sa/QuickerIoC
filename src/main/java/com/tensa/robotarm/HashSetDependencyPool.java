package com.tensa.robotarm;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@link HashSet} based {@link DependencyPool} interface implementation.
 */
public class HashSetDependencyPool implements DependencyPool {
	
	protected final Set<Dependency> dependencies;
	
	public HashSetDependencyPool(final int initSize) {
		this.dependencies = new HashSet<>(initSize);
	}
	
	public HashSetDependencyPool() {
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
	public @NonNull Set<Dependency> getAllDependencies() {
		return Collections.unmodifiableSet(dependencies);
	}
	
	@Override
	public void register(@NonNull final Object object, @NonNull final String name, @NonNull final Class<?> clazz) {
		dependencies.add(new Dependency(object, name, clazz));
	}
	
	@Override
	public void remove(@NonNull final String name, @NonNull final Class<?> clazz) {
		dependencies.removeIf(v -> Objects.equals(v.getName(), name) && Objects.equals(v.getClazz(), clazz));
	}
	
	@Override
	public void remove(@NonNull final Class<?> clazz) {
		dependencies.removeIf(v -> Objects.equals(v.getName(), "") && Objects.equals(v.getClazz(), clazz));
	}
	
}
