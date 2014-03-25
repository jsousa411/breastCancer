package wisconsinBreastCancer;

import java.util.ArrayList;
import java.util.List;

public class Compute {
	
	
	 
	 
	 static Double[] dataSample;
	 static List<Double[]> M_Count = new ArrayList<Double[]>();
	 static List<Double[]> B_Count = new ArrayList<Double[]>();
	 
	 static List<Double[]> M_Count_CONST = new ArrayList<Double[]>();
	 static List<Double[]> B_Count_CONST = new ArrayList<Double[]>();
	 
	 //WIDTH BINNING - CONSTANT values for WIDTH range
	 static Double[] M_RANGE_MeanRadius          =  {10.040, 14.34, 16.64, 18.94, 21.24, 23.54, 25.84, 28.17}; //new Double[7];
	 static Double[] M_RANGE_texture             =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_perimeter           =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_area                =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_smoothness          =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_compactness         =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_concavity           =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_concave             =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_symmetry            =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] M_RANGE_fractaldimension    =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	                                                           	
	 static Double[] B_RANGE_MeanRadius          =  {7.60, 8.40, 9.86, 11.32, 12.79, 14.25, 15.71, 17.91};//new Double[7];
	 static Double[] B_RANGE_texture             =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_perimeter           =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_area                =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_smoothness          =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_compactness         =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_concavity           =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_concave             =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	 static Double[] B_RANGE_symmetry            =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; // new Double[5];
	 static Double[] B_RANGE_fractaldimension    =  {12.050, 13.210, 15.51, 17.81, 20.12, 22.41, 24.71, 28.161}; //new Double[5];
	
	 //COUNTE HOLDERS FOR OCCURENCES IN EACH RANGE
	 static Double[] M_MeanRadius          =  new Double[7];
	 static Double[] M_texture             =  new Double[5];
	 static Double[] M_perimeter           =  new Double[5];
	 static Double[] M_area                =  new Double[5];
	 static Double[] M_smoothness          =  new Double[5];
	 static Double[] M_compactness         =  new Double[5];
	 static Double[] M_concavity           =  new Double[5];
	 static Double[] M_concave             =  new Double[5];
	 static Double[] M_symmetry            =  new Double[5];
	 static Double[] M_fractaldimension    =  new Double[5];
	                                                        
	 static Double[] B_MeanRadius          =  new Double[7];
	 static Double[] B_texture             =  new Double[5];
	 static Double[] B_perimeter           =  new Double[5];
	 static Double[] B_area                =  new Double[5];
	 static Double[] B_smoothness          =  new Double[5];
	 static Double[] B_compactness         =  new Double[5];
	 static Double[] B_concavity           =  new Double[5];
	 static Double[] B_concave             =  new Double[5];
	 static Double[] B_symmetry            =  new Double[5];
	 static Double[] B_fractaldimension    =  new Double[5]; 
                                          
	 
	
	public Compute(){
		
		M_Count.add(M_MeanRadius);
		M_Count.add(M_texture);
		M_Count.add(M_perimeter);
		M_Count.add(M_area);
		M_Count.add(M_smoothness);
		M_Count.add(M_compactness);
		M_Count.add(M_concavity);
		M_Count.add(M_concave);
		M_Count.add(M_symmetry);
		M_Count.add(M_fractaldimension);
		
		B_Count.add(B_MeanRadius);        
		B_Count.add(B_texture);         
		B_Count.add(B_perimeter);       
		B_Count.add(B_area);            
		B_Count.add(B_smoothness);      
		B_Count.add(B_compactness);     
		B_Count.add(B_concavity);       
		B_Count.add(B_concave);         
		B_Count.add(B_symmetry);        
		B_Count.add(B_fractaldimension);
		
		M_Count_CONST.add(M_RANGE_MeanRadius);      
		M_Count_CONST.add(M_RANGE_texture);         
		M_Count_CONST.add(M_RANGE_perimeter);       
		M_Count_CONST.add(M_RANGE_area);            
		M_Count_CONST.add(M_RANGE_smoothness);      
		M_Count_CONST.add(M_RANGE_compactness);     
		M_Count_CONST.add(M_RANGE_concavity);       
		M_Count_CONST.add(M_RANGE_concave);         
		M_Count_CONST.add(M_RANGE_symmetry);        
		M_Count_CONST.add(M_RANGE_fractaldimension);
		                            
		B_Count_CONST.add(B_RANGE_MeanRadius);      
		B_Count_CONST.add(B_RANGE_texture);         
		B_Count_CONST.add(B_RANGE_perimeter);       
		B_Count_CONST.add(B_RANGE_area);            
		B_Count_CONST.add(B_RANGE_smoothness);      
		B_Count_CONST.add(B_RANGE_compactness);     
		B_Count_CONST.add(B_RANGE_concavity);       
		B_Count_CONST.add(B_RANGE_concave);         
		B_Count_CONST.add(B_RANGE_symmetry);        
		B_Count_CONST.add(B_RANGE_fractaldimension);
		
		for(int i = 0; i < M_Count.size(); i++){
			
			for(int j = 0; j < M_Count.get(i).length; j++){
				
				M_Count.get(i)[j] = 0.0;
				B_Count.get(i)[j] = 0.0;
			}
		}
		
	}
	
	public Compute(Double[] dataValues){
		
		dataSample = dataValues;
		
	}

	static Double  testResul(){
		
		if(dataSample[0] == 1.0){
			
			return 0.5;
		}
		
		
		return 0.01;
	}
	
}
