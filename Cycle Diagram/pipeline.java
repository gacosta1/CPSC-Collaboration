import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class pipeline {

	static List<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
	
public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
	
		String localFile = null;
						
		Scanner scan = new Scanner(System.in);
		String local = args[0];
		String fileName = local;

		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((localFile = bufferedReader.readLine()) != null) {
				ArrayList<String> info = new ArrayList<String>();
				String instr = null;
				String src1 = null;
				String src2 = null;
				String dest = null;
				
				System.out.println(localFile);
				if(localFile.contains("add")){
					instr = localFile.substring(0, 4);
					dest = localFile.substring(4,6);
					src1 = localFile.substring(7,9);
					src2 = localFile.substring(10, 12);
					info.add(instr);
					info.add(dest);
					info.add(src1);
					info.add(src2);
				}
				
				if(localFile.contains("sub")){
					instr = localFile.substring(0, 4);
					dest = localFile.substring(4,6);
					src1 = localFile.substring(7,9);
					src2 = localFile.substring(10, 12);
					info.add(instr);
					info.add(dest);
					info.add(src1);
					info.add(src2);
				}
					
				if(localFile.contains("lw")){
					src1 = localFile.substring(0, 2);
					src2 = localFile.substring(2, 12);
					info.add(src1);
					info.add(src2);
				}
				
				if(localFile.contains("sw")){
					src1 = localFile.substring(0, 2);
					src2 = localFile.substring(2, 12);
					info.add(src1);
					info.add(src2);
				}
				
				arr.add(info);
			}
			
			System.out.println();
			
			String norm = "  IF ID EX DM WB";
			String stall1 = "  IF    ID EX DM WB"; //2 before
			String stall2 = "  IF       ID EX DM WB"; //1 before;
			String space = " ";
			String stallSpace1 = "   ";
			String stallSpace2 = "      ";
			int k, h;
			int flag1 = 0, flag2 = 0;
			
		for(int i = 0; i < arr.size(); i++){
			for(int j = 0; j < arr.get(i).size(); j++){
				System.out.print(arr.get(i).get(j));
				if(j != (arr.get(i).size()) -1 && (j != 0)){
					System.out.print(",");
				}
			}
			
			// these two for loops print out the correct amount of space
			// before each iterations of the instructions fetched
			if(i>0) {
				for(h=0;h<i;h++){
					System.out.print(space);	
				}
			}
			
			for(k = 0; k < i*2; k++) {
				System.out.print(space);
				
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
			}			
		}
	
		System.out.println();
				
		bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
//				 ex.printStackTrace();
		}
	} 
}