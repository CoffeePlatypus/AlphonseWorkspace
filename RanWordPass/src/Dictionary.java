import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
	
	private File dictionaryFile;
	private int size;
	private String [] dict;
	private int maxLenght, minLenght;
	
	
	public Dictionary(File f, int min, int max) throws IOException{
		maxLenght = max;
		minLenght = min;
		dictionaryFile = f;
		size = countSize();
		dict = new String[size];
		createDictionary();
	}
	
	public void createDictionary() throws IOException {
		BufferedReader rin = new BufferedReader( new FileReader(dictionaryFile));
		int count = 0;
		String word = rin.readLine();
		while(word != null) {
			if(word.length() > minLenght && word.length() < maxLenght){
				dict[count] = word;
				count++;
			}
			word = rin.readLine();
		}
		rin.close();
	}
	
	private int countSize() throws IOException {
		BufferedReader counter = new BufferedReader(new FileReader(dictionaryFile));
		int count = 0;
		String word = counter.readLine();
		while( word != null) {
			if(word.length() > minLenght && word.length() < maxLenght){
				count++;
			}
			word = counter.readLine();
		}
		counter.close();
		return count;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getWord(int i) {
		return dict[i];
	}

}
