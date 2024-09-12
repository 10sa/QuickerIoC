package com.tensa.robotarm;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashSetDependencyPoolTest {
	
	@SneakyThrows
	@Test
	void registerTest() {
		val pool = new HashSetDependencyPool();
		
		val dependency1 = new Object();
		
		pool.register(dependency1, "", null);
		assertThat(pool.getDependency("", Object.class)).hasValue(dependency1);
		assertThat(pool.getDependency(Object.class)).hasValue(dependency1);
		pool.remove(dependency1.getClass());
		
		val dependency2 = new Object();
		pool.register(dependency2, "", null);
		assertThat(pool.getDependency("", Object.class)).hasValue(dependency2);
		assertThat(pool.getDependency(Object.class)).hasValue(dependency2);
	}
	
}