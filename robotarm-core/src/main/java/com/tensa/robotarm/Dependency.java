package com.tensa.robotarm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dependency {
	
	private Object object;
	
	private String name;
	
	private Class<?> clazz;
	
}
