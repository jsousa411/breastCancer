package wisconsinBreastCancer;

import java.text.DecimalFormat;

public class naiveBayClassifier {
	
	@SuppressWarnings("static-access")
	public static void main(String [] args){	
		
		
		//#4.iii(1)  Use Equal Width Binning method
	    //Look through each column and get the max and min values to use as boundarie parameters.  
		//the Widths will be hard coded based on observations made in 4.i
		
		//#4.iii(2)  Assume a Gausian distribution model
				
		//#4.iii(3)  Choose a distribution model for each attribute
		
		//breast cancer data object performs the counting
		//Its constructor iterates through all the data 
		//in Compute.java object and counts the number of items
		//in each attribute range
		breastCancer patients = new breastCancer();
		
		
		//Get a BreastCancer object, so we can have data to analyze
		/*for( int i = 0; i < patients.dataValueList.size(); i++){
			
			String tempData = "";
			for(int j = 0; j < patients.dataValueList.get(i).values.length; j++){
				
				tempData += patients.dataValueList.get(i).values[j]+",\t";
			}
			
			//output each set of data read i.e. X(c1,c2,c3...,c10)
			System.out.print("\nBreast cancer data "+(i+5)+" is:  "+tempData+"\n");
			System.out.print("\nBreast cancer total "+ patients.probabilityM[0] +"\n");
		}*/

		
		DecimalFormat df = new DecimalFormat("####0.000");
		int total = 0;
		System.out.print("\n **********B TYPE*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		for(int i = 0; i < Compute.B_Count.size(); i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			System.out.print("\n\n\n"+Compute.attributes[i]+"  \n");
			
			for(int j = 0; j < Compute.B_Count_CONST.get(i).length -1; j++){
			
				System.out.print("\nRange:  "+df.format(Compute.B_Count_CONST.get(i)[j+1]- Compute.B_Count_CONST.get(i)[j])+ " for "+ Compute.B_Count_CONST.get(i)[j] + " to "+ Compute.B_Count_CONST.get(i)[j+1]+
								 ":  COUNT = "+ Compute.B_Count.get(i)[j]);
				
				total += Compute.B_Count.get(i)[j];
				
				Compute.totCountB[j]++;
			}
			
			System.out.print("\n Total:  "+total);
			
			patients.totalB = total;
			
			total = 0;
		}
				
		total = 0;
		System.out.print("\n\n\n **********M TYPE*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		
		for(int i = 0; i < Compute.M_Count.size(); i++){//patients.dataStats.B_Count.get(i).length; i++){

			System.out.print("\n\n\n"+Compute.attributes[i]+"  \n");
			
			for(int j = 0; j < Compute.M_Count_CONST.get(i).length-1; j++){
				
				
				System.out.print("\nRange:  "+ df.format(Compute.M_Count_CONST.get(i)[j+1]- Compute.M_Count_CONST.get(i)[j])+ " for "+  Compute.M_Count_CONST.get(i)[j] + " to "+ Compute.M_Count_CONST.get(i)[j+1]+
								 ":  COUNT = "+ Compute.M_Count.get(i)[j]);
				
				total += Compute.M_Count.get(i)[j];
				Compute.totCountM[j]++;
			}
			System.out.print("\n Total:  "+total);
			
			patients.totalM = total;
			total = 0;
			
		}
		
		//System.out.print("\n\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Total M count:  "+patients.totalM+" and total B count: "+ patients.totalB+"\n\n\n");
		System.out.print("\n\n\n");
		
		total = 0;
		
		System.out.print("\n **********Equal Width B*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		for(int i = 0; i < equalWidth.B_Count.size(); i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			System.out.print("\n\n\n"+ equalWidth.attributes[i%10]+"  \n");
			
			int ewSize = equalWidth.B_Count_CONST.get(i).length;
			
			for(int j = 0; j < ewSize -1; j++){
			
				System.out.print("\nRange:  "+df.format( equalWidth.B_Count_CONST.get(i)[j+1]- equalWidth.B_Count_CONST.get(i)[j])+ " for "+ equalWidth.B_Count_CONST.get(i)[j] + " to "+equalWidth.B_Count_CONST.get(i)[j+1]+
								 ":  COUNT = "+ equalWidth.B_Count.get(i)[j]);
				
				total += equalWidth.B_Count.get(i)[j];
				equalWidth.totCountB[j]++;
				
			}
			
			System.out.print("\n Total:  "+total);
			//equalWidth.totCountB[i]=total;
			
			
			total = 0;
		}
				
		total = 0;
		System.out.print("\n\n\n **********Equal Width M TYPE*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		
		for(int i = 0; i < equalWidth.M_Count.size(); i++){//patients.dataStats.B_Count.get(i).length; i++){

			System.out.print("\n\n\n"+equalWidth.attributes[i%10]+"  \n");
			
			for(int j = 0; j < equalWidth.M_Count_CONST.get(i).length-1; j++){
				
				
				Double end1 = equalWidth.M_Count_CONST.get(i)[j+1],
					   end2 = equalWidth.M_Count_CONST.get(i)[j],
					   end3 = end1-end2;
				System.out.print("\nRange:  "+df.format(end3)+ " for "+  equalWidth.M_Count_CONST.get(i)[j] + " to "+ equalWidth.M_Count_CONST.get(i)[j+1]+
								 ":  COUNT = "+ equalWidth.M_Count.get(i)[j]);
				
				total += equalWidth.M_Count.get(i)[j];
				equalWidth.totCountM[j]++;
			}
			System.out.print("\n Total:  "+total);
			//equalWidth.totCountM[i]=total;
			total = 0;
			
		}
		
		
		System.out.print("\n\n\n");
		
		total = 0;
		
		System.out.print("\n **********Gaussian B*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		for(int i = 0; i < gaussianDistrBtn.B_Count.size(); i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			System.out.print("\n\n\n"+ gaussianDistrBtn.attributes[i%10]+"  \n");
			
			int ewSize = gaussianDistrBtn.B_Count_CONST.get(i).length;
			
			for(int j = 0; j < ewSize  ; j++){
				
				Double 	   end1,
				   end2,
				   end3,
				   end4 = 0.0;
				
				if(j == 0){
					
					end1 = gaussianDistrBtn.B_Count_CONST.get(i)[j];
					 
					 
					System.out.print("\nRange:  "+df.format(end4)+ " for "+  0.0 + " to "+ 
						  end1 + ":  COUNT = "+ df.format(gaussianDistrBtn.B_Count.get(i)[j]));
				}else {
				
					
					end1 = gaussianDistrBtn.B_Count_CONST.get(i)[j];
					end2 = gaussianDistrBtn.B_Count_CONST.get(i)[j-1];
					end4 = end3 = end1-end2;
					
					if(end1 == 30000){
					

						end1 = gaussianDistrBtn.B_Count_CONST.get(i)[j-1];
						end2 = gaussianDistrBtn.B_Count_CONST.get(i)[j-2];
						end4 = end3 = end1-end2;
						
						System.out.print("\nRange:  "+df.format(end4)+ " for "+  end1 + " to "+ 
								"remaining"+ ":  COUNT = "+ df.format(gaussianDistrBtn.B_Count.get(i)[j]));
						total += (int) Math.round( 100.00 *gaussianDistrBtn.B_Count.get(i)[j]/100.00);
						if(j < gaussianDistrBtn.totCountB.length)
							gaussianDistrBtn.totCountB[j]++;
						break;
					}
					System.out.print("\nRange:  "+df.format(end3)+ " for "+  end2 + " to "+ 
							end1+ ":  COUNT = "+ df.format(gaussianDistrBtn.B_Count.get(i)[j]));
				}
				
				/*System.out.print("\nRange:  "+df.format(end3)+ " for "+  end2 + " to "+ 
						end1+ ":  COUNT = "+ gaussianDistrBtn.B_Count.get(i)[j]);*/
				
				total += (int) Math.round( 100.00 * gaussianDistrBtn.B_Count.get(i)[j]/100.00);
				if(j < gaussianDistrBtn.totCountB.length)
					gaussianDistrBtn.totCountB[j]++;
				
			}
			
			System.out.print("\n Total:  "+total);
			//equalWidth.totCountB[i]=total;
			
			
			total = 0;
		}
				
		total = 0;
		System.out.print("\n\n\n **********Gaussian M*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		
		for(int i = 0; i < gaussianDistrBtn.M_Count.size(); i++){//patients.dataStats.B_Count.get(i).length; i++){

			System.out.print("\n\n\n"+gaussianDistrBtn.attributes[i%10]+"  \n");
			
			for(int j = 0; j < gaussianDistrBtn.M_Count_CONST.get(i).length ; j++){
				
				Double 	   end1,
						   end2,
						   end3,
						   end4 = 0.0;
				if(j == 0){
				
					end1 = gaussianDistrBtn.M_Count_CONST.get(i)[j];
					 
					 
					System.out.print("\nRange:  "+df.format(end4)+ " for "+  0.0 + " to "+ 
						  end1 + ":  COUNT = "+ gaussianDistrBtn.M_Count.get(i)[j]);
				}else if( j > 0 && j < gaussianDistrBtn.M_Count_CONST.get(i).length){
				
					
					end1 = gaussianDistrBtn.M_Count_CONST.get(i)[j];
					end2 = gaussianDistrBtn.M_Count_CONST.get(i)[j-1];
					end4 = end3 = end1-end2;
					
					if(end1 == 30000){
					

						end1 = gaussianDistrBtn.M_Count_CONST.get(i)[j-1];
						end2 = gaussianDistrBtn.M_Count_CONST.get(i)[j-2];
						end4 = end3 = end1-end2;
						
						System.out.print("\nRange:  "+df.format(end4)+ " for "+  end1 + " to "+ 
								"remaining"+ ":  COUNT = "+ gaussianDistrBtn.M_Count.get(i)[j]);
						total += (int) Math.round( 100.00 *gaussianDistrBtn.M_Count.get(i)[j]/100.00);
						if(j < gaussianDistrBtn.totCountM.length)
							gaussianDistrBtn.totCountM[j]++;
						break;
					}
					System.out.print("\nRange:  "+df.format(end3)+ " for "+  end2 + " to "+ 
							end1+ ":  COUNT = "+ gaussianDistrBtn.M_Count.get(i)[j]);
				}else if (j+1 < gaussianDistrBtn.M_Count_CONST.get(i).length){
					
					/*end1 = gaussianDistrBtn.M_Count_CONST.get(i)[j];
					end2 = gaussianDistrBtn.M_Count_CONST.get(i)[j-1];
					end3 = end1-end2;*/
					
					end1 = gaussianDistrBtn.M_Count_CONST.get(i)[j];
					 
					 
					System.out.print("\nRange:  "+df.format(end4)+ " for "+  end1 + " to "+ 
							"remaining"+ ":  COUNT = "+ gaussianDistrBtn.M_Count.get(i)[j]);
				}else{}
				
				
				
				total += (int) Math.round( 100.00 *gaussianDistrBtn.M_Count.get(i)[j]/100.00);
				if(j < gaussianDistrBtn.totCountM.length)
					gaussianDistrBtn.totCountM[j]++;
			}
			System.out.print("\n Total:  "+total);
			//equalWidth.totCountM[i]=total;
			total = 0;
			
		}
		
		
		System.out.print("\n\n\n");
		 
	}

}
