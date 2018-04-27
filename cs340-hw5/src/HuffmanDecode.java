
import	java.io.*;	
import	java.util.*;

public	class	HuffmanDecode	{	
	public	HuffmanDecode(String in, String	out)	throws IOException{	
		//Implements	the	huffman	decoding	algorithm	
		//Add		private	methods	as	needed	
		//try {
			HuffmanInputStream din=new HuffmanInputStream(in);
			char c=128;
			HuffmanTree tree=new HuffmanTree(din.getTree(),c);
			//System.out.println(tree);
			BufferedWriter nim=new BufferedWriter(new FileWriter(out));
			int numCharacters=din.totalChars();
			//numCharacters=4;
			
			for(int i=0;i<numCharacters;i++) {
				//System.out.println("for" +i);
				tree.moveRoot();
				String oie="";
				while(!tree.atLeaf()) {
					if(din.readBit()==0) {
						//oie=oie+0;
						tree.moveLeft();
					}else{
						//oie=oie+1;
						tree.moveRight();
					}
					
				}
				//System.out.println("leaf value "+tree.current());
				//System.out.println(oie+" "+tree.current());
				nim.write(tree.current());
				
			}
			nim.close();
//		} catch(IOException e) {
//			System.out.println("HuffmanDecode IOException");
//		}
	}	
	
	public	static	void	main(String	args[])	throws IOException{	
	 	new	HuffmanDecode(args[0],	args[1]);	
	}	
}
