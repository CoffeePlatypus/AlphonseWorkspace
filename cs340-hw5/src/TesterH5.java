import java.io.IOException;

public class TesterH5 {
	
	public static void main(String[] args)  throws IOException{
		
		boolean makeFile=true;
		char c=128;
		
//		if(makeFile) {
//			HuffmanOutputStream out=new HuffmanOutputStream("C:\\temp\\out1","ab"+c+"c"+c+"de"+c+"fg"+c+c+c,9);
//			out.writeBit(0);
//			out.writeBit(0);
//			out.writeBit(1);
//			out.writeBit(1);
//			out.writeBit(1);
//			out.writeBit(1);
//			out.writeBit(1);
//			out.writeBit(1);
//			out.writeBit(0);
//			//out.writeBit(1);
//			out.close();
//		}else {
//			HuffmanInputStream in=new HuffmanInputStream("C:\\temp\\out1");
//			int read=in.totalChars();
//			for(int i=0;i<read;i++) {
//				System.out.println(in.readBit());
//			}
//		}
		//		ab?c?de?fg???	om?we??
		/*o00
		m01
		true
		w10
		e11
		true
		
		HuffmanTree tree= new HuffmanTree("om"+c+"we"+c+c,c);
		System.out.println(tree.toString());
		tree.makeList();
		*/
		
		if(makeFile) {
			HuffmanEncode coder=new HuffmanEncode("C:\\temp\\test1.txt","C:\\temp\\bin1");
			
		}else{
			HuffmanDecode decoder=new HuffmanDecode("C:\\temp\\bin1","C:\\temp\\retest1.txt");
		}
		
		
	}

}
