
public class H4<T extends Comparable<? super T>> {
	T keys[];
	Object data[];
	int maxChildren;
	int size;
	
	public H4(int s,int d) {
		keys=(T[])new Comparable[s];	//Dont worry
		data=new Object[s];
		size=0;
		maxChildren=d;
	}
	
	public void set(T[] k, Object[] d,int s) {
		keys=k;
		data=d;
		size=s;
	}
	
	public T getMinKey() {	//keys are not unique
		//PRE: !empty
		return keys[0];
	}
	
	public Object getMinData() {
		return data[0];
	}
	
	public void removeMin() {
		//PRE: !empty()
		size--;
		if(size==0) {
			return;
		}
		T tempKey=keys[size];
		Object temp=data[size];
		int parent=0;
		int child=indexOfSmallestChild(0);
		//System.out.println("smallest child "+child);
		while(child<=size) {
			parent=(child-1)/maxChildren;
			if(tempKey.compareTo(keys[child])<0) break;
			System.out.println("");
			keys[parent]=keys[child];
			data[parent]=data[child];
			int tchild=indexOfSmallestChild(child);
			if(tchild<0) {
				//child=indexOfSmallestChild(parent);
				keys[child]=tempKey;
				data[child]=temp;
				return;
			}else{
				child=tchild;
			}
		}
		keys[(child-1)/maxChildren]=tempKey;
		data[(child-1)/maxChildren]=temp;
	}
	
	private int indexOfSmallestChild(int i) {
		if(maxChildren*i+1>size||maxChildren*i+1<0) {
			return -1;
		}
		T smallest=keys[maxChildren*i+1];
		int smallestIndex=maxChildren*i+1;
		for(int j=2;j<=maxChildren;j++) {
			T temp=keys[maxChildren*i+j];
			int tempIndex=maxChildren*i+j;
			if(tempIndex>size) {
				//System.out.println("temp too big");
				return smallestIndex;
			}
			if(smallest.compareTo(temp)>0) {
				smallest=temp;
				smallestIndex=tempIndex;
			}
		}
		return smallestIndex;
	}

	public void insert(T k,Object d) {
		//PRE: !full()
		//size++;
		int child=size;
		int parent=(child-1)/maxChildren;
		while(child>0 && keys[parent]!=null && keys[parent].compareTo(k)>0) {
			//System.out.println("entered Loop");
			keys[child]=keys[parent];
			data[child]=data[parent];
			child=parent;
			parent=(child-1)/maxChildren;
			
		}
		
		if(size==0 || child==0) {
			keys[0]=k;
			data[0]=d;
			size++;
			return;
			//System.out.println("if "+d.toString());
		}
		data[child]=d;
		keys[child]=k;
		size++;
			
	}
	
	public boolean empty() {
		return size==0;
	}
	
	public boolean full() {
		return size==keys.length;
	}
	
	public int getSize() {
		return size;
	}
	
	public String toString() {
		String toPrint="";
		for(int i=0;i<size;i++) {
			toPrint=toPrint+keys[i]+" "+data[i]+"\n";
		}
		return toPrint;
	}

}
