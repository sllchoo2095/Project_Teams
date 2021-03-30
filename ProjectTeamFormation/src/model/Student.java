package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Student implements Serializable{
	
	private static final long serialVersionUID = 1309293175594563625L;
	//To prevent the Invalid Class Exception
	//Docs.oracle.com. 2020. Serializable (Java Platform SE 7 ). 
	//[online] Available at: <https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html> [Accessed 16 October 2020].
	private String studentID, studFirstName,studSurname;
	private char personalityType;
	
	private Project proj;//using composition to access the project. 
	private HashMap<String,Integer>grades = new HashMap<String,Integer>();
	private HashMap<String,Integer>preferences = new HashMap<String,Integer>();
	private ArrayList<String> conflicts = new ArrayList<String>();


	public Student(String studentID) {
		this.studentID=studentID;
	}
	
	//Constructor if the student has a full information. 
	public Student(String studentID,String firstName, String lastName, String P, int pGrade,String N, int nGrade,String A, int aGrade, String W, int wGrade, char personality, String conflict1, String conflict2 ) {
		this.studentID=studentID;
		this.studFirstName =firstName;
		this.studSurname =lastName; 
		this.grades.put(P,pGrade);
		this.grades.put(N,nGrade);
		this.grades.put(A, aGrade);
		this.grades.put(W, wGrade);
		this.personalityType= personality;
		this.conflicts.add(conflict1);
		this.conflicts.add(conflict2);
		
	}
	
	public Student(String studentID,String firstName, String lastName, String P, int pGrade,String N, int nGrade,String A, int aGrade, String W, int wGrade, char personality, String conflict1) {
		this.studentID=studentID;
		this.studFirstName =firstName;
		this.studSurname =lastName; 
		this.grades.put(P,pGrade);
		this.grades.put(N,nGrade);
		this.grades.put(A, aGrade);
		this.grades.put(W, wGrade);
		this.personalityType= personality;
		this.conflicts.add(conflict1);
		this.conflicts.add(null);//If there is only 1 conflict
		
	}
	

	public Student(String studentID,String firstName, String lastName, String P, int pGrade,String N, int nGrade,String A, int aGrade, String W, int wGrade, char personality ) {
		this.studentID=studentID;
		this.studFirstName =firstName;
		this.studSurname =lastName; 
		this.grades.put(P,pGrade);
		this.grades.put(N,nGrade);
		this.grades.put(A, aGrade);
		this.grades.put(W, wGrade);
		this.personalityType= personality;
		this.conflicts.add(null);
		this.conflicts.add(null);// When the student doesn't have any conflicts. 
		
	}

	public Student(String studentID, String P, int pGrade,String N, int nGrade,String A, int aGrade, String W, int wGrade, char personality, String conflict1, String conflict2 ) {
		this.studentID=studentID;
		
		this.grades.put(P,pGrade);
		this.grades.put(N,nGrade);
		this.grades.put(A, aGrade);
		this.grades.put(W, wGrade);
		this.personalityType= personality;
		this.conflicts.add(conflict1);
		this.conflicts.add(conflict2);
		
	}
	
	public Student(String studentID,String firstName, String surname, String P, int pGrade,String N, int nGrade,String A, int aGrade, String W, int wGrade ) {
		this.studentID=studentID;
		this.studFirstName=firstName;
		this.studSurname = surname;
		this.grades.put(P,pGrade);
		this.grades.put(N,nGrade);
		this.grades.put(A, aGrade);
		this.grades.put(W, wGrade);
		
	}
	
	//To set the preferences for each student 

	public Student(String studentID, String projID1,int proj1,String projID2,int proj2,String projID3,int proj3, String projID4,int proj4) {
		this.studentID=studentID;
		this.preferences.put(projID1, proj1);
		this.preferences.put(projID2, proj2);
		this.preferences.put(projID3, proj3);
		this.preferences.put(projID4, proj4);
	}


	public String getStudFirstName() {
		return studFirstName;
	}
	

	public char getPersonalityType() {
		return personalityType;
	}
	
	public char setPersonalityType(char personality) {
		return this.personalityType=personality;
	}

	public String getStudSurname() {
		return studSurname;
	}

	public String getStudentID() {
		return studentID;
	}
	
	@Override
	public String toString() {
		return this.getStudentID()+" "+this.studFirstName+" "+this.studSurname+" "+ mapToString(this.getGrades())+ " "+this.getPersonalityType()+ " "+this.conflictsToString(this.getConflicts())+ " "+ mapToString(this.getPreferences());
	}


	public HashMap<String,Integer> getPreferences() {
		return preferences;
	}

	public void setPreferences(HashMap<String,Integer> preferences) {
		this.preferences = preferences;
	}
	

	public void setPreferences(String projID1,int proj1,String projID2,int proj2,String projID3,int proj3, String projID4,int proj4) {
		this.preferences.put(projID1,proj1);
		this.preferences.put(projID2,proj2);
		this.preferences.put(projID3,proj3);
		this.preferences.put(projID4,proj4);
		
	}

	public ArrayList<String> getConflicts() {
		return conflicts;
	}


	public void addConflicts(String conflicts) {
		this.conflicts.add(conflicts);
	}
	
	//Array list to String
	//Dotnetperls.com. 2020. Java Convert Arraylist To String. [online] 
	//Available at: <https://www.dotnetperls.com/convert-arraylist-string-java> [Accessed 10 August 2020].
	//printing the ArrayList of conflicts without the [] and ,
	public String conflictsToString(ArrayList<String> conflicts) {
		
		return String.join(" ",conflicts);
		
	}
	
	//method?, H., Filotto, N., ツ, Φ. and Turner, A., 2020. How To Override = Symbol While Converting Hashmap To String Using Tostring Method?. 
	//[online] Stack Overflow. Available at: <https://stackoverflow.com/questions/39896042/how-to-override-symbol-while-converting-hashmap-to-string-using-tostring-methob> 
	//[Accessed 15 October 2020].
	//Printing a string integer map  without the {} and =
	public   <K, V> String mapToString(HashMap<String, Integer> map) {
		
		return map.entrySet()
		        .stream()
		        .map(entry -> entry.getKey() + " " + entry.getValue())
		        .collect(Collectors.joining(" ", " ", " "));
	}

	public HashMap<String,Integer> getGrades() {
		return grades;
	}

	public void setGrades(HashMap<String,Integer> grades) {
		this.grades = grades;
	}
	
	public boolean checkStudentConflicts(String StudentID)  {

		boolean conflict=false;
		
			if(this.getConflicts().contains(StudentID)) {
	
				conflict=true;
			}else {
				System.out.println("Student "+ this.getStudentID() + " has conflict with "+ StudentID);
				conflict=false;
			}
	
		return conflict;
	}
	
	//checking if the project is within student's project preferences 
	public boolean top2Pref(String projID) {
		boolean top2Pref=false;
		if(this.preferences.containsKey(projID)) {
			
			if(this.preferences.get(projID) > 2) {
				top2Pref=true;
			}else {
				top2Pref=false;
			}
		}else {
			
			top2Pref =false;
		}
		return top2Pref;
	}

	public Project getProj() {
		return proj;
	}

	public void setProj(Project proj) {
		this.proj = proj;
	}
	
	
}
