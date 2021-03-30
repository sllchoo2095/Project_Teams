package model;
import java.io.Serializable;
import java.util.HashMap;

public class Company implements Serializable {
	private static final long serialVersionUID = 1309293175594563625L;
	//To prevent the Invalid Class Exception
	//Docs.oracle.com. 2020. Serializable (Java Platform SE 7 ). 
	//[online] Available at: <https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html> [Accessed 16 October 2020].
	//This class holds all the company attributes. 
	private Project proj;
	private HashMap<String,Integer> skillRank = new HashMap<String,Integer>();
	private String companyID;
	private String companyName, URL,address;
	private int ABN_Number;
	
	public Company(String companyID,String companyName, int ABN_Number, String URL,String address) {
		this.companyID= companyID;
		this.companyName =companyName;
		this.ABN_Number = ABN_Number;
		this.URL = URL;
		this.address =address;
	}
	
	public Company(String companyName, int ABN_Number, String URL,String address) {
		
	
		this.companyName =companyName;
		this.ABN_Number = ABN_Number;
		this.URL = URL;
		this.address =address;
	}


	public String getCompanyID() {
		return companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public int getABN_Number() {
		return ABN_Number;
	}

	public String getURL() {
		return URL;
	}

	public String getAddress() {
		return address;
	}
	
	public String toString() {
		return this.getCompanyID()+ " "+ this.getCompanyName()+"  "+ this.getABN_Number()+" "+this.getURL()+" "+this.getAddress();
	}
	
	

	

}
