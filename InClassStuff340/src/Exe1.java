import java.io.*;
import java.util.*;

public class Exe1 {
//CS 340 Fall 2016 Example 1
//Implement a singly linked list of ints

	private class Node {
		private int data;
		private Node next;
		private Node(int d, Node n) {
			data = d;
			next = n;
		}
	}

	private Node head;

	public Ex1() {
		head = null; //no sentinel node
	}

	public void insertFirst(int d) {
	//insert d as the new first item in the list
	}

	public void insertLast(int d)  {
	//insert d as the new last item in the list
	}

	public String toString() {
	//return a comma delimited string of the contents of the list
	}
	
	public int sum() {
		return sum(head);
	}
	
	private int sum(Node n) {
		if(n==null) {
			return 0;
		}
		return n.data + sum(n.next);
	}
	
	public int max() {
		//return max(head);
		return max(head,Integer.MAX_VALUE);
	}
	
	private int max(Node h) {
		if(h==null) {
			return Integer.MAX_VALUE;
		}
		int m=max(h.next);
		if(m>h.data) {
			return m;
		}
		return h.data;
	}
	
	private int max(Node h, int m) {
		if(h==null) {
			return m;
		}
		if(h.data>m) {
			m=h.data;
		}
		return max(h.next,m);
	}

	public static void main(String args[]) throws IOException {
	//main expects the name of a text file as a command line argument. Each line in the file
	//will contain a comma delimited sequence of integers. For each line in the file 
	//create a list, insert each integer into the front and the back of the list, 	
	//convert the list contents into a comma delimited string and print the string to 
	//standard output
	}
}	
