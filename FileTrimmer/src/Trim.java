import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Trim {
	
	public static void main(String args[]) throws IOException{
		BufferedReader rin = new BufferedReader(new FileReader(new File("middic.txt")));
		BufferedWriter wout = new BufferedWriter(new FileWriter(new File("C:\\temp\\moddic.txt")));
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		
		System.out.println("1 to keep, 0 to delete, 9 to quit");
		String line = rin.readLine();
		while(line != null && !quit) {
			System.out.print(line + " : ");
			int i = input.nextInt();
			if(i == 1) {
				wout.write(line);
				wout.newLine();
			}
			if(i == 9) {
				quit = true;
			}
			line = rin.readLine();
			
		}
		
	}

}
