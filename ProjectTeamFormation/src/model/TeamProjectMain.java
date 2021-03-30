package model;

import java.util.Scanner;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

public class TeamProjectMain {
//Some of the code is from my previous submissions in Programming Fundamentals Sem 1 2020. 


	public static void main(String[] args) throws IOException{
		
		//Make an object to pass to my mediator class
		MenuOptions mo= new MenuOptions();
		
		 
		int input=0;
		
		boolean endProgram = false;
		boolean validInput = false;
		boolean  validID=false;
	

		
		Scanner sc = new Scanner (System.in);
		
	while (!endProgram){

			do {

			try {
					//Making the menu interface. 
				String[] menu = { "\n Please select and option ", 
							"\n (1) Add Company",
							"\n (2) Add Project Owner", 
							"\n (3) Add Project",
							"\n (4) Upload Companies, Owners,Projects and Students infromation",
							"\n (5) Capture Student Personalities",
							"\n (6) Add Student Preferences" ,
							"\n (7) Shortlist Projects",
							"\n (8) Form Teams", 
							"\n (9) Display Team Fitness Metrics", 
							"\n (10) Export data to Text Files",
							"\n (11) Export data to Serialised Files",
							"\n (12) Auto Team Allocation Algorithm",
							"\n (13) Quit"
							
					};	


				for (int i = 0; i < menu.length; i++) {
					System.out.println(menu[i]);
				}
				System.out.println("Enter an option: ");
				input = sc.nextInt();
				
					

					if (input >0 && input<14) {
					
						validInput = true;
						break; 
					}
					else {
						System.out.println(input+" is not an option");
						validInput=false;
					}
				
			}
			
			catch (InputMismatchException ime) {
				System.out.println("Incorrect input type");
				validInput = false;
				sc.next();
				
			}
			catch(Exception e) {
				System.out.println("Something went wrong");
				validInput = false;
				sc.next();
			
			} finally {
			
			}
			
				
			}while(!validInput);
			
			
			switch(input) {
				case 1: 
					//Below is the menu input for the companies
					mo.menuCompanies();
				
						break;
					
				case 2:
					
					//Below is the menu input for owners. 
					mo.menuOwners();
					
					break;
					
				case 3:
					//Below is for the menu input of projects 
					mo.menuProjects();
					
					break;
				
				case 4:
					System.out.println("Upload Companies, Owners, Projects & Students information");
					mo.uploads();
					
					break;
		
			
				case 5:
					System.out.println("Capture Student Personalities and conflicts");
					
			
					mo.captureStudentPersonalities();
				
					break;
				case 6:
					System.out.println("Add preferences ");
					
			
					mo.addStudentPreferences();
			
	
					break;
			
				case 7:
					System.out.println("Shortlist projects");
					mo.shortlistPreferences();
					break;
					
				case 8:
					System.out.println("Form Team");
					mo.menuFormTeam();
					break;
					
				case 9:
					System.out.println("Display Team Fitness Metrics ");
				
					mo.displayTeamFitnessMetrics();
				
					
					break;
				case 10:
					
					System.out.println("Export to Text file");
					mo.exportDataToTxt();
					
					
					
					break;
					
				case 11:
				
					System.out.println("Export to Serialised File");
				
					mo.serialiseData();
				
					break;
				case 12:
					System.out.println("Call auto sort algorithm.");
					mo.callAutoSort();
					break;
					
				case 13:
					
					System.out.println("Goodbye");
					endProgram=true;
				
					break;
					
					
					
	

				}//switch 
			
			

			}//WHILE!Endprogram making sure it doesn't quit. 

			}//main
}
