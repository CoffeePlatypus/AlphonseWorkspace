
public class BinaryHeap {
	
	int[] contents;
	int size;
	
	public BinaryHeap(int s) {
		contents=new int[s+1];
		size=0;
	}
	
	public void insert(int k) {
		//! full
		size++;
		int child=size;
		int parent=child/2;
		while(contents[parent]>k) {
			contents[child]=contents[parent];
			child=parent;
			parent=child/2;
		}
		contents[child]=k;
	}
	
	public int removeMin() {
		//!empty
		int parent=1;
		int child=2;
		int d=contents[1];
		int k=contents[size];
		size--;
		while(child<=size) {
			parent=child/2;
			if(child<=size && contents[child+1]<contents[child]) {
				child++;
			}
			if(k<contents[child]) break;
			contents[parent]=contents[child];
			child=child*2;
		}
		contents[child/2]=k;
		return d;
	}
	
	public boolean empty() {
		return size==0;
	}
	
	public int getMin() {
		//!empty;
		return contents[1];
	}
	
	public boolean full() {
		return size==contents.length-1;
	}
	
	public int getSize() {
		return size;
	}

}
