package de.tud.robotics.ur;

public class ParsingException extends Exception {

	private static final long serialVersionUID = -107283333844299081L;
	
	public ParsingException(String message,Exception e) {
		super(message,e);
	}

}
