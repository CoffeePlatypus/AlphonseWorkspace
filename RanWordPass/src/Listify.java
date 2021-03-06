import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Listify {
	
	public static void main(String [] args) throws IOException {
		BufferedReader rin = new BufferedReader( new FileReader("JuliaPassDictionary.txt"));
		BufferedWriter wout = new BufferedWriter(new FileWriter("JSDictList.txt"));
		String word = rin.readLine();
		int count = 0;
		String line="";
		while(word != null) {
			if(word.length()<8){
				word = ", \""+(word.charAt(0) +"").toUpperCase() + word.substring(1)+"\"";
				wout.write(word);
			}
			word = rin.readLine();
		}
		wout.close();
		rin.close();
	}

}
