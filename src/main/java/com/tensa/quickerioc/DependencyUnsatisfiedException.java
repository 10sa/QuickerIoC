package com.tensa.quickerioc;

/**
 * Exception for state of dependency unsatisfied.
 */
public class DependencyUnsatisfiedException extends DependencyException {
	
	public DependencyUnsatisfiedException() {
		super();
	}
	
	public DependencyUnsatisfiedException(final String message) {
		super(message);
	}
	
	public DependencyUnsatisfiedException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public DependencyUnsatisfiedException(final Throwable cause) {
		super(cause);
	}
	
	public DependencyUnsatisfiedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
