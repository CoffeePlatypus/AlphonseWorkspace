import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class H3 {
	
	int keySize;
	int dataSize;
	RandomAccessFile list;
	long head;
	long free;
	int numNodes;
	
	private class Node {
		private int[] key;
		private char[] data;
		private long next;
		
		private Node(int[] k, char[] d, long n) {
			key=k;
			data=d;
			next=n;
		}
		
		private Node(long addr) throws IOException {
			list.seek(addr);
			key=new int[keySize];
			data=new char[dataSize];
			for(int i=0; i<keySize; i++) {
				key[i]=list.readInt();
			}
			for(int i=0;i<dataSize;i++) {
				data[i]=list.readChar();			/////Will there be as many chars as dataSize?
			}
			next=list.readLong();
			//System.out.println(next);
		}
		
		private void writeNode(long addr) throws IOException{
			list.seek(addr);
			writeKey();
			writeData();
			list.writeLong(next);
		}

		private void writeData() throws IOException {
			for(int i=0;i<dataSize;i++) {
				list.writeChar(data[i]);
			}
		}

		private void writeKey() throws IOException {
			for(int i=0;i<keySize;i++) {
				list.writeInt(key[i]);
			}
		}
	}
	
	public H3(int ks, int ds, String fileName) throws IOException {
		keySize=ks;
		dataSize=ds;
		head=0;
		free=0;
		//numNodes=0;
		File f=new File(fileName);	
		if (f.exists())	{	
			f.delete();	
		}	
		list = new RandomAccessFile(f, "rw");
		list.writeLong(head);
		list.writeLong(free);
		list.writeInt(keySize);
		list.writeInt(dataSize);
	}
	
	public H3(String fileName) throws IOException{
		list = new RandomAccessFile(new File(fileName), "rw");
		list.seek(0);
		head=list.readLong();
		free=list.readLong();
		keySize=list.readInt();
		dataSize=list.readInt();
		//numNodes=countNodes(head);
	}
	
	public void insert(int[] k, char[] d) throws IOException {
		Node toAdd=new Node(k,d,0);
		long addr=getFree();
		//System.out.println("to add at " +addr);
		if(head==0) {
			//System.out.println("now has head");
			head=addr;
			toAdd.writeNode(addr);
			//System.out.println("h read next at"+new Node(addr).next);
			return;
		}
		Node temp=new Node(head);
		if(compareKeys(k,temp.key)<0) {
			//System.out.println("new head");
			toAdd.next=head;
			//System.out.println(toAdd.next);
			toAdd.writeNode(addr);
			head=addr;
			//System.out.println("nh read next at"+new Node(addr).next);
			return;
		}
		Node next;
		long holdAddr=head;
		while(temp.next!=0 && compareKeys(k,(next=new Node(temp.next)).key)>0) {
			//System.out.println("     "+temp.next);
			holdAddr=temp.next;
			temp=next;
		}
		toAdd.next=temp.next;
		temp.next=addr;
		toAdd.writeNode(addr);
		temp.writeNode(holdAddr);
		//System.out.println("read next at"+new Node(addr).next);
	}
	
	public void remove(int[] k) throws IOException {
		Node temp;
		long tempAddr;
		boolean ret=false;
		while(head!=0 && compareKeys(k, (temp=new Node(head)).key)==0) {
			//tempAddr=head;
			//System.out.println(head);
			long hold=temp.next;
			addFree(head,temp);
			head=hold;
			ret= true;
			//System.out.println(temp.next+"removed head, new head "+head);
		}
		if(head==0 ||ret) return;
		temp=new Node(head);
		tempAddr=head;
		while(temp.next!=0) {
			Node nextN=new Node(temp.next);
			if(compareKeys(nextN.key, k)==0) {
				long hold=nextN.next;
				addFree(temp.next,nextN);
				//System.out.println("temp.next "+temp.next+"nextN next "+hold);
				temp.next=hold;
				temp.writeNode(tempAddr);
			}else{
				tempAddr=temp.next;
				temp=nextN;
			}
		}
	}
	
	private	long getFree() throws IOException {	
	 	long addr=0;	
	 	Node temp;	
	 	if(free==0)	{	
			addr=list.length();	
	 	}else{	
			addr=free;	
			temp=new Node(free);	
			free=temp.next;	
	 	}	
	 	return addr;	
	}
	
	private	void addFree(long newFree,Node temp) throws IOException {	
	 	temp.next=free;	
	 	temp.writeNode(newFree);	
	 	free=newFree;	
	}
	
	public void print() throws IOException {
		long addr =	head;	
	 	Node temp;
	 	//System.out.println("head at "+addr);
	 	//System.out.println("free at "+free);
	 	while(addr !=0){	
			temp=new Node(addr);	
			System.out.println(printKey(temp)+printChars(temp));	
			//System.out.println(temp.next+" next");
			addr=temp.next;	
	 	}	
	}
	
	private String printChars(Node temp) {
		String chars="";
		for(int i=0;i<dataSize;i++) {
			chars=chars+temp.data[i];
		}
		return chars;
	}

	private String printKey(Node n) {
		String keys="";
		for(int i=0;i<keySize;i++) {
			keys=keys+n.key[i]+" ";
		}
		return keys;
	}
	
	public int compareKeys(int[]k1,int[]k2) {
		for(int i=0;i<keySize;i++) {
			if(k1[i]!=k2[i]) {
				return ((Integer)k1[i]).compareTo((Integer)k2[i]);
			}
		}
		return 0;
	}
	
	public String[] find(int[] key) throws IOException {
		if(head==0) return null;
		long addr=head;
		int count=0;
		String found="";
		while(addr!=0){
			Node temp=new Node(addr);
			if(compareKeys(key,temp.key)==0) {
				count++;
				found=found+" "+printChars(temp);
			}
			addr=temp.next;
		}
		String [] names=new String[count];
		Scanner fin= new Scanner(found);
		fin.useDelimiter(" ");
		for(int i=0; i<count; i++) {
			names[i]=fin.next();
		}
		fin.close();
		return names;
		
	}
	
	public void close() throws IOException {
		list.seek(0);	
	 	list.writeLong(head);	
	 	list.writeLong(free);	
	 	list.close();	
	}
}
