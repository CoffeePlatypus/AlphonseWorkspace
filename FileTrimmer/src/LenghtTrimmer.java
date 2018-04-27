import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LenghtTrimmer {
	public static void main(String args[]) throws IOException{
		BufferedReader rin = new BufferedReader(new FileReader(new File("C:\\temp\\middic.txt")));
		BufferedWriter wout = new BufferedWriter(new FileWriter(new File("C:\\temp\\middic1.txt")));
		//boolean quit = false;
		
		String line = rin.readLine();
		while(line != null) {
			char e = line.charAt(line.length()-1);
			//Syste
			if( !(e == 'e' || e == 'i' || e == 'o' || e == 'a' || e == 'u' )){
				//if(! line.contains("abb") && ! line.contains("aj") && line.contains("ak")) {
					wout.write(line);
					wout.newLine();
				//}
			}
			line = rin.readLine();
		}
		
	}
}
