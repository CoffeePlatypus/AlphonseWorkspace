
public class BinarySearchTree {
	
	private class Node {
		private Node left,right;
		private int data;
		public Node(int d, Node l, Node r) {
			data=d;
			left=l;
			right=r;
		}
	}
	
	private Node root;
	
	
	public boolean search(int d) {
		return search(root,d);
	}
	
	private boolean search(Node r, int d) {
		if(r==null) return false;
		if(d<r.data) {
			return search(r.left,d);
		}else if(d>r.data) {
			return search(r.right,d);
		}else{
			return true;
		}
	}
	
	public void insert(int d) {
		root=insert(root,d);
		
	}
	public Node insert(Node n,int d) {
		if(n==null) return new Node(d,null, null);
		if
	}

}
