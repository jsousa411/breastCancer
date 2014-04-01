package wisconsinBreastCancer;

import java.text.DecimalFormat;
import java.util.List;

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
		
		int[] correctGuess = new int[3];
		int[] wrongGuess = new int[3];
		int[] Bcount = new int[3];
		int[] Mcount = new int[3];
		int[] falseB = new int[3];
		int[] falseM = new int[3];
		Double[] accuracy = new Double[3];
		
		for(int i = 10; i > 0; i--){
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
			
			learningSetCount = (TOTAL_RECORDS/i);
			testDriver.driver(learningSetCount );//this calls the driver
			
			patients = new breastCancer("KfoldData/");//breastCancer.PATH);
			
			//test learning set against the whole data set
			driverDataList = patients.readData1("Data/");//read all the data provided
			
			Integer[] mx = Compute.totCountB.clone();
			int test = patients.dataValueList.size();//this should be the same as learningSetCount
			
			
			totalRecords = driverDataList.size();//TOTAL_RECORDS 
			for(int j = 0; j < totalRecords; j++){
			
				Double[] testArray = driverDataList.get(j);
				
				//call the driver that classifies the data
				Double[] classificatinReslt;// = testDriver.classifier(testArray);
				
				classificatinReslt = testDriver.classifier(testArray);
			
				//based on the classifier result's 
				//increment counters
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
			}//end test driver loop
			
			for(int q = 0; q < 3; q++){
				DecimalFormat df = new DecimalFormat("#");
		        df.setMaximumFractionDigits(4);
		        accuracy[q] = (Double)(100.00*(double)correctGuess[q]/(double)totalRecords);
		        if(q == 0){
		        	System.out.print("\nResult for Histogram evaluation based distribution:  ");
		        }else if(q == 1){
		        	
		        	System.out.print("\nResult for Gaussian based distribution:  ");
		        	
		        }else{
		        	
		        	System.out.print("\nResult for Equal Width based distribution:  ");
		        }
		        
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
