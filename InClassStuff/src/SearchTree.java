
public interface SearchTree<T extends Comparable<T>> {
	public int size();
	public boolean contains(T item);
	public T minimum();
	public T maximum();
	public void add();
	public boolean isEmpty();
	public void remove(T item);

}
