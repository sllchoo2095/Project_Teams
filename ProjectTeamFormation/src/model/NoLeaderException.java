package model;


public class NoLeaderException extends Exception {

	String message= "There is no leader (personality type A) in the team.";
	public  NoLeaderException(String message) {
		super(message);
		
	}
}
