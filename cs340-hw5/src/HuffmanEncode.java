import	java.io.*;	
import	java.util.*;

public class HuffmanEncode {	
	private final int NUM_CHARS=128;
	private int[] charCount=new int[NUM_CHARS];
	private int totalNumChars=0;
	private String[] encoding=new String[NUM_CHARS];
	//Implements	the	huffman	encoding	algorithm	
	//Add		private	methods	as	needed
	public HuffmanEncode(String	in,	String	out)	{
		try {
			BufferedReader rin=new BufferedReader(new FileReader(in));
			countChars(rin);
			//System.out.println("characters Counted");
			rin.close();
			PriorityDHeap<Integer> heap=makeHeap();
			//System.out.println("made heap");
			//Should Priority heap be of type Integer?
			HuffmanTree tree=makeTree(heap);
			//System.out.println("made tree");
			HuffmanOutputStream fin= new HuffmanOutputStream(out,tree.toString(),totalNumChars);
			Iterator<String> hufferator=tree.iterator();
			while(hufferator.hasNext()) {
				String temp=hufferator.next();
				encoding[(int)temp.charAt(0)]=temp.substring(1, temp.length());
			}
			//printRay(encoding);
			BufferedReader tin=new BufferedReader(new FileReader(in));
			encodeFile(tin,fin);
			tin.close();
			fin.close();		
			
		} catch (IOException e) {
			System.out.println("IOException HuffmanEncode");
		}
			
	}	
//	private void printRay(String[] a) {
//		System.out.println("encoding");
//		for(int i=0;i<a.length;i++) {
//			System.out.println((char)i+a[i]);
//			//System.out.println(a[i]);
//		}
//		System.out.println("end");	
//	}
	
	private void encodeFile(BufferedReader tin, HuffmanOutputStream fin) throws IOException {
//		String line=tin.readLine();
//		while(line!=null) {
//			for(int i=0;i<line.length();i++) {
//				String temp=encoding[i];
//				for(int j=0;j<temp.length();j++) {
//					if(temp.charAt(j)=='0') {
//						fin.writeBit(0);
//					}else{
//						fin.writeBit(1);
//					}
//				}
//				//int temp=Integer.parseInt(encoding[i]);
//				//writeInt(temp,fin);
//			}
//			line=tin.readLine();
//		}
		int charn= tin.read();
		while(charn>=0) {
			//System.out.print((char)charn);
			String temp=encoding[charn];
			for(int j=0;j<temp.length();j++) {
				if(temp.charAt(j)=='0') {
					//System.out.print(0);
					fin.writeBit(0);
				}else{
					//System.out.print(1);
					fin.writeBit(1);
				}
			}
			charn=tin.read();
			//System.out.println();
		}
		
	}
	
	private HuffmanTree makeTree(PriorityDHeap<Integer> heap) {
		while(heap.getSize()!=1) {
			//System.out.println("treeing "+heap.getSize());
			HuffmanTree t1=(HuffmanTree) heap.getMinData();
			//System.out.println("   t1"+t1.toString());
			int k1=heap.getMinKey();
			heap.removeMin();
			HuffmanTree t2=(HuffmanTree) heap.getMinData();
			//System.out.println("   t2"+t2.toString());
			int k2=heap.getMinKey();
			heap.removeMin();
			heap.insert(k1+k2, new HuffmanTree(t1,t2,(char)NUM_CHARS));
		}
		//System.out.println("Tree= "+heap.getMinData().toString());
		return (HuffmanTree) heap.getMinData();
	}
	private void countChars(BufferedReader rin) throws IOException{
		//System.out.println("started counting");
		/*String line=rin.readLine();
		while(line!=null) {
			for(int i=0;i<line.length();i++) {
				charCount[line.charAt(i)]++;
				totalNumChars++;
			}
			char n='\n';
			charCount[n]++;
			totalNumChars++;
			line=rin.readLine();
		}*/
		int charn= rin.read();
		while(charn>=0) {
			charCount[charn]++;
			totalNumChars++;
			charn=rin.read();
		}
	}
	
	private PriorityDHeap<Integer> makeHeap() {
		PriorityDHeap<Integer> heap=new PriorityDHeap<Integer>(NUM_CHARS,2); ///WHat should number of max children be?
		for(int i=0;i<NUM_CHARS;i++) {
			if(charCount[i]>0) {
				heap.insert(charCount[i], new HuffmanTree((char) i));
			}
		}
		return heap;
	}
	
	public	static	void	main(String	args[])	{	
	 	new	HuffmanEncode(args[0],	args[1]);	
	}	
}