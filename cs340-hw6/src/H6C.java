import java.io.*;
import java.util.*;

public class H6C {

		DBTable t1;
		DBTable t2;
		DBTable t3;

		int t1Fields[] = {10, 20};
		int t2Fields[] = {5, 10, 30};
		int t3Fields[] = {35};

	private void search(int val)  throws IOException {

		LinkedList<String> fields1;
		LinkedList<String> fields2;
		LinkedList<String> fields3;

		fields1 = t1.search(val);
		print(fields1, val);
		//fields2 = t2.search(val);
		//print(fields2, val);

	}

	private void print(LinkedList<String> f, int k) {
		if (f == null || f.size() == 0) { 
			System.out.println("Not Found "+k);
			return;
		}
		System.out.print(""+k+" ");
		for (int i = 0; i < f.size(); i++)
			System.out.print(f.get(i)+" ");
		System.out.println();
	}

	private char[][] makeFields(int fields[], int k) {
		char f[][] = new char[fields.length][];
		for (int i = 0; i < f.length; i++) {
			f[i] = Arrays.copyOf((new Integer(k)).toString().toCharArray(), fields[i]);
		}
		return f;
	}
			

	

	public H6C() throws IOException {
		int i;

		t1 = new DBTable("f1", t1Fields, 60);
		
		for (i = 0; i < 21; i=i+4) {
			t1.insert(i, makeFields(t1Fields, i));

		}
		for(i=1;i<20;i=i+9){
			t1.insert(i, makeFields(t1Fields, i));
			t1.insert(i+1, makeFields(t1Fields, i+1));
			t1.insert(i+2, makeFields(t1Fields, i+2));
		}
		System.out.println("print tree");
		t1.printTree();
		System.out.println("done");
		for(i=3;i<20 ;i=i+8) {
			t1.remove(i);
		}
		System.out.println("print tree");
		t1.printTree();
		System.out.println("done");
//		System.out.println();
//		for(i=1;i<500;i++) {
//			t1.insert(i, makeFields(t1Fields,i));
//		}
		t2 = new DBTable("f2", t2Fields, 72);
////		for (i = 0; i < 1080; i++) {
////			t2.insert(i, makeFields(t2Fields, i));
////
////		}
//
//		for (i = 0; i < 500; i = i + 4) {
//			t1.remove(i);
//
//		}

//		for (i = 0; i < 1080; i = i + 5) {
//			t2.remove(i);
//
//		}

//		for (i = 3; i < 500; i = i + 4) {
//			t1.remove(i);
//
//		}
//
//		for (i = 4; i < 1080; i = i + 5) {
//			t2.remove(i);
//
//		}

		Scanner scan = new Scanner(System.in);
		int val;
		
		System.out.print("Enter a search value or -1 to quit: ");
		val = scan.nextInt();
		while (val != -1) {
			search(val);
			System.out.print("\nEnter a search value or -1 to quit: ");
			val = scan.nextInt();
		}


		t1.close();
		t2.close();

		t1 = new DBTable("f1");
		t2 = new DBTable("f2");

		System.out.print("Enter a search value or -1 to quit: ");
		val = scan.nextInt();
		while (val != -1) {
			search(val);
			System.out.print("\nEnter a search value or -1 to quit: ");
			val = scan.nextInt();
		}

		t1.close();
		t2.close();
	

	}


		

	public static void main(String args[])  throws IOException  {
		new H6C();
	}
}