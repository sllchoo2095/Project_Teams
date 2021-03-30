package model;
import java.io.Serializable;

public class Owner implements Serializable{
	private static final long serialVersionUID = 1309293175594563625L;
	//To prevent the Invalid Class Exception
	//Docs.oracle.com. 2020. Serializable (Java Platform SE 7 ). 
	//[online] Available at: <https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html> [Accessed 16 October 2020].
	//Holds all of the attributes and methods for Owners
	private String ownerID,companyID; 
	private String ownFirstName, ownSurname, role,email;
	
	public Owner(String ownerID, String ownFirstName,String ownSurname,String role, String email,String companyID){
		this.ownFirstName=ownFirstName;
		this.ownSurname=ownSurname;
		this.ownerID=ownerID;
		this.role =role;
		this.email=email;
		this.companyID=companyID;
		
	}

	public String getOwnerID() {
		return ownerID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public String getOwnFirstName() {
		return ownFirstName;
	}

	public String getOwnSurname() {
		return ownSurname;
	}

	public String getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return this.getCompanyID()+";"+ this.getCompanyID()+";"+ this.getOwnFirstName()+";"+this.getOwnSurname()+";"+ this.getRole()+";"+this.getEmail();
		}
	
	
	

}
