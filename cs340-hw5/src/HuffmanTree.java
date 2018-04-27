
import	java.util.*;	
public class HuffmanTree {	
	private	class Node {	
	 	private	Node left;	
	 	private	char data;	
	 	private	Node right;
	 	
	 	private	Node(Node l, char d, Node r) {	
			left=l;	
			data=d;	
			right=r;	
	 	}
	 	
	 	public boolean isLeaf() {
	 		return left==null;
	 	}
	}	
	
	private	Node root;	
	private	Node current;
	private int notLeaf=128;
	
	public	HuffmanTree()	{	
		root	=	null;	
		current	=	null;	
	}
	
	public HuffmanTree(char d) {	
		root=new Node(null,d,null);
	}
	
	public	HuffmanTree(String t, char	nonLeaf)	{
		notLeaf=nonLeaf;
		//Assumes	t	represents	a	post	order	representation	of	the	tree		
		//where	a	node	is	either	a	leaf	or	has	two	children.	nonLeaf		
		//is	the	char	value	of	the	data	in	the	non-leaf	nodes
		Stack<Node> stack=new Stack<Node>();
		for(int i=0;i<t.length();i++) {
			if(t.charAt(i)!= nonLeaf) {
				stack.push(new Node(null,t.charAt(i),null));
			}else{
				Node right=stack.pop();
				Node left=stack.pop();
				stack.push(new Node(left,nonLeaf,right));
			}
		}
		if(stack.size()==1) {
			root=stack.pop();
		}
			
	}
	
	public	HuffmanTree(HuffmanTree	b1,	HuffmanTree	b2,	char	d)	{
		root=new Node(b1.root,d,b2.root);
	
	}	
	//The	following	methods	allow	a	user	object	to	follow	a	path	in	the	tree.	
	//Each	method	except	atLeaf	and	current	changes	the	value	of	current	
	//atLeaf	returns	true	of	the	current	posiPon	is	a	leaf,	otherwise	it	returns	false	
	//current	returns	the	data	value	in	the	current	Node	

	public	void	moveRoot()	{	
		// What does move root do? does it move current to root?
		current=root;
	}
	
	public	void	moveLeft()	{	
		current=current.left;
	}
	
	public	void	moveRight()	{	
		current=current.right;
	}
	
	public	boolean atLeaf()	{	
		return current.left==null && current.right==null; //I know this is redundant but I like to be sure 
	}
	
	public char current() {	
		return current.data;
	}	
	//Inner	class	to	create	an	iterator.	The	iterator	allows	the	user	class	to	find	all	paths	from	
	//the	root	to	a	leaf.	The	paths	are	seqeunces	of	0s	and	1s.	0	means	left	and	1	means	right	
	//You	will	find	it	easier	to	find	all	the	paths	when	the	iterator	is	created.	
	public class PathIterator implements Iterator<String> {	
		LinkedList<String> pathy;
		Iterator<String> patherator;
		//in what order is it supposed to iteraterate? ///
	 	public	PathIterator()	{
	 		pathy=new LinkedList<String>();
	 		makePath(root,"");
	 		patherator=pathy.iterator();
	 	}	
	 	
	 	public void makePath(Node n,String path) {
			if(n.isLeaf()) {
				//System.out.println(n.data+path);
				pathy.add(n.data+path);
				return;
			}
			makePath(n.left,path+"0");
			makePath(n.right,path+"1");
		}		

		public	boolean hasNext()	{	
			return patherator.hasNext();
	 	}	
	 	
	 	public	String	next()	{	
	 		return patherator.next();
	 	}	
	 	
	 	public	void	remove()	{	
	 	//opPonal	method	not	implemented	
	 	}	
	}
	
	public Iterator<String> iterator()	{	
		return new PathIterator();
	}
	
	public	String	toString()	{	
		return toString(root);
	}	
	
	private String toString(Node n) {
		if(n==null) return "";
		return toString(n.left)+toString(n.right)+n.data;
	}
}