
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Stack;


public class BTree {
	
	RandomAccessFile f;
	private int order;
	private int blockSize;
	private long root;
	private long free;
	private Stack<BTreeNode> path;
	private Stack<Long> pathAddr;
	private int sendToParent;
	//BTreeNode node;
	
	private class BTreeNode {
		private int count;	//num of keys  // make count negative for leaf
		private int iPath=0;
		private int[] keys;
		private long[] children; //addr of children or addr in DB table
		//todo constucton //todo isleaf? // read or write from file
		
		public BTreeNode() {
			
		}
		
		public BTreeNode(int c, int[] k, long[] ch) {
			count=c;
			keys=k;
			children=ch;
		}
		
		public BTreeNode(long addr) throws IOException {
			//System.out.println("Addr"+addr);
			f.seek(addr);
			count=f.readInt();
			//System.out.println("	isLeaf "+isLeaf());
			//System.out.println("order "+order);
			keys=new int[order-1];
			children=new long[order];
			for(int i=0;i<order-1;i++) {   
				if(i>=size()) {
					int j=f.readInt();
					//System.out.println("		extra key "+j);
				}else{
					keys[i]=f.readInt();
					//System.out.println("	key at i "+keys[i]);
				}
			}
			long holdn=0;
			for(int i=0;i<order;i++) {
				if(isLeaf()&& i==order-1) {
					children[order-1]=f.readLong();
					//System.out.println("	next "+children[order-1]);
				}else if(!isLeaf()&& i==size()) {
					children[i]=f.readLong();
					//System.out.println("	child at i "+children[i]);
				}else if(i>size()-1) {
					holdn=f.readLong();
					//System.out.println("		extra child "+holdn);
				}else{
					children[i]=f.readLong();
					//System.out.println("	child at i "+children[i]);
				}
			}
//			if(isLeaf()) {
//				System.out.println("order "+order+" size "+size());
//				if(order==size()+1) {
//					System.out.println("here        "+holdn);
//					children[order-1]=holdn;
//				}
//				System.out.println("linked leaf?"+children[order-1]);
//			}
		}

				
		public void writeNode(long addr) throws IOException {
			//System.out.println("wrote node at" +addr);
			f.seek(addr);
			f.writeInt(count);
			//System.out.println("keys");
			for(int i=0;i<order-1;i++) {
				//System.out.println(keys[i]);
				f.writeInt(keys[i]);
			}
			//System.out.println("children");
			for(int i=0;i<order;i++) {
				//System.out.println(children[i]);
				f.writeLong(children[i]);
			}
			//System.out.println("written and now check and read");
			//BTreeNode nody=new BTreeNode(addr);
			
		}
		
		public int size() {
			return Math.abs(count);
		}
		
		public boolean isLeaf() {
			return count<=0;
		}
		
		public boolean isFull() {
			return size()==(order-1);
		}
		
		public boolean bigEnough() {
			return size()-1>=2; 
		}
		
		public void setI(int i) {
			iPath=i;
		}
		
	}
	
	public BTree() {
		
	}
	
	public BTree(String filename,int bsize) throws IOException {
		//pre order>=4;
		//make new Btree
		//if previous file then delete
		order=bsize/12;
		blockSize=bsize;
		File fil=new File(filename);
		if(fil.exists()) {
			fil.delete();
		}
		f=new RandomAccessFile(fil,"rw");
		
		root=0;
		free=0;
		f.seek(0);
		f.writeLong(root);
		f.writeLong(free);
		f.writeInt(blockSize);
		//System.out.println("----------new b tree order "+order+" --------------------");
	}
	
	public BTree(String filename) throws IOException{
		//open existing
		// read order, root, free;
		f=new RandomAccessFile(filename,"rw");
		root=f.readLong();
		free=f.readLong();
		blockSize=f.readInt();
		order=blockSize/12;
		//System.out.println("----------old b tree order "+order+" -------------------");
	}
	
	public boolean insert(int k, long addr) throws IOException{ //addr of current node or newNode
		//insert a new key
		//addr is the address of the row that contains tree
		// return true for insert or false if dublicate key
		//special case empty
		if(root==0) {
			int[] newKeys=new int[order-1];
			long[] newChildren=new long[order];
			newKeys[0]=k;
			newChildren[0]=addr;
			BTreeNode nody=new BTreeNode(-1,newKeys,newChildren);
			long nodeAddr=getFree();
			nody.writeNode(nodeAddr);
			root=nodeAddr;
			writeRoot(root);
			return true;
		}
		path=new Stack<BTreeNode>();
		pathAddr=new Stack<Long>();
		path.push(new BTreeNode(root));
		pathAddr.push(root);
		if(!inSearch(new BTreeNode(root),k)) {
			return false;
		}
		boolean split=true;
		BTreeNode node=path.pop();
		long nodeAddr=pathAddr.pop();
		if(!node.isFull()) {
			//System.out.println("Fits In Leaf, write to "+nodeAddr);
			addToNode(k, addr, node);
			node.writeNode(nodeAddr);
			split=false;
		}else{
			//System.out.println("Split Leaf");
			BTreeNode newNode=splitLeaf(k,addr,node);
			int val=newNode.keys[0];
			Long loc=getFree();
			//System.out.println("loc" +loc);
			node.children[order-1]=loc;
			node.writeNode(nodeAddr);
			newNode.writeNode(loc);
			node.writeNode(nodeAddr);
			//System.out.println("print Node");
			//print(nodeAddr);//
			//System.out.println();
			//System.out.println("print newNode");
			//print(loc);//
			//System.out.println();
			while(!path.isEmpty()&&split) {
				node=path.pop();
				nodeAddr=pathAddr.pop();
				if(!node.isFull()) {
					//System.out.println("room in parent");
					addToNode(val,loc,node);
					node.writeNode(nodeAddr);
					split=false;
				}else{
					//System.out.println("Split non leaf");
					newNode=split(val,loc,node);
					val=sendToParent;
					node.writeNode(nodeAddr);
					loc=getFree();
					newNode.writeNode(loc);
				}
			}
			
			if(split) {
				int[] newKeys=new int[order-1];
				long[] newChildren=new long[order];
				newKeys[0]=val;
				newChildren[0]=root;
				newChildren[1]=loc;
				newNode=new BTreeNode(1,newKeys,newChildren);
				loc=getFree();
				newNode.writeNode(loc);
				root=loc;
				writeRoot(root);
			}
		}
		return true;
		
	}
	private void writeRoot(long r) throws IOException{
		f.seek(0);
		f.writeLong(r);
	}

	public void check(long addr) throws IOException{
		BTreeNode n=new BTreeNode(addr);
	}
	
	private BTreeNode split(int val, Long loc, BTreeNode node) {
		//System.out.println("val "+val+"loc "+loc);
		int[] newKeys=new int[order-1];
		long[] newChildren=new long[order];
		int[] bigKeys=new int[order];
		long[] bigChildren=new long[order+1];
		for(int i=0;i<node.size();i++) {
			bigKeys[i]=node.keys[i];
			bigChildren[i]=node.children[i];
		}
		bigChildren[order-1]=node.children[order-1];
		addNon(bigKeys,bigChildren,val,loc);
		//printArray(bigKeys);//
		//System.out.println();
		//printArray(bigChildren);//
		int holdSize=node.size()+1;
		node.count=0;
		int county=0;
		//System.out.println("holdsize/2 "+holdSize/2+" holdSize "+holdSize);
		for(int i=0;i<holdSize;i++) {
			if(i==(holdSize/2)) {
				//System.out.println("got to send");
				sendToParent=bigKeys[i];
				node.children[i]=bigChildren[i];
			}else if(i<(holdSize/2)) {
				node.keys[i]=bigKeys[i];
				node.children[i]=bigChildren[i];
				node.count++;
			}else{
				newKeys[county]=bigKeys[i];
				newChildren[county]=bigChildren[i];
				county++;
			}
		}
		newChildren[county]=bigChildren[holdSize];
//		System.out.println("node");
//		printArray(node.keys);
//		System.out.println("children");
//		printArray(node.children);
//		System.out.println("newNode");
//		printArray(newKeys);
//		System.out.println("children");
//		printArray(newChildren);
//		System.out.println("Send to parent" +sendToParent);
		BTreeNode newNode=new BTreeNode(county,newKeys,newChildren);
		//newNode.children[order-1]=next;
		return newNode;
		
	}

	private BTreeNode splitLeaf(int k,long addr, BTreeNode node) {  //how to split and insert k
		int[] newKeys=new int[order-1];
		long[] newChildren=new long[order];
		int[] bigKeys=new int[order];
		long[] bigChildren=new long[order+1];
		long next=node.children[order-1];
		//System.out.println("hold next as "+next);
		for(int i=0;i<node.size();i++) {
			bigKeys[i]=node.keys[i];
			bigChildren[i]=node.children[i];
		}
		addLe(bigKeys,bigChildren,k,addr);
		//printArray(bigKeys);
		int holdSize=node.size()+1;
		node.count=0;
		int county=0;
		for(int i=0;i<holdSize;i++) {
			//System.out.println(i+"<"+node.size()/2);
			if(i<(holdSize/2)) {
				node.keys[i]=bigKeys[i];
				node.children[i]=bigChildren[i];
				node.count--;
			}else{
				newKeys[county]=bigKeys[i];
				newChildren[county]=bigChildren[i];
				county++;
			}
		}
//		System.out.println("node");
//		printArray(node.keys);
//		System.out.println("newNode");
//		printArray(newKeys);
		BTreeNode newNode=new BTreeNode(-1*county,newKeys,newChildren);
		newNode.children[order-1]=next;
		return newNode;
	}
	
	private void printArray(int[] o) {
		for(int i=0;i<o.length;i++) {
			System.out.println(o[i]);
		}
	}
	private void printArray(long[] o) {
		for(int i=0;i<o.length;i++) {
			System.out.println(o[i]);
		}
	}
	
	private void addLe(int[] bigKeys, long[] bigChildren, int k, long addr) {
		for(int i=order-1; i>=0;i--) {
			//System.out.println(bigKeys[i]+"<"+k);
			if(i==0) {
				bigKeys[0]=k;
				bigChildren[0]=addr;
			}else if(bigKeys[i-1]<k) {
				//System.out.println("reached");
				bigKeys[i]=k;
				bigChildren[i]=addr;
				return;
			}else{
				bigChildren[i]=bigChildren[i-1];
				bigKeys[i]=bigKeys[i-1];
			}
		}
	}
	
	private void addNon(int[] bigKeys, long[] bigChildren, int val, Long loc) {
		for(int i=order-1; i>=0;i--) {
			//System.out.println(bigKeys[i]+"<"+k);
			if(i==0) {
				bigChildren[i+1]=bigChildren[0];
				bigChildren[0]=loc;
				bigKeys[0]=val;
			}else if (bigKeys[i-1]<val) {
				//System.out.println("reached");
				bigKeys[i]=val;
				bigChildren[i+1]=loc;
				return;
			}else{
				//System.out.println(i+" i that is bad and causes problems");
				bigChildren[i+1]=bigChildren[i];
				bigKeys[i]=bigKeys[i-1]; ///Problem fixed?
			}
		}
		
		
	}

	private void addToNode(int k, long addr, BTreeNode temp) {
		//temp.count--;
		int i=findSpot(temp.keys,temp.size(),k);
		if(temp.isLeaf()) {
			for(int j=temp.size();j>i;j--) {
				temp.children[j]=temp.children[j-1];
				temp.keys[j]=temp.keys[j-1];
			}
			temp.keys[i]= k;
			temp.children[i]=addr;
			temp.count--;
		}else{
			for(int j=temp.size();j>i;j--) {
				temp.children[j+1]=temp.children[j];
				temp.keys[j]=temp.keys[j-1];
			}
			temp.keys[i]= k;
			temp.children[i+1]=addr;
			temp.count++;
		}
			
	}

	private int findSpot(int[] keys, int count, int k) {
		for(int i=0;i<count;i++) {
			if(k<keys[i]) {
				return i;
			}
		}
		return count;
	}

	private boolean inSearch(BTreeNode n, int k) throws IOException {
		//System.out.println("count "+n.count);
		if(n.isLeaf()) {
			for(int i=0;i<n.size();i++) {
				if(n.keys[i]==k) {
					return false;
				}
			}
			return true;
		}
		for(int i=0;i<n.size();i++) {
			if(k<n.keys[i]) {
				BTreeNode temp=new BTreeNode(n.children[i]);
				temp.setI(i);
				pathAddr.push(n.children[i]);
				path.push(temp);    
				return inSearch(temp, k);
			}
		}
		long tempAddr=n.children[n.size()];
		BTreeNode temp=new BTreeNode(tempAddr);
		path.push(temp);
		pathAddr.push(tempAddr);
		return inSearch(temp,k);	
	}
	
	public long remove(int k) throws IOException{
		//remove the key from the BTree
		//return the adress of the row or 0 if the key is not found
		//20pt
		System.out.println("value to delete "+k);
		path=new Stack<BTreeNode>();
		pathAddr=new Stack<Long>();
		path.push(new BTreeNode(root));
		pathAddr.push(root);
		if(inSearch(new BTreeNode(root),k)) {
			return 0;
		}
		long hold=search(k);
		boolean tooSmall=true;
		BTreeNode node=path.pop();
		long nodeAddr=pathAddr.pop();
		System.out.print("node to delete "+k+" k from");
		print(nodeAddr);
		if(node.bigEnough() || nodeAddr==root) {
			System.out.println("Not too small");
			int i=getIndex(k,node);
			shiftLe(i,node);
			tooSmall=false;
			node.writeNode(nodeAddr);
			addFree(hold);
			return hold;
		}else{
			System.out.println("too small");
			BTreeNode nextLeaf=new BTreeNode(node.children[order-1]);
			int i=getIndex(k,node);
			int holdK;
			long holdA;
			long holdN=node.children[order-1];
			if(i==0) {
				holdK=node.keys[1];
				holdA=node.children[i];
			}else{
				holdK=node.keys[0];
				holdA=node.children[0];
			}
			addToNode(holdK,holdA,nextLeaf);
			nextLeaf.writeNode(holdN);
			///// needa update removed nodes next but how?
			if(!path.isEmpty()) {
				node=path.pop();
				nodeAddr=pathAddr.pop();
			}
			BTreeNode parent;
			long parentAddr;
			BTreeNode sibling;
			long siblingAddr;
			
			while(!path.isEmpty() && tooSmall) {
				if(node.bigEnough()) {
					System.out.println("parent big enough");
					int j=node.iPath;
					shift(i,node);
					node.writeNode(nodeAddr);
					tooSmall=false;
				}else{
					System.out.println("parent too small");
					parent=path.pop();
					parentAddr=pathAddr.pop();
					siblingAddr=parent.children[i+1];
					sibling=new BTreeNode(siblingAddr);
					merge(node,sibling);
					sibling.writeNode(siblingAddr);
					node=parent;
					nodeAddr=parentAddr;
				}
			}
			if(tooSmall) {
				root=nodeAddr;
			}
			
		}
		
	
		addFree(hold);
		return hold;
		
	}
	
	private void merge(BTreeNode node, BTreeNode sibling) {
		int i=node.iPath;
		if(i==0) {
			i=1;
		}else{
			i=0;
		}
		addToNode(node.keys[i],node.children[i],sibling);
		
	}

	private void shift(int index, BTreeNode n) {
		for(int i=index-1;i<n.size()-1;i++) {
			n.children[i]=n.children[i+1];
			n.keys[i]=n.keys[i+1];
		}
		n.children[n.size()-1]=n.children[n.size()];
		n.count--;
	}

	private int getDeletedI(int k,BTreeNode n) {
		for(int i=0;i<n.size();i++) {
			if(k>n.keys[i]) {
				return i;
			}
		}
		return n.size()-1;
	}

	private void shiftLe(int index,BTreeNode n) {
		for(int i=index;i<n.size()-1;i++) {
			n.children[i]=n.children[i+1];
			n.keys[i]=n.keys[i+1];
		}
		n.count++;
	}

	private int getIndex(int k, BTreeNode n) {
		for(int i=0;i<n.size();i++) {
			if(n.keys[i]==k) {
				return i;
			}
		}
		return -1;
	}

	public long search(int k) throws IOException {
		//return addr or 0 if not found
		return search(new BTreeNode(root),k);
	}
	
	private long search(BTreeNode n,int k) throws IOException {
		if(n.isLeaf()) {
			for(int i=0;i<n.size();i++) {
				if(n.keys[i]==k) {
					return n.children[i];
				}
			}
			return 0;
		}
		for(int i=0;i<n.size();i++) {
			if(k<n.keys[i]) {
				return search(new BTreeNode(n.children[i]), k);
			}
		}
		return search(new BTreeNode(n.children[n.size()]),k);	
	}
	
	public LinkedList<Long> search(int low,int high) throws IOException{
		//pre low<=high
		// 10 pt ec
		//return a list of row address for all keys in the range low to high inclusive
		// if none in range return empty list
		LinkedList<Long> listy=new LinkedList<Long>();
//		System.out.println("root of my problems "+root);
//		System.out.println("order is "+order);
		BTreeNode nody=new BTreeNode(root);
		while(!nody.isLeaf()) {
			for(int i=0;i<nody.size();i++) {
				//System.out.println(low+"<"+nody.keys[i]);
				if(low<=nody.keys[i]) {
					nody=new BTreeNode(nody.children[i]);
					break;
				}
				if(i+1==nody.size()){
					nody=new BTreeNode(nody.children[nody.size()]);
				}
			}
		}
		int i=0;
		while(nody.keys[i]<=high) {
			if(low<=nody.keys[i]) {
				listy.add(nody.children[i]);
			}
			i++;
			if(i>nody.size()-1) {
				long next=nody.children[order-1];
				//System.out.println(next);
				if(next==0) {
					break;
				}
				nody=new BTreeNode(next);
				i=0;
			}
		}
		return listy;
	}
	
	public void print() throws IOException {
		//print BTree nodes to standerd output
		//print one node per line
		print(root);
	}

	private void print(long addr) throws IOException {
		//System.out.println("Meow");
		//System.out.println("addr "+addr);
		BTreeNode nody=new BTreeNode(addr);
		if(nody.isLeaf()) {
			System.out.print("Count: "+nody.count+" ");
			System.out.print("Keys: ");
			for(int i=0; i<nody.size(); i++) {
				System.out.print(nody.keys[i] +" ");
			}
			System.out.print("Children: ");
			for(int i=0;i<nody.size();i++) {
				//System.out.println(i);
				System.out.print(nody.children[i]+" ");
			}
			System.out.println("Next"+nody.children[order-1]);
		}else {
			System.out.print("Count: "+nody.count+" ");
			System.out.print("Keys: ");
			for(int i=0; i<=nody.size()-1; i++) {
				System.out.print(nody.keys[i] +" ");
			}
			System.out.print("Children: ");
			for(int i=0;i<=nody.size();i++) {
				//System.out.println(i);
				System.out.print(nody.children[i]+" ");
			}
			for(int i=0;i<nody.size()+1;i++) {
				System.out.println();
				print(nody.children[i]);
			}
		}
	}
	
	public LinkedList<Long> getLeafs() throws IOException{
		LinkedList<Long> listy=new LinkedList<Long>();
		if(root==0) {
			return listy;
		}
		BTreeNode n=new BTreeNode(root);
		while(!n.isLeaf()) {
			n=new BTreeNode(n.children[0]);
		}
		int i=0;
		while(true) {
			listy.add(n.children[i]);
			i++;
			if(i>n.size()-1) {
				long next=n.children[order-1];
				System.out.println(next);
				if(next==0) {
					break;
				}
				n=new BTreeNode(next);
				i=0;
			}
		}
		return listy;
	}
	
	public void close() throws IOException{
		f.seek(0);
		f.writeLong(root);
		f.writeLong(free);
		f.close();
		
	}
	
	private	long getFree() throws IOException {	
	 	long addr=0;		
	 	if(free==0)	{
			addr=f.length();	
	 	}else{	
			addr=free;	
			f.seek(addr);
			free=f.readLong();	
	 	}	
	 	return addr;	
	}
	
	private	void addFree(long addr) throws IOException {	
	 	f.seek(addr);	
	 	f.writeLong(free);
	 	free=addr;
	}
}
