package wisconsinBreastCancer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class kFoldValidator {
	
	static String learnDataPath = "KfoldData/";
	static int kFactor = 56;
	static int total = 0;
	static DecimalFormat df = new DecimalFormat("####0.0000");
	static breastCancer patients;// = new breastCancer(breastCancer.PATH);
	static Double[][] resultsAll = new Double[6][10];	
	static Double[] overAll	= new Double[6];
	
	//This is used to write data to a file 
	//to control the 10-fold learning data	
	static void driver(int kValue){
		
		//this is the K number i.e. 1,2,3...10
		kFactor = kValue;
		
		//write data to file
		writeData(learnDataPath);
		 
	}
	
	
	//This method checks the location of the range
	//where the value of each element in attributes[]
	//fits within its attribute Distribution Model
	//Then it takes the total count of elements from that
	//Range within that Distribution model and calculates a 
	//probability for that attribute
	//each attribute probability calculated is used for the
	//overall classification
	static Double[] classifier(Double[] attributes){
		
		Double probabilityB = 1.0;//(double) ((double)patients.totalB/(Double.valueOf((double)patients.totalM )+ (double)patients.totalB));
		Double probabilityM = 1.0;//(double) ((double)patients.totalM/((double)patients.totalM + (double)patients.totalB));
		
		DecimalFormat df = new DecimalFormat("0.###E0");
		//df.format()
		
		Double total = (double) patients.totalB;//Compute.totCountB[i];
		Double outcomeB = 0.0;//outcome for the histogram based model
		Double outcomeM = 0.0;
		Double gOutcomeB = 0.0,//outcome for Gaussian model
			   gOutComeM = 0.0,
			   equalOutComeB = 0.0,//outcome for equal width model
			   equalOutComeM = 0.0,
			   localOutcomeB = 0.0,//outcome for each attribute as we loop...used
			   					   //for debugging
			   localOutcomeM = 0.0;
		
		int counter1 = Compute.B_Count.size();
		int countConst;
		int indexB = 0;
		int indexM = 0;
		int gIndexB = 0,
		    gIndexM = 0,
		    equalIndexB = 0,
		    equalIndexM = 0;
		
		Double value;
		
		/*System.out.print("\n\n\n"+
		"/*****HISTROGRAM BASED Distribution KFold Validator****");*/
	 	
		for(int i = 0; i < counter1 ; i++){//patients.dataStats.B_Count.get(i).length; i++){ 
		 
			value = attributes[i+1];
			Double[] B = Compute.B_Count_CONST.get(i),
					 M = Compute.M_Count_CONST.get(i);
			
			indexB = searchValue(B,value); 
			
			indexM = searchValue(M,value); 
			
			//for the first go round
			if(i == 0){

				//get the frequency for this occurrence
				Double subCount = Compute.B_Count.get(i)[indexB];
				Double subCountM = Compute.M_Count.get(i)[indexM];
				
				//this is to handle the 0 probability
				if(subCount == 0.0 ){
					
					subCount = 1.0;
				}
				//this is to handle the 0 probability
				if(subCountM == 0.0){
					
					subCountM = 1.0;
				}
				//histogram based distribution outcome
				localOutcomeB  = subCount/total;
				
				outcomeB = localOutcomeB;
				
				localOutcomeM  = subCountM/patients.totalM;
				
				outcomeM = localOutcomeM;
		
				//for the remaining of the loop
			}else{
				
				//get frequency
				Double subCount = Compute.B_Count.get(i)[indexB];
				Double subCountM = Compute.M_Count.get(i)[indexM];
				
				//this is to handle the 0 probability
				if(subCount == 0.0 ){
					
					subCount = 1.0;
				}
				//this is to handle the 0 probability
				if(subCountM == 0.0){
					
					subCountM = 1.0;
				}
				
				//histogram based distribution outcome
				localOutcomeB = subCount/patients.totalB;
				
				//multiply the next attribute's probability
				outcomeB *= localOutcomeB ;
				
				localOutcomeM = subCountM/patients.totalM;
				
				//multiply the next attribute's probability
				outcomeM *= localOutcomeM;
				 
			}
			
			 
		}//end Histogram observation model
		
		overAll[0] = outcomeB*probabilityB;
		overAll[1] = outcomeM*probabilityM;
		
		/* "/*****Gaussian Distribution KFold Validator**** ");*/
		
		counter1 = gaussianDistrBtn.B_Count_CONST.size();
				
				
		for(int i = 0; i < counter1 ; i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			 
			
			value = attributes[i+1];
			 
			gIndexB = searchValue(gaussianDistrBtn.B_Count_CONST.get(i),value);
			// System.out.print("\nB Found "+value+" at index: "+gIndexB+"\n");
			 
			
		    gIndexM = searchValue(gaussianDistrBtn.M_Count_CONST.get(i),value);
		   // System.out.print("\nM Found "+value+" at index: "+gIndexM+"\n");

			if(i == 0){

				Double subCount =  gaussianDistrBtn.B_Count.get(i)[gIndexB];
				Double subCountM = gaussianDistrBtn.M_Count.get(i)[gIndexM];
				
				if(subCount == 0.0 ){
					
					subCount = 1.0;
				}
				
				if(subCountM == 0.0){
					
					subCountM = 1.0;
				}
				
				//Double total = (double) patients.totalB;//Compute.totCountB[i];
				 
				//Gaussian distribution outcome
				localOutcomeB = gOutcomeB = subCount/total;
				localOutcomeM = gOutComeM = subCountM/patients.totalM;
			    
			    
				
			}else{
				 
				Double subCount =  gaussianDistrBtn.B_Count.get(i)[gIndexB];
				Double subCountM = gaussianDistrBtn.M_Count.get(i)[gIndexM];
				
				if(subCount == 0.0 ){
					
					subCount = 1.0;
				}
				
				if(subCountM == 0.0){
					
					subCountM = 1.0;
				}
				
				//Gaussian distribution outcome
				localOutcomeB =subCount/total;
				
				gOutcomeB *= localOutcomeB;
				
			    localOutcomeM = subCountM/patients.totalM;
			    
			    gOutComeM *= localOutcomeM;
			}
			
			resultsAll[2][i] = localOutcomeB;
			resultsAll[3][i] = localOutcomeM;
			/*if(localOutcomeB > 0.00001 && localOutcomeM > 0.00001){
				System.out.print("\n\nThe Gaussian outcome for B is:  "+df.format(localOutcomeB));
				System.out.print("\n\nThe Gaussian outcome for M is:  "+df.format(localOutcomeM)); 
			}else{
				System.out.print("\n\nThe Gaussian outcome for B is:  "+localOutcomeB);
				System.out.print("\n\nThe Gaussian outcome for M is:  "+localOutcomeM); 
			}
			System.out.print("\n\n");*/
			
		}
		
		overAll[2] = gOutcomeB *probabilityB;
		overAll[3] = gOutComeM * probabilityM;
		
		/*System.out.print("\n the overall outcome for B is:  " + gOutcomeB + " and for M is:  "+ gOutComeM);//localOutcomeB
		
		System.out.print("\n\n\n"+
		"/*****EQUAL WIDTH Distribution KFold Validator**** ");*/
		
		counter1 = equalWidth.B_Count_CONST.size();
		
		for(int i = 0; i < counter1 ; i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			//System.out.print("\n\n\n"+Compute.attributes[i]+"  \n");
			
			//countConst = Compute.B_Count_CONST.get(i).length;
			
			value = attributes[i+1];
			 
		    equalIndexB = searchValue(equalWidth.B_Count_CONST.get(i),value);
		   // System.out.print("\nB Found "+value+" at index: "+equalIndexB+"\n");
		    
		    equalIndexM = searchValue(equalWidth.M_Count_CONST.get(i),value); 
		  //  System.out.print("\nM Found "+value+" at index: "+equalIndexM+"\n");
		    
			if(i == 0){

				Double subCount =  equalWidth.B_Count.get(i)[equalIndexB];
				Double subCountM = equalWidth.M_Count.get(i)[equalIndexM];
				
				if(subCount == 0.0 ){
					
					subCount = 1.0;
				}
				
				if(subCountM == 0.0){
					
					subCountM = 1.0;
				}
				
				//Double total = (double) patients.totalB;//Compute.totCountB[i];
				 
			    //equal width distribution outcome
				localOutcomeB = equalOutComeB = subCount/total;
				localOutcomeM = equalOutComeM = subCountM/patients.totalM; 
				
			}else{
				//histogram based distribution outcome
				Double subCount =  equalWidth.B_Count.get(i)[equalIndexB];
				Double subCountM = equalWidth.M_Count.get(i)[equalIndexM];
				
				if(subCount == 0.0 ){
					
					subCount = 1.0;
				}
				
				if(subCountM == 0.0){
					
					subCountM = 1.0;
				}
			    
			    //equal width distribution outcome
			    localOutcomeB = subCount/total;
			    equalOutComeB *=  localOutcomeB;
			    
			    localOutcomeM = subCountM/patients.totalM; 
			    equalOutComeM *= localOutcomeM ;
			}
			
			resultsAll[4][i] = localOutcomeB;
			resultsAll[5][i] = localOutcomeM;
			
			/*if(localOutcomeB > 0.00001 && localOutcomeM > 0.00001){
				System.out.print("\n\nThe Equal Width outcome for B is:  "+df.format(localOutcomeB));
				System.out.print("\n\nThe Equal Width outcome for M is:  "+df.format(localOutcomeM));
			}else{
				
				System.out.print("\n\nThe Equal Width outcome for B is:  "+ localOutcomeB);
				System.out.print("\n\nThe Equal Width outcome for M is:  "+ localOutcomeM);
				
			}
			
			System.out.print("\n\n");*/
			
		}
				
		overAll[4] = equalOutComeB *probabilityB;
		overAll[5] = equalOutComeM * probabilityM;
		
		/*System.out.print("\n the overall outcome for B is:  " + df.format(equalOutComeB) + " and for M is:  "+df.format(equalOutComeM));//localOutcomeB
		*//***************************RETURN FINAL RESULT BASED ON HISTOGRA DISTRIBUTION*//*

		System.out.print("\n\n\n"+"                "+"Histogram Based Model"+"                "+"Gaussian Based Model"+"                "+"Equal Width Model");
		for(int i = 0; i < counter1 ; i++){//patients.dataStats.B_Count.get(i).length; i++){
			System.out.print("\n\n\n"+Compute.attributes[i]+"\n");
					for(int j = 0; j < 1; j++){
						System.out.print("B:                "+df.format(resultsAll[j][i])
										  +"                                "+df.format(resultsAll[j+2][i])
										  +"                                "+df.format(resultsAll[j+4][i]));
						
						System.out.print("\nM:                "+df.format(resultsAll[j+1][i])
											+"                                "+df.format(resultsAll[j+3][i])
											+"                                "+df.format(resultsAll[j+5][i]));
					}
					
					
		}
		System.out.print("\n\n*********************************************************************************************************"+
						 "\n\nOverall:  \nB:");
		for(int i = 0; i < 6; i+=2){
			System.out.print("                "+df.format(overAll[i])+"                ");
		}
		System.out.print("\nM:");
		for(int i = 1; i < 6; i+=2){
			System.out.print("                "+df.format(overAll[i])+"                ");
		}*/
		
		return overAll;
	}
	
	
	
	//Displays an array to the console
	static public void displayArray(Double[] array){
		int i =0;
		
		System.out.print("\n[ ");
		for(i = 0; i < array.length-1; i++){
			
			System.out.print(array[i]+ ",");
		}
		
		System.out.print(array[i]+ " ]\n");
	}
	
	//Searches for a value within the array and returns the index of that array
	static public int searchValue(Double[] dataArray, Double searchVal){
		
		int i = 0;
		
		//System.out.print("\n\nSearching for:  "+searchVal+" in array:  ");
		//displayArray(dataArray);
		
		
		
		//if the value is way small or
		//less than the first potential range
		//value, we might have an outlier: return the
		//first index i.e. 0
		if(dataArray[i] >= searchVal){
			
			
			return i;
			
		}else{
			
			//look through array for 
			//potential range of value
			while(i < dataArray.length){
				
				//if value is smaller than current
				//range keep searching
				if(dataArray[i] < searchVal){
					
					i++;
					
				}else{
					
					//if value is smaller or 
					//equal searched value
					//return index
					if(dataArray[i] >= searchVal){
						
						return i;
						
					}
				}
			}
		}
		
		return i-1;//if we did not find the value then go with outlier 
	}
	
	//Read data from file at location: PATH
		//Reads one line at at time and stores it in 
		//breastCancer.values[] array and then adds this array
		//to a list<> of objects of type breastCancer
	static public boolean writeData(String filePath){
			
					
			File directory = new File(filePath); //path to directory with data files to written to
			File readDirectory = new File("Data/"); //path to directory with data files to be read
			BufferedReader br = null;
			BufferedWriter bw = null;//buffer reader to read from a file
			String line = ""; //line read from a file
			String delimiter = ",";//delimiter for "line"
			String[] tmpLn;//temporary delimited line, which is used to construct a
						   //breastCancer object
			String tempStr;
			String[] tmpStrR;
			Double[] tmpDbl;
			int kfold = 0;
			
			File[] fileName = directory.listFiles();//array of file names 
													//in the directory		
			File[] readfileName = readDirectory.listFiles();
			try{
			   for(File file: fileName)
				for(File fileRead: readfileName){
					
					
					//read from file
					if(fileRead.isFile()){
						 
							br = new BufferedReader(new FileReader(fileRead.getAbsoluteFile()));
							bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
							while( (line = br.readLine()) != null && kfold < kFactor){
							
								//tmpLn = line.split(delimiter);//parse each column by a comma	
				
									/*for(File file: fileName){
										
										
										//read from file
										if(file.isFile()){ */

												
												//learning set
												//for(int k = 0; k <  breastCancer.dataValueList.size()/kfold; k++){
													
													//count equalWidth fequency
													//tmpDbl = breastCancer.dataValueList.get(k).values;
													
													//tempStr = Arrays.toString(tmpDbl);
													
													//tempStr = tempStr.substring(1, tempStr.length() -1) ;
																				
													 bw.write(line);//tempStr);
													 bw.newLine();
													 kfold++;
										
											
												//} 
										/*}
									}*/
								}
							}
						}
			   
			 /*  System.out.print("\n\nUsing "+kfold+" records to learn\n\n");*/
										
						
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
						if (bw != null) {
							
							try {
								
								bw.close();
								br.close();
								
								//catch file close related exception
							} catch (IOException e) {
								
								e.printStackTrace();
								
								return false;//an error occurred return false
								
							}
						}
					//}
					
					
				//}
				
			}

			//returns true if all data is read successfully
			return true;
			
		}//end of constructor(string input)

}
