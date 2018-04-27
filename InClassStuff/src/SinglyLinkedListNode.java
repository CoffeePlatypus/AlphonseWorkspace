
public class SinglyLinkedListNode<E> {
	private SinglyLinkedListNode<E> next;
	private E value;
	
	public SinglyLinkedListNode(E value, SinglyLinkedListNode<E> next) {
		setNext(next);
		setValue(value);
	}
	
	public E getValue() {
		return value;
	}
	
	public void setNext(SinglyLinkedListNode<E> next) {
		this.next=next;
	}
	
	public void setValue(E value) {
		this.value=value;
	}

	public SinglyLinkedListNode<E> getNext() {
		return next;
	}
}
