import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class makeTests {
	public static void main(String args []) throws IOException {
		BufferedWriter wout = new BufferedWriter(new FileWriter("testLenght.txt"));
		for(int i = 0; i<32768; i++) {
			wout.write("echo "+i+"\n");
		}
		wout.close();
	}

}
