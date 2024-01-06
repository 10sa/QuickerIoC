package com.tensa.robotarm;

import com.tensa.robotarm.annotation.Dependency;
import com.tensa.robotarm.annotation.Injection;
import lombok.SneakyThrows;
import lombok.val;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class DependencyManagerImplTest {
	
	DependencyManagerImpl manager;
	
	@BeforeEach
	void beforeEach() {
		manager = new DependencyManagerImpl(new HashSetDependencyPool());
	}
	
	@SneakyThrows
	@Test
	void initializeTest() {
		val dependency1 = new ByteBuddy()
			.subclass(Object.class)
			.annotateType(AnnotationDescription.Builder.ofType(Dependency.class).build())
			.make()
			.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
			.getLoaded();
		
		val dependency2 = new ByteBuddy()
			.subclass(Object.class)
			.annotateType(AnnotationDescription.Builder.ofType(Dependency.class)
				.define("clazz", Object.class)
				.define("value", "dependency2-name")
				.build())
			.make()
			.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
			.getLoaded();
		
		val injectionTarget = new ByteBuddy()
			.subclass(Object.class)
			.annotateType(AnnotationDescription.Builder.ofType(Dependency.class).build())
			.defineField("dependency1", dependency1, Modifier.PUBLIC)
			.annotateField(AnnotationDescription.Builder.ofType(Injection.class).build())
			.defineField("dependency2", Object.class, Modifier.PUBLIC)
			.annotateField(AnnotationDescription.Builder.ofType(Injection.class)
				.define("clazz", Object.class)
				.define("value", "dependency2-name")
				.build())
			.make()
			.load(getClass().getClassLoader())
			.getLoaded();
		
		manager.addDependencyScanner(() -> Arrays.asList(
			new DependencyScanner.ScanResult(dependency1, dependency1.getAnnotation(Dependency.class)),
			new DependencyScanner.ScanResult(dependency2, dependency2.getAnnotation(Dependency.class)),
			new DependencyScanner.ScanResult(injectionTarget, injectionTarget.getAnnotation(Dependency.class))
		));
		
		manager.initialize();
		
		val dependencies = manager.getDependencyPool().getDependencies(injectionTarget);
		assertEquals(1, dependencies.size());
		assertEquals(injectionTarget, dependencies.get(0).getClass());
		
		val dependency = dependencies.get(0);
		assertEquals(dependency1, dependency.getClass().getDeclaredField("dependency1").get(dependency).getClass());
		assertEquals(dependency2, dependency.getClass().getDeclaredField("dependency2").get(dependency).getClass());
	}
	
	@SneakyThrows
	@Test
	void injectTest() {
		val injectObject = new ByteBuddy()
			.subclass(Object.class)
			.defineField("dependency", Object.class, Modifier.PUBLIC)
			.annotateField(AnnotationDescription.Builder.ofType(Injection.class).build())
			.make()
			.load(this.getClass().getClassLoader())
			.getLoaded()
			.newInstance();
		
		val dependencyObject = new Object();
		manager.getDependencyPool().register(dependencyObject, "", Object.class);
		manager.inject(injectObject);
		
		assertEquals(
			dependencyObject,
			injectObject.getClass()
				.getDeclaredField("dependency")
				.get(injectObject)
		);
	}
	
	@SneakyThrows
	@Test
	void allowCastingInjectTest() {
		val injectObject = new ByteBuddy()
			.subclass(Object.class)
			.defineField("dependency", Object.class, Modifier.PUBLIC)
			.annotateField(AnnotationDescription.Builder.ofType(Injection.class)
				.define("allowCasting", false)
				.build())
			.make()
			.load(this.getClass().getClassLoader())
			.getLoaded()
			.newInstance();
		
		val dependencyObject = "string";
		manager.getDependencyPool().register(dependencyObject, "", Object.class);
		assertThrowsExactly(
			DependencyUnsatisfiedException.class,
			() -> manager.inject(injectObject)
		);
	}
	
}