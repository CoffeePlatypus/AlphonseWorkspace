import java.util.Iterator;
import java.util.LinkedList;

public class TestTable {
	public static void main(String[] args) {
		SymbolTable hashbrowns=new SymbolTable(20);
		LinkedList<String> all=new LinkedList<String>();
		
		for(int i=65;i<90;i++) {
			//System.out.println("i"+i);
			
			char upper=(char) i;
			char lower=(char)(i+32);
			String k=upper+" "+lower;
			System.out.println("String hashed "+k);
			System.out.println("	Add k at line "+i+" "+hashbrowns.insert(k, i));
			if(i%3==0) {
				//test insert
				System.out.println("	Add k at line "+(i+32)+" "+hashbrowns.insert(k, i+32));
				System.out.println("	Add k at 0 "+hashbrowns.insert(k, 0));
			}
			if(i%6==0) {
				//Tests Remove
				all.add(k);
				System.out.println("Get lines "+hashbrowns.getLines(k));
				System.out.println("removed "+hashbrowns.delete(k));
			}
			System.out.println();
		}
		System.out.println("Items that were removed");
		while(!all.isEmpty()) {
			System.out.println(all.removeFirst());
		}
		System.out.println();
		
		//Tests iterator
		//You may want to comment out remove code above to see that every letter
		//of the alaphbet is in iterator
		System.out.println("Iterate over items");
		Iterator<String> it=hashbrowns.iterator();
		while(it.hasNext()) {
			String next=it.next();
			//all.add(next);
			System.out.println(next);
		}
		System.out.println();	
	}

}
