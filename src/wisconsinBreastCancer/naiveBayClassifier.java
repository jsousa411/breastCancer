package wisconsinBreastCancer;

public class naiveBayClassifier {
	
	public static void main(String [] args){	
		
		breastCancer patients = new breastCancer();//breast cancer data object
		
		
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

		
		
		int total = 0;
		System.out.print("\n **********B TYPE*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		for(int i = 0; i < 1; i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			for(int j = 0; j < patients.dataStats.B_Count_CONST.get(i).length-1; j++){
			
				System.out.print("\nRange:  "+ patients.dataStats.B_Count_CONST.get(i)[j] + " to "+patients.dataStats.B_Count_CONST.get(i)[j+1]+
								 ":  COUNT = "+patients.dataStats.B_Count.get(i)[j]);
				
				total += patients.dataStats.B_Count.get(i)[j];
				
				
			}
		}
		
		System.out.print("\n Total:  "+total);
		total = 0;
		System.out.print("\n\n\n **********M TYPE*****************  \n");
		System.out.print("\nThe following are the stats value for  Mean Radius:  \n");
		
		for(int i = 0; i < 1; i++){//patients.dataStats.B_Count.get(i).length; i++){
			
			for(int j = 0; j < patients.dataStats.M_Count_CONST.get(i).length-1; j++){
			
				System.out.print("\nRange:  "+ patients.dataStats.M_Count_CONST.get(i)[j] + " to "+patients.dataStats.M_Count_CONST.get(i)[j+1]+
								 ":  COUNT = "+patients.dataStats.M_Count.get(i)[j]);
				
				total += patients.dataStats.M_Count.get(i)[j];
			}
		}
		
		System.out.print("\n Total:  "+total);
		System.out.print("\n\n\n");
		
		
	}

}
