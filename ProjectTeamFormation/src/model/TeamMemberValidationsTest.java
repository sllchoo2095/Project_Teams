package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TeamMemberValidationsTest {
	Project p1,p2,p3,p4,p5;

	 Student stu1,stu2,stu3,stu4,stu5,stu6,stu7,stu8,stu9,stu10,stu11,stu12,stu13,stu14,stu15,stu16,stu17,stu18,stu19,stu20, stu21;
	 MenuOptions mo;
	 StandardDeviation sd;
	 

@BeforeClass
public static void setUpBeforeClass()throws Exception{
	
}

@Before
public void setUp() throws Exception{
	
	mo=new MenuOptions();
	sd=new StandardDeviation();

	
	

	
	 p1= new Project ("P1","Project number 1 ", "Description 1 ", "O1",4,3,2,1);
	 p2= new Project ("P2","Project number 2 ", "Description 2", "O2",2,4,3,1);
	 p3= new Project ("P3","Project number 3 ", "Description 3", "O3",1,2,3,4);
	 p4= new Project ("P4","Project number 4", "Description 4", "O4",3,1,2,4);
	 p5= new Project("P5", "Project number 5", "Description 5", "05",3,1,2,4);
	 
	//Adding projects to the common MenuOptions class 
		mo.addProjects("P1", p1);
		mo.addProjects("P2", p2);
		mo.addProjects("P3", p3);
		mo.addProjects("P4", p4);
		mo.addProjects("P5", p5);
	 
	stu1= new Student("S1","Mary", "Box", "P", 4,"N", 3,"A",2,"W", 4, 'A', "S4", "S2");
	stu2= new Student("S2","Bill", "Bot", "P", 2,"N", 3,"A",1,"W", 4, 'B', "S3");
	 stu3= new Student("S3","Raj", "Mahal",  "P", 3,"N", 4,"A",2,"W", 1, 'C');
	 stu4= new Student("S4", "Amran", "Ibrahim", "P", 1,"N", 2,"A",4,"W", 3, 'D');
	 
	 stu5= new Student("S5","Han", "Ming", "P", 1,"N", 2,"A",4,"W", 3, 'A');
	 stu6= new Student("S6", "Abdullah", "Mohammed","P", 2,"N", 1,"A",3,"W", 4, 'A');
	 stu7= new Student("S7","Elena", "Korsak", "P", 1,"N", 2,"A",4,"W", 3, 'B');
	 stu8= new Student("S8", "Itzumi", "Sake", "P", 2,"N", 1,"A",4,"W", 3, 'A');
	 
	 stu9= new Student("S9","Kimiko", "Natsumi", "P", 1,"N", 2,"A",3,"W", 4, 'B');
	 stu10= new Student("S10","Albert", "Schyler", "P",4,"N", 3,"A",2,"W", 1, 'C');
	 stu11= new Student("S11","Kim", "Go", "P", 2,"N", 3,"A",1,"W", 4, 'D');
	 stu12= new Student("S12","Tim", "Bert", "P", 1,"N", 2,"A",3,"W", 4, 'B');
	 
	 stu13= new Student("S13","Jason","Gryllakis", "P", 4,"N", 3,"A",3,"W", 2, 'A');
	 //S14-S16
	 stu14= new Student("S14","Jill","Kennedy",  "P",4,"N", 4,"A",4,"W", 4, 'B');
	 stu15= new Student("S15","Lucy","Stud", "P", 2,"N", 3,"A",1,"W", 4, 'C');
	 stu16= new Student("S16","Todd", "MacKenny", "P", 1,"N", 2,"A",3,"W", 4, 'D');
		
	stu17= new Student("S17","Marcus", "Versai", "P", 4,"N", 3,"A",3,"W", 2, 'A');
	stu18= new Student("S18","Lily", "Evans", "P",3,"N", 3,"A",2,"W", 4, 'B');
	stu19= new Student("S19","Ling", "Hua", "P", 2,"N", 3,"A",1,"W", 4, 'C');
	stu20= new Student("S20", "Tom", "Newman","P", 1,"N", 2,"A",3,"W", 4, 'D');
	
	
	stu21= new Student("S20", "Patrica", "Lucita","P", 1,"N", 1,"A",1,"W", 1, 'A');
 
	p1.setMembers("S1", stu1,"S2", stu2, "S3", stu3, "S4", stu4);
	p2.setMembers("S5", stu5,"S6", stu6, "S7", stu7, "S8", stu8);
	p3.setMembers("S9", stu9,"S10", stu10, "S11", stu11, "S12", stu12);
	p4.setMembers("S13", stu13);
	p5.setMembers("S17", stu17, "S18", stu18, "S19", stu19, "S20", stu20);
	

	//Setting preferences for project 1
	stu1.setPreferences("P1",4,"P2",2,"P3",1,"P4",3);//50% Members have set P1 as top 2 preferences
	stu2.setPreferences("P1",2,"P2",1,"P3",4,"P4",3);
	stu3.setPreferences("P1",4,"P2",3,"P3",1,"P4",2);
	stu4.setPreferences("P1",1,"P2",2,"P3",4,"P4",3);
	
	//For when none of the students have elected project P2 in their top 2 preferences. 
	//75% Members chose project 2
	stu5.setPreferences("P1",1,"P2",3,"P3",4,"P4",3);
	stu6.setPreferences("P1",2,"P2",4,"P3",3,"P4",1);
	stu7.setPreferences("P1",2,"P2",4,"P3",1,"P4",3);
	stu8.setPreferences("P1",3,"P2",2,"P3",3,"P4",1);
	
	//Setting the prefrences for project 3
	//100% of Members want project 3

	stu9.setPreferences("P3",4,"P2",3,"P1",4,"P4",2);
	stu10.setPreferences("P3",4,"P2",4,"P1",2,"P4",3);
	stu11.setPreferences("P3",4,"P2",2,"P1",3,"P4",4);
	stu12.setPreferences("P3",4,"P2",2,"P1",4,"P3",3);
	
	//Setting the prefrences for project 4
	//75% members want this project  
	stu13.setPreferences("P4",1,"P2",2,"P1",4,"P3",3);
	stu14.setPreferences("P4",3,"P2",3,"P1",4,"P3",2);
	stu15.setPreferences("P4",4,"P2",4,"P1",2,"P3",3);
	stu16.setPreferences("P4",4,"P2",2,"P1",3,"P3",4);
	
	//Setting up preferences for project 5 
	//100% Members want this project 
	stu17.setPreferences("P5",4,"P2",3,"P1",3,"P3",2);
	stu18.setPreferences("P5",3,"P2",3,"P1",2,"P3",4);
	stu19.setPreferences("P5",3,"P2",4,"P1",1,"P3",2);
	stu20.setPreferences("P5",4,"P2",2,"P1",3,"P3",1);
	//Negative grades for S20  which shouldn't be included in the project 5 average
	//Should be 100% teamamtes in this 
	
	
}


	@Test
	public void correctStudentConflict() throws  StudentConflictException {
	
		
	
		assertFalse(" No conflict in this team", p5.checkStudentConflicts("S17"));
		assertFalse(" No conflict with this student", p5.checkStudentConflicts("S18"));
		assertFalse(" Conflicts exist in this team", p5.checkStudentConflicts("S19"));
		assertFalse(" Conflicts exist in this team", p5.checkStudentConflicts("S20"));
	}
	
	
	
	@Test(expected= StudentConflictException.class)
	public void incorrectStudentConflict() throws StudentConflictException{
		
		
		assertTrue(" Conflicts exist in this team", p1.checkStudentConflicts("S4"));
		assertTrue(" Conflicts exist in this team", p1.checkStudentConflicts("S2"));
		assertTrue(" Conflicts exist in this team", p1.checkStudentConflicts("S3"));

		}

	@Test 
	public void correctPersonalityBalance() throws  PersonalityImbalanceException{

		assertFalse("3 or mor personalities in this team", p5.checkPersonalityImbalance());
	
	}
	
	
	@Test (expected=PersonalityImbalanceException.class)
	public void incorrectPersonalityBalance() throws  PersonalityImbalanceException{

		assertTrue("There are less than 3 types of personalities in this team", p2.checkPersonalityImbalance());
	
	}
	
	
	@Test
	public void correctLeader() throws NoLeaderException{
		
		assertTrue("There is a leader", p5.checkLeader());
		
		}	
	
	@Test(expected= NoLeaderException.class)
	public void incorrectNoLeader() throws NoLeaderException{
		
		assertFalse("There is no leader in this team", p3.checkLeader());
		
		}	
	
	@Test
	public void correctAddMember() throws RepeatedMemberException {

		assertFalse("This person isn't goong to be added to the same project multiple times", p4.repeatedMemberCheck("S21"));
	}
	
	@Test(expected= RepeatedMemberException.class)
	public void inccorrectAddSameMemberMultipleTimes() throws RepeatedMemberException {

		assertTrue("This person is already in this project", p4.repeatedMemberCheck("S13"));
	}
	
	
	@Test
	public void correctAddingStudentWithPreExistingProject() throws InvalidMemberException {
	
		assertFalse("This person isn't already allocated to a project", p4.invalidMemberCheck(stu21));
	
	}
	
	
	@Test(expected=InvalidMemberException.class)
	public void incorrectAddingStudentWithPreExistingProject() throws InvalidMemberException {
	
		assertTrue("This person is already allocated in another project", p5.invalidMemberCheck(stu9));
	
	}




	

}
