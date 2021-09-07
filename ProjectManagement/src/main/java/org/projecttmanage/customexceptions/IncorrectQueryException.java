package org.projecttmanage.customexceptions;

public class IncorrectQueryException extends Exception{

	private static final long serialVersionUID = 1L;
	public IncorrectQueryException() {
		super("Incorrect Query clauses have been given and hence the correct query cannot be formed");
	}
}
