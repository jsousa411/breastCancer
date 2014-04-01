package wisconsinBreastCancer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//breastCancer object is a data Type object
//used to hold the data for Breast Cancer results
public class breastCancer {
	
	final static Double MAX = 30000.0;
	static String PATH = "Data/";
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
	static Double[] probabilityM = new Double[10];
	static Double[] probabilityB = new Double[10];	
	
	static int totalM = 0,
			   totalB = 0;
		
	 //list with all the data read from file
	static List<breastCancer> dataValueList = new ArrayList<breastCancer>();
	
	static Compute dataStats = new Compute();
	static equalWidth eW 	 = new equalWidth();
	static gaussianDistrBtn gd = new gaussianDistrBtn();
	Double[][] dataM ;//= new Double[10][212];
	Double[][] dataB ;//= new Double[10][357];
	
	static Double [][][] dataInput = new Double [2][10][400];//store all values read from file
	static int dataM1_Counter = 0;
	static int dataB1_Counter = 0;
	
	public breastCancer(String filePath){
				
		
		values = new Double[11];
		probabilityM = new Double[10];
		probabilityB = new Double[10];	
		
		totalM = 0;
		totalB = 0;
			
		 //list with all the data read from file
		dataValueList = new ArrayList<breastCancer>();
		
		dataStats = new Compute();
		eW 	 = new equalWidth();
		gd = new gaussianDistrBtn();
		
		dataInput = new Double [2][10][400];//store all values read from file
		dataM1_Counter = 0;
		dataB1_Counter = 0;
		
		
		readData(filePath);//4.iii(3) gets built here
		
		Double widthConst = 0.0;
		DecimalFormat df2 = new DecimalFormat("###.######");
          
		
		
		//This is prep work for 4.iii(2) i.e. setting up the ranges
		for(int i = 0; i < equalWidth.min.length; i++){
			
			if(equalWidth.min[i] > 0.0000){
				
				widthConst = Double.valueOf(df2.format((equalWidth.max[i] - equalWidth.min[i]) / Double.valueOf(equalWidth.WIDTH - 1 )));
				
			}else{
				
				widthConst = Double.valueOf(df2.format((equalWidth.max[i] ) / Double.valueOf(equalWidth.WIDTH - 1 )));
			}
			//System.out.print("For "+ equalWidth.attributes[i]+" ranges are:  ");
			
			
			//assign equal width ranges to boundary arrays
			for(int j = 0; j < equalWidth.WIDTH; j++){
				
				if(j == 0){//initial boundary
					
					equalWidth.M_Count_CONST.get(i)[j] = Double.valueOf(df2.format(equalWidth.min[i]  - widthConst/5.0));
					equalWidth.B_Count_CONST.get(i)[j] = Double.valueOf(df2.format(equalWidth.min[i] -  widthConst/5.0));
					
				}else if( j+1 == equalWidth.WIDTH){//final boundary
					
															//make it just a bit bigger because it's not inclusive
															//so we want to make sure the values at the max(boundary)
															//get included
					equalWidth.M_Count_CONST.get(i)[j] = Double.valueOf(df2.format(equalWidth.max[i] + widthConst/5.0));					
					equalWidth.B_Count_CONST.get(i)[j] = Double.valueOf(df2.format(equalWidth.max[i] + widthConst/5.0));
					
				}
				else{
					//intermediate boundary values
					equalWidth.M_Count_CONST.get(i)[j] = Double.valueOf(df2.format(equalWidth.min[i] + Double.valueOf(j)*widthConst));
					equalWidth.B_Count_CONST.get(i)[j] = Double.valueOf(df2.format(equalWidth.min[i] + Double.valueOf(j)*widthConst));
					
					
				}
				//System.out.print(" "+equalWidth.M_Count_CONST.get(i)[j]);
			}
			
			//System.out.print(" with min:  "+ equalWidth.min[i] +" and max:  "+equalWidth.max[i]+"\n\n\n");
		}
		
		//4.iii(1) gets built here
		for(int k = 0; k < dataValueList.size(); k++){
		
			//count equalWidth fequency
			
			 populateEqualWidth(dataValueList.get(k).values); 
			
		}		
		
		
		//4.iii(2) gets built here
		
		populateGaussianDist();
		
	}
	
	
	//4.iii(1) frequency count
	//after reading all the data we know the max and min for each attribute
	//and it's stored in equalWidth.max[] and equalWidthmin[]
	//now calculate the frequencies for equalWidth
			
	public void populateEqualWidth(Double[] valuesIn){
		
		Double tempArrayValue = 0.0;
		//System.out.print("\nlength of array is:  "+valuesIn.length);
		
					
						/***********************************************************************/
						/** IMPLEMENT a list that contains arrays with counts i.e. frequency
						/ *  for each of the bars in the histogram graph**/			
						/***********************************************************************/
				try{	
		 
					//positive test result i.e. M
					if(valuesIn[0] == 1.0){
						 
						int arraySize =valuesIn.length-1;// equalWidth.M_Count.size();
						//keep the -1 to prevent array out of bounds at
						//line : tempArrayValue = Double.valueOf(valuesIn[i+2]);
						for(int i = 0; i < arraySize; i++){		
							   
							   tempArrayValue = Double.valueOf(valuesIn[i+1]);
							   
							   int subArrSize = equalWidth.M_Count.get(i).length;
							   
							   for(int j = 0; j < subArrSize-1; j++){									   
								  
								   if((j+1) < equalWidth.M_Count_CONST.get(i).length){
									   
									   if( tempArrayValue >= equalWidth.M_Count_CONST.get(i)[j] && 
											   tempArrayValue < equalWidth.M_Count_CONST.get(i)[j+1] ){
										   
										   
										   //at this range...for M result type
										   equalWidth.M_Count.get(i)[j] += 1.0;//increment the frequency for this result
										   break;
										   
									   }
								   }
							   }
							   
						}
					}else{//negative test result i.e. B

						
						int arraySize = valuesIn.length-1;// equalWidth.B_Count.size();
						//keep the -1 to prevent array out of bounds at
						//line : tempArrayValue = Double.valueOf(valuesIn[i+2]);
						for(int i = 0; i < arraySize; i++){
							    
							 tempArrayValue = Double.valueOf(valuesIn[i+1]);
							 int subArrSize = equalWidth.B_Count.get(i).length;
							 
							 
							   for(int j = 0; j < subArrSize; j++){
								   
										
										 
								  if((j+1) < equalWidth.B_Count_CONST.get(i).length){
									  Double[] arr1 = equalWidth.B_Count_CONST.get(i);
									  Double v1 = arr1[j],
											 v2 = arr1[j+1];
									   if( tempArrayValue >= v1 && 
											   tempArrayValue < v2 ){
										   
										 //  System.out.print("\n\naccessing B_COUNT_CONST at i: "+i +" and j : "+ j);
										 
										   totalB++;
										   //at this range...for M result type
										   equalWidth.B_Count.get(i)[j] += 1.0;//increment the frequency for this result
										   break;
										   
									   }else if( (tempArrayValue < v1)//this is for data below the range 
											     && j == 0){
										   
										   totalB++;
										   equalWidth.B_Count.get(i)[j] += 1.0;
										   break;
									   }else{}
								  }
								 
								   									   
								   
							   }
							   								   
							   
						}
						
					}
				}catch(ArrayIndexOutOfBoundsException e){
					
					
					e.printStackTrace();
					
				}catch(Exception e){
					
					e.printStackTrace();
				}
		
	}//end equal Width
	
/************************************************************************/	
	//4.iii(2) frequency count
		//after reading all the data we know the total count for M and B
		//now we distribute the data according to a normal distribution
		//and then set the boundaries
				
		public void populateGaussianDist(){
			
			Double[] gaussianDistribution = {0.001, 0.022, 0.136, 0.341,  0.341, 0.136, 0.022, 0.001};
			
			 
			int kLen = 0;
			
			 
			for(int i = 0; i < dataInput.length -1 ; i++){//10
				
				 
				
				for(int y = 0; y < dataInput[i].length; y++){//400
				
				 
										
					for(int h = 0; h < dataInput[i][y].length; h++){//
						
						if(dataInput[i][y][h] == null){
							
							dataInput[i][y][h] = MAX; 
							
						}
						
						 
					}
					
					
					for(int h = 0; h < dataInput[i][y].length; h++){//
						
						if(dataInput[i+1][y][h] == null){
							
							dataInput[i+1][y][h] = MAX; 
							
						}
					}
					
					//sort each array dataInput[i][y]	
				    Double[] tempDbl = dataInput[i][y].clone();
					
				    Arrays.sort(tempDbl);
					
					dataInput[i][y] = tempDbl.clone();
					
					tempDbl = dataInput[i+1][y].clone();
					
					Arrays.sort(tempDbl);

					dataInput[i+1][y] = tempDbl.clone();
					
										//the length is WIDTH, which = 8 for gaussianDistrBrn
					for(int k = 0; k < gaussianDistrBtn.M_Count.get(i).length; k++){
					
						kLen = gaussianDistrBtn.M_Count.get(i).length;
						
						Double frequencyM = 0.0;// = gaussianDistribution[k]*dataM1_Counter;
						Double frequencyB = 0.0;// = gaussianDistribution[k]*dataB1_Counter;
						
						int iRangeM = 0,
							iRangeB = 0;
						
						
							//calculate the frequency
							frequencyM = gaussianDistribution[k]*dataM1_Counter;
							frequencyB = gaussianDistribution[k]*dataB1_Counter;							
							
							/*iRangeM =  (int) (gaussianDistribution[k]*dataM1_Counter);
							iRangeB =  (int) (gaussianDistribution[k]*dataB1_Counter);
							*/
						
							iRangeM = (int) (dataM1_Counter/kLen);
							iRangeB = (int) (dataB1_Counter/kLen);
						
						//assign range
						if(k == 0){
						 
							//the range starts with the first element in the sorted array
							gaussianDistrBtn.M_Count_CONST.get(y)[k] = dataInput[1][y][0];
							gaussianDistrBtn.B_Count_CONST.get(y)[k] = dataInput[0][y][0];
														
							
						}else{
							
							//next we are going to the next element that is at the 
							//Gaussian Distribution border value
							gaussianDistrBtn.M_Count_CONST.get(y)[k] = dataInput[1][y][iRangeM*k];
							
							gaussianDistrBtn.B_Count_CONST.get(y)[k] = dataInput[0][y][iRangeB*k];
														 
							
						}
					
						//assign frequency i.e. how many points are within 
						//this range of the Gaussian distribution
						gaussianDistrBtn.M_Count.get(y)[k] = 100.00*frequencyM/100.00;
						gaussianDistrBtn.B_Count.get(y)[k] = 100.00*frequencyB/100.00;
					}
					
					//assign frequency count
				}
				
			}
			
			
			
		}//end Gaussian Distribution
		
	/************************************************************************/	

	
	//4.iii(3)
		//This constructor is invoked form readData()
		//every time a new line is read, a new breastCancer(valueIn) is 
		//instantiated and then inserted into dataValueList
		//Constructor with just string array input
		public breastCancer(String[] valuesIn){
			Double tempArrayValue = 0.0;
			//System.out.print("\nlength of array is:  "+valuesIn.length);
			
			
						
	/***********************************************************************/
	/** IMPLEMENT a list that contains arrays with counts i.e. frequency
	/ *  for each of the bars in the histogram graph**/			
	/***********************************************************************/
					try{	
			
						if(valuesIn[1].contains("M")){
							
							if(probabilityM[0] == null){
								
								for(int i = 0; i < probabilityM.length; i++){
									probabilityM[i] = 0.0;
								}
								
							}
							//positive
							values[0] = 1.0;
							
							//probabilityM[0]++;
							 
							
						}else{
						
							if(probabilityB[0] == null){
								
								for(int i = 0; i < probabilityB.length; i++){
									probabilityB[i] = 0.0;
								}
								
							}
							
							//negative i.e. benign
							values[0] = 0.0;							
							probabilityB[0]++;
							
							
						}
						
						//positive test result i.e. M
						if(valuesIn[1].contains("M")){
							 
							int arraySize = Compute.M_Count.size();
							//keep the -1 to prevent array out of bounds at
							//line : tempArrayValue = Double.valueOf(valuesIn[i+2]);
							for(int i = 0; i < arraySize; i++){
								
								totalM++;
								tempArrayValue = Double.valueOf(valuesIn[i+2]);
							if((i+2) == 2){
							    for(int y = 0; y < dataInput[1].length ; y++){
							    	dataInput[1][y][dataM1_Counter] = Double.valueOf(valuesIn[y+2]);
							    }
								dataM1_Counter++;
							}
							
							
								values[i+1] = tempArrayValue;
								probabilityM[i] = tempArrayValue;			
								
								//this is to get the (max - min)/range = equal width
								//for 4.iii(1)
								if(equalWidth.max[i] < tempArrayValue){
									
									equalWidth.max[i] = tempArrayValue;
								}
								
								if(equalWidth.min[i]  > tempArrayValue ){
									
									equalWidth.min[i] = tempArrayValue;
									
								}
								
								 //Below is a more simple code of what the following
								   //loop does
								   /*if(tempArrayValue > 7.65 && tempArrayValue< 8.4){
									
									  //i traverses: Mean Radius	texture	perimeter	area	smoothness	compactness	concavity	concave	symmetry	fractal dimension
									   dataStats.B_Count.get(i)[0]++;
									   
								   }else if(tempArrayValue > 7.65 && tempArrayValue < 8.4){
									   
									   dataStats.B_Count.get(i)[1]++;
									   
								   }*/
								   
								   int subArrSize = Compute.M_Count.get(i).length;
								   
								   for(int j = 0; j < subArrSize-1; j++){									   
									  
									   //check which width the current tempArrayValue falls within
									   if((j+1) < Compute.B_Count_CONST.get(i).length){
										   if( tempArrayValue >= Compute.M_Count_CONST.get(i)[j] && 
												   tempArrayValue < Compute.M_Count_CONST.get(i)[j+1] ){
											    
											   //at this range...for M result type
											   Compute.M_Count.get(i)[j] += 1.0;//increment the frequence for this result
											   break;
											   
										   }					   
									   }
								   }
								   
							}
						}else{//negative test result i.e. B

							int arraySize = Compute.B_Count.size();
							//keep the -1 to prevent array out of bounds at
							//line : tempArrayValue = Double.valueOf(valuesIn[i+2]);
							for(int i = 0; i < arraySize; i++){
								   
								    totalB++;
								    
									tempArrayValue = Double.valueOf(valuesIn[i+2]);
									if((i+2) == 2){
									    for(int y = 0; y < dataInput[0].length ; y++){
									    	dataInput[0][y][dataB1_Counter] = Double.valueOf(valuesIn[y+2]);
									    }
										dataB1_Counter++;
									}
									values[i+1] = tempArrayValue;
									 
									probabilityB[i]= tempArrayValue;
									
									//this is to get the (max - min)/range = equal width
									//for 4.iii(1)
									if(equalWidth.max[i] < tempArrayValue){
										
										equalWidth.max[i] = tempArrayValue;
									}
									
									if(equalWidth.min[i]  > tempArrayValue ){
										
										equalWidth.min[i] = tempArrayValue;
										
									}
									
								   //Below is a more simple code of what the following
								   //loop does
								   /*if(tempArrayValue > 7.65 && tempArrayValue< 8.4){
									
									  //i traverses: Mean Radius	texture	perimeter	area	smoothness	compactness	concavity	concave	symmetry	fractal dimension
									   dataStats.B_Count.get(i)[0]++;
									   
								   }else if(tempArrayValue > 7.65 && tempArrayValue < 8.4){
									   
									   dataStats.B_Count.get(i)[1]++;
									   
								   }*/
								   
								   int subArrSize = Compute.B_Count.get(i).length;
									 
								   for(int j = 0; j < subArrSize; j++){
									   
									 //check which width the current tempArrayValue falls within
									  if((j+1) < Compute.B_Count_CONST.get(i).length){
										   if( tempArrayValue >= Compute.B_Count_CONST.get(i)[j] && 
												   tempArrayValue < Compute.B_Count_CONST.get(i)[j+1] ){
											   
											 //  System.out.print("\n\naccessing B_COUNT_CONST at i: "+i +" and j : "+ j);
											    
											   //at this range...for M result type
											   Compute.B_Count.get(i)[j] += 1.0;//increment the frequency for this result
											   break;
											   
										   }
									  }							   
									   
								   }
								   
							}
							
						}
					}catch(ArrayIndexOutOfBoundsException e){
						
						
						e.printStackTrace();
						
					}catch(Exception e){
						
						e.printStackTrace();
					}
					
		}

	
	//Read data from file at location: PATH
	//Reads one line at at time and stores it in 
	//breastCancer.values[] array and then adds this array
	//to a list<> of objects of type breastCancer
	public boolean readData(String filePath){
		
				
		File directory = new File(filePath); //path to directory with data files
		
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
						
						
						//4.iii(3) frequency count:
						//create a breast cancer object
						//by assigning input values from the line just read
						//from column 3 to 12 into the "new breastCancer" object
						//while assigning values, calculate the fequencies
						//and store it in Compute.M_count<ListOfArray>M_attributename[] and
						//				  Compute.B_count<ListOfArray>B_attributename[]
						//where the length of B_attribute name is number of widths
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
							
							return false;//an error occurred return false
							
						}
					}
				}
				
				
			}
			
		}

		//returns true if all data is read successfully
		return true;
		
	}//end of constructor(string input)
	
	
		//Read data from file at location: filePath
		//Reads one line at at time and stores it in 
		//data[] array and then adds this array
		//to a list<> and returns the list
		 static public List<Double[]> readData1(String filePath){
			
			 List<Double[]> localList = new ArrayList<Double[]>();
			 Double[] data;
					
			File directory = new File(filePath); //path to directory with data files
			
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
							
							data = new Double[tmpLn.length-1];
							
							for(int i = 0; i < tmpLn.length-1; i++){
								
								  
								if(  tmpLn[i+1].equals("M")){
									
									data[i] = 1.0;
									
								}else if (tmpLn[i+1].equals("B")){
									
									data[i] = 0.0;
									
								}else{
									data[i] = Double.valueOf(tmpLn[i+1]);
								}
								
							}

							localList.add(data);
							
						} 
						
						//handle exceptions
					} catch (FileNotFoundException e) {
						
						e.printStackTrace();
						
						
					} catch (IOException e) {
						
						e.printStackTrace();
						
						
					} catch(Exception e){
						
						e.printStackTrace();
						
						
					}finally {//close file buffer reader
						if (br != null) {
							
							try {
								
								br.close();
								
								//catch file close related exception
							} catch (IOException e) {
								
								e.printStackTrace();
								
								
								
							}
						}
					}
					
					
				}
				
			}

			//returns true if all data is read successfully
			return localList;
			
		}//end of constructor(string input)
	

}//end of class breastCancer
