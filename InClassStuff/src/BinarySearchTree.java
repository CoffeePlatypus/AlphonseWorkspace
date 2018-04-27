import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<T>> implements SearchTree<T> {
	private Node root;
	
	public BinarySearchTree() {
		root=null;
	}
	
	public boolean add(T item) {
		root=add(item, root);
		return true;
	}
	
	private Node add(T item, Node t) {
		if(t==null) {
			return new Node(item,null,null);
		}
		if(item.compareTo(t.value)<0) {
			t.left=add(item,t.left);
		}else{
			t.right=add(item,t.right);
		}
		return t;
		
	}
	
	public void remove(T item) {
		remove(item, root);
	}
	
	private Node remove(T item, Node t) {
		if(t==null) {
			return null;
		}
		if(item.compareTo(t.value)<0) {
			t.left= remove(item,t.left);
			return t;
		}else if(item.compareTo(t.value)>0) {
			t.right=remove(item, t.right);
			return t;
		}
		if(t.isLeaf()) {
			return null;
		}else if(t.numOfChildren()==1) {
			return t.left != null ? t.left : t.right;
		}else {
			T min=minimum(t.right);
			t.right=remove(min,t.right);
			t.value=min;
			return t;
		}
	}
	public boolean isEmpty() {
		return size()==0;
		//return rool 
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node t) {
		if(t==null) return 0;
		return size(t.left)+size(t.right)+1;
	}
	@Override
	public boolean contains(T item) {
		return contains(item,root);
	}
	
	private boolean contains(T item, Node t) {
		if(t==null){
			return false;
		}
		if(t.value.equals(item)) {
			return true;
		}else if(t.value.compareTo(item)<0) {
			return contains(item,t.left);
		}else{
			return contains(item, t.right);
		}
	}
	public T minimum(){
		if(root==null) throw new NoSuchElementException();
		return minimum(root);
	}
	private T minimum(Node t) {
		if(t.left==null) {
			return t.value;
		}else{
			return minimum(t.left);
		}
	}
	
	public T maximum(){
		if(root==null) throw new NoSuchElementException();
		return maximum(root);
	}
	private T maximum(Node t) {
		if(t.right==null) {
			return t.value;
		}else{
			return maximum(t.right);
		}
	}
	
	private class Node {
		Node left, right;
		T value;
		
		public Node(T v, Node left, Node right) {
			this.left=left;
			this.right=right;
			value=v;
		}
		
		public boolean isLeaf() {
			return left==null && right==null;
		}
		
		public int numOfChildren() {
			int val=left==null ? 0:1;
			val+=right==null ? 0:1;
			return val;
		}
	}
	
	public static void main(String args []){
		BinarySearchTree<Integer> stumpy= new BinarySearchTree<Integer>();
		for(int i=0;i<20; i++) {
			stumpy.add(Math.random());
		}
	}

	
}
