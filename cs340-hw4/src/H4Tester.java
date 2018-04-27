import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class H4Tester {
	
	public static void main(String args[]) {
		H4<String> heap=new H4<String>(10,20);
		/*
		String[] k={};
		String[] d={}
		int s=5;
		heap.set(k, d,s);
		*/
		heap.insert("b", "beep");
		heap.insert("d", "drat");
		heap.insert("e", "erg");
		heap.insert("f", "fan");
		heap.insert("z", "last");
		heap.insert("a", "apple");
		heap.insert("c", "cat");
		heap.insert("c", "cittten");
		//heap.insert("x", );
		while(!heap.empty()) {
			System.out.println(heap);
			heap.removeMin();
		}
		
	}

}
