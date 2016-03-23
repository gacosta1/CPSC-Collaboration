import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.InputStreamReader;

public class pipeline {

	static List<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
	
public static void main(String[] args) throws java.io.IOException {	
		String strHold = null; //buffer to hold lines as they are read in
        Scanner sc = new Scanner(System.in); //scanner to read in from stdio
		    
		while(sc.hasNext()){
		strHold = sc.nextLine(); //read in line by line
		
			ArrayList<String> info = new ArrayList<String>(); //creates arrayList of data
			String instr = null; //instruction to be put in arrayList
			String src1 = null; //first source to be put in arrayList
			String src2 = null; //Second source to be put in arrayList
			String dest = null; //Destination register to be put in arrayList
			
			//Parsing of each line into arrayList of arrayLists(which contain line data)
			if(strHold.contains("add")){
				instr = strHold.substring(0, 4);
				dest = strHold.substring(4,6);
				src1 = strHold.substring(7,9);
				src2 = strHold.substring(10, 12);
				info.add(instr);
				info.add(dest);
				info.add(src1);
				info.add(src2);
			}
			
			if(strHold.contains("sub")){
				instr = strHold.substring(0, 4);
				dest = strHold.substring(4,6);
				src1 = strHold.substring(7,9);
				src2 = strHold.substring(10, 12);
				info.add(instr);
				info.add(dest);
				info.add(src1);
				info.add(src2);
			}
				
			if(strHold.contains("lw")){
				src1 = strHold.substring(0, 2);
				src2 = strHold.substring(2, 12);
				info.add(src1);
				info.add(src2);
			}
			
			if(strHold.contains("sw")){
				src1 = strHold.substring(0, 2);
				src2 = strHold.substring(2, 12);
				info.add(src1);
				info.add(src2);
			}
			
			arr.add(info);
		} //endwhile
		
		
		String norm = "  IF ID EX DM WB"; //normal output (no stalls)
		String stall1 = "  IF    ID EX DM WB"; //dependencies fall two lines before current, 1 stall
		String stall2 = "  IF       ID EX DM WB"; //dependencies fall one line before current, 2 stalls
		String space = " "; //accounts for space between cycles in output
		String stallSpace1 = "   "; //accounts for one stall in previous cycles
		String stallSpace2 = "      "; //accounts for two stalls in previous cycles
		int k, h;
		int flag1 = 0, flag2 = 0;
		
		for(int i = 0; i < arr.size(); i++){
			for(int j = 0; j < arr.get(i).size(); j++){
				System.out.print(arr.get(i).get(j));
				if(j != (arr.get(i).size()) -1 && (j != 0)){
					System.out.print(","); //formatting of output
				}						   //prints out info in list of arrayLists
			}
		
			// these two for loops print out the correct amount of space
			// before each iterations of the instructions fetched
			
			if(i>0) {
				for(h=0;h<i;h++){
					System.out.print(space);	//prints out a space for each concurrent space between cycles
				}
			}
		
			for(k = 0; k < i*2; k++) {
				System.out.print(space);	//prints out two spaces at beginning of each output line
			
			}
		
			// start out printing out the pipe stages with no stalls
			if(i == 0)
				System.out.println(norm);
			
			// if the second line uses a destination register before it
			// then print out two stalls spaces or else print a normal
			// pipe stage
			if(i == 1){
				if(arr.get(i).get(0).contains("add") || arr.get(i).get(0).contains("sub")) {
				
					for(k=0;k<flag2;k++) {
						System.out.print(stallSpace2);
					}
				
					if(arr.get(i-1).get(1).contains(arr.get(i).get(2)) || arr.get(i-1).get(1).contains(arr.get(i).get(3))){
						flag2++;
						System.out.println(stall2);
					}
				
					else{
						System.out.println(norm);
					}
				}

				if(arr.get(i).get(0).contains("sw") || arr.get(i).get(0).contains("lw")) {
				
					for(k=0;k<flag2;k++) {
						System.out.print(stallSpace2);
					}
				
					if(arr.get(i-1).get(1).contains(arr.get(i).get(1))){
						flag2++;
						System.out.println(stall2);
					
					}
				
					else
						System.out.println(norm);
				}
			}
		
			// if the third or more lines use a destination register before it
			// or 2 lines before then print out either one or two stall spaces
			// or else print a normal pipe stage
			if(i > 1){
				if(arr.get(i).get(0).contains("add") || arr.get(i).get(0).contains("sub")) {
					for(k=0;k<flag2;k++) {
						System.out.print(stallSpace2);
					}
					for(k=0;k<flag1;k++) {
						System.out.print(stallSpace1);
					}
				
					// if a destination register is used before then make two stall spaces
					if(arr.get(i-1).get(1).contains(arr.get(i).get(2)) || arr.get(i-1).get(1).contains(arr.get(i).get(3))){
						flag2++;
						System.out.println(stall2);
					}	
					// if a destination register is used 2 lines before then make one stall space
					else if(arr.get(i-2).get(1).contains(arr.get(i).get(2)) || arr.get(i-2).get(1).contains(arr.get(i).get(3))){
						flag1++;
						System.out.println(stall1);
					}	
					// else just print out the normal stages
					else {
						System.out.println(norm);
					}
				}
			
				else if(arr.get(i).get(0).contains("sw") || arr.get(i).get(0).contains("lw")){
					for(k=0;k<flag2;k++) {
						System.out.print(stallSpace2);
					}
					for(k=0;k<flag1;k++) {
						System.out.print(stallSpace1);
					}
				
					// if a destination register is used before then make two stall spaces

					if(arr.get(i).get(1).contains(arr.get(i-1).get(1))){
						flag2++;
						System.out.println(stall2);
					}
					// if a destination register is used 2 lines before then make one stall space
					else if(arr.get(i).get(1).contains(arr.get(i-2).get(1))){
						flag1++;
						System.out.println(stall1);
					}
					// else just print out the normal stages
					else{
						System.out.println(norm);
					}	
				}
// 				else if((arr.get(i).get(0).contains("add") || arr.get(i).get(0).contains("sub")) &&
// 				((arr.get(i-1).get(0).contains("sw") || (arr.get(i-1).get(0).contains("sw"))))) {
// 					if(arr.get(i).get(2).contains(arr.get(i-1).get(1)) || (arr.get(i).get(3).contains(arr.get(i-1).get(1))))
// 						System.out.println(stall2);
// 					else
// 						System.out.println(norm);
// 				}
			}			
		}
	} 
}