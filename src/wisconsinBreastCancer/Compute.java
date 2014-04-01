package wisconsinBreastCancer;

import java.util.ArrayList;
import java.util.List;

public class Compute { 
	
	 static Double[] dataSample;//stores a whole row of data from file
	 static List<Double[]> M_Count = new ArrayList<Double[]>();//stores all M related frequency
	 static List<Double[]> B_Count = new ArrayList<Double[]>();//stores all B related frequency
	 
	 
	 static List<Double[]> M_Count_CONST = new ArrayList<Double[]>();//stores M ranges
	 static List<Double[]> B_Count_CONST = new ArrayList<Double[]>();//stores B ranges
	 
	 //data counters
	 static Integer[] totCountB = new Integer[10];
	 static Integer[] totCountM = new Integer[10];
	 
	 
	 //the hard coded values were based on the graph observations
	 //an attempt to group the data set evenly was made based on graph inspection
	 
	 //WIDTH BINNING - CONSTANT values for WIDTH range based on graph observations
	 static Double[] M_RANGE_MeanRadius          =  {10.040,  18.94, 21.24,  25.84, 28.17}; //M mean radius ranges;
	 static Double[] M_RANGE_texture             =  {11.83, 15.49, 19.18, 22.84, 26.51, 30.17, 33.84, 39.4};  
	 static Double[] M_RANGE_perimeter           =  {71.85, 95.20, 110.8, 126.4, 141.9, 157.44, 173.0, 188.6};  
	 static Double[] M_RANGE_area                =  {365.0,789.46, 1074.72, 1359.99, 1645.27,1930.52, 2215.80,2501.10};  
	 static Double[] M_RANGE_smoothness          =  {0.045, 0.07, 0.076, 0.082, 0.093, 0.105, 0.117,0.128, 0.164};
	 static Double[] M_RANGE_compactness         =  {0.019, 0.041, 0.085, 0.106, 0.128, 0.15, 0.172, 0.237,0.345}; 
	 static Double[] M_RANGE_concavity           =  {0.0239, 0.0845, 0.145, 0.2052, 0.266, 0.326, 0.387, 0.480}; 
	 static Double[] M_RANGE_concave             =  {0.0201, 0.0512, 0.0820, 0.113,0.144, 0.16, 0.19, 0.221, 0.252};
	 static Double[] M_RANGE_symmetry            =  {0.1172, 0.1720, 0.2085, 0.250, 0.2812, 0.32, 0.36};
	 static Double[] M_RANGE_fractaldimension    =  {0.049, 0.064, 0.0766, 0.09, 0.11}; 
	                                                           	
	 static Double[] B_RANGE_MeanRadius          =  {6.95, 11.32, 12.79, 14.25, 17.91}; 
	 static Double[] B_RANGE_texture             =  {9.65, 12.89, 16.12, 19.35, 22.57, 25.78, 27.41, 30.64, 33.9};  
	 static Double[] B_RANGE_perimeter           =  {43.60, 57.93, 67.40,76.84, 86.30, 95.75, 105.20, 115.0};  
	 static Double[] B_RANGE_area                =  {143.44, 313.20, 426.37, 539.52, 652.70, 765.84, 879.00, 992.16}; 
	 static Double[] B_RANGE_smoothness          =  {0.045, 0.07, 0.128, 0.164}; 
	 static Double[] B_RANGE_compactness         =  {0.019, 0.345};
	 static Double[] B_RANGE_concavity           =  {0.0000, 0.0615, 0.1230, 0.2150, 0.338, 0.47}; 
	 static Double[] B_RANGE_concave             =  {0.0000, 0.0181, 0.0362, 0.0542, 0.0723, 0.099};
	 static Double[] B_RANGE_symmetry            =  {0.0918,  0.2171, 0.2529, 0.2886, 0.32};
	 static Double[] B_RANGE_fractaldimension    =  {0.0490,0.06, 0.07, 0.08, 0.09, 0.10}; 
	
	 //COUNT/FEQUENCY HOLDERS FOR OCCURENCES IN EACH RANGE
	 static Double[] M_MeanRadius          =  new Double[5];
	 static Double[] M_texture             =  new Double[8];
	 static Double[] M_perimeter           =  new Double[8];
	 static Double[] M_area                =  new Double[8];
	 static Double[] M_smoothness          =  new Double[9];
	 static Double[] M_compactness         =  new Double[9];
	 static Double[] M_concavity           =  new Double[8];
	 static Double[] M_concave             =  new Double[9];
	 static Double[] M_symmetry            =  new Double[7];
	 static Double[] M_fractaldimension    =  new Double[5];
	                                                        
	 static Double[] B_MeanRadius          =  new Double[5];
	 static Double[] B_texture             =  new Double[9];
	 static Double[] B_perimeter           =  new Double[8];
	 static Double[] B_area                =  new Double[8];
	 static Double[] B_smoothness          =  new Double[4];
	 static Double[] B_compactness         =  new Double[2];
	 static Double[] B_concavity           =  new Double[6];
	 static Double[] B_concave             =  new Double[6];
	 static Double[] B_symmetry            =  new Double[5];
	 static Double[] B_fractaldimension    =  new Double[6]; 
                                          
	 
	
	public Compute(){
		//if the list is not set yet
		//initialize it
	if(M_Count.isEmpty()){
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
		//initialize array
		for(int i = 0; i < M_Count.size(); i++){
			
			for(int j = 0; j < M_Count.get(i).length; j++){
				
				M_Count.get(i)[j] = 0.0;
				 
			}
		}
		//initialize array
		for(int i = 0; i < B_Count.size(); i++){
			
			for(int j = 0; j < B_Count.get(i).length; j++){
				
				 
				B_Count.get(i)[j] = 0.0;
			}
		}
		
		//initialize array
		for(int i = 0; i < totCountB.length; i++){
			
			totCountB[i] = totCountM[i] = 0;
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
