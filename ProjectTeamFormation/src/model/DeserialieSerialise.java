package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import model.*;

public class DeserialieSerialise {

	// Writing to text file
	// https://stackoverflow.com/questions/15413467/writing-from-hashmap-to-a-txt-file
	// Print keys and values
	// https://www.techiedelight.com/print-all-keys-and-values-map-java/

	public void serialise(Map<String, Company> companies, Map<String, Owner> owners, Map<String, Project> projects,
			Map<String, Student> students) {

		try {

			String currentDirectory = System.getProperty("user.dir");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serialised_data.ser"));

			out.writeObject(companies);
			out.writeObject(owners);
			out.writeObject(projects);
			out.writeObject(students);
			out.close();
			System.out.println("Serialised Companies, Owners, Projects & Students information to serialised_data.ser");

		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void serialiseProjects(Map<String, Project> projects) {

		try {
			String currentDirectory = System.getProperty("user.dir");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serialised_Projects.ser"));

			out.writeObject(projects);
			out.close();
			System.out.println("Serialised to serialised_Projects.ser");

		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void deserialise(Map<String, Company> companies, Map<String, Owner> owners, Map<String, Project> projects,
			Map<String, Student> students) {

		try {
			String currentDirectory = System.getProperty("user.dir");
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream("serialised_data.ser"));

			companies = (HashMap<String, Company>) oIn.readObject();
			owners = (HashMap<String, Owner>) oIn.readObject();
			projects = (HashMap<String, Project>) oIn.readObject();
			students = (HashMap<String, Student>) oIn.readObject();

			oIn.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("serialised_data.txt not found. ");
			fnfe.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ClassNotFoundException c) {

			c.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, Project> deserialiseProjects(String filename) {
		Map<String, Project> projects = new HashMap<String, Project>();
		try {

			String currentDirectory = System.getProperty("user.dir");
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(filename));

			projects = (HashMap<String, Project>) oIn.readObject();

			oIn.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("companies.txt not found. ");
			fnfe.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("Class company not found . ");
			c.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return projects;

	}

	public Map<String, Company> deserialiseCompanies(String filename) {
		Map<String, Company> companies = new HashMap<String, Company>();
		try {

			String currentDirectory = System.getProperty("user.dir");
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(filename));

			companies = (HashMap<String, Company>) oIn.readObject();

			oIn.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("companies.txt not found. ");
			fnfe.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("Class company not found . ");
			c.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return companies;

	}

	public Map<String, Owner> deserialiseOwners(String filename) {
		Map<String, Owner> owners = new HashMap<String, Owner>();
		try {

			String currentDirectory = System.getProperty("user.dir");
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(filename));

			owners = (HashMap<String, Owner>) oIn.readObject();

			oIn.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("companies.txt not found. ");
			fnfe.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("Class company not found . ");
			c.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return owners;

	}

	public Map<String, Student> deserialiseStudents(String filename) {
		Map<String, Student> students = new HashMap<String, Student>();
		try {

			String currentDirectory = System.getProperty("user.dir");
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(filename));

			students = (HashMap<String, Student>) oIn.readObject();

			oIn.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("companies.txt not found. ");
			fnfe.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("Class company not found . ");
			c.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return students;

	}

}
