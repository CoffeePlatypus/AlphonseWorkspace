import H1.Node;
import H2.VertexNode;

public class Quiz1<T extends Comparable <? super T>> {
		
	private class Node {
		T data;
		Node next;
		Node(T d, Node n) {
			data=d;
			next=n;
		}
	}
	private Node head;
	
	public boolean findT(T d) {
		Node temp=head;
		while(temp!=null) {
			if(temp.data.equals(d)) {
				return true;
			}
			temp=temp.next;
		}
	}
	
	public boolean find(T d) {
		find(head, d);
	}
	
	private boolean find(Node n, T d) {
		if(n==null) return false;
		if(n.data.equals(d)) return true;
		return find(n.next,d);
	}
	
	public T min() {
		return min(head);
	}

	private T min(Node n) {
		if(n.next==null) return n.data;
		T m=min(n.next);
		if(n.data.compareTo(m)<0) return n.data;
		return m;
	}
	
	
	////////////////////HOld
	numVertices++;
	VertexNode temp=getNodeBefore(vertices,name);
	if(temp==null) {
		vertices=new VertexNode(name,null);
		return;
	}else if(name.compareTo(temp.name)<0){
		vertices=new VertexNode(name,vertices);
		return;
	}
	temp.nextV=new VertexNode(name,temp.nextV);
}
private VertexNode getNodeBefore(VertexNode v, String name) {
	if(v==null) {
		return null;
	}
	if(v.nextV==null) {
		return v;
	}
	if(name.compareTo(v.nextV.name)>0) {
		return getNodeBefore(v.nextV,name);
	}
	return v;
	
}

}
