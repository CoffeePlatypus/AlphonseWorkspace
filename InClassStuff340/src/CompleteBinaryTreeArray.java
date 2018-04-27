
public class CompleteBinaryTreeArray {

	String[] contents;
	int size;
	
	public CompleteBinaryTreeArray(int c) {
		contents=new String[c];
		size=0;
	}

	public String inOrder() {
		return inOrder(1);
	}
	
	public void insert(String s) {
		size++;
		contents[size]=s;
	}
	
	private String inOrder(int i) {
		if(i>size) return "";
		return inOrder(2*i) +" "+contents[i]+" "+inOrder(2*i+1);
	}
	
	public String postorder() {
		return postorder(1);
	}

	private String postorder(int i) {
		if(i>size) return "";
		return postorder(2*i)+" "+postorder(2*i+1)+" "+contents[i];
	}

}
