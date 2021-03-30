package model;

public class UnpopularProjectException extends Exception {
	
	//To be implemented if someone attempts to make a team for a project that isn't in any of the members top 2 preferences. 

	public UnpopularProjectException(String message) {
		super(message);
	}
}
                    