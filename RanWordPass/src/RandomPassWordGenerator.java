import java.io.File;
import java.io.IOException;

public class RandomPassWordGenerator {
	public static char[] specialChars = { '!', '.', ',', '^', '*', '#'};
	
	public static void main(String args[]) {
		try {
			
		//Dictionary d = new Dictionary(new File("dictionary1.txt"), 3, 8);
		Dictionary d = new Dictionary(new File("middic1.txt"), 3, 8);
		int size = d.getSize();
		String pass = cap(d.getWord(getRanI(size))) + cap(d.getWord(getRanI(size))) + getRanI(20);
		
		while(pass.length() < 12) {
			pass = pass + getRanI(9);
		}
		
		pass = pass + specialChars[getRanI(specialChars.length)];
		
		System.out.println(pass);
		
		}catch (IOException e) {
			System.out.println("Error");
		}
		
	}
	
	public static int getRanI(int max) {
		return (int)(Math.random() * max);
	}
	
	public static String cap(String s) {
		return (s.charAt(0)+"").toUpperCase()+ s.substring(1);
	}
	
	

}
