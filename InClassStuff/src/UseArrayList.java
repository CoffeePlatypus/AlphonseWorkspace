import java.util.Iterator;

public class UseArrayList {
	ArrayList<Integer> data= new ArrayList<Integer>();
	for(int i=0; i<10; i++) {
		data.add(i);
	}
	Iterator<Integer> it=data.iterator();
	while(it.hasNext()) {
		System.out.println(it.next());
	}
	data.add(0,-1);
}

