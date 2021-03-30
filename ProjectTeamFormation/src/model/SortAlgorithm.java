package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortAlgorithm {

	private StandardDeviation sd = new StandardDeviation();

	private HashSet<Student> potentialTeamMembers = new HashSet<Student>();

	// Setting the threshold that the program will need to consider for the sutoSwap
	// method

	public double calTotalSD(Map<String, Project> projectsHash)
			throws InvalidGradeException, UnpopularProjectException {

		double prefSD = sd.calculateSD(sd.compileProjCalcPrefPercentages(projectsHash));
		double avgSkillSD = sd.calculateSD(sd.compileProjAverageSkillCompetencies(projectsHash));
		double skillGapSD = sd.calculateSD(sd.compileProjSkillShortFall(projectsHash));

		double totalSD = prefSD + avgSkillSD + skillGapSD;

		return totalSD;

	}

	public Map<String, Project> autoSwap(Map<String, Project> projectsHash, Map<String, Student> studentsHash) {

		double autoAssignTotalSD = 0.0, thresholdSD = 0.0;

		int iterations = 0;

		ArrayList<String> studentIDs = new ArrayList<String>();

		Map<String, Project> reassignedProjects = new HashMap<String, Project>();

		Map<String, Project> idealReassignedProjects = new HashMap<String, Project>();

		boolean foundSmallestSD = false;

		try {
			thresholdSD = calTotalSD(projectsHash);// set the threshold
		} catch (InvalidGradeException e1) {

			e1.printStackTrace();
		} catch (UnpopularProjectException e1) {

			e1.printStackTrace();
		}

		for (String id : studentsHash.keySet()) {
			studentIDs.add(id);
		}

		while (foundSmallestSD == false) {

			System.out.println("=========outer NUMBER ==== " + iterations);
			for (int i = 0; i < studentIDs.size() - 1; i++) {
				for (int j = i + 1; j < studentIDs.size(); j++) {

					try {
						System.out.println("studentIDs.get(i)  ===" + studentIDs.get(i));
						System.out.println("studentIDs.get(j)  ===" + studentIDs.get(j));
						reassignedProjects = swapStudents(studentIDs.get(i), studentIDs.get(j), projectsHash,
								studentsHash);

						autoAssignTotalSD = calTotalSD(reassignedProjects);

						System.out.println("thresholdSD = " + thresholdSD);

						System.out.println("autoAssignTotalSD = " + autoAssignTotalSD);

						if (autoAssignTotalSD < thresholdSD) {
							thresholdSD = autoAssignTotalSD;
							iterations = 0;

							idealReassignedProjects = reassignedProjects;// keep the projects hashmap that has the

							System.out.println("foundSmallestSD = " + foundSmallestSD);
							System.out.println("=========ITERATION NUMBER ==== " + iterations);
							foundSmallestSD = true;
							return idealReassignedProjects;
						} else {
							iterations++;// continue iterating if the total SD is greater than threshold
							foundSmallestSD = false;

							if (iterations == 30) {
								return idealReassignedProjects;
							}
							System.out.println("foundSmallestSD = " + foundSmallestSD);
							System.out.println("=========ITERATION NUMBER ==== " + iterations);

						} // if else
					} catch (Exception e) {
						e.printStackTrace();
						continue;// continue the while loop even if exceptions are found.
					}
				}

			}

		}
		return idealReassignedProjects;
	}// while loop

//Generic swap students method. 

	public Map<String, Project> swapStudents(String studentID1, String studentID2, Map<String, Project> projectsHash,
			Map<String, Student> studentsHash) throws NoLeaderException, UnpopularProjectException {
		Student stu1 = null, stu2 = null;
		Project proj1 = null, proj2 = null;

		for (Project proj : projectsHash.values()) {
			if (proj.getMembers().containsKey(studentID1)) {
				proj1 = proj;

			} else if (proj.getMembers().containsKey(studentID2)) {
				proj2 = proj;

			} else {

			}
		}
		System.out.println("Project 1 = " + proj1);
		System.out.println("Project 2 = " + proj2);
		if (studentsHash.containsKey(studentID1) && studentsHash.containsKey(studentID2) && proj1 != null
				&& proj2 != null) {

			if (proj1 != proj2) {
				stu1 = studentsHash.get(studentID1);
				stu2 = studentsHash.get(studentID2);

				proj1.getMembers().remove(studentID1);
				proj2.getMembers().remove(studentID2);

				proj1.getMembers().put(studentID2, stu2);
				proj2.getMembers().put(studentID1, stu1);

				// Just in case if one of the projects doesn't have a leader after swap.
				// swap back.
				if (proj1.checkLeader() == false || proj2.checkLeader() == false) {

					System.out.println("change");

					proj1.getMembers().remove(studentID2);
					proj2.getMembers().remove(studentID1);

					proj1.getMembers().put(studentID1, stu1);
					proj2.getMembers().put(studentID2, stu2);
				}
				return projectsHash;
			}

		} else if (studentsHash.containsKey(studentID1) && studentsHash.containsKey(studentID2) && proj1 == proj2) {

			return projectsHash;
		}

		else {
			System.out.println("This swap didn't meet the requirements. ");
		}

		return projectsHash;
	}

}
