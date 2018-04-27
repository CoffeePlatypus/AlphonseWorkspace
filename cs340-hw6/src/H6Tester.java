import java.io.IOException;
import java.util.LinkedList;

public class H6Tester {

	public static void main(String[] args) throws IOException{
//		int[] stuff={4,5};
//		
//		DBTable DataBase=new DBTable("C:\\temp\\test1",stuff,60);
//		char[][] fields1={{'m','e','o','w'},{'k','i','t','t','y'}};
//		char[][] fields2={{'a','b','c','d'},{'e','f','g','h','i'}};
//		char[][] fields3={{'m','a','t','t'},{'p','a','t','t','y'}};
//		char[][] fields4={{'z','a','c','h'},{'1','2','3','4','5'}};
//		char[][] fields5={{'g','r','e','g'},{'g','e','r','g','y'}};
//		DataBase.insert(5, fields1);
//		DataBase.insert(4, fields5);
//		DataBase.insert(6, fields2);
//		DataBase.insert(10, fields3);
//		DataBase.insert(7, fields4);
//		LinkedList<LinkedList<String>> stringy=DataBase.search(5,7);
//		//DataBase.print();
////		System.out.println(stringy.isEmpty());
//		while(!stringy.isEmpty()) {
//			LinkedList<String> lissss=stringy.removeFirst();
//			while(!lissss.isEmpty()) {
//				System.out.println(lissss.removeFirst());
//			}
//		}
		//60 76
		BTree treent=new BTree("test111",76);
		treent.insert(50, 50);
		treent.insert(60, 60);
		treent.insert(40, 40);
		treent.insert(90, 90);
		treent.insert(70, 70);
		treent.insert(20, 20);
		System.out.println("print tree 1");
		treent.print();
		//System.out.println("find 90 "+treent.search(90));
		treent.insert(10, 10);
		treent.insert(30, 30);
		treent.insert(45, 45);
		treent.insert(35, 35);
		treent.print();
		//treent.check(136);
//		treent.insert(80, 80);
//		treent.insert(100, 100);
//		treent.insert(110, 110);
//		treent.insert(120, 120);
		System.out.println("print tree");
		treent.print();
		//System.out.println(treent.remove(90));
		//System.out.println(treent.remove(45));
//		
//		System.out.println("print tree");
//		treent.print();
		//System.out.println(treent.search(90));
//		System.out.println("searching");
		LinkedList<Long> list=treent.search(50, 90);
		while(!list.isEmpty()) {
			System.out.println(list.removeFirst());
		}
		treent.close();
		

	}

	
//	for(int i=0;i<order-1;i++) {   ////////////Should this be size-1? but that breaks stuff
//	//System.out.println(i+"  "+keys.length+" "+size());
//	if(i>size()) {
//		f.readInt();
//	}else{
//		keys[i]=f.readInt();
//		System.out.println(keys[i]);
//	}
////System.out.println(keys[i]);
//}
////System.out.println("read children");
//for(int i=0;i<order;i++) {
//	if(i>size()) {
//		f.readLong();
//	}else if(i==size()){
//		children[order-1]=f.readLong();
//	}else{
//		children[i]=f.readLong();
//	}
//}
	
	
}
