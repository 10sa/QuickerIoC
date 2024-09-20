package com.tensa.robotarm;

/**
 * Exception for about dependency.
 */
public class DependencyException extends RuntimeException {
	
	public DependencyException() {
	}
	
	public DependencyException(final String message) {
		super(message);
	}
	
	public DependencyException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public DependencyException(final Throwable cause) {
		super(cause);
	}
	
	public DependencyException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
