package com.tensa.robotarm;

import com.tensa.robotarm.annotation.Injection;
import lombok.NonNull;
import lombok.val;

import java.lang.reflect.Field;
import java.util.*;

public class DependencyManagerImpl implements DependencyManager {
	
	protected final List<DependencyScanner> scanners = new LinkedList<>();
	
	@NonNull
	protected final DependencyPool dependencyPool;
	
	public DependencyManagerImpl(@NonNull final DependencyPool dependencyPool) {
		this.dependencyPool = dependencyPool;
	}
	
	public void initialize() {
		val dependencies = new LinkedList<DependencyScanner.ScanResult>();
		for (val scanner : scanners)
			dependencies.addAll(scanner.scan());
		
		dependencies.sort(Comparator.comparing(v -> ((DependencyScanner.ScanResult)v).getAnnotation().priority()).reversed());
		
		for (val dependencyInfo : dependencies) {
			val annotation = dependencyInfo.getAnnotation();
			final Class<?> registerClass = annotation.clazz().equals(Void.class) ? dependencyInfo.getClazz() : annotation.clazz();
			
			try {
				dependencyPool.register(dependencyInfo.getClazz().newInstance(), annotation.value(), registerClass);
			}
			catch (Exception e) {
				throw new IllegalStateException("Failed to create dependency instance.", e);
			}
		}
		
		for (val dependency : dependencyPool.getAllDependencies())
			inject(dependency.getObject());
	}
	
	public void addDependencyScanner(@NonNull final DependencyScanner scanner) {
		scanners.add(scanner);
	}
	
	public List<DependencyScanner> getScanners() {
		return Collections.unmodifiableList(scanners);
	}
	
	@Override
	public void inject(@NonNull final Object object) {
		for (val field : object.getClass().getDeclaredFields()) {
			val injectInfo = field.getAnnotation(Injection.class);
			if (injectInfo != null)
				injectDependency(object, field, injectInfo);
		}
	}
	
	@Override
	public @NonNull DependencyPool getDependencyPool() {
		return dependencyPool;
	}
	
	protected void injectDependency(final Object object, final Field field, final Injection injectInfo) {
		val name = injectInfo.value();
		final Class<?> clazz = injectInfo.clazz().equals(Void.class) ? field.getType() : injectInfo.clazz();
		
		val dep = dependencyPool.getDependency(name, clazz);
		
		// If dependency is required but not present.
		if (injectInfo.required() && !dep.isPresent())
			throw new DependencyUnsatisfiedException();
		
		dep.ifPresent(v -> {
			// If dependency casting not allowed, but type is not equals (= exact match)
			if (!injectInfo.allowCasting() && !Objects.equals(clazz, v.getClass())) {
				if (injectInfo.required())
					throw new DependencyUnsatisfiedException();
			}
			else
				setField(object, field, v);
		});
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
	
}
