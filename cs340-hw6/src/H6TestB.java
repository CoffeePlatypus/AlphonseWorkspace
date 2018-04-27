import java.io.*;
import java.util.*;

public class H6TestB {

		DBTable t1;
		DBTable t2;
		DBTable t3;

		int t1Fields[] = {10, 20};
		int t2Fields[] = {5, 10, 30};
		int t3Fields[] = {35};


	private void insert1(int limit)   throws IOException{
		int i;
		for (i = 0; i < limit; i = i+2) {
			t1.insert(i, makeFields(t1Fields, i));
			t2.insert(i, makeFields(t2Fields, i));
			t3.insert(i, makeFields(t3Fields, i));
		}
		for (i = limit+1; i >= 1 ; i = i-2) {
			t1.insert(i, makeFields(t1Fields, i));
			t2.insert(i, makeFields(t2Fields, i));
			t3.insert(i, makeFields(t3Fields, i));
		}
	}

	private void search1(int vals[])  throws IOException {

		LinkedList<String> fields1;
		LinkedList<String> fields2;
		LinkedList<String> fields3;

		for (int i = 0;  i < vals.length; i++) {
			fields1 = t1.search(vals[i]);
			print(fields1, vals[i]);
			fields2 = t1.search(vals[i]);
			print(fields2, vals[i]);
			fields3 = t1.search(vals[i]);
			print(fields3, vals[i]);
		}

	}

	private void rangeSearch(int low, int high) throws IOException  {
		LinkedList<LinkedList<String>> list1;
		LinkedList<LinkedList<String>> list2;
		LinkedList<LinkedList<String>> list3;

		list1 = t1.search(low, high);
		if (list1.isEmpty()) { 
			System.out.println("No values in the range "+low+" to "+high+" in t1");
		}
		else {
			printList(list1);
		}
		list2 = t2.search(low, high);
		if (list2.isEmpty()) { 
			System.out.println("No values in the range "+low+" to "+high+" in t2");
		}
		else {
			printList(list2);
		}
		list3 = t3.search(low, high);
		if (list3.isEmpty()) { 
			System.out.println("No values in the range "+low+" to "+high+" in t3");
		}
		else {
			printList(list3);
		}
	}

	private void printList(LinkedList<LinkedList<String>> list) throws IOException  {
		for (int i = 0; i < list.size(); i++) {
			LinkedList<String> items= list.get(i);
			int k = new Integer(items.removeFirst());
			print(items, k);
		}

	}




	private void remove1(int limit)  throws IOException {
		for (int i = limit/2; i < limit; i = i+2) {
			t1.remove(i);
			t2.remove(i);
			t3.remove(i);
		}
	}

	private void remove2(int limit)  throws IOException {
		for (int i = limit/2; i >= 10 ; i = i-2) {
			t1.remove(i);
			t2.remove(i);
			t3.remove(i);
		}
	}
	

	public H6TestB() throws IOException {
		int limit;

		t1 = new DBTable("f1", t1Fields, 60);
		t2 = new DBTable("f2", t2Fields, 72);
		t3 = new DBTable("f3", t3Fields, 132);

		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the input limit: ");
		limit = scan.nextInt();

		insert1(limit);

		System.out.print("Enter a first value to search for: ");
		int s1 = scan.nextInt();
		System.out.print("Enter a second value (greater than or equal to the first) to search for: ");
		int s2 = scan.nextInt();
		System.out.print("Enter a third value to search for: ");
		int s3 = scan.nextInt();


		System.out.println("Equality Search\n");
		int vals[] = {s1, s2, s3};
		search1(vals);

		System.out.println("First Range Search\n");		
		rangeSearch(s1, s2);


		
		//remove1(limit);

		t1.close();
		t2.close();
		t3.close();

		t1 = new DBTable("f1");
		t2 = new DBTable("f2");
		t3 = new DBTable("f3");

		//remove2(limit);

		//System.out.println("Print after remove 2\n");

		t1.print();
		t2.print();
		t3.print();

		System.out.println("Second Range Search\n");

		rangeSearch(s1, s2);
		

		t1.close();
		t2.close();
		t3.close();
		scan.close(); ///I added this

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
			
		

	public static void main(String args[])  throws IOException  {
		new H6TestB();
	}
}