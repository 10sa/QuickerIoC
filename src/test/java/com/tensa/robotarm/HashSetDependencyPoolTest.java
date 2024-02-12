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
		
		pool.register(dependency1, "", Void.class);
		assertThat(pool.getDependency("", Object.class)).isEqualTo(dependency1);
		pool.remove(Void.class);
		
		val dependency2 = new Object();
		pool.register(dependency2, "", Void.class);
		assertThat(pool.getDependency("", Object.class)).isEqualTo(dependency2);
	}
	
}