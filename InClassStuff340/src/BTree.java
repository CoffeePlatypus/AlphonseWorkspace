import java.io.RandomAccessFile;

public class BTree {
	
	RandomAccessFile f;
	int order;
	long root;
	long free;
	
	private class BTreeNode {
		private int count;	//num of keys  // make count negative for leaf
		private int[] keys;
		private long[] children; //addr of children or addr in DB table
		//todo constucton //todo isleaf? // read or write from file
	}
	
	public BTreee(String filename,int order) {
		//pre order>=4;
		//make new Btree
		//if previous file then delte
	}
	
	public BTree(String filename) {
		//open existing
		// read order, root, free;
	}
	
	public boolean insert(int k, long addr) {
		//insert a new kewy
		//addr is the adress of the row that contains tree
		// return true for insert or false if dublicate key
	}
	
	public long remove(int k) {
		//remove the key from the BTree
		//return the adress of the row or 0 if the key is not found
	}
	
	public long search(int k) {
		//return addr or 0 if not found
	}
	
	public LinkedList<Long> search(int low,int high) {
		//pre low<=high
		// 10 pt ec
		//return a list of row adress for all keys in the range low to high inclusive
		// if none in range return empty list
	}
	
	public void print() {
		//print BTree nodes to standerd output
		//print one node per line
	}
	

}
