import java.util.AbstractList;

public class DoublyLinkedListWithSentinals<E> extends  AbstractList<E>{
	private Node sentinel;
	
	public DoublyLinkedListWithSentinals() {
		sentinel= new Node(null, null, null);//value, next,prev
		sentinel.setNext(sentinel);
		sentinel.setPrevious(sentinel);
	}
	
	public boolean addFirst(E e) {
		Node node=new Node(E e, sentinel.getNext() ,sentinel);
		sentinel.getNext.setPrevious(node);
		sentinel.setNext(node);
	}
	
	public boolean addEnd(E e) {
		Node node=new Node(E e,sentinel, sentinel.getPrevious());
		sentinel.getPrevious().setNext(node);
		sentinel.setPrevious(node);
	}
	
	
	private class Node {
		Node nextNode;
		Node previousNode;
		E nodeValue;
		
		public Node(E value, Node n, Node p) {
			nextNode=n;
			previousNode=p;
			this.v=value;
		}
	}

}
