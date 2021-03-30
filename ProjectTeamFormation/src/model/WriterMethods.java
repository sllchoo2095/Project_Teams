package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WriterMethods{
	
	 
	 public void writeOwners(Map<String,Owner> owners) {
			
		 try {
				String currentDirectory = System.getProperty("user.dir");
				PrintWriter pr = new PrintWriter(
						new BufferedWriter(new FileWriter(currentDirectory + "/owners.txt", true)));
				
				Iterator<Entry<String, Owner>> it = owners.entrySet().iterator();
				while (it.hasNext() ) {

			        // the key/value pair is stored here in pairs
			        Entry<String, Owner> pairs = it.next();
			        System.out.println("Value is " + pairs.getValue().toString());

			  
			        pr.write(pairs.getValue().toString() + "\n");
				}
				
				
				pr.close();
				System.out.println("Successfully wrote owners.txt");
			} catch (IOException e) {
				System.out.println(" An error occurred.");
				e.printStackTrace();
			}
	 }
	 
	 public void writeCompanies(Map<String,Company> companies) {
			
		 try {
				String currentDirectory = System.getProperty("user.dir");
				PrintWriter pr = new PrintWriter(
						new BufferedWriter(new FileWriter(currentDirectory + "/companies.txt", true)));
				
				Iterator<Entry<String, Company>> it = companies.entrySet().iterator();
				while (it.hasNext() ) {

			        // the key/value pair is stored here in pairs
			        Entry<String, Company> pairs = it.next();
			        System.out.println("Value is " + pairs.getValue());

			        // since you only want the value, we only care about pairs.getValue(), which is written to out
			        pr.write(pairs.getValue() + "\n");
				}
				
				pr.close();
				System.out.println("Successfully wrote companies.txt");
			} catch (IOException e) {
				System.out.println(" An error occurred.");
				e.printStackTrace();
			}
	 }
	 
	 public void writeProjects(Map<String, Project> projects) {
			
		 try {
				String currentDirectory = System.getProperty("user.dir");
				PrintWriter pr = new PrintWriter(
						new BufferedWriter(new FileWriter(currentDirectory + "/projects.txt", true)));
				
				Iterator<Entry<String, Project>> it = projects.entrySet().iterator();
				while (it.hasNext() ) {

			  
			        Entry<String, Project> pairs = it.next();
			        System.out.println("Value is " + pairs.getValue());

			  
			        pr.write(pairs.getValue() + "\n");
				}
				
				pr.close();
				System.out.println("Successfully wrote projects.txt");
			} catch (IOException e) {
				System.out.println(" An error occurred.");
				e.printStackTrace();
			}
		 
	 }
	 
	 public void writeStudents(Map<String, Student> students) {
			
		 try {
				String currentDirectory = System.getProperty("user.dir");
				PrintWriter pr = new PrintWriter(
						new BufferedWriter(new FileWriter(currentDirectory + "/students_export.txt", true)));
				
				Iterator<Entry<String, Student>> it = students.entrySet().iterator();
				while (it.hasNext() ) {

			  
			        Entry<String, Student> pairs = it.next();
			        System.out.println(pairs.getValue());
			  
			        pr.write(pairs.getValue() + "\n");
				}
				
				pr.close();
				System.out.println("Successfully wrote students_export.txt");
			} catch (IOException e) {
				System.out.println(" An error occurred.");
				e.printStackTrace();
			}
		 
		 
	 }

	//rewrite to be able to write all the student info/ preferences at once. 
		public void writeToFile(Student s, String type) {
			String fileName = "";
			String currentDirectory = System.getProperty("user.dir");

			if (type.equals("info")) {
				fileName = "studentinfo.txt";
			} else if (type.equals("preferences")) {
				fileName = "preferences.txt";
			}

			try {
				FileWriter myWriter = new FileWriter(fileName, true);
				BufferedWriter out = new BufferedWriter(myWriter);
				if (type.equals("info")) {
					myWriter.write(s.toString() + "\n");
					System.out.println("Successfully wrote studentsinfor.txt");
				} else if (type.equals("preferences")) {
					myWriter.write(s.getStudentID() + " ");
					myWriter.write(s.getPreferences().toString()+"\n");
					System.out.println("Successfully wrote preferences_FINAL_TEST.txt");
				}
				
				myWriter.close();
				
			} catch (IOException e) {
				System.out.println(" An error occurred.");
				e.printStackTrace();
			}
		}
		

public void writeProjShortlistToFile(String projShortlist) throws IOException {
	try {
		String currentDirectory = System.getProperty("user.dir");
		PrintWriter pr = new PrintWriter(
				new BufferedWriter(new FileWriter(currentDirectory + "/project_preferences.txt", true)));
		pr.write(projShortlist +"\n");
		pr.close();
		System.out.println("Successfully wrote project_preferences.txt");
	} catch (IOException e) {
		System.out.println(" An error occurred.");
		e.printStackTrace();
	}
}


}
