package de.codelake.unpweb.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2386735959652483289L;

	public EntityNotFoundException() {
		this("Entity not found.");
	}

	public EntityNotFoundException(final String message) {
		super(message);
	}
}
