import java.util.Queue;

public class ArrayQueue<E> implements Queue<E> {
	private E[]data;
	private int size;
	private int begining, end;
	public ArrayQueue(){
		data=(E[])new Object;
		size=0;
		end=0;
		begining=0;
	}
	public boolean enqueue(E e) {
		//resize if nessicary
		data[end]=e;
		size++;
		end=(end+1)%data.length;//always less than length
		//does this??????????????????
		//end++;
		//if(end==data.length) {
			///end=0;
		//}
		return true;
	}

}
