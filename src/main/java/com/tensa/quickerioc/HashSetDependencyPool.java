package com.tensa.quickerioc;

import com.tensa.quickerioc.annotation.Injection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.val;

import java.lang.reflect.Field;
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
	public @NonNull Set<Object> getAllDependencies() {
		return Collections.unmodifiableSet(dependencies);
	}
	
	@Override
	public void injectDependencies(final Object object) {
		for (val field : object.getClass().getDeclaredFields()) {
			val injectInfo = field.getAnnotation(Injection.class);
			if (injectInfo != null)
				injectDependency(object, field, injectInfo);
		}
	}
	
	protected void injectDependency(final Object object, final Field field, final Injection injectInfo) {
		val name = injectInfo.value();
		final Class<?> clazz = injectInfo.clazz().equals(Void.class) ? field.getDeclaringClass() : injectInfo.clazz();
		
		val dep = getDependency(name, clazz);
		if (injectInfo.required() && !dep.isPresent())
			throw new DependencyUnsatisfiedException();
		
		dep.ifPresent((v -> setField(object, field, dep)));
	}
	
	private static void setField(final Object object, final Field field, final Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		}
		catch (Exception e) {
			throw new DependencyException(e);
		}
	}
	
	@Data
	@AllArgsConstructor
	protected static class Dependency {
		
		private String name;
		
		private Class<?> clazz;
		
		private Object object;
		
	}
	
}
