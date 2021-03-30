package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadMethods {
	
	//Class to lambda stream read methods
	

public Map<String,Company> readCompaniesFile(String fileName, Map<String,Company>companiesHash) throws IOException {
		
		Stream<String> rows =Files.lines(Paths.get(fileName));
		
		companiesHash=  rows.map(x->x.split(";"))
					.collect(Collectors.toMap(x->x[0], x-> new Company(x[0], x[1], Integer.parseInt(x[2]), x[3], x[4])));

			//rows.close();
		
		return companiesHash;
			
		}
	
	public  Map<String,Owner> readOwnersFile(String fileName, Map<String,Owner>ownersHash) throws IOException {
		
		Stream<String> rows =Files.lines(Paths.get(fileName));
		
		ownersHash=  rows.map(x->x.split(";"))
				.collect(Collectors.toMap(x->x[0], x-> new Owner(x[0], x[1], x[2], x[3], x[4],x[5])));

			//rows.close();
		return ownersHash;
			
		}
	

	public Map<String, Project>readProjectsFile(String fileName, Map<String, Project>projectsHash) throws IOException {
		
		Stream<String> rows =Files.lines(Paths.get(fileName));

		projectsHash=  rows.map(x->x.split(";"))
				.collect(Collectors.toMap(x->x[0], x-> new Project(x[0], x[1], x[2], x[3], Integer.parseInt(x[4]),Integer.parseInt(x[5]), Integer.parseInt(x[6]), Integer.parseInt(x[7]))));

			//rows.close();
		
		return projectsHash;
		
			
		}
	
public Map<String, Student> readStudentsFile(String fileName,Map<String, Student>studentsHash ) throws IOException {
		
		Stream<String> rows =Files.lines(Paths.get(fileName));
	
		

		studentsHash=  rows.map(x->x.split(" "))
				.collect(Collectors.toMap(x->x[0], 
						x-> new Student(x[0],x[1], x[2], x[3], Integer.parseInt(x[4]), x[5], Integer.parseInt(x[6]), x[7], Integer.parseInt(x[8]),x[9], Integer.parseInt(x[10]), x[11].charAt(0), x[12],x[13])));

		//rows.close();
	return studentsHash;
			
		}

public Map<String, Student>  readPreferences(String fileName,Map<String, Student>studentsHash) throws IOException {

	 BufferedReader reader = new BufferedReader(new FileReader(fileName));
	 Student stu=null;
	 boolean setPref= false; 
	 try {
	      
	        String line;
	        while ((line = reader.readLine()) != null) {
	         
	                String[] prefInfo = line.split(" ");
	                String studentID=prefInfo[0];
	                
	                String projID1= prefInfo[1];
	                int proj1Pref= Integer.parseInt(prefInfo[2]);
	                
	                String projID2= prefInfo[3];
	                int proj2Pref= Integer.parseInt(prefInfo[4]);
	                
	                String projID3= prefInfo[5];
	                int proj3Pref= Integer.parseInt(prefInfo[6]);
	                
	                String projID4= prefInfo[7];
	                int proj4Pref= Integer.parseInt(prefInfo[8]);
	                
	                if(studentsHash.containsKey(studentID)) {
	                	stu=studentsHash.get(studentID);
	                	
	                	stu.setPreferences(projID1, proj1Pref, projID2, proj2Pref, projID3, proj3Pref, projID4, proj4Pref);
	                	
	                }else {
	                	System.out.println(" This student does not exist in the data base");
	                }
	            
	           
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	 return studentsHash;
	 

	}



}
