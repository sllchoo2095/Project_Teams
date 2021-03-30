package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controller.ProjectTeamFormationController;

public class MenuOptions {

	// Mediator class and also hold the common methods for the program.

	StandardDeviation sd = new StandardDeviation();

	private SortAlgorithm sa = new SortAlgorithm();
	private HashMap<String, Integer> projectsPreferences = new HashMap<String, Integer>();

	private Map<String, Student> students = new HashMap<String, Student>();

	private Map<String, Owner> owners = new HashMap<String, Owner>();

	private Map<String, Company> companies = new HashMap<String, Company>();

	private Map<String, Project> projects = new HashMap<String, Project>();

	// Instantiated set to determine unique inputs for project skill requirements in
	// menuProjects()
	private Set<Integer> inputSetNum = new HashSet<Integer>();

	boolean endProgram = false;
	boolean validInput = false;
	boolean validID = false;
	boolean valid = false;

	public MenuOptions() {

		this.companies = companies;
		this.owners = owners;
		this.projects = projects;
		this.students = students;
		this.projectsPreferences = projectsPreferences;

	}

//Instances of other classes 
	private WriterMethods wm = new WriterMethods();
	private DeserialieSerialise ds = new DeserialieSerialise();

	private ReadMethods rm = new ReadMethods();
	Scanner sc = new Scanner(System.in);

////HASHMAP GETTERS 

	public Map<String, Student> getStudents() {
		return students;
	}

	public Map<String, Owner> getOwners() {
		return owners;
	}

	public Map<String, Company> getCompanies() {
		return companies;
	}

	public Map<String, Project> getProjects() {
		return projects;
	}

//Hashmap setters 
	public void setStudents(Map<String, Student> students) {
		this.students = students;
	}

	public void setOwners(Map<String, Owner> owners) {
		this.owners = owners;
	}

	public void setCompanies(Map<String, Company> companies) {
		this.companies = companies;
	}

	public void setProjects(Map<String, Project> projects) {
		this.projects = projects;
	}

//Based on code from Dipto Pratyaksa Week 3 lab

	public void menuCompanies() throws IOException {
		String comp = "C", companyID = "", companyName = "", URL = "", address = "";
		int ABN_Number = 0;
		boolean validID = false, valid = false;// Instiantiating the variables for company
		Scanner sc = new Scanner(System.in);
		do {
			try {
				System.out.println("Do you want to continue to Company details (x to cancel) ");
				String id = sc.nextLine();
				if (id.toLowerCase().contains("x")) {
					break;
				} else {
				}
				while (!validID) {
					System.out.println("------------COMPANY DETAILS-------------------------- ");
					System.out.println("Enter company ID number C: ");
					int tempInt = sc.nextInt();
					String temp = Integer.toString(tempInt);
					String tempID = comp.concat(temp);
					if (checkCompanyID(tempID)) {
						System.out.println("CompanyID already exits, unavailable");
					} else {
						companyID = comp.concat(temp);
						validID = true;
					}

				}

				System.out.println("Enter company name: ");
				companyName = sc.next();

				System.out.println("Enter ABN number: ");
				ABN_Number = sc.nextInt();

				System.out.println("Enter company URL: ");
				URL = sc.next();

				sc.nextLine();

				System.out.println("Enter company address: ");
				address = sc.nextLine();

				System.out.println("About to write ");

				addCompany(companyID, new Company(companyID, companyName, ABN_Number, URL, address));

				valid = true;

			} catch (InputMismatchException ime) {
				System.out.println("Wrong input type try again");
				sc.next();
				valid = false;
			} catch (Exception e) {
				System.out.println("Something went wrong ===Company");
				sc.next();
				valid = false;
			} finally {
				System.out.println("Company Finally");

			}

		} while (!valid);

	}

////
	public void menuOwners() {
		String own = "O", proj = "P", ownerID = "", tempCompID = "", ownFirstName = "", ownSurname = "", role = "",
				email = "", rankStr = "";
		String comp = "C", companyID = "";

		boolean validCompID = false, valid = false, validOwn = false;
		Scanner sc = new Scanner(System.in);// Instiantiating the variables for owners
		do {
			try {

				System.out.println("Do you want to continue to Project Owner details (x to cancel) ");
				String id = sc.nextLine();
				if (id.toLowerCase().contains("x")) {
					break;
				} else {
				}

				while (!validCompID) {
					System.out.println("------------COMPANY ID FOR PROJECT OWNER-------------------------- ");
					System.out.println("Enter company ID number C: ");
					int tempInt = sc.nextInt();
					String temp = Integer.toString(tempInt);
					String tempID = comp.concat(temp);
					if (checkCompanyID(tempID)) {
						companyID = comp.concat(temp);

						validCompID = true;
					} else {

						validCompID = false;
					}
				}

				while (!validOwn) {
					System.out.println("------------PROJECT OWNER DETAILS------------------ ");
					System.out.println("Enter the project owner ID number O: ");
					String ownerInt = Integer.toString(sc.nextInt());
					ownerID = own.concat(ownerInt);

					if (checkProjID(ownerID) == true) {
						System.out.println("Project already exists, please enter new project");
						System.out.println("Existing projects: " + this.projects.keySet());
						validOwn = false;

					} else {
						validOwn = true;
					}
				}

				System.out.println("Enter the project owner's first name: ");
				ownFirstName = sc.next();

				System.out.println("Enter the project owner's surname: ");
				ownSurname = sc.next();
				sc.nextLine();

				System.out.println("Enter the project owner's role: ");
				role = sc.nextLine();
				sc.nextLine();

				System.out.println("Enter the project owner's email: ");
				email = sc.nextLine();

				addOwners(ownerID, new Owner(ownerID, ownFirstName, ownSurname, role, email, companyID));

				valid = true;
			} catch (InputMismatchException ime) {
				System.out.println("Wrong input type try again");
				sc.next();
				valid = false;
			} catch (Exception e) {
				System.out.println("Something went wrong ===Owners");
				sc.next();
				valid = false;
			} finally {
				System.out.println("Company Finally");

			}

		} while (!valid);

	}

	public void menuProjects() {

		String proj = "P", title = "", tempOwnerID = "", ownerID = "", projID = "", description = "", skillsRank = "";
		int p = 0, n = 0, a = 0, w = 0;

		String own = "O", comp = "C", projInt = "", ranking = "";

		Scanner sc = new Scanner(System.in);

		boolean validOwnID = false, valid = false, validProj = false, validSkillP = false, validSkillN = false,
				validSkillA = false, validSkillW = false;
		do {
			try {

				System.out.println("Do you want to continue to Project  details? (x to cancel) ");
				String id = sc.nextLine();
				if (id.toLowerCase().contains("x")) {
					break;
				} else {
				}
				System.out.println("------------PROJECT DETAILS--------------------------- ");

				while (!validOwnID) {
					System.out.println("Project owner's ID that this project belongs to O ");

					String temp = Integer.toString(sc.nextInt());
					tempOwnerID = own.concat(temp);

					if (checkOwnerID(tempOwnerID) == true) {
						ownerID = tempOwnerID;
						validOwnID = true;
					} else {
						validOwnID = false;
						System.out.println("Invalid Project owner ID");
					}
				}

				while (!validProj) {
					System.out.println("Enter project ID number P");
					projInt = Integer.toString(sc.nextInt());
					projID = proj.concat(projInt);

					if (checkProjID(projID) == true) {
						System.out.println("Project already exists, please enter new project");
						System.out.println("Existing projects: " + projects.keySet());
						validProj = false;

					} else {
						validProj = true;
					}
				}

				System.out.println("Enter project title: ");
				title = sc.nextLine();
				sc.nextLine();
				System.out.println("Enter a one line description of the project :");
				description = sc.nextLine();
				sc.nextLine();

				while (!validSkillP) {
					System.out.println("Skill ranking for Programming and Software Engineering : ");
					p = sc.nextInt();
					if (checkNum(p) == true) {
						System.out.println("Valid input for P is  " + p);
						validSkillP = true;
					} else {
						System.out.println("Incorrect input. Please enter a value between 1-4");
						validSkillP = false;
					}
				}

				while (!validSkillN) {
					System.out.println("Skill ranking for Networking & Security: ");
					n = sc.nextInt();

					if (uniqueNum(n) == true) {
						System.out.println("Valid input for N is " + n);
						validSkillN = true;

					} else {
						System.out.println("Unique input please");
					}
				}

				while (!validSkillA) {
					System.out.println("Skill ranking for Analytics and Big Data:  ");
					a = sc.nextInt();

					if (uniqueNum(a) == true) {
						System.out.println("Valid input for N is " + a);
						validSkillA = true;

					} else {
						System.out.println("Unique input please");
					}
				}

				while (!validSkillW) {
					System.out.println("Skill ranking for Web and Mobile Applications:  ");
					w = sc.nextInt();

					if (uniqueNum(w) == true) {
						System.out.println("Valid input for N is " + w);
						validSkillW = true;

					} else {
						System.out.println("Unique input please");
					}
				}

				addProjects(projID, new Project(projID, title, description, ownerID, ranking, p, n, a, w));

				valid = true;
			}

			catch (InputMismatchException ime) {
				ime.printStackTrace();
				valid = false;
				sc.next();
			} catch (Exception e) {
				e.printStackTrace();
				valid = false;
				sc.next();
			} finally {

			}

		} while (!valid);

		// wm.writeProjects(this.projects);
	}

	// Reference to write out the hashmap
//https://stackoverflow.com/questions/15413467/writing-from-hashmap-to-a-txt-file

//Getting the Student value out of a hashmap
//https://stackoverflow.com/questions/7857935/how-to-get-an-object-from-a-hashmap-in-java

//A method that uploads everything from text file
	public void uploads() throws IOException {

		String currentDirectory = System.getProperty("user.dir");

		System.out.println("The current working directory is " + currentDirectory);

		setCompanies(rm.readCompaniesFile(currentDirectory + "/companies_test.txt", this.companies));
		setOwners(rm.readOwnersFile(currentDirectory + "/owners_test.txt", this.owners));
		setProjects(rm.readProjectsFile(currentDirectory + "/projects_test.txt", this.projects));
		setStudents(rm.readStudentsFile(currentDirectory + "/students_test_name.txt", this.students));
		setStudents(rm.readPreferences(currentDirectory + "/preferences_test_1.txt", this.students));

		System.out.println("All student, Owners, Projects and companies data have been loaded ");

	}

	public void captureStudentPersonalities() {

		Scanner sc = new Scanner(System.in);
		boolean found = false, validEntry = false, validConflict = false;
		;
		String conflicts = " ";
		char personality;

		do {
			System.out.println("Which student do you want to update personalities and conflicts? (z to cancel) ");
			String id = sc.nextLine();
			if (id.toLowerCase().contains("z")) {
				break;
			}

			try {

				Student aStudent = null;

				if (this.students.containsKey(id)) {
					aStudent = (Student) this.students.get(id);
				}

				if (aStudent != null) {
					System.out.println("Found student in database as: ");
					System.out.println(aStudent.toString());
					while (!validEntry) {
						System.out.println("Enter " + aStudent.getStudentID() + "'s personality type?");
						personality = sc.nextLine().toUpperCase().charAt(0);

						if (personality == 'A' || personality == 'B' || personality == 'C' || personality == 'D') {

						} else {
							validEntry = false;
							System.out.println("Invalid personality type");

						}
						System.out.println("Updated personality type for " + aStudent.getStudentID());
						aStudent.setPersonalityType(personality);
						validEntry = checkPersonalitiesLimit();
						System.out.println(validEntry);

					}

					while (!validConflict) {
						System.out.println(
								"Enter up to 2 student IDs that this student may have potential conflict with (Eg: S1;S2), press z if there are no conflicts.  ");
						conflicts = sc.nextLine();
						if (conflicts.toLowerCase().contains("z")) {
							found = true;
							break;
						}

						if (conflicts.contains(aStudent.getStudentID())) {
							System.out.println("Invalid conflict, a student can't be in conflict with themeselves.");
							validConflict = false;
						} else {
							validConflict = true;
							System.out.println("Valid conflicts");
						}

					}

					String[] arrConflicts = conflicts.split(";");

					for (int i = 0; i < arrConflicts.length; i++) {

						aStudent.addConflicts(arrConflicts[i]);
					}
					System.out.println(aStudent.toString());

					wm.writeToFile(aStudent, "info");

					found = true;

				} else {
					System.out.println("Student does not exist");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (!found);

	}

//Adding PREFERENCES

//References for how to convert string into hashmap
//Thetopsites.net. 2020. How To Convert String Into Hashmap In Java. [online] 
	// Available at: <https://www.thetopsites.net/article/51898673.shtml> [Accessed
	// 8 May 2020].

	public void addStudentPreferences() {

		boolean found = false, validEntry = false;
		String pref = " ";
		HashMap<String, Integer> prefMap = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		do {
			try {
				System.out.println("Available students " + this.students.size());
				for (String key : this.students.keySet()) {
					System.out.println(key);
				}
				System.out.println("Enter student that you want to update preferences for: ");
				String id = sc.nextLine().trim();

				if (id.toLowerCase().contains("z")) {
					break;
				}
				Student aStudent = null;
				// Checks if Student exists.
				if (this.students.containsKey(id)) {
					aStudent = (Student) this.students.get(id);

				} else {
					System.out.println("This student deosn't exist");
					break;
				}

				if (aStudent != null) {
					System.out.println("Student " + aStudent.toString() + "'s information");

					System.out.println(
							"Enter preferences for " + aStudent.getStudentID() + " (Eg: P1 1;P2 2;P3 3;P4 4) ");
					pref = sc.nextLine();

					String[] keyValuePairs = pref.split(";");

					for (String pair : keyValuePairs) // iterate over the pairs
					{
						String[] entry = pair.split(" "); // split the pairs to get key and value
						String num = entry[1].trim();
						int rank = Integer.parseInt(num);
						prefMap.put(entry[0].trim(), rank); // add them to the hashmap and trim whitespaces
					}
					aStudent.setPreferences(prefMap);
					System.out.println("Updated preferences");
					System.out.println(aStudent.getPreferences().toString());

					wm.writeToFile(aStudent, "preferences");

					found = true;

				} else {
					System.out.println("Student does not exist");
					found = false;
				}

			}

			catch (Exception e) {
				e.printStackTrace();
				found = false;

			}

		} while (!found);

	}

//To only obtain the top 5 projects. 

	public void shortlistPreferences() {
		generateTopProjects(sortByRanking(compileProjectPreferences()));
	}

////Milestone 2 

	public void menuFormTeam() {
		Scanner sc = new Scanner(System.in);
		String proj = "P", stu = "S", student1ID = " ", student2ID = " ", student3ID = " ", student4ID = " ";
		boolean validPojID = false, valid = false, validStudents = false;
		Project projectObj = null;

		Student student1 = null, student2 = null, student3 = null, student4 = null;

		do {
			try {
				System.out.println("Do you want to continue to form teams (x to cancel) ");
				String id = sc.nextLine();
				if (id.toLowerCase().contains("x")) {
					break;
				}

				while (!validPojID) {
					System.out.println("------------FORM TEAMS!-------------------------- ");
					System.out.println("Enter project ID number P");
					String projInt = Integer.toString(sc.nextInt());
					String projID = proj.concat(projInt);

					if (this.projects.containsKey(projID)) {
						projectObj = (Project) this.projects.get(projID);
						validPojID = true;
					}
				}

				System.out.println("Enter student ID number for the first student in the team. S");
				student1ID = stu.concat(sc.next());

				System.out.println("Enter student ID number for the second student in the team. S");
				student2ID = stu.concat(sc.next());

				System.out.println("Enter student ID number for the third  student in the team. S");
				student3ID = stu.concat(sc.next());

				System.out.println("Enter student ID number for the fourth student in the team. S");
				student4ID = stu.concat(sc.next());

				if (this.students.containsKey(student1ID) && this.students.containsKey(student2ID)
						&& this.students.containsKey(student3ID) && this.students.containsKey(student4ID)) {
					student1 = (Student) this.students.get(student1ID);
					student2 = (Student) this.students.get(student2ID);
					student3 = (Student) this.students.get(student3ID);
					student4 = (Student) this.students.get(student4ID); // Checking if the students exist

				}

				else {
					valid = false;

					System.out.println("Invalid sutdents");
					break;

				}

				// Add students to project.

				// This methods sets the students to a certain project.

				projectObj.setMembers(student1ID, student1, student2ID, student2, student3ID, student3, student4ID,
						student4);

				for (Student member : projectObj.getMembers().values()) {
					if (projectObj.checkStudentConflicts(member.getStudentID())) {

						projectObj.getMembers().remove(member.getStudentID());
						valid = false;

					} else if (projectObj.checkTeamSize() > 4) {

						projectObj.getMembers().clear();
						valid = false;
					} else if (projectObj.invalidMemberCheck(member) == true) {
						projectObj.getMembers().clear();
						valid = false;
					} else if (projectObj.checkPersonalityImbalance() == true) {
						projectObj.getMembers().clear();
						valid = false;
					} else if (projectObj.calcPrefPercentage() == 0) {
						projectObj.getMembers().remove(member.getStudentID());
						valid = false;
					} else {
						valid = false;
					}
				}

				if (projectObj.checkPersonalityImbalance() == true || projectObj.checkLeader() == false) {

					projectObj.getMembers().clear();
					System.out.println(
							"There's either a personalitiy imbalance or there is no leader. Please reconficure the team");
					valid = false;
				} else {

					System.out.println(projectObj.getProjID() + " has members " + projectObj.getMembers().keySet());
				}

				valid = true;

			} catch (InputMismatchException ime) {
				System.out.println("Wrong input type try again");
				ime.printStackTrace();
				sc.next();
				valid = false;
			} catch (AddingTooManyProjectMembersException atmpme) {
				atmpme.printStackTrace();
				sc.next();
				valid = false;
			}

			catch (InvalidMemberException im) {

				im.printStackTrace();
				sc.next();
				valid = false;

			} catch (NoLeaderException nle) {
				System.out.println("There is no leader in this team configuration");
				nle.printStackTrace();
				sc.next();
				valid = false;
			} catch (PersonalityImbalanceException pie) {

				pie.printStackTrace();

				valid = false;

			} catch (StudentConflictException sce) {
				System.out.println("Students have conflicts with each other");
				sce.printStackTrace();
				sc.next();
				valid = false;
			} catch (UnpopularProjectException upe) {
				upe.printStackTrace();
				sc.next();
				valid = false;
			}

			catch (Exception e) {

				e.printStackTrace();
				projectObj.getMembers().clear();

				valid = false;
			} finally {
				System.out.println("Company Finally");
				break;

			}

		} while (!valid);

	}

//Display Team Fitness metrics

	public void displayTeamFitnessMetrics() {
		try {

			for (Project p : this.projects.values()) {

				if (p.getMembers() != null) {
					System.out.println("----------PROJECT " + p.getProjID() + "'s TEAM FITNESS METRICS---------");
					p.totalAverageSkillCompetency();
					p.calcPrefPercentage();
					p.totalSkillShortFall();

				}
			}
		} catch (UnpopularProjectException motupe) {
			motupe.printStackTrace();

		} catch (InvalidGradeException ige) {
			ige.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//Make method to retrieve all prefereneces from the students

//Uses of linked Hashmap
//beginnersbook.com. 2020. Linkedhashmap In Java. [online] 
//Available at: <https://beginnersbook.com/2013/12/linkedhashmap-in-java/> [Accessed 2 August 2020].

//Mensah, D., Frisch, E., Kolokolov, A. and Nair, R., 2020. How To Sum Hashmap Values With Same Key Java. [online] Stack Overflow. 
//Available at: <https://stackoverflow.com/questions/36554356/how-to-sum-hashmap-values-with-same-key-java> [Accessed 5 August 2020].

	public Map<String, Integer> compileProjectPreferences() {
		Map<String, Integer> projectPrefrences = new HashMap<String, Integer>();
		String value = " ", proID = " ", num = " ";
		int prefRank = 0;

		for (Student stu : this.students.values()) {
			String pref = stu.getPreferences().toString();// this will give out something like {Pr3=3, Pr2=2, Pr4=4,
															// Pr1=1}.
			System.out.println(pref);
			value = pref.substring(1, pref.length() - 1); // Removes the { }

			String[] projPrefPairs = value.split(",");

			for (String p : projPrefPairs) {
				String[] entry = p.split("=");

				if (entry.length == 2) {
					proID = entry[0].trim();

					num = entry[1].trim();
					prefRank = Integer.parseInt(num);
				}
				projectPrefrences.merge(proID, prefRank, Integer::sum);// Final solution

			}
		}
		return projectPrefrences;
	}

//Dipto Pratyaksa code from week 3 tutorial 
	public Map<String, Integer> sortByRanking(final Map<String, Integer> projPrefMap) {

		return projPrefMap.entrySet().stream().sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	}

//Javatutorial.net. 2020. Java Iterate Through A Hashmap Example | Java Tutorial Network. [online] 
//Available at: <https://javatutorial.net/java-iterate-hashmap-example> [Accessed 5 August 2020].

	public void generateTopProjects(Map<String, Integer> projPrefMap) {

		Map<String, Integer> projectPrefRank = sortByRanking(compileProjectPreferences());

		// count projects
		int totalProjects = 0;
		for (String proj : this.projects.keySet()) {
			totalProjects++;
		}
		int totalStudents = countStudents();

		int maxRank = 5;

		if (totalStudents % totalProjects != 0) {
			maxRank = totalStudents / 4;
		}

		int rank = 1;

		System.out.println("MaxRank = " + maxRank);

		Iterator<Entry<String, Integer>> it = projPrefMap.entrySet().iterator();

		while (it.hasNext() && rank <= maxRank) {

			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
			try {
				System.out.println(pair.getKey() + " = " + pair.getValue());
				wm.writeProjShortlistToFile(pair.getKey() + " = " + pair.getValue());
				rank++;
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

	public ArrayList<Double> compileProjAverageSkillCompetencies(Map<String, Project> projectsHash)
			throws InvalidGradeException {

		ArrayList<Double> projAverageSkillCompetencies = new ArrayList<Double>();

		for (Project p : projectsHash.values()) {

			if (p.getMembers().size() != 0) {

				projAverageSkillCompetencies.add(p.totalAverageSkillCompetency());

			}
		}

		return projAverageSkillCompetencies;

	}

	public ArrayList<Double> compileProjCalcPrefPercentages(Map<String, Project> projectsHash)
			throws InvalidGradeException, UnpopularProjectException {

		ArrayList<Double> projCalcPrefPercentage = new ArrayList<Double>();

		for (Project p : projectsHash.values()) {

			if (p.getMembers().size() != 0) {

				if (p.calcPrefPercentage() == 0.0) {

					projCalcPrefPercentage.add(0.0);
				} else {
					projCalcPrefPercentage.add(p.calcPrefPercentage());
				}
			}
		}

		return projCalcPrefPercentage;

	}

	public ArrayList<Double> compileProjSkillShortfall(Map<String, Project> projectsHash) throws InvalidGradeException {
		ArrayList<Double> projSkillShortFall = new ArrayList<Double>();
		for (Project p : projectsHash.values()) {

			if (p.getMembers().size() != 0) {

				projSkillShortFall.add(p.totalSkillShortFall());

			}

		}

		return projSkillShortFall;
	}

//Helper methods to add items to the hashmaps for students, owners, companies, projects 	
	public void addCompany(String compID, Company comp) {
		this.companies.put(compID, comp);
	}

	public void addOwners(String ownID, Owner own) {
		this.owners.put(ownID, own);
	}

	public void addProjects(String projID, Project proj) {
		this.projects.put(projID, proj);
	}

	public void addStudents(String stuID, Student stu) {
		this.students.put(stuID, stu);

	}

	public void swapStudents(String studentID1, String studentID2, Map<String, Student> studentsHash,
			Map<String, Project> projectsHash) {
		Student stu1 = null, stu2 = null;
		Project proj1 = null, proj2 = null;
		// Not sure if this will work.

		for (Project proj : projectsHash.values()) {
			if (proj.getMembers().containsKey(studentID1)) {
				proj1 = proj;
			} else if (proj.getMembers().containsKey(studentID2)) {
				proj2 = proj;
			} else {

			}
		}

		if (studentsHash.containsKey(studentID1) && studentsHash.containsKey(studentID2)) {
			stu1 = studentsHash.get(studentID1);
			stu2 = studentsHash.get(studentID2);

		} else {
			System.out.println("At least one of these students doesn't exist in the database");
		}

		proj1.getMembers().remove(studentID1);
		proj2.getMembers().remove(studentID2);

		proj1.getMembers().put(studentID2, stu2);
		proj2.getMembers().put(studentID1, stu1);
		System.out.println("Selected students have been swapped");

	}

	public int countStudents() {
		int studCount = 0;
		for (String studID : this.students.keySet()) {
			studCount++;
		}
		return studCount;
	}

///Helper methods to check if the 

	// https://www.geeksforgeeks.org/hashmap-containskey-method-in-java/
	public boolean checkCompanyID(String companyID) {
		if (this.companies.containsKey(companyID)) {
			System.out.println("CompanyID Already exists.");
			return true;
		} else {
			System.out.println("CompanyID is available. ");
			return false;
		}
	}

	// make method which checks if projID is matched to ownerID

	public boolean checkOwnerID(String ownID) {
		if (this.owners.containsKey(ownID)) {
			System.out.println("Owner ID Already exists.");
			return true;
		} else {
			System.out.println("Owner ID is available.");
			return false;
		}
	}

	public boolean checkProjID(String projID) {
		if (this.projects.containsKey(projID)) {
			System.out.println("Project ID already exists.");
			return true;
		} else {
			System.out.println("Project ID is available.");
			return false;
		}
	}

	public void testSerialiseDeserialise() {

		ds.serialise(this.companies, this.owners, this.projects, this.students);
		ds.deserialise(this.companies, this.owners, this.projects, this.students);

	}

	public void exportDataToTxt() {
		wm.writeCompanies(this.companies);
		wm.writeOwners(this.owners);
		wm.writeProjects(this.projects);
		wm.writeStudents(this.students);

	}

	public void serialiseData() {

		ds.serialise(this.companies, this.owners, this.projects, this.students);

	}

	public boolean checkPersonalitiesLimit() throws ExcessStudentPersonalities {

		int maxPersonalities = countStudents() / 4;

		System.out.println("Maximumn number of personalities =" + maxPersonalities);

		int countA = 0, countB = 0, countC = 0, countD = 0;

		Iterator<Entry<String, Student>> it = this.students.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Student> pair = (Map.Entry<String, Student>) it.next();
			char personalityType = pair.getValue().getPersonalityType();
			if (personalityType == 'A') {
				countA++;
			} else if (personalityType == 'B') {
				countB++;
			} else if (personalityType == 'C') {
				countC++;
			} else if (personalityType == 'D') {
				countD++;
			} else {
			}
		}
		if (countA <= maxPersonalities && countB <= maxPersonalities && countC <= maxPersonalities
				&& countD <= maxPersonalities) {

			System.out.println("Number of type A students= " + countA);
			System.out.println("Number of type B students= " + countB);
			System.out.println("Number of type C students= " + countC);
			System.out.println("Number of type D students= " + countD);
			return true;
		} else {
			System.out.println("Number of type A students= " + countA);
			System.out.println("Number of type B students= " + countB);
			System.out.println("Number of type C students= " + countC);
			System.out.println("Number of type D students= " + countD);
			System.out.println("Too many students have been allocated to a persoanlity type.");
			return false;
		}

	}

//Number validations for inputijng project preferences. 

	public boolean checkNum(int numInput) {

		int input = numInput;

		if (input > 0 && input < 5) {
			inputSetNum.add(input);

			return true;
		} else {
			return false;
		}

	}

	public boolean uniqueNum(int numInput) {
		if (!inputSetNum.contains(numInput)) {
			checkNum(numInput);
			System.out.println("Valid & unique ranking.");
			return true;
		} else if (inputSetNum.contains(numInput)) {
			System.out.println("Score already used, please use another ");
			return false;
		} else {
			System.out.println(" unique num Invalid. Please enter a value between 1-4");
			return false;
		}
	}

//Make a method to show how many students have preferred the project
//Coder, I., Forsberg, S. and Degloorkar, S., 2020. Storing And Retrieving Arraylist Values From Hashmap. [online] Stack Overflow. 
	// Available at:
	// <https://stackoverflow.com/questions/19541582/storing-and-retrieving-arraylist-values-from-hashmap>
	// [Accessed 3 August 2020].

	public Map<String, List<String>> complieStudentsPrefProjects(Map<String, Project> projectsHash,
			Map<String, Student> studentsHash) {
		Map<String, List<String>> studentsPreferProj = new HashMap<String, List<String>>();

		ArrayList<String> inputStudents = new ArrayList<String>();
		// Need it to make it return the studentsPrefer Projects.

		for (String projID : projectsHash.keySet()) {

			for (Student stu : studentsHash.values()) {

				if (stu.top2Pref(projID) == true) {

					inputStudents.add(stu.getStudentID());

				}
				studentsPreferProj.put(projID, inputStudents);
			}
		}
		return studentsPreferProj;

	}

	public void callAutoSort() {

		Map<String, Project> autoAssignedProjects = new HashMap<String, Project>();
		double prefSD = 0.0, avgSkillSD = 0.0, skillGapSD = 0.0;
		boolean sorted = false;

		do {
			try {// deserialising the information from serialised_data.ser
				String currentDirectory = System.getProperty("user.dir");
				ObjectInputStream oIn = new ObjectInputStream(new FileInputStream("serialised_data.ser"));

				this.companies = (HashMap<String, Company>) oIn.readObject();
				this.owners = (HashMap<String, Owner>) oIn.readObject();
				this.projects = (HashMap<String, Project>) oIn.readObject();
				this.students = (HashMap<String, Student>) oIn.readObject();
				oIn.close();

				autoAssignedProjects = sa.autoSwap(this.projects, this.students);

				prefSD = sd.calculateSD(sd.compileProjCalcPrefPercentages(autoAssignedProjects));
				avgSkillSD = sd.calculateSD(sd.compileProjAverageSkillCompetencies(autoAssignedProjects));
				skillGapSD = sd.calculateSD(sd.compileProjSkillShortFall(autoAssignedProjects));
				double totalSD = prefSD + avgSkillSD + skillGapSD;

				System.out.println("==============SUGGESTED TEAM COMBINATIONS===================");

				for (Project p : autoAssignedProjects.values()) {
					System.out.println(p.getProjID() + " : " + p.getMembers().keySet());
				}

				System.out.println("TOTAL OPTIMAL SD = " + totalSD);
				System.out.println("Preference % SD = " + prefSD);
				System.out.println("avgSkillSD = " + avgSkillSD);
				System.out.println("skillGapSD = " + skillGapSD);

				sorted = true;

			} catch (FileNotFoundException fnfe) {
				System.out.println("serialised_data.txt not found. ");
				fnfe.printStackTrace();
				sorted = false;

			} catch (IOException ioe) {
				ioe.printStackTrace();
				sorted = false;
			} catch (ClassNotFoundException c) {

				c.printStackTrace();
				sorted = false;
			} catch (InvalidGradeException e) {

				e.printStackTrace();
				sorted = false;
			} catch (UnpopularProjectException e) {

				e.printStackTrace();
				sorted = false;
			}

			catch (Exception e) {
				e.printStackTrace();

			}

		} while (!sorted);

	}

}// Team Project class
