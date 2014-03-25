package wisconsinBreastCancer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//breastCancer object is a data Type object
//used to hold the data for Breast Cancer results
public class breastCancer {
	
	final static String PATH = "C:/test/breastCancer/data/";
	////	texture	perimeter	area	smoothness	compactness	concavity	concave	symmetry	fractal dimension
	Double radius = 0.0,
			texture = 0.0,
		   perimiter = 0.0,
		   area = 0.0,
		   smoothness = 0.0,
		   compactness = 0.0,
		   concavity = 0.0,
		   concave = 0.0,
		   symmetry = 0.0,
		   fractal_dim = 0.0;
	
	//data holder; it's equivalent to the double 
	//variables defined above
	Double[] values = new Double[11];
	static Double[] probabilityM = new Double[11];
	static Double[] probabilityB = new Double[11];	
	
	
	 //list with all the data
	static List<breastCancer> dataValueList = new ArrayList<breastCancer>();
	
	static Compute dataStats = new Compute();
	
	public breastCancer(){
		
		readData();
		
	}
	
	
	//Constructor with Double parameter input
	public breastCancer(Double texture1, Double perim1, Double area1, Double smooth1,
									 Double compact1, Double conc1, Double symm1, Double fractal1){
		
				   texture = texture1;
				   perimiter = perim1;
				   area = area1;
				   smoothness = area1;
				   compactness = smooth1;
				   concavity = compact1;
				   concave = conc1;
				   symmetry = symm1;
				   fractal_dim = fractal1;
		
	}
	
	//Constructor with string parameter input
		public breastCancer(String texture1, String perim1, String area1, String smooth1,
							String compact1, String conc1, String symm1, String fractal1){
			
					   texture = Double.valueOf(texture1);
					   perimiter = Double.valueOf(perim1);
					   area = Double.valueOf(area1);
					   smoothness = Double.valueOf(area1);
					   compactness = Double.valueOf(smooth1);
					   concavity = Double.valueOf(compact1);
					   concave = Double.valueOf(conc1);
					   symmetry = Double.valueOf(symm1);
					   fractal_dim = Double.valueOf(fractal1);
			
		}
		
		//Constructor with just string array input
		public breastCancer(String[] valuesIn){
			Double tempArrayValue = 0.0;
			//System.out.print("\nlength of array is:  "+valuesIn.length);
			
						
/**************************************************************************************/
/** IMPLEMENT a list that contains arrays with counts i.e. frequency
 *  for each of the bars in the histogram graph**/			
/**************************************************************************************/
						
			
						if(valuesIn[1].contains("M")){
							
							if(probabilityM[0] == null){
								
								for(int i = 0; i < 11; i++){
									probabilityM[i] = 0.0;
								}
								
							}
							//positive
							values[0] = 1.0;
							
							probabilityM[0]++;
							 
							
						}else{
						
							if(probabilityB[0] == null){
								
								for(int i = 0; i < 11; i++){
									probabilityB[i] = 0.0;
								}
								
							}
							
							//negative i.e. benign
							values[0] = 0.0;							
							probabilityB[0]++;
							
							
						}
						
						//positive test result i.e. M
						if(valuesIn[1].contains("M")){
							 
							
							for(int i = 0; i < dataStats.B_Count.size(); i++){
								
								tempArrayValue = Double.valueOf(valuesIn[i+2]);
								values[i] = tempArrayValue;
								probabilityM[i] = tempArrayValue;			
								
								
								 //Below is a more simple code of what the following
								   //loop does
								   /*if(tempArrayValue > 7.65 && tempArrayValue< 8.4){
									
									  //i traverses: Mean Radius	texture	perimeter	area	smoothness	compactness	concavity	concave	symmetry	fractal dimension
									   dataStats.B_Count.get(i)[0]++;
									   
								   }else if(tempArrayValue > 7.65 && tempArrayValue < 8.4){
									   
									   dataStats.B_Count.get(i)[1]++;
									   
								   }*/
								   
								   for(int j = 0; j < dataStats.M_Count.get(i).length; j++){
									   
									  
										   if( tempArrayValue >= dataStats.M_Count_CONST.get(i)[j] && 
												   tempArrayValue < dataStats.M_Count_CONST.get(i)[j+1] ){
											   
											   dataStats.M_Count.get(i)[j] += 1.0;
											   break;
											   
										   }
									  
										  
									   									   
									   
								   }
								   
							}
						}else{//negative test result i.e. B
							for(int i = 0; i < dataStats.B_Count.size(); i++){
								   
									tempArrayValue = Double.valueOf(valuesIn[i+2]);
									values[i] = tempArrayValue;
									probabilityB[i]= tempArrayValue;
									
									
								   //Below is a more simple code of what the following
								   //loop does
								   /*if(tempArrayValue > 7.65 && tempArrayValue< 8.4){
									
									  //i traverses: Mean Radius	texture	perimeter	area	smoothness	compactness	concavity	concave	symmetry	fractal dimension
									   dataStats.B_Count.get(i)[0]++;
									   
								   }else if(tempArrayValue > 7.65 && tempArrayValue < 8.4){
									   
									   dataStats.B_Count.get(i)[1]++;
									   
								   }*/
								   
								   for(int j = 0; j < dataStats.B_Count.get(i).length; j++){
									   
									  
									  
										   if( tempArrayValue >= dataStats.B_Count_CONST.get(i)[j] && 
												   tempArrayValue < dataStats.B_Count_CONST.get(i)[j+1] ){
											   
											   dataStats.B_Count.get(i)[j] += 1.0;
											   
										   }
									 
									   									   
									   
								   }
								   								   
								   
							}
							
						}
			
		}

	
	public breastCancer get(){
		
		return this;
	}
	
	
	
	public boolean readData(){
		
		File directory = new File(PATH); //path to directory with data files
		
		BufferedReader br = null;//buffer reader to read from a file
		String line = ""; //line read from a file
		String delimiter = ",";//delimiter for "line"
		String[] tmpLn;//temporary delimited line, which is used to construct a
					   //breastCancer object
		
		File[] fileName = directory.listFiles();//array of file names 
												//in the directory		
	
		
		
		for(File file: fileName){
			
			
			//read from file
			if(file.isFile()){
				try{
				
					br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
					
					while( (line = br.readLine()) != null){
					
						tmpLn = line.split(delimiter);//parse each column by a comma						
						
						//create a breast cancer object
						//by assigning input values from the line just read
						//from column 3 to 12 into the "new breastCancer" object
						dataValueList.add(new breastCancer(tmpLn));
						
					}
				
					//handle exceptions
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
					return false;
					
				} catch (IOException e) {
					
					e.printStackTrace();
					return false;
					
				} catch(Exception e){
					
					e.printStackTrace();
					return false;
					
				}finally {//close file buffer reader
					if (br != null) {
						
						try {
							
							br.close();
							
							//catch file close related exception
						} catch (IOException e) {
							
							e.printStackTrace();
							
							return false;
							
						}
					}
				}
				
				
			}
			
		}
		
		 
		
		return true;
		
	}
	

}
