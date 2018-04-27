import	java.io.*;	

public class HuffmanInputStream	extends	BitInputStream	{	
	private String tree;	
	private	int totalChars;	
	private int index=8;
	private int[] bits=new int[8];
	private int all;
	public HuffmanInputStream(String filename) {	
	 	super(filename);	
	 	try	{	
			tree=d.readUTF();	
			totalChars=d.readInt();	
			
	 	}catch (IOException	e)	{
	 		System.out.println("HuffmanInputStream IOException");
	 	}	
	}
	
	public void printBits() {
		for(int i=0;i<bits.length;i++) {
			System.out.println(bits[i]);
		}
	}
	
	public	int readBit() throws IOException{
		
			if(index==8) {
				readByte();
				index=1;
				return bits[0];
			}
			return bits[index++];
//		}catch(IOException e) {
//			System.out.println("blagh");
//		}
//		return bits[index++];
		
	}
	
	private void readByte() throws IOException {
		int bite=d.readUnsignedByte();
		//System.out.println("Byte "+bite);
		for(int i=7; i>=0; i--) {
			bits[i]=bite%2;
			bite=bite/2;
		}
	}

	public String getTree() {			
		return tree;
	}
	
	public int totalChars()	{
		return totalChars;
	}
	
	public void close() {				
		try {
			d.close();
		} catch (IOException e) {
			System.out.println("HuffmanInputStream IOException close");
		}
	}	
}