/* 
 * Author:  Joao Sousa
 * SFSU Spring 2014
 * CSC 869
 * 04/04/2014
 * Mini Project # 2
 * This program performs data classification by implementing the 
 * Naive Bayesan model
 * 
 * To execute the program import package into Eclipse and execute. The program
 * has all necessary data files were extracted with the project, so they should be
 * included as part of the package import process
 *
 
 How to review the code:

The program’s main function is under naiveBayClassifier.java this file, which instantiates breastCancer.java and kFold.java objects.

breastCancer.java contains code to build/populate the models ranges and also count the frequency of the data within that range.

kFold.java contains code to classify the data

naiveBayClassifier.java gets result/data from breastCancer.java, feeds it into kFold.java and then based on the result from kFold.java it determines if a specific data record is B or M, which it then determines if it’s correct or incorrect.  Based on the frequency of correct and incorrect reports from kFold.java, naiveBayClassifier.java’s main function outputs the report seen above.
Results are displayed to the console.

Compute.java is the class for the Histogram Observation based model.

gaussianDistrBtn.java is the Guassian based model class.

equalWidth.java is the Equal Width based model.

All the model classes get instantiated by breastCancer.java class.
 * 
 */

package wisconsinBreastCancer;

import java.text.DecimalFormat;
import java.util.List;

//This is the main driver of the program.
public class naiveBayClassifier {
	
	//4.iv Evaluation module
	static kFoldValidator testDriver;
	
	//4.iii Naive Bayesian classifier
	//infrastructure to retain
	//information learned
	static breastCancer patients;
	
	@SuppressWarnings("static-access")
	public static void main(String [] args){	
		
		final int TOTAL_RECORDS = 568;
		
		List<Double[]> driverDataList;
		//#4.iii(1)  Use Equal Width Binning method
	    //Look through each column and get the max and min values to use as boundarie parameters.  
		//the Widths will be hard coded based on observations made in 4.i
		
		//#4.iii(2)  Assume a Gausian distribution model
				
		//#4.iii(3)  Choose a distribution model for each attribute
		
		//breast cancer data object performs the counting
		//Its constructor iterates through all the data 
		//in Compute.java object and counts the number of items
		//in each attribute range
		testDriver = new kFoldValidator();
		
		int totalRecords = 0;
		int learningSetCount = 0;
		
		//data structure to hold true/false
		//results from kFoldValidator.java
		int[] correctGuess = new int[3];
		int[] wrongGuess = new int[3];
		int[] Bcount = new int[3];
		int[] Mcount = new int[3];
		int[] falseB = new int[3];
		int[] falseM = new int[3];
		Double[] accuracy = new Double[3];//accuracy rate for each model
		
		//loop 10 times for K-10 fold
		for(int i = 10; i > 0; i--){
			
			//set all data counters to zero 
			totalRecords = 0;
			
			for(int q = 0; q < 3; q++){
				
				correctGuess[q] = 0;
				wrongGuess[q] = 0;
				Bcount[q] = 0;
				Mcount[q] = 0;
				falseB[q] = 0;
				falseM[q] = 0;
				accuracy[q] = 0.0;
			}
			
			//kfold data volume counter
			learningSetCount = (TOTAL_RECORDS/i);
			
			//tell kFold how much data to be used for learning set
			testDriver.driver(learningSetCount );//this calls the driver
			
			//pass data from kFold to breastCancer for the learning set
			patients = new breastCancer("KfoldData/");//breastCancer.PATH);
			
			//test learning set against the whole data set
			driverDataList = patients.readData1("Data/");//read all the data provided
			
			totalRecords = driverDataList.size();//TOTAL_RECORDS
			
			//Loop for all the records tested i.e. 568 total records
			for(int j = 0; j < totalRecords; j++){
			
				//Get record to compare it with the result
				Double[] testArray = driverDataList.get(j);
				
				//call the driver that classifies the data
				Double[] classificatinReslt;// = testDriver.classifier(testArray);
				
				//Get result array with 3 result: one for each model
				classificatinReslt = testDriver.classifier(testArray);
			
				//Check the result for each model
				for(int q = 0; q < 3; q++){
					
					Double guessedDiagnosis = 0.0 ;
					
					//classificatinReslt[q] = {histogram result for B, histogram result for M,
											//Gaussian result for B, Gaussian result for m,
											//equal width result for B, equal width result for M}
					if(classificatinReslt[q*2] > classificatinReslt[(q*2+1)]){
						
						guessedDiagnosis = 0.0;
					}else{
						
						guessedDiagnosis = 1.0;
					}
					
					//Classifer guessed M
					if(guessedDiagnosis == 1.0){			
	
						//M is equivalent to 1.0 is stored in testArray[0] element
						//and B is equivalent to 0.0 which is stored in  testArray[0] element
						
						//If we really provided an M
						if(testArray[0] == 1.0){
							
							correctGuess[q]++;
							Mcount[q]++;
							
						}else{//otherwise we guessed wrong
							
							wrongGuess[q]++;
							falseM[q]++;
						}
						
					//Classifier guessed B
					}else{
						
						//if we really have a B
						if(testArray[0]== 0.0){
							Bcount[q]++;
							correctGuess[q]++;
							
						}else{//otherwise we guessed wrong
							wrongGuess[q]++;
							falseB[q]++;
						}
					}
				
				
				
				}
				
				if(j == 567){
					
					@SuppressWarnings("unused")
					int ju = 0;
					ju++;
				}
			}//end test driver loop
			
			
			//Output final report for each K-Fold
			for(int q = 0; q < 3; q++){
				
				//decimal data formatter to round to specific sig figs
				DecimalFormat df = new DecimalFormat("#");
				
				//set to 4 sig figs
		        df.setMaximumFractionDigits(4);
		        
		        //calculate accurracy for model at value q
		        accuracy[q] = (Double)(100.00*(double)correctGuess[q]/(double)totalRecords);
		        
		        //Display message for the model at q
		        if(q == 0){
		        	System.out.print("\nResult for Histogram evaluation based distribution:  ");
		        }else if(q == 1){
		        	
		        	System.out.print("\nResult for Gaussian based distribution:  ");
		        	
		        }else{
		        	
		        	System.out.print("\nResult for Equal Width based distribution:  ");
		        }
		        
		        //Display report content
				System.out.print("For K = "+(11-i)+"\n");
				System.out.print("\nLearning set count:  "+learningSetCount);
				System.out.print("\nTotal records tested:  "+totalRecords);
				System.out.print("\nTotal correctly guesses:  "+correctGuess[q]);
				System.out.print("\nTotal wrong guesses:  "+wrongGuess[q]);
				System.out.print("\n\nCorrect  \tWrong  <---Guess");
				System.out.print("\n"+Bcount[q]+"\t\t"+falseB[q]+"\t|B");
				System.out.print("\n"+Mcount[q]+"\t\t"+falseM[q]+"\t|M");
				System.out.print("\nRate of accuracy:  "+df.format(accuracy[q])+"%");
				System.out.print("\n\n");
				
			}
			System.out.print("\n******************************************************");
			 
				
				 
		 }//end of K loop
		
				 
	}
	
	

}
