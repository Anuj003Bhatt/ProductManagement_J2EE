package org.anuj.projectmanage.propertiesinterface;

public class PropertyNotDefinedException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyNotDefinedException(){
		super("The property for the keu does not exist in the file");
	}
	
	public PropertyNotDefinedException(String message) {
		super(message);
	}
	
}
