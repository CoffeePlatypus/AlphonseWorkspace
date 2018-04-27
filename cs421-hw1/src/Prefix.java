import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Prefix {
	
	public static void main(String [] args) {
//		try {
//			BufferedReader rin = new BufferedReader(new FileReader(args[0]));
//			String temp = rin.readLine();
//			while(temp != null) {
//				Evaluator e = new Evaluator(temp);
//				System.out.println(e.evaluate());
//				temp=rin.readLine();
//			}
//			rin.close();
//			
//		} catch (IOException e) {
//			System.out.println("Generic Error Message");
//		}
//		
		Evaluator e = new Evaluator("( + ( - 10 2 ) 3 )");
		System.out.println(e.evaluate()+"\n");
		Evaluator e1 = new Evaluator("( + a 3 )");
		System.out.println(e1.evaluate()+"\n");
		Evaluator e2 = new Evaluator("( ( ( a 3 ) ( b 10 ) ) ( * a ( + b 1 ) ) )");
		System.out.println(e2.evaluate()+"\n");
		Evaluator e3 = new Evaluator("( ( ( a 5 ) ) ( * a ( ( ( a 3 ) ( b 10 ) ) ( * a ( + b 1 ) ) ) ) )");
		System.out.println(e3.evaluate()+"\n");

	}
	
	
	
	
	
	

	
}
