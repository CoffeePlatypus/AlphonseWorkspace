import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;

public class DBTable {
	
	private RandomAccessFile rows; //the file that stores the row in the table
	private long free; //head of the free list space for rows
	private int numOfFields; //2
	private int otherFieldLenghts[]; // 10 and 20 from example
	private BTree tree;
	
	private class Row{
		private int keyField;
		private char otherFields[][];
		
		public Row(long addr) throws IOException{
			//System.out.println("addr"+addr);
			rows.seek(addr);
			keyField=rows.readInt();
			otherFields=new char[numOfFields][1];
			for(int i=0;i<numOfFields;i++) {
				otherFields[i]=new char[otherFieldLenghts[i]];
				for(int j=0;j<otherFieldLenghts[i];j++) {
					//System.out.println("[i] "+i+" [j] "+j);
					char c=rows.readChar();
					if(c!=0) {
						//System.out.println(c);
					}
					otherFields[i][j]=c;
				}
			}
		}
		
		public Row(int kF, char[][] oF) {
			keyField=kF;
			otherFields=oF;
		}
		
		public void writeRow(long addr) throws IOException{
			rows.seek(addr);
			rows.writeInt(keyField);
			//System.out.println("Write Row");
			for(int i=0;i<numOfFields;i++) {
				for(int j=0;j<otherFieldLenghts[i];j++) {
					//if(otherFields[i][j]!=0) {
						//System.out.println(otherFields[i][j]);
					//}
					rows.writeChar(otherFields[i][j]);
				}
				//System.out.println();
			}
		}
	}
	
	public DBTable( String filename, int fl[], int bsize) throws IOException{
		/*
		 * use this constructor to create an new db table
		 *  filename is the name of the file for the table
		 *  fl is lenght of other fields
		 *  fl.lenght incates how many other fields are part of the row
		 *  bsize is block size
		 *  a b treee must be created for the key field in table
		 *  delete preexisting files
		 */
		File f=new File(filename);
		if(f.exists()) {
			f.delete();
		}
		rows=new RandomAccessFile(f,"rw");
		otherFieldLenghts=fl;
		numOfFields=fl.length;
		free=0;
		rows.writeInt(numOfFields);
		for(int i=0;i<numOfFields;i++) {
			rows.writeInt(otherFieldLenghts[i]);
		}
		rows.writeLong(free);
		tree=new BTree(filename+"Index",bsize);
	}
	
	public DBTable(String filename) throws IOException{
		rows=new RandomAccessFile(filename,"rw");
		rows.seek(0);
		numOfFields=rows.readInt();
		otherFieldLenghts=new int[numOfFields];
		for(int i=0;i<numOfFields;i++) {
			otherFieldLenghts[i]=rows.readInt();
			//System.out.println("fieldLenghtsf at i"+ otherFieldLenghts[i]);
		}
		free=rows.readLong();
		tree=new BTree(filename+"Index");
	}
	
	public boolean insert(int key, char fields[][]) throws IOException{
		/*
		 * if a row with the key is not in the table the row is added and the method
		 *  returns true otherwise the row is not added and the mehtod return false
		 *  the method must use the btreee to determine if a row with the key exists
		 *  if the row is added the key is also added into the btree
		 */
		Row rowney=new Row(key,fields);
		long addr=getFree();
		boolean added=tree.insert(key, addr);
		if(added) {
			rowney.writeRow(addr);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean remove(int key) throws IOException{
		/*
		 * if a row with the key is in the table it is removed and true is returned
		 *  toehrwise false is returned
		 *   the method must us the btree to determine if a row with the key exists
		 *   if the rwo is deleted the key must be deleted from the btreee
		 */
		long addr=tree.remove(key);
		if(addr!=0) {
			addFree(addr);
		}
		return addr!=0;
	}
	
	public LinkedList<String> search(int key) throws IOException{
		/*
		 * if a row with the key is found in the table return a list o the 
		 * no null charachers
		 * if row with key is not found return empty list
		 * use equality search
		 */
		LinkedList<String> listy=new LinkedList<String>();
		long addr=tree.search(key);
		if(addr==0) {
			return listy;
		}else{
			Row rowney=new Row(addr);
			for(int i=0;i<numOfFields;i++) {
				String wordy="";
				for(int j=0;j<otherFieldLenghts[i];j++) {
					char c=rowney.otherFields[i][j];
					if(c==0) {
						break;
					}
					wordy=wordy+c;
				}
				listy.add(wordy);
			}
		}
		return listy;
	}
	
	public LinkedList<LinkedList<String>> search(int low,int high) throws IOException{
		/*
		 * low<high 
		 * none return empty list
		 * ranged search in tree
		 */
		LinkedList<Long> longList= tree.search(low, high);
		LinkedList<LinkedList<String>> longerList=new LinkedList<LinkedList<String>>();
		Iterator<Long> longiterator=longList.iterator();
		while(longiterator.hasNext()) {
			LinkedList<String> listy=new LinkedList<String>();
			long addr=longiterator.next();
			//System.out.println("addr of list "+addr);
			Row rowney=new Row(addr);
			for(int i=0;i<numOfFields;i++) {
				String wordy="";
				//System.out.println("Other field lenghts"+ otherFieldLenghts[i]);
				for(int j=0;j<otherFieldLenghts[i];j++) {
					char c=rowney.otherFields[i][j];
					if(c==0) {
						//System.out.println("break");
						break;
					}
					wordy=wordy+c;
				}
//				System.out.println("worddy "+wordy);
//				System.out.println("is empty 0.o? "+wordy.equals(""));
				if(!wordy.equals("")) {
					listy.add(wordy);
				}
			}
			longerList.add(listy);
		}
		//printyListy(longerList);
		return longerList;
		
	}
	
	private void printyListy(LinkedList<LinkedList<String>> stringy) {
		while(!stringy.isEmpty()) {
			LinkedList<String> lissss=stringy.removeFirst();
			while(!lissss.isEmpty()) {
				System.out.println(lissss.removeFirst());
			}
		}
		
	}

	public void close() throws IOException{
		
		rows.close();
		
	}
	
	private	long getFree() throws IOException {	
	 	long addr=0;		
	 	if(free==0)	{	
			addr=rows.length();	
	 	}else{	
			addr=free;	
			rows.seek(addr);
			free=rows.readLong();	
	 	}	
	 	return addr;	
	}
	
	private	void addFree(long addr) throws IOException {	
	 	rows.seek(addr);	
	 	rows.writeLong(free);
	 	free=addr;
	}
	
	public void print() throws IOException{
		LinkedList<Long> longList=tree.getLeafs();
		Iterator<Long> listiterator=longList.iterator();
		while(listiterator.hasNext()) {
			Row rowney=new Row(listiterator.next());
			for(int i=0;i<numOfFields;i++) {
				for(int j=0;j<otherFieldLenghts[i];j++) {
					char c=rowney.otherFields[i][j];
					if(c==0) {
						break;
					}
					System.out.print(c);;
				}
				System.out.println();
			}
		}
	}
	
	public void printTree() throws IOException{
		tree.print();
	}
	
	

}
