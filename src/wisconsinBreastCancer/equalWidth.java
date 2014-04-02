
/* 
 * Author:  Joao Sousa
 * SFSU Spring 2014
 * CSC 869
 * 04/04/2014
 * Mini Project # 2
 * This program performs data classification by implementing the 
 * Naive Bayesan model
 * 
 * This file looks similar to Compute.java and gaussianDistrBtn.java
 * as it's a copy and slight modification to hold the design structure
 * for this model.
 * 
 * Notice the hard coded values for the ranges:  These were based 
 * on the histogram observation.
 * 
 * M_RANGE arrays hold M data for each attribute
 * B_RANGE arrays hold B data for each attribute
 * 
 * The arrays are then inserted into a list
 * such as: M_Count_CONST or B_Count_CONST (these hold the array with ranges) and
 * M_Count or B_Count (these hold the frequency count)
 * 
 * Some counters and iteration counters are used to 
 * keep track of the data stored in teh arrays and lists
 * 
 * totCountB
 * totCountM
 * dataSample
 * 
 * What's different in this file?
 * 
 * NO hard coded range values
 * 
 */

package wisconsinBreastCancer;

import java.util.ArrayList;
import java.util.List;

public class equalWidth {

	 // the graphs had a even width distribution between 14 to 18
	 static int WIDTH = 16;//based on observation of graphs set to 16
	 
	 static Double[] dataSample;
	 static List<Double[]> M_Count = new ArrayList<Double[]>();
	 static List<Double[]> B_Count = new ArrayList<Double[]>();
	 
	 static List<Double[]> M_Count_CONST = new ArrayList<Double[]>();
	 static List<Double[]> B_Count_CONST = new ArrayList<Double[]>();
	 
	 static String[] attributes = { "MeanRadius",  
									 "texture",  
									 "perimeter",  
									 "area",  
									 "smoothness",  
									 "compactness",   
									 "concavity",  
									 "concave",  
									 "symmetry",  
									 "fractaldimension"};
	 
	 static Integer[] totCountB = new Integer[WIDTH];
	 static Integer[] totCountM = new Integer[WIDTH];
	 static Double[]	max		= new Double[10];
	 static Double[]	min		= new Double[10];
	 
	  //WIDTH BINNING - CONSTANT values for WIDTH range based on graph
	  //observation
		 static Double[] M_RANGE_MeanRadius       =  new Double[WIDTH];
		 static Double[] M_RANGE_texture          =  new Double[WIDTH];
		 static Double[] M_RANGE_perimeter        =  new Double[WIDTH];
		 static Double[] M_RANGE_area             =  new Double[WIDTH];
		 static Double[] M_RANGE_smoothness       =  new Double[WIDTH];
		 static Double[] M_RANGE_compactness      =  new Double[WIDTH];
		 static Double[] M_RANGE_concavity        =  new Double[WIDTH];
		 static Double[] M_RANGE_concave          =  new Double[WIDTH];
		 static Double[] M_RANGE_symmetry         =  new Double[WIDTH];
		 static Double[] M_RANGE_fractaldimension =  new Double[WIDTH];
		                                                               
		 static Double[] B_RANGE_MeanRadius       =  new Double[WIDTH];
		 static Double[] B_RANGE_texture          =  new Double[WIDTH];
		 static Double[] B_RANGE_perimeter        =  new Double[WIDTH];
		 static Double[] B_RANGE_area             =  new Double[WIDTH];
		 static Double[] B_RANGE_smoothness       =  new Double[WIDTH];
		 static Double[] B_RANGE_compactness      =  new Double[WIDTH];
		 static Double[] B_RANGE_concavity        =  new Double[WIDTH];
		 static Double[] B_RANGE_concave          =  new Double[WIDTH];
		 static Double[] B_RANGE_symmetry         =  new Double[WIDTH];
		 static Double[] B_RANGE_fractaldimension =  new Double[WIDTH];
		                                                                 
		 //COUNT HOLDERS FOR OCCURENCES IN EACH RANGE                    
		 static Double[] M_MeanRadius          =  new Double[WIDTH];     
		 static Double[] M_texture             =  new Double[WIDTH];     
		 static Double[] M_perimeter           =  new Double[WIDTH];     
		 static Double[] M_area                =  new Double[WIDTH];     
		 static Double[] M_smoothness          =  new Double[WIDTH];     
		 static Double[] M_compactness         =  new Double[WIDTH];     
		 static Double[] M_concavity           =  new Double[WIDTH];     
		 static Double[] M_concave             =  new Double[WIDTH];     
		 static Double[] M_symmetry            =  new Double[WIDTH];     
		 static Double[] M_fractaldimension    =  new Double[WIDTH];     
		                                                                 
		 static Double[] B_MeanRadius          =  new Double[WIDTH];     
		 static Double[] B_texture             =  new Double[WIDTH];     
		 static Double[] B_perimeter           =  new Double[WIDTH];     
		 static Double[] B_area                =  new Double[WIDTH];     
		 static Double[] B_smoothness          =  new Double[WIDTH];     
		 static Double[] B_compactness         =  new Double[WIDTH];     
		 static Double[] B_concavity           =  new Double[WIDTH];     
		 static Double[] B_concave             =  new Double[WIDTH];     
		 static Double[] B_symmetry            =  new Double[WIDTH];     
		 static Double[] B_fractaldimension    =  new Double[WIDTH];     
	
		 //Main constructor
	public equalWidth(){
		
		//If data is not yet initialized,
		//then set it
	  if(M_Count.isEmpty()){
		

			 //Add data structure to list and initialize 
			 //counters to 0  
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
		
		for(int i = 0; i < max.length; i++){
			
			
			max[i] = 0.0;
			min[i] = Double.MAX_VALUE;
		}
		
		for(int i = 0; i < M_Count.size(); i++){
			
			for(int j = 0; j < M_Count.get(i).length; j++){
				
				M_Count.get(i)[j] = 0.0;
				totCountB[j] = totCountM[j] = 0;
				
				 
			}
		}
		
		for(int i = 0; i < B_Count.size(); i++){
			
			for(int j = 0; j < B_Count.get(i).length; j++){
				
				 
				B_Count.get(i)[j] = 0.0;
			}
		}
	  }
		
	}
	
	public equalWidth(Double[] dataValues){
		
		 
		dataSample = dataValues;
		
	}
	
	public equalWidth(int width){
		
		WIDTH = width;
	}

	static Double  testResul(){
		
		if(dataSample[0] == 1.0){
			
			return 0.5;
		}
		
		
		return 0.01;
	}
}
