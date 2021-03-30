package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Project implements Serializable {
	
	private static final long serialVersionUID = 1309293175594563625L;
	//To prevent the Invalid Class 
	//Docs.oracle.com. 2020. Serializable (Java Platform SE 7 ). 
	//[online] Available at: <https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html> [Accessed 16 October 2020].

	private String projID, ownerID, companyID;// should these be protected?
	private String title, description;
	private String ranking, skill;
	private int p = 0, n = 0, a = 0, w = 0;

	private HashMap<String, Integer> techSkills = new HashMap<String, Integer>();

	private HashMap<String, Student> members = new HashMap<String, Student>();

	// private char techSkill;
	private int rank = 0;

	public Project(String projID, String title, String description, String ownerID, String ranking, int p, int n, int a,
			int w) {
		this.title = title;
		this.projID = projID;
		this.description = description;
		this.ownerID = ownerID;
		this.ranking = ranking;
		this.setTechSkills(p, n, a, w);

	}

	public Project(String projID, String title, String description, String ownerID, int p, int n, int a, int w) {
		this.title = title;
		this.projID = projID;
		this.description = description;
		this.ownerID = ownerID;
		this.setTechSkills(p, n, a, w);

	}

	public String getProjID() {
		return projID;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public HashMap<String, Integer> getTechSkills() {
		return techSkills;
	}

//setting the skill requiremetns for the projects. 
	public void setTechSkills(int pScore, int nScore, int aScore, int wScore) {
		this.techSkills.put("P", pScore);
		this.techSkills.put("N", nScore);
		this.techSkills.put("A", aScore);
		this.techSkills.put("W", wScore);

	}

	public void setTechSkills(Map<String, Integer> reorderedMap) {
		this.techSkills.putAll(reorderedMap);
	}

	public HashMap<String, Student> getMembers() {
		return members;
	}

	public void setMembers(String student1ID, Student stu1, String student2ID, Student stu2, String student3ID,
			Student stu3, String student4ID, Student stu4) {
		this.members.put(student1ID, stu1);
		stu1.setProj(this);
		this.members.put(student2ID, stu2);
		stu2.setProj(this);
		this.members.put(student3ID, stu3);
		stu3.setProj(this);
		this.members.put(student4ID, stu4);
		stu4.setProj(this);

	}

//If trying to set the 
	public void setMembers(String student1ID, Student stu1) throws AddingTooManyProjectMembersException {

		if (this.checkTeamSize() < 4) {
			this.members.put(student1ID, stu1);
			stu1.setProj(this);
		} else {
			throw new AddingTooManyProjectMembersException("This project already has 4 members!");
		}

	}

	@Override
	public String toString() {
		return this.getProjID() + " " + this.getOwnerID() + " " + this.getTitle() + " "
				+ mapToString(this.getTechSkills()) + " " + this.getDescription();
	}

//Make a method to remove student if they have invalid grades. 

	public void removeInvalidStudent(String StudentID) throws InvalidGradeException {

		if (this.members.containsKey(StudentID)) {
			Student stu = this.members.get(StudentID);

			for (Integer grade : stu.getGrades().values()) {

				if (grade < 1 || grade > 4) {

					this.members.remove(stu.getStudentID());

					throw new InvalidGradeException("Invalid grades for this student " + stu.getStudentID());
				} else {

				}

			}
		}
	}

//Calculating the average skill competency for each subject across all memebers
	public HashMap<String, Double> averageSkillCompetency() throws InvalidGradeException {

		HashMap<String, Double> average = new HashMap<String, Double>();

		double size = (double) this.members.size();

		// loop though each student
		for (Student s : this.members.values()) {

			for (String subject : s.getGrades().keySet()) {
				// get grade for subject

				if (s.getGrades().get(subject) < 1 || s.getGrades().get(subject) > 4) {
					this.members.remove(s.getStudentID());// Removes student with negative grades.

					throw new InvalidGradeException("Invalid grades for this student " + s.getStudentID());

				} else {
					int grade = s.getGrades().get(subject);
					// put in Hashmap
					average.compute(subject, (key, val) -> (val == null) ? (grade / size) : (val + (grade / size)));
				}

			}
		}
		return average;

	}

//calculating the entire average skill competency cobmining every subject and student of the team.
	public double totalAverageSkillCompetency() throws InvalidGradeException {

		double average = 0.0, averagePerStudent = 0.0;
		double sum = 0.0;

		double size = (double) this.members.size();

		// loop though each student
		for (Student s : this.members.values()) {

			removeInvalidStudent(s.getStudentID());

			for (Integer grade : s.getGrades().values()) {
				// get grade for subject

				if (grade < 1 || grade > 4) {
					this.members.remove(s);// Removes student with negative grades.

					throw new InvalidGradeException("Invalid grades. Grades are less than 1 or greater than 4.");

				} else {

					sum += grade;

				}
			}

			averagePerStudent = sum / 4;
		}

		average = averagePerStudent / size;

		return average;

	}

	public double calcPrefPercentage() throws UnpopularProjectException{
		int bestPrefCount = 0;
		double bestPrefPercentage = 0.0;

		double size = 0.0;

		for (Student s : this.members.values()) {

			for (Integer grade : s.getGrades().values()) {

				if (grade < 1 || grade > 4) {// checking the grades of the student and if they are valid.
					this.members.remove(s);
					size--;

					if (s.top2Pref(this.projID) == true) {
						bestPrefCount++;
					}
				} else {

					size = 4.0;

				}
			}
		}

		for (Student s : this.members.values()) {
			if (s.top2Pref(this.projID) == true) {

				bestPrefCount++;

			}
		}

		bestPrefPercentage = (double) ((bestPrefCount / size) * 100);

		if (bestPrefPercentage == 0.0) {
			// If 0% of members actually had their allocated project not in the top 2
			// preferences.
			throw new UnpopularProjectException("Only " + bestPrefPercentage + "% team members had this project "
					+ this.projID + " in their top 2 preferences");
		}

		return bestPrefPercentage;
	}

//Calculating skill short fall for each skill in the same team. 
	// This gives total skills shortfall for each team

	public double totalSkillShortFall() throws InvalidGradeException {
		double skillShort = 0.0, skillShortPerCategory = 0.0;
		HashMap<String, Double> average = averageSkillCompetency();

		for (String subject : average.keySet()) {
			if (this.techSkills.get(subject) >= average.get(subject)
					|| this.techSkills.get(subject) <= average.get(subject)) {

				skillShort += Math.abs(((double) this.techSkills.get(subject) - average.get(subject)));
			}

		}

		return skillShort;
	}

//Skill Short fall for each category within a team. 

	public HashMap<String, Double> skillShortFall() throws InvalidGradeException {

		double skillShort = 0.0;
		HashMap<String, Double> average = averageSkillCompetency();
		HashMap<String, Double> shortFallPerCategory = new HashMap<String, Double>();
		for (String subject : average.keySet()) {
			if (this.techSkills.get(subject) > average.get(subject)) {

				skillShort = (double) (this.techSkills.get(subject) - average.get(subject));
				shortFallPerCategory.put(subject, skillShort);
			}

		}
		return shortFallPerCategory;

	}

//Checking the size of the project 

	public int checkTeamSize() throws AddingTooManyProjectMembersException {
		int countMembers = 0;
		int maxMembers = 4;

		for (Student stu : this.members.values()) {
			countMembers++;
		}

		if (countMembers > maxMembers) {

			throw new AddingTooManyProjectMembersException("There are already 4 members in this team.");

		} else {

		}
		return countMembers;

	}

//No leader exception

	public boolean checkLeader() throws NoLeaderException {
		int typeA = 0;
		boolean hasLeader = false;
		for (Student stu : this.members.values()) {
			if (stu.getPersonalityType() == 'A') {
				typeA++;
			}
		}

		if (typeA == 0) {

			hasLeader = false;
			throw new NoLeaderException("There is no leader (personality type A) in the team.");

		} else {
			hasLeader = true;
		}

		return hasLeader;
	}

//Checking if student already exists in the project 

	public boolean repeatedMemberCheck(String studentID) throws RepeatedMemberException {
		boolean repeatedMember = false;

		if (this.members.containsKey(studentID)) {

			repeatedMember = true;

			throw new RepeatedMemberException(
					"Student " + studentID + "is already allocated to project " + this.projID);
		} else {
			repeatedMember = false;
		}

		return repeatedMember;

	}

//Checking that the student member is allocated to another project
	public boolean invalidMemberCheck(Student studentIn) throws InvalidMemberException {

		boolean assingedToOtherProj = false;

		if (studentIn.getProj() != this && studentIn.getProj() != null) {
			assingedToOtherProj = true;

			throw new InvalidMemberException(
					studentIn.getStudentID() + "has already been allocated to another project");
		} else {
			assingedToOtherProj = false;
		}
		return assingedToOtherProj;
	}

//Checking personality imbalance. 

	public boolean checkStudentConflicts(String StudentID) throws StudentConflictException {

		boolean conflict = false;
		for (Student stu : this.members.values()) {

			if (stu.getConflicts().contains(StudentID)) {

				conflict = true;

				throw new StudentConflictException(
						"Team members in " + this.projID + " have conflict with " + StudentID);
			} else {
	
				conflict = false;
			}
		}
		return conflict;
	}

	public int countPersonalityTypes() {
		ArrayList<Character> personalityArr = new ArrayList<Character>();

		for (Student stu : this.members.values()) {
			personalityArr.add(stu.getPersonalityType());
		}

		return (int) personalityArr.stream().distinct().count();

	}

	public boolean checkPersonalityImbalance() throws PersonalityImbalanceException {

		int types = countPersonalityTypes();

		boolean imbalancedPersonalities = false;

		if (types < 3) {
			imbalancedPersonalities = true;
			throw new PersonalityImbalanceException("Less than 3 unique personalities in project team " + this.projID);

		} else {

			imbalancedPersonalities = false;
		}
		return imbalancedPersonalities;
	}

//method?, H., Filotto, N., ツ, Φ. and Turner, A., 2020. How To Override = Symbol While Converting Hashmap To String Using Tostring Method?. [online] Stack Overflow. 
//Available at: <https://stackoverflow.com/questions/39896042/how-to-override-symbol-while-converting-hashmap-to-string-using-tostring-metho> [Accessed 7 August 2020].
	public static <K, V> String mapToString(HashMap<String, Integer> map) {

		return map.entrySet().stream().map(entry -> entry.getKey() + " " + entry.getValue())
				.collect(Collectors.joining(" ", " ", " "));

	}

	public static <K, V> String membersToString(HashMap<String, Student> map) {

		return map.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.joining(" ", " ", " "));

	}

}
