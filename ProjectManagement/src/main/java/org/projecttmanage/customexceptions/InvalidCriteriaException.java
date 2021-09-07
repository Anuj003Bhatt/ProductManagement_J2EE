package org.projecttmanage.customexceptions;

public class InvalidCriteriaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidCriteriaException() {
		super("Invalid Criteria is been formed please check the ViewCriteria agin");
	}
	public InvalidCriteriaException(String message) {
		super(message);
	}
}
