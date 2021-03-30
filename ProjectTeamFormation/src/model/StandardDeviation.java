package model;
import java.util.ArrayList;
import java.util.Map;

public class StandardDeviation {
	//Code based off from Dipto Pratyaksa's tutorial. 

  


    public static double calculateSD(ArrayList<Double> numbers)
    {
        double sum = 0.0, square = 0.0;
       
        int length = numbers.size();

        for(double num : numbers) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numbers) {
            square += Math.pow(num - mean, 2);
          
        }
 
        return Math.sqrt(square/length);
    }
    
    
    

public ArrayList<Double> compileProjAverageSkillCompetencies(Map<String,Project> projectsHash) throws InvalidGradeException {
	
	 
	ArrayList<Double> projAverageSkillCompetencies = new ArrayList<Double>();

	
	for(Project p:projectsHash.values()) {
		
		if(p.getMembers().size()!=0) {
			
				projAverageSkillCompetencies.add(p.totalAverageSkillCompetency());
				
			}
		}
	
	return projAverageSkillCompetencies;
	
}

public ArrayList<Double> compileProjCalcPrefPercentages(Map<String,Project> projectsHash) throws InvalidGradeException,UnpopularProjectException   {
	
	ArrayList<Double> projCalcPrefPercentage = new ArrayList<Double>();

	
	for(Project p:projectsHash.values()) {
		
		if(p.getMembers().size()!=0) {
		
			if(p.calcPrefPercentage()==0.0) {
				
				projCalcPrefPercentage.add(0.0);
			}else {
				projCalcPrefPercentage.add(p.calcPrefPercentage());
			}
		}
	}
	
	return projCalcPrefPercentage;
	

}

public ArrayList<Double> compileProjSkillShortFall(Map<String,Project>projectsHash) throws InvalidGradeException {
	ArrayList<Double> projSkillShortFall = new ArrayList<Double>();
	for(Project p:projectsHash.values()) {
		
		if(p.getMembers().size()!=0) {
			
			projSkillShortFall.add(p.totalSkillShortFall());
					
		}
		
	}
	
	return projSkillShortFall;
}

    
  

    
}


