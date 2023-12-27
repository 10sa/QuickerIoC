package com.tensa.robotarm;

import com.tensa.robotarm.annotation.Dependency;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Interface of dependency scanner.
 */
public interface DependencyScanner {
	
	/**
	 * Do scanning dependencies.
	 *
	 * @return Dependencies classes.
	 */
	List<ScanResult> scan();
	
	/**
	 * Dependency result data class.
	 */
	@Getter
	@ToString
	@EqualsAndHashCode
	@AllArgsConstructor
	public static class ScanResult {
		
		private Class<?> clazz;
		
		private Dependency annotation;
		
	}
	
}
