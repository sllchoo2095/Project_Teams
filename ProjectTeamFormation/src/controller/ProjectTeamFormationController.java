package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import model.*;

import databasePackage.*;

public class ProjectTeamFormationController {

	private MenuOptions mo = new MenuOptions();;

	private DatabaseMethods dbm;

	private StandardDeviation sd = new StandardDeviation();

	private SortAlgorithm sa = new SortAlgorithm();
	private DeserialieSerialise ds = new DeserialieSerialise();

	@FXML
	private BarChart<String, Double> prefGraph;

	@FXML
	private ListView<Project> allProjectsList;

	@FXML
	private CategoryAxis prefXAxis;

	@FXML
	private NumberAxis prefYAxis;

	@FXML
	private BarChart<String, Double> averageCompetencyLevel;

	@FXML
	private CategoryAxis avgXAxis;

	@FXML
	private NumberAxis avgYAxis;

	@FXML
	private BarChart<String, Double> skillsGapGraph;

	@FXML
	private CategoryAxis skillsGapXAxis;

	@FXML
	private NumberAxis skillsGapYAxis;

	@FXML
	private Label projectID3;
	@FXML
	private Label projectID2;
	@FXML
	private Label projectID1;
	@FXML
	private Label projectID4;
	@FXML
	private Label projectID5;

	@FXML
	private ListView<Student> project2MembersList;

	@FXML
	private Label prefSD;

	@FXML
	private Label avgCompetencySD;

	@FXML
	private Label skillsGapSD;

	@FXML
	private TextField studentIDTxt2;

	@FXML
	private ListView<Student> project1MembersList;

	@FXML
	private ListView<Student> project3MembersList;

	@FXML
	private ListView<Student> project4MembersList;

	@FXML
	private ListView<Student> project5MembersList;

	@FXML
	private TextField studentTxt1;

	@FXML
	private Button autoSort;

	@FXML
	private Button undoButton;

	@FXML
	private Button exportButton;

	@FXML
	private ListView<Student> studentsList;

	private String selectedProjectID;

	public Stack<Map<String, Project>> recordStack = new Stack<Map<String, Project>>();//For keeping track of swaps. 

	public ArrayList<Project> projArrayList = new ArrayList<Project>();//To populate the projects ListView

	private Map<String, Student> studentsHash = new HashMap<String, Student>();
	private Map<String, Owner> ownersHash = new HashMap<String, Owner>();
	private Map<String, Company> companiesHash = new HashMap<String, Company>();
	private Map<String, Project> projectsHash = new HashMap<String, Project>();

	public Map<String, Student> getStudentsHash() {
		return this.studentsHash;
	}

	@FXML
	void newDB(ActionEvent event) throws Exception {

		dbm = new DatabaseMethods();
		// check the connection

		try {
			String DB_NAME = "milestone4DB.db";
			dbm.createConnection(DB_NAME);
			dbm.getConnection(DB_NAME);

			dbm.makeTables();
			dbm.addStudentRows(this.studentsHash, DB_NAME);
			dbm.addProjectRows(this.projectsHash, DB_NAME);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void updateDB(ActionEvent event) throws Exception {

		dbm = new DatabaseMethods();

		ArrayList<String> inputStudents = new ArrayList<String>();
		ArrayList<String> inputProjects = new ArrayList<String>();

		try {
			String DB_NAME = "milestone4DB.db";
			dbm.createConnection(DB_NAME);
			dbm.getConnection(DB_NAME);

			dbm.studentSQLStatements(this.studentsHash);
			dbm.projectsSQLStatements(this.projectsHash);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void initialize() throws Exception {

		try {
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream("serialised_data.ser"));
			this.companiesHash = (HashMap<String, Company>) oIn.readObject();
			this.ownersHash = (HashMap<String, Owner>) oIn.readObject();
			this.projectsHash = (HashMap<String, Project>) oIn.readObject();
			this.studentsHash = (HashMap<String, Student>) oIn.readObject();

			oIn.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		setAllProjectList(this.projectsHash);// sets the Projects List view with project arraylist

		prefXAxis.setLabel("Teams");
		prefYAxis.setLabel("Percentage of Team Members with Top 2 Prefrences");
		avgXAxis.setLabel("Teams");
		avgYAxis.setLabel("Average Skill Competency");
		skillsGapXAxis.setLabel("Teams");
		skillsGapYAxis.setLabel("Skills Gap");

		updateStudentList(studentsList, studentsHash);// ignore this

		updateMembersList(project1MembersList, projArrayList.get(0));// constructor of (listView, project arraylist)
		updateMembersList(project2MembersList, projArrayList.get(1));
		updateMembersList(project3MembersList, projArrayList.get(2));
		updateMembersList(project4MembersList, projArrayList.get(3));
		updateMembersList(project5MembersList, projArrayList.get(4));

		projectID1.setText(projArrayList.get(0).getProjID());
		projectID2.setText(projArrayList.get(1).getProjID());
		projectID3.setText(projArrayList.get(2).getProjID());
		projectID4.setText(projArrayList.get(3).getProjID());
		projectID5.setText(projArrayList.get(4).getProjID());

		Map<String, Double> prefSeries = new TreeMap<String, Double>();

		Iterator<Entry<String, Project>> it = this.projectsHash.entrySet().iterator();
		while (it.hasNext()) {

			Entry<String, Project> pairs = it.next();

			prefSeries.put(pairs.getKey(), pairs.getValue().calcPrefPercentage());
		}

		XYChart.Series dataSeries1 = new XYChart.Series();

		Map<String, Double> avgSkillCompetencies = new TreeMap<String, Double>();

		try {

			Iterator<Entry<String, Project>> it2 = this.projectsHash.entrySet().iterator();
			while (it2.hasNext()) {

				Entry<String, Project> pairs = it2.next();

				avgSkillCompetencies.put(pairs.getKey(), pairs.getValue().totalAverageSkillCompetency());

			}

		} catch (InvalidGradeException e) {

			e.printStackTrace();
		}

		XYChart.Series dataSeries2 = new XYChart.Series();

		Map<String, Double> skillGaps = new TreeMap<String, Double>();

		Iterator<Entry<String, Project>> it3 = this.projectsHash.entrySet().iterator();
		while (it3.hasNext()) {

			Entry<String, Project> pairs = it3.next();

			skillGaps.put(pairs.getKey(), pairs.getValue().totalSkillShortFall());

		}

		XYChart.Series dataSeries3 = new XYChart.Series();

		dataSeries1.setName("Preference Percentage");
		for (String c : prefSeries.keySet())
			dataSeries1.getData().add(new XYChart.Data(c, prefSeries.get(c)));
		prefGraph.getData().clear();
		prefGraph.getData().add(dataSeries1);

		dataSeries2.setName("Average Skill Competency");
		for (String c : avgSkillCompetencies.keySet())
			dataSeries2.getData().add(new XYChart.Data(c, avgSkillCompetencies.get(c)));
		averageCompetencyLevel.getData().clear();
		averageCompetencyLevel.getData().add(dataSeries2);

		dataSeries3.setName("Skills Gap");
		for (String c : skillGaps.keySet())
			dataSeries3.getData().add(new XYChart.Data(c, skillGaps.get(c)));
		skillsGapGraph.getData().clear();
		skillsGapGraph.getData().add(dataSeries3);

		setSDs();

	}

	// Based on Charles Thevathayan code in week 6 lecture.
	public void update() throws Exception {

		setAllProjectList(projectsHash);// sets the Projects List view with project arraylist

		updateStudentList(studentsList, studentsHash);//
		updateMembersList(project1MembersList, projArrayList.get(0));// constructor of (listView, project arraylist)
		updateMembersList(project2MembersList, projArrayList.get(1));
		updateMembersList(project3MembersList, projArrayList.get(2));
		updateMembersList(project4MembersList, projArrayList.get(3));// Populating the list views of student in each
																		// project.
		updateMembersList(project5MembersList, projArrayList.get(4));

		projectID1.setText(projArrayList.get(0).getProjID());
		projectID2.setText(projArrayList.get(1).getProjID());
		projectID3.setText(projArrayList.get(2).getProjID());
		projectID4.setText(projArrayList.get(3).getProjID());
		projectID5.setText(projArrayList.get(4).getProjID());// Setting the label of each project

		Map<String, Double> prefSeries = new TreeMap<String, Double>();

		Iterator<Entry<String, Project>> it = this.projectsHash.entrySet().iterator();
		while (it.hasNext()) {

			Entry<String, Project> pairs = it.next();

			prefSeries.put(pairs.getKey(), pairs.getValue().calcPrefPercentage());
		}

		XYChart.Series dataSeries1 = new XYChart.Series();

		Map<String, Double> avgSkillCompetencies = new TreeMap<String, Double>();

		try {

			Iterator<Entry<String, Project>> it2 = this.projectsHash.entrySet().iterator();
			while (it2.hasNext()) {

				Entry<String, Project> pairs = it2.next();

				avgSkillCompetencies.put(pairs.getKey(), pairs.getValue().totalAverageSkillCompetency());

			}

		} catch (InvalidGradeException e) {

			e.printStackTrace();
		}

		XYChart.Series dataSeries2 = new XYChart.Series();

		Map<String, Double> skillGaps = new TreeMap<String, Double>();

		Iterator<Entry<String, Project>> it3 = this.projectsHash.entrySet().iterator();
		while (it3.hasNext()) {

			Entry<String, Project> pairs = it3.next();

			skillGaps.put(pairs.getKey(), pairs.getValue().totalSkillShortFall());

		}

		XYChart.Series dataSeries3 = new XYChart.Series();

		dataSeries1.setName("Preference Percentage");
		for (String c : prefSeries.keySet())
			dataSeries1.getData().add(new XYChart.Data(c, prefSeries.get(c)));
		prefGraph.getData().clear();
		prefGraph.getData().add(dataSeries1);

		dataSeries2.setName("Average Skill Competency");
		for (String c : avgSkillCompetencies.keySet())
			dataSeries2.getData().add(new XYChart.Data(c, avgSkillCompetencies.get(c)));
		averageCompetencyLevel.getData().clear();
		averageCompetencyLevel.getData().add(dataSeries2);

		dataSeries3.setName("Skills Gap");
		for (String c : skillGaps.keySet())
			dataSeries3.getData().add(new XYChart.Data(c, skillGaps.get(c)));
		skillsGapGraph.getData().clear();
		skillsGapGraph.getData().add(dataSeries3);

		setSDs();

	}

	// Also update the GUI based on after the swap.
	public void updateAfterSwap(Map<String, Project> newProjectsHash) throws Exception {

		setAllProjectList(newProjectsHash);
		updateStudentList(studentsList, studentsHash);

		updateMembersList(project1MembersList, projArrayList.get(0));
		updateMembersList(project2MembersList, projArrayList.get(1));
		updateMembersList(project3MembersList, projArrayList.get(2));
		updateMembersList(project4MembersList, projArrayList.get(3));
		updateMembersList(project5MembersList, projArrayList.get(4));// Populating the list views of student in each
																		// project.

		projectID1.setText(projArrayList.get(0).getProjID());
		projectID2.setText(projArrayList.get(1).getProjID());
		projectID3.setText(projArrayList.get(2).getProjID());
		projectID4.setText(projArrayList.get(3).getProjID());
		projectID5.setText(projArrayList.get(4).getProjID());// Setting the label of each project

		// potentially refactor to put the hashmap into the tree maps
		Map<String, Double> prefSeries = new TreeMap<String, Double>();

		Iterator<Entry<String, Project>> it = newProjectsHash.entrySet().iterator();
		while (it.hasNext()) {

			Entry<String, Project> pairs = it.next();

			prefSeries.put(pairs.getKey(), pairs.getValue().calcPrefPercentage());

		}

		XYChart.Series dataSeries1 = new XYChart.Series();

		Map<String, Double> avgSkillCompetencies = new TreeMap<String, Double>();

		try {

			Iterator<Entry<String, Project>> it2 = newProjectsHash.entrySet().iterator();
			while (it2.hasNext()) {

				Entry<String, Project> pairs = it2.next();

				avgSkillCompetencies.put(pairs.getKey(), pairs.getValue().totalAverageSkillCompetency());

			}

		} catch (InvalidGradeException e) {

			e.printStackTrace();
		}

		XYChart.Series dataSeries2 = new XYChart.Series();

		Map<String, Double> skillGaps = new TreeMap<String, Double>();

		Iterator<Entry<String, Project>> it3 = newProjectsHash.entrySet().iterator();
		while (it3.hasNext()) {

			Entry<String, Project> pairs = it3.next();

			skillGaps.put(pairs.getKey(), pairs.getValue().totalSkillShortFall());

		}

		XYChart.Series dataSeries3 = new XYChart.Series();

		dataSeries1.setName("Preference Percentage ");
		for (String c : prefSeries.keySet())
			dataSeries1.getData().add(new XYChart.Data(c, prefSeries.get(c)));
		prefGraph.getData().clear();
		prefGraph.getData().add(dataSeries1);

		dataSeries2.setName("Average Skill Competency");
		for (String c : avgSkillCompetencies.keySet())
			dataSeries2.getData().add(new XYChart.Data(c, avgSkillCompetencies.get(c)));
		averageCompetencyLevel.getData().clear();
		averageCompetencyLevel.getData().add(dataSeries2);

		dataSeries3.setName("Skills Gap");
		for (String c : skillGaps.keySet())
			dataSeries3.getData().add(new XYChart.Data(c, skillGaps.get(c)));
		skillsGapGraph.getData().clear();
		skillsGapGraph.getData().add(dataSeries3);

		setSDs(newProjectsHash);

	}

	public void setAllProjectList(Map<String, Project> projectsHash) {

		// add all projects into an arraylist

		projArrayList.addAll(projectsHash.values());// only 5 projects.

		// Adding items to the list view
		ObservableList<Project> projectsToView = FXCollections.observableArrayList(projArrayList);

		allProjectsList.setItems(projectsToView);

		System.out.println(projectsHash.keySet());
		for (Project p : projectsToView) {
			System.out.println(p.getProjID());
			System.out.println(p);
		}
	}

	/// Method to organise the each oof the 5 projects lists.

	public void updateMembersList(ListView<Student> sl, Project p) {

		System.out.println("--------------------UPDATE MEMEBERS LIST------" + p.getProjID());
		ObservableList<Student> members = FXCollections.observableArrayList();

		members.addAll(p.getMembers().values());

		sl.setItems(members);

	}

	public void updateStudentList(ListView<Student> sl, Map<String, Student> studentsHash) {
		ObservableList<Student> members = FXCollections.observableArrayList();
		sl.setItems(members);

		for (Student s : studentsHash.values()) {
			if (s.getProj() == null) {
				sl.getItems().add(s);
			}

		}
	}

	@FXML
	void removeStudent(ActionEvent event) throws Exception {
		Student stud = null;
		Project projOfStudent = null;
		mo = new MenuOptions();
		String student1ID = studentTxt1.getText();

		if (this.studentsHash.containsKey(student1ID)) {
			stud = this.studentsHash.get(student1ID);

			for (Project p : this.projectsHash.values()) {
				if (p.getMembers().containsKey(student1ID))
					;
				projOfStudent = p;
			}

			System.out.println("REMOVE " + stud.getStudentID() + " " + projOfStudent.getProjID());

		} else if (stud != null && projOfStudent == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Valid Student No Project");
			alert.setContentText("Please upload project information ");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Invalid Student and Project");
			alert.setContentText("Please upload project information ");
			alert.showAndWait();
		}

		projOfStudent.getMembers().remove(student1ID);
		stud.setProj(null);
		update();

	}
//

	public void setSDs() throws Exception {

		String prefSDStr = String.valueOf(sd.calculateSD(mo.compileProjCalcPrefPercentages(projectsHash)));
		String avgCompetencySDStr = String.valueOf(sd.calculateSD(mo.compileProjAverageSkillCompetencies(projectsHash)))
				.concat("%");
		String skillsGapSDStr = String.valueOf(sd.calculateSD(mo.compileProjSkillShortfall(projectsHash)));

		prefSD.setText(prefSDStr);
		avgCompetencySD.setText(avgCompetencySDStr);
		skillsGapSD.setText(skillsGapSDStr);

	}

	public void setSDs(Map<String, Project> newProjectsHash) throws Exception {

		String prefSDStr = String.valueOf(sd.calculateSD(mo.compileProjCalcPrefPercentages(newProjectsHash)));
		String avgCompetencySDStr = String
				.valueOf(sd.calculateSD(mo.compileProjAverageSkillCompetencies(newProjectsHash))).concat("%");
		String skillsGapSDStr = String.valueOf(sd.calculateSD(mo.compileProjSkillShortfall(newProjectsHash)));

		prefSD.setText(prefSDStr);
		avgCompetencySD.setText(avgCompetencySDStr);
		skillsGapSD.setText(skillsGapSDStr);

	}

	// Sets the selected Project into a project ID string
	@FXML
	void sumbitProject(MouseEvent event) {

		Project selectedProject = allProjectsList.getSelectionModel().getSelectedItem();
		selectedProjectID = selectedProject.getProjID();

	}

	@FXML
	void addStudent(ActionEvent event) throws Exception {
		mo = new MenuOptions();
		String studentID = studentTxt1.getText();
		String projectID = selectedProjectID;

		try {

			if (this.projectsHash.get(projectID).checkStudentConflicts(studentID) == true) {

			} else if (this.projectsHash.get(projectID).repeatedMemberCheck(studentID) == true) {

			} else if (this.projectsHash.get(projectID).invalidMemberCheck(this.studentsHash.get(studentID)) == true) {

			} else if (this.projectsHash.get(projectID).checkTeamSize() == 4) {

			} else if (this.projectsHash.get(projectID).checkTeamSize() == 3) {

				if (this.projectsHash.get(projectID).checkLeader() == false) {

				} else if (this.projectsHash.get(projectID).checkPersonalityImbalance() == true) {

				} else {

					Student studentToAdd = this.studentsHash.get(studentID);
					Project selectedProject = this.projectsHash.get(projectID);

					selectedProject.getMembers().put(studentToAdd.getStudentID(), studentToAdd);
					studentToAdd.setProj(selectedProject);
					update();
				}

			} else {
				Student studentToAdd = this.studentsHash.get(studentID);
				Project selectedProject = this.projectsHash.get(projectID);

				selectedProject.getMembers().put(studentToAdd.getStudentID(), studentToAdd);
				update();
			}

		} catch (PersonalityImbalanceException pie) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Personality Imbalance Error");
			alert.setHeaderText("This team has less than 3 types of personalities");
			alert.setContentText("Please change the team members ");
			alert.showAndWait();
		}

		catch (InvalidMemberException ime) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Member Exception");
			alert.setHeaderText("This student already has been allocated to another project.");
			alert.setContentText(
					"Please remove the student from their project first before reallocating them to anotheer project.");
			alert.showAndWait();
		}

		catch (RepeatedMemberException rme) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Repeated Member Exception");
			alert.setHeaderText("This student has already been allocated to this project.");
			alert.setContentText("Try again.");
			alert.showAndWait();
		}

		catch (AddingTooManyProjectMembersException atmpme) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Too Many Students In This Team Exception");
			alert.setHeaderText("Only a maximum of 4 students in a team.");
			alert.setContentText("Please remove a student");
			alert.showAndWait();

		} catch (StudentConflictException sce) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Student Conflict Exception");
			alert.setHeaderText("Someone in this team has a conflict with student " + studentID + ".");
			alert.setContentText("Please swap with different studnet ");
			alert.showAndWait();

		} catch (NoLeaderException nle) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Leader Exception");
			alert.setHeaderText("There are no leaders (personality type A) in project " + projectID + ".");
			alert.setContentText("Please add at least one student with a type A personality");
			alert.showAndWait();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void undo(ActionEvent event) throws Exception {

		if (recordStack.size() > 0) {

			System.out.println("RecordStack before pop =" + recordStack.size());

			this.projectsHash = recordStack.pop();

			System.out.println("RecordStack after pop =" + recordStack.size());

			for (Project proj : this.projectsHash.values()) {
				System.out.println(proj.getProjID() + " " + proj.getMembers().keySet());
			}

			System.out.println("RECORD STACK POP UNDO ");

			updateAfterSwap(this.projectsHash);

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("You cannot do further undo!");
			alert.setHeaderText("Already undone all swaps.");
			alert.setContentText("You've already undone all the swaps");
			alert.showAndWait();
		}

	}

	@FXML
	void swapProjects(ActionEvent event) throws Exception {
		mo = new MenuOptions();
		sa = new SortAlgorithm();
		ds = new DeserialieSerialise();
		String student1ID = studentTxt1.getText();
		String student2ID = studentIDTxt2.getText();

		Map<String, Project> newProjectsHash = new HashMap<String, Project>();

		// validate if the students exist

		if (this.studentsHash.get(student1ID) == null || this.studentsHash.get(student2ID) == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("At least one of these students do not exist in the database");
			alert.setContentText("Please upload students' information ");
			alert.showAndWait();
		} else if (this.studentsHash.get(student1ID).getProj() == null
				|| this.studentsHash.get(student2ID).getProj() == null) {
			// else if condition checking if the students have projects.

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("At least one of these students do not exist in the database");
			alert.setContentText("Please upload students' information ");
			alert.showAndWait();
		}

		else {// This was to sort out the problem of memory allocation of different projects
				// hashmap.

			// serialise the orignal projects
			ds.serialiseProjects(this.projectsHash);

			newProjectsHash = ds.deserialiseProjects("serialised_Projects.ser");

			// push the original state.
			recordStack.push(newProjectsHash);
			// update the GUI after the swap.
			this.projectsHash = sa.swapStudents(student1ID, student2ID, projectsHash, studentsHash);

			updateAfterSwap(this.projectsHash);
		}

	}

	// Exports the changes to the serialised file serialised_data.ser

	@FXML
	void exportToSer(ActionEvent event) {

		ds.serialise(companiesHash, ownersHash, projectsHash, studentsHash);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exported Data ");
		alert.setHeaderText("Exported data to serialised file");
		alert.setContentText(
				"All information about student, project, companies and owners can be found in serialised_data.ser. ");
		alert.showAndWait();

	}

}
