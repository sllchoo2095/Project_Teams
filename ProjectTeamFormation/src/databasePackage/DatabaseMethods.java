package databasePackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import model.*;
import controller.*;

//Based on code shared in Vu Mai's tutorials for JDBC. 
//https://stackoverflow.com/questions/20150880/insert-a-hashmap-into-any-database-table
public class DatabaseMethods {
	
	private static String TABLE_NAME= null; 
	private String inputTableName =null;
	
	private final String DB_NAME = "milestone4DB.db";

public void createConnection (String DB_NAME) throws SQLException, ClassNotFoundException{
	try (Connection con = getConnection(DB_NAME)) {
		
		System.out.println("Connection to database " + DB_NAME + " created successfully");
		
	} catch (Exception e) {
		
		System.out.println(e.getMessage());
	}

}

public Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {

String url = "jdbc:sqlite:database/"+dbName;

Connection con = DriverManager.getConnection(url);

		
return con;
}
	
public boolean tableExistence(String inputTableName) {
	boolean exists= false;

	try (Connection con = getConnection(DB_NAME)) {
	
		TABLE_NAME=setTableName(inputTableName);
		
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
		
		if(tables != null) {
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + " exists.");
				exists=true;
			}
			else {
				System.out.println("Table " + TABLE_NAME + " does not exist.");
				exists= false;
			}	
			tables.close();
		} else {
			System.out.println(("Problem with retrieving database metadata"));
			exists=false;
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
		exists=  false;
	}
	return exists;
}



	public String setTableName(String inputTableName) {
		if(inputTableName.contentEquals("STUDENT")) {
			
			return "STUDENT";
		}else if(inputTableName.contentEquals("PROJECT")) {
			return "PROJECT";
		}else if(inputTableName.contentEquals("TEAMS") ) {
			return "TEAMS";
		}else {
			return null;
		}
		
	}
	
	public void makeTables() throws SQLException, ClassNotFoundException{
		
		makeTable("STUDENT",this.DB_NAME );
		makeTable("PROJECT",this.DB_NAME);
	
	}
	
	
	
	public void makeTable(String inputTableName, String dbname)throws SQLException {
		
		TABLE_NAME=setTableName(inputTableName).toUpperCase();
		
		try (Connection con = getConnection(dbname);
				Statement stmt = con.createStatement();
		) {
			//int result=0;
			
			TABLE_NAME = setTableName(inputTableName).toUpperCase();
			
			
			if (tableExistence(TABLE_NAME)==true) {
				
				deleteTables(TABLE_NAME);
				
			}else {
				}
				
				
				if(TABLE_NAME.contentEquals("STUDENT")){
		
					stmt.execute("CREATE TABLE " + TABLE_NAME + " ("
							+ "Student_ID VARCHAR(4) NOT NULL,"
							+ "Student_First_Name VARCHAR(30) NOT NULL,"
							+ "Student_Surname VARCHAR(30) NOT NULL,"
							+ "Grades VARCHAR(20) NOT NULL," 
							+ "Personality_Type CHAR,"
							+ "Conflicts VARCHAR(10)," 
							+ "Project_Preferences VARCHAR(40),"
							+ "PRIMARY KEY (Student_ID))");
					
					System.out.println("STUDENT table succressfully created");
					
				} else if(TABLE_NAME.contentEquals("PROJECT") ) {
					
					stmt.execute("CREATE TABLE " + TABLE_NAME + " ("
							+ "Project_ID VARCHAR(4) NOT NULL,"
							+ "Owner_ID VARCHAR(4) NOT NULL," 
							+ "Title VARCHAR(100) NOT NULL,"
							+ "Description VARCHAR(300) NOT NULL,"
							+ "Technical_Requirements VARCHAR(30) NOT NULL,"
							+ "Members VARCHAR(100)," 
							+ "Average_Skill_Competency FLOAT  NOT NULL,"
							+ "Top_2_Preferences FLOAT NOT NULL,"
							+ "Skills_Gap FLOAT NOT NULL,"
							+ "PRIMARY KEY (Project_ID))");
					
				System.out.println("PROJECT table succressfully created");
					
				} 
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		
		}
		
	}
	
	public void deleteTables(String table) {
		TABLE_NAME=setTableName(inputTableName).toUpperCase().trim();
		
		try (Connection con = getConnection(this.DB_NAME);
				Statement stmt = con.createStatement();
		) {
			int result = stmt.executeUpdate("DROP TABLE "+TABLE_NAME);
			
			if(result == 0) {
				System.out.println("Table " + TABLE_NAME + " has been deleted successfully");
			} else {
				System.out.println("Table " + TABLE_NAME + " was not deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
public void addStudentRows(Map<String, Student>studentsHash,String DB_NAME) {
	TABLE_NAME="STUDENT";
	ProjectTeamFormationController controller= new ProjectTeamFormationController();
	
	try (Connection con = getConnection(DB_NAME);
			
			Statement stmt = con.createStatement();
			
	) {

		
	int count= 0;

	for(Student stu: studentsHash.values()) {
		stmt.executeUpdate("INSERT INTO STUDENT (Student_ID,Student_First_Name,Student_Surname,Grades,Personality_Type, Conflicts, Project_Preferences)"
                + "VALUES('" + stu.getStudentID()
                + "','" + stu.getStudFirstName()
                + "','" + stu.getStudSurname()
                + "','" + stu.mapToString(stu.getGrades())
                + "','" + stu.getPersonalityType()
                + "','" + stu.getConflicts()
                + "','" + stu.mapToString(stu.getPreferences()) + "');");
		count++;
	}

		System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
		
		System.out.println(count + " row(s) affected");

	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	
}

public void addProjectRows(Map<String, Project>projectsHash,String DB_NAME) {
	TABLE_NAME="PROJECT";
	
	try (Connection con = getConnection(DB_NAME);	
			
			Statement stmt = con.createStatement();	
	) {
	
		int count = 0;
		
		for (Project proj : projectsHash.values()) {
			stmt.executeUpdate("insert into PROJECT (Project_ID,Owner_ID,Title, Description, Technical_Requirements,Members,Average_Skill_Competency, Top_2_Preferences, Skills_Gap)"
	                + "VALUES('" + proj.getProjID()
	                + "','" + proj.getOwnerID()
	                + "','" + proj.getTitle()
	                + "','" + proj.getDescription()
	                + "','" + proj.mapToString(proj.getTechSkills()) 
	                + "','" + proj.membersToString(proj.getMembers())
	                + "','" + String.valueOf(proj.totalAverageSkillCompetency())
	                + "','" + String.valueOf(proj.calcPrefPercentage())
	                + "','" + String.valueOf(proj.totalSkillShortFall()) + "');");
			count++;
		}
		System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
		System.out.println(count + " row(s) affected");

	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
}
///i CANNOT HEAR YOU 


public void studentSQLStatements(Map<String, Student>studentsHash){
	//ArrayList<String> studentsInput =new <String> ArrayList();
	
	try (Connection con = getConnection(this.DB_NAME);
			Statement stmt = con.createStatement();	) {
		
		for(Student stu: studentsHash.values()) {
			
			String query=("REPLACE INTO "+ "STUDENT (Student_ID,Student_First_Name,Student_Surname,Grades,Personality_Type, Conflicts, Project_Preferences)"
	                + "VALUES('" + stu.getStudentID()
	                + "','" + stu.getStudFirstName()
	                + "','" + stu.getStudSurname()
	                + "','" + stu.mapToString(stu.getGrades())
	                + "','" + stu.getPersonalityType()
	                + "','" + stu.getConflicts()
	                + "','" + stu.mapToString(stu.getPreferences()) + "');");
			//studentsInput.add(query);
		stmt.executeUpdate(query);
		}
		System.out.println("Student insert statements");
	
		
	} catch(Exception e) {
		System.out.println(e.getMessage());
	
	}	
	
}

public void projectsSQLStatements(Map<String, Project>projectsHash){
	//ArrayList<String> studentsInput =new <String> ArrayList();
	
	try (Connection con = getConnection(this.DB_NAME);
			Statement stmt = con.createStatement();	) {
		
		for(Project proj: projectsHash.values()) {
			
			String query=("REPLACE INTO "+  "PROJECT (Project_ID,Owner_ID,Title, Description, Technical_Requirements,Members,Average_Skill_Competency, Top_2_Preferences, Skills_Gap)"
	                + "VALUES('" + proj.getProjID()
	                + "','" + proj.getOwnerID()
	                + "','" + proj.getTitle()
	                + "','" + proj.getDescription()
	                + "','" + proj.mapToString(proj.getTechSkills()) 
	                + "','" + proj.membersToString(proj.getMembers())
	                + "','" + String.valueOf(proj.totalAverageSkillCompetency())
	                + "','" + String.valueOf(proj.calcPrefPercentage())
	                + "','" + String.valueOf(proj.totalSkillShortFall()) + "');");
		
		stmt.executeUpdate(query);
		}
		System.out.println("PROJECTS  insert statements");
	
		
	} catch(Exception e) {
		System.out.println(e.getMessage());
	
	}	
	
}




}
