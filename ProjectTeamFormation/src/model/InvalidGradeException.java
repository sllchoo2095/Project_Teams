package model;

public class InvalidGradeException extends Exception{
	//Custom exception that will print out an error message. 
	//This exception is a subclass of excpetion 
	public  InvalidGradeException (String message) {
		super(message);
	}

}
