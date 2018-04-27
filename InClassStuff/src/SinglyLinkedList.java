import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> extends AbstractList<E> {
	private SinglyLinkedListNode<E> head;
	private int size;
	
	public SinglyLinkedList() {
		size=0;
		head=null;
	}
	
	public boolean add(E e) {
		SinglyLinkedListNode<E> node= new SinglyLinkedListNode<E>(e, null);
		if(head==null) {
			head=node;
		}else{
			SinglyLinkedListNode<E> last=head;
			while(last.getNext()!=null){
				last=last.getNext();
			}
			last.setNext(node);
		}
		size++;
		return true;
	}
	
	public boolean addAtFront(E e) {
		head=new SinglyLinkedListNode<E>(e, head);
		size++;
		return true;
	}
	
	public E get(int i) throws IndexOutOfBoundsException{
		if(i>size) {
			throw new IndexOutOfBoundsException();
		}
		SinglyLinkedListNode<E> ith=head;
		for(int j=0; j<i; j++) {
			ith=ith.getNext();
		}
		return ith.getValue();
	}
	
	public int size() {
		 return size;
	}
	
	public boolean contains(Object x) {
		SinglyLinkedListNode<E> current=head;
		while(current!=null) {
			if(current.getValue().equals(x)) { //does this code allow for value to be null?	//////////NULL IS EVERYTYPE?????!!!
				return true;
			}else{
				current=current.getNext();
			}
		}
		return false;
	}
	
	public E remove(int i) throws IndexOutOfBoundsException{
		if(i>=size) {
			throw new IndexOutOfBoundsException();
		}
		SinglyLinkedListNode<E> previous=null;
		SinglyLinkedListNode<E> current=head;
		for(int j=0; j<i; j++) {
			previous=current;
			current=current.getNext();
		}
		if(previous==null) {
			head=current.getNext();
		}else{
			previous.setNext(current.getNext());
		}
		size--;
		return current.getValue();
	}
	
	public E removeFirst() {
		if(size==0) {
			throw new NoSuchElementException();
		}
		SinglyLinkedListNode<E> temp=head;
		head=head.getNext();
		size--;
		return temp.getValue();
	}
	
	public Iterator<E> iterator() {
		return new LinkedListIterator<E>(head);
	}
	private static LinkedListIterator<E> implements Iterator<E> {
		SinglyLinkedListNode<E> next;
		
		public LinkedListIterator(head) {
			next=head;
		}
		
		public E next() {
			if(!hasNext) {
				throw new NoSuchElementException();
			}
			E result=next.getValue();
			next=next.getNext();
			return result;
		}
		public boolean hasNext() {
			return next!=null;
		}
		}
	}
}
