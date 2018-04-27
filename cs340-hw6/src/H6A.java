import java.io.*;
import java.util.*;

//Tests insert and search
public class H6A {

		DBTable t1;
		DBTable t2;
		DBTable t3;

		int t1Fields[] = {10, 20};
		int t2Fields[] = {5, 10, 30};
		int t3Fields[] = {35};


	private void insert(int limit)   throws IOException{
		int i;
		for (i = 0; i < limit; i = i+5) {
			t1.insert(i, makeFields(t1Fields, i));
			t2.insert(i, makeFields(t2Fields, i));
			t3.insert(i, makeFields(t3Fields, i));
		}
		for (i = limit+1; i >= 1 ; i = i-5) {
			t1.insert(i, makeFields(t1Fields, i));
			t2.insert(i, makeFields(t2Fields, i));
			t3.insert(i, makeFields(t3Fields, i));
		}
	}

	private void search(int val)  throws IOException {

		LinkedList<String> fields1;
		LinkedList<String> fields2;
		LinkedList<String> fields3;

		fields1 = t1.search(val);
		print(fields1, val);
		fields2 = t2.search(val);
		print(fields2, val);
		fields3 = t3.search(val);
		print(fields3, val);

	}


	private void print(LinkedList<String> f, int k) {
		if (f == null) { 
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
			
			

	public H6A() throws IOException {
		int limit;

		t1 = new DBTable("f1", t1Fields, 60);
		t2 = new DBTable("f2", t2Fields, 72);
		t3 = new DBTable("f3", t3Fields, 132);

		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the input limit: ");
		limit = scan.nextInt();

		insert(limit);

		System.out.print("Enter a search value or -1 to quit: ");
		int val = scan.nextInt();
		while (val != -1) {
			search(val);
			System.out.print("\nEnter a search value or -1 to quit: ");
			val = scan.nextInt();
		}

		t1.close();
		t2.close();
		t3.close();

		t1 = new DBTable("f1");
		t2 = new DBTable("f2");
		t3 = new DBTable("f3");

		System.out.print("Enter a search value or -1 to quit: ");
		val = scan.nextInt();
		while (val != -1) {
			search(val);
			System.out.print("\nEnter a search value or -1 to quit: ");
			val = scan.nextInt();
		}

		t1.close();
		t2.close();
		t3.close();
	}



	public static void main(String args[])  throws IOException  {
		new H6A();
	}
}