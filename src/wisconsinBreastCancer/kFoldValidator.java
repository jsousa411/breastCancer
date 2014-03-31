package wisconsinBreastCancer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class kFoldValidator {
	
	static int kFactor = 56;
	static int total = 0;
	static DecimalFormat df = new DecimalFormat("####0.000");
	static breastCancer patients;// = new breastCancer(breastCancer.PATH);
	
	
	static void driver(){
		
		
		
		writeData("KfoldData/");
		 
	}
	
	static Double classifier(Double[] attributes){
		
		Double outcomeB = 0.0;
		Double outcomeM = 0.0;
		int counter1 = Compute.B_Count.size();
		int countConst;
		
		for(int i = 0; i < counter1 ; i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			System.out.print("\n\n\n"+Compute.attributes[i]+"  \n");
			
			countConst = Compute.B_Count_CONST.get(i).length;
			
				for(int j = 0; j < countConst; j++){
				
				  if(j>0){
					if(attributes[i+1] > Compute.B_Count_CONST.get(i)[j-1] && attributes[i+1] <= Compute.B_Count_CONST.get(i)[j]){
						
						if(j>0)
						System.out.print("\nRange:  "+df.format(Compute.B_Count_CONST.get(i)[j]- Compute.B_Count_CONST.get(i)[j-1])+ " for "+
										Compute.B_Count_CONST.get(i)[j-1] + " to "+ Compute.B_Count_CONST.get(i)[j]+
										":  COUNT = "+ Compute.B_Count.get(i)[j]);
						
						System.out.print("\n\nYou inputted:  "+attributes[i+1]);
						System.out.print("\n\n**B** Probability is:  "+ Compute.B_Count.get(i)[j]+" / "+ Compute.totCountB[j] +" = "+ Compute.B_Count.get(i)[j]/Compute.totCountB[j]);
						
						outcomeB += Compute.B_Count.get(i)[j]/Compute.totCountB[j];
						
					}else if( j == Compute.B_Count_CONST.get(i).length -1){
						
						System.out.print("\nRange:  "+df.format(Compute.B_Count_CONST.get(i)[j]- Compute.B_Count_CONST.get(i)[j-1])+ " for "+
						Compute.B_Count_CONST.get(i)[j-1] + " to "+ Compute.B_Count_CONST.get(i)[j]+
								 ":  COUNT = "+ Compute.B_Count.get(i)[j]);
						
						System.out.print("\n\nYou inputted:  "+attributes[i+1]);
						System.out.print("\n\n**B** Probability is:  "+ Compute.B_Count.get(i)[j]+" / "+ Compute.totCountB[j] +" = "+ Compute.B_Count.get(i)[j]/Compute.totCountB[j]);
						
						outcomeB += Compute.B_Count.get(i)[j]/Compute.totCountB[j];
						
					}
				  }else{
					  
					  if(attributes[i+1] > Compute.B_Count_CONST.get(i)[j] && attributes[i+1] <= Compute.B_Count_CONST.get(i)[j+1]){
							
							 
							System.out.print("\nRange:  "+df.format(Compute.B_Count_CONST.get(i)[j+1]- Compute.B_Count_CONST.get(i)[j])+ " for "+
											Compute.B_Count_CONST.get(i)[j] + " to "+ Compute.B_Count_CONST.get(i)[j+1]+
											":  COUNT = "+ Compute.B_Count.get(i)[j]);
							
							System.out.print("\n\nYou inputted:  "+attributes[i+1]);
							System.out.print("\n\n**B** Probability is:  "+ Compute.B_Count.get(i)[j]+" / "+ Compute.totCountB[j] +" = "+ 
							Compute.B_Count.get(i)[j]/Compute.totCountB[j]);
							
							outcomeB += Compute.B_Count.get(i)[j]/Compute.totCountB[j];
				  }
					
					  
				}
				
				for(j = 0; j < Compute.M_Count_CONST.get(i).length -1; j++){
					
					if(attributes[j+1] > Compute.M_Count_CONST.get(i)[j] && attributes[j+1] <= Compute.M_Count_CONST.get(i)[j+1]){
						
						System.out.print("\nRange:  "+df.format(Compute.M_Count_CONST.get(i)[j+1]- Compute.M_Count_CONST.get(i)[j])+ 
										" for "+ Compute.M_Count_CONST.get(i)[j] + " to "+ Compute.M_Count_CONST.get(i)[j+1]+
										":  COUNT = "+ Compute.M_Count.get(i)[j]);
						
						System.out.print("\n\nYou inputted:  "+attributes[j+1]);
						System.out.print("\n\n&&M&& Probability is:  "+ Compute.M_Count.get(i)[j]+" / "+ breastCancer.totalM+" = "+
						Compute.M_Count.get(i)[j]/Compute.totCountM[j]);
						
						outcomeM += Compute.M_Count.get(i)[j]/Compute.totCountM[j];
					}
					
					  
				}
			}
		}
			
		if(outcomeM > outcomeB)
			return 1.0;
		else
			return 0.0;
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
							
								tmpLn = line.split(delimiter);//parse each column by a comma	
				
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
