import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryFileLinkedList {
	RandomAccessFile f;
	private long head;	//first	8	bytes	in	the	file		
	private long free;	//second	8	bytes	in	the	file	
	
	private class Node {
		private	int	data;	
		private	long next;
		private Node(int d, long n) {	
			data=d;
			next=n;	
		}
		
		private	Node(long addr) throws IOException{
			f.seek(addr);
			data=f.readInt();
			next=f.readLong();	
		}

		private	void writeNode(long addr) throws IOException	{
			f.seek(addr);	
			f.writeInt(data);	
			f.writeLong(next);
		}
	}
	
	public BinaryFileLinkedList(String	filename,	boolean	clear) throws IOException {
		File path=new File(filename);	
		if (path.exists() && clear)	{	
			path.delete();	
		}	
		f = new RandomAccessFile(path, "rw");
		if (clear) {	
			head = 0;
			free=0;	
			f.writeLong(head);	
			f.writeLong(free);	
		}else{	
			f.seek(0);
			head=f.readLong();	
			free=f.readLong();	
		}	
	}
	
	public void insertFirst(int	d) throws IOException {
		long addr;
		Node temp;	
		addr=getFree();			
		temp=new Node(d,head);
		temp.writeNode(addr);	
		head=addr;	
	}
	
	public void insertLast(int d) throws IOException	 {	
		Node temp;
		long tempAddr;
		Node newNode= new Node(d, 0);
		long addr=getFree();
		if (head==0) {	
			head=addr;	
		}else{
			temp=new Node(head);
			tempAddr = head;
			while (temp.next !=	0) {
				tempAddr = temp.next;	
				temp=new Node(temp.next);	
			}	
				temp.next	=	addr;	
				temp.writeNode(tempAddr);	
			}	
			newNode.writeNode(addr);		
	}	
	public	void	remove(int	d)		throws	IOException	{	
	 	long addr;	
	 	long addr1;	
	 	Node temp;	
	 	Node temp1;	
	 	while (head!=0 && (temp=new Node(head)).data==d) {	
			addr = temp.next;	
			addFree(head,temp);	
			head=addr;	
	 	}	
	 	if(head==0)	return;	
	 	temp=new Node(head);	
	 	addr=head;	
	 	while(temp.next!=0)	{	
			temp1=new Node(temp.next);	
			addr1=temp.next;	
			if(temp1.data==d) {	
				temp.next=temp1.next;	
				addFree(addr1,temp1);	
			}else{	
				temp.writeNode(addr);	
				temp=temp1;	
				addr=addr1;	
			}	
	 	}	
	 	temp.writeNode(addr);
	}
	
	private	long getFree() throws IOException {	
	 	long addr=0;	
	 	Node temp;	
	 	if(free==0)	{	
			addr=f.length();	
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
	
	public void	print()	throws	IOException	{	
	 	long addr =	head;	
	 	Node temp;	
	 	while(addr !=0){	
			temp=new Node(addr);	
			System.out.print(""+temp.data+"	");	
			addr=temp.next;	
	 	}	
	 	System.out.println();	
	}	
	public void close() throws IOException {	
	 	f.seek(0);	
	 	f.writeLong(head);	
	 	f.writeLong(free);	
	 	f.close();	
	}	
}
