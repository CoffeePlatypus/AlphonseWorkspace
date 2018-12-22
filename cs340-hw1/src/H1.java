// Julia Froegel

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class H1<T extends Comparable <? super T>> {

	private class Node {
		T data;
		Node next;
		Node(T d, Node n) {
			data=d;
			next=n;
		}
	}

	private Node head;

	public H1() {
		head=null;
	}

	public void insert(T d) {
	//Pre: the list is sorted in ascending order
	//Post: d has been added to the list and the list is sorted in ascending order
		if(head==null) {		//Can combine first two cases
			head=new Node(d, null);
			return;
		}else if(d.compareTo(head.data)<0){
			head=new Node(d,head);
			return;
		}
		Node temp=head;
		while(temp.next!=null && d.compareTo(temp.next.data)>0) {
			temp=temp.next;
		}
		Node add=new Node(d,temp.next);
		temp.next=add;

	}

	public String toString() {
	// returns a comma delimited string of the contents of list
	// the implementation must be recursive. use and auxilary method to do most of the work;
		return printNodes(head);
	}

	private String printNodes(Node n) {
		if(n==null) {
			return "";
		}else if(n.next==null) {
			return n.data.toString();
		}
		return n.data.toString()+","+ printNodes(n.next);
	}

	public static void main(String args[]) throws IOException {
		File f = new File(args[0]);
		//System.out.print(f.exists());
		BufferedReader rin=new BufferedReader(new FileReader(f));
		String line=rin.readLine();
		while(line!=null) {
			Scanner scanType=new Scanner(line);
			scanType.useDelimiter(":");
			String type=scanType.next();

			if(scanType.hasNext()) {
				String ger=scanType.next();
				Scanner scanner= new Scanner(ger);
				scanner.useDelimiter(",");

				if(type.equals("String")) {
					H1<String> list=new H1<String>();
					while(scanner.hasNext()){
						list.insert(scanner.next());
						//System.out.println(list.toString());
					}
					System.out.println(list.toString());
				}else{
					H1<Integer> list=new H1<Integer>();
					while(scanner.hasNext()) {
						list.insert(new Integer(scanner.next()));
					}
					System.out.println(list.toString());
				}
				scanner.close();
			}else{
				System.out.println();
			}
			scanType.close();
			line=rin.readLine();
		}
		rin.close();
	}
}
