import	java.io.*;	

public class HuffmanOutputStream extends BitOutputStream {	
	private int bity;
	private int bitsFilled;
	public HuffmanOutputStream(String filename, String	tree, int totalChars) {	
	 	super(filename);
	 	bitsFilled=0;
	 	try	{	
	 		
			d.writeUTF(tree);	
			d.writeInt(totalChars);	
	 	} catch (IOException e)	{	
	 		System.out.println("HuffmanOutputStream IOException");
	 	}	
	}	
	public	void	writeBit(int	bit)	{
		//PRE	bit	==	0	||	bit	==	1
		try {
			if(bitsFilled==0) {
				bity=bit;
				bitsFilled++;
			}else if(bitsFilled==7) {
				//bity=bity*10;
				bity=bity<<1;
				bity=bity+bit;
				//System.out.println("Bit written " +bity);
				d.writeByte(bity);
				bitsFilled=0;
			}else {
				//bity=bity*10;
				//System.out.println(bity+"before");
				bity=bity<<1;
				bity=bity+bit;
				//System.out.println(bity+"after");
				bitsFilled++;
			}
		} catch(IOException e) {
			System.out.println("HuffmanOutputStream IOException writeBit");
		}
	}	
	public	void	close()	{	
		try {
			if(bitsFilled>0) {
				//bity=bity*(int)Math.pow(10, (8-bitsFilled));
				bity=bity<<(8-bitsFilled);
				d.writeByte(bity);
			}
			d.close();
		}catch (IOException e) {
			System.out.println("HuffmanOutputStream IOException close");
		}
	}	
}	