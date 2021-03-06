import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayList<E> extends AbstractList<E> { 
	protected E[] data; //no generic arrays
	protected int size;
	public static final int DEFAULT_CAPACITY=100; //final->Can't be reassigned;
	public static final int DEFAULT_INCREMENT=100;//<-convention naming for final variables;
	//static: associated the 'thing' with the containing class. All instances refer to that one thing
					
	@SuppressWarnings("unchecked")
	public ArrayList() {
		data=(E[]) new Object[DEFAULT_CAPACITY]; //100 is capacity not size
		size=0;
	}
	public boolean add(E e) {
		ensureCapacity(size+1);
		data[size++]=e;
		return true;
	}
	public int getCapitity() {
		return data.length;
	}
	//Ensures that the capacity of the ArrayList is 
	// greater than or equal to capacity
	private void ensureCapacity(int capacity) {
		if(getCapitity() < capacity) {
			E[] temp=(E[]) new Object[size+DEFAULT_INCREMENT];
			for(int i=0;i<size();i++) {
				temp[i]=data[i];
			}
			data=temp;
		}
	}
	public boolean isEmpty() {
		return size()==0;
	}
	public int size(){
		return size;
	}
	public boolean contains(Object object) {
		for(int i=0; i<size(); i++) {
			if(data[i].equals(object)) {
				return true;
			}
		}
		return false;
	}
	public E get(int index) {
		checkIndex(index);
		return data[index];
	}
	public void checkIndex(int i) throws IndexOutOfBoundsException {
		if(i<0 || i>=size()) {
			throw new IndexOutOfBoundsException(); 
		}
	}
	public E set(int index, E element) {
		checkIndex(index);
		E temp=data[index];
		data[index]=element;
		return temp;
		// Why do you need to check the index?
		//ArrayList<Integer> x=new ArryList<Integer>();
		//x.set(3,3);
	}
	public E remove(int index) {
		checkIndex(index);
		E temp=data[index];
		for(int i=index; i<size()-1; i++) {
			data[i]=data[i+1];
		}
		data[size-1]=null; //remove last element because it is not part of list and it occupies space //let it go!!
		size--;
		return temp;
	}
	public void add(int index, E element) {
		checkIndex(index);
		for(int i=size()-1; i>=index ;i--) {
			data[i+1]=data[i];
		}
		data[index]=element;
		size++;
	}
	public void clear() {
		size=0;
		data=(E[]) new Object[DEFAULT_CAPACITY];
	}
	public int indexOF(Object o) {
		for(int i=0; i<size(); i++) {
			if(data[i].equals(o)) {
				return i;
			}
			
		}
		return -1;
	}
	public boolean remove(Object object) {
		int indexOfObject=indexOf(object);
		if(indexOfObject>=0) {
			remove(indexOfObject);
			return true;
		}
		return false;
	}
	public List<E> sublist(int fromIndex, int toIndex) {
		List<E> result= new /*java.util.*/ArrayList<E>();
		for(int i=fromIndex;i<toIndex;i++) {
			result.add(get(i));
		}
		return result;
	}
	private class ArrayIterator implements Iterator<E> {
		int nextIndex;
		
		ArrayIterator() {
			nextIndex=0;
		}
		@Override
		public boolean hasNext() {
			return nextIndex<size();
		}

		@Override
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return data[nextIndex++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	public Iterator<E> iterator() {
		return new ArrayIterator();
	}

}
