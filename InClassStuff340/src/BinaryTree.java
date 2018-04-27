
public class BinaryTree {
	
	Bnode root;
	
	public int height() {
		return height(root);
	}
	
	private int height(Bnode r) {
		if(r==null) return -1; 				//base case is usually null but not always
		int hl=height(r.left);
		int hr=height(r.right);
		if(hl>hr) return hl+1;
		return hr+1;
	}
	
	public int sum() {
		return sum(root);
	}

	private int sum(Bnode r) {
		if(r==null) return 0;
		return sum(r.left)+sum(r.right)+r.data; 
	}

	private class Bnode {
		private Bnode left;
		private Bnode right;
		private int data;
		
		private Bnode(Bnode l,Bnode r,int d) {
			left=l;
			right=r;
			data=d;
		}
	}

}
