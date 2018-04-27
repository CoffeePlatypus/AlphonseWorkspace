import	java.util.*;	
public	class	SymbolTable	{
	
	private	class	Node	{	
	 	private	String key;	
	 	private	LinkedList<Integer>	lines;//used	for	a	list	of	line	numbers	on	which	the	key	was	found		
	 	private	Node next;	
	 	
	 	private	Node(String	k,	int lineNum,	Node	x)	{	
			key	=k;	
			lines=new LinkedList<>();	
			lines.addLast(lineNum);	
			next=x;	
	 	}	
		private	void addLineNum(int lineNum)	{	
		 	//if	lineNum	is	not	in	the	list	of	line	numbers		
		 	//add	it	to	the	end	of	the		list	of	lineNums
			if(!lines.contains(lineNum)) {
				lines.addLast(lineNum);
			}
		}	
	}
	
	private	Node table[];	
	private	int tableSize;	
	
	public	SymbolTable(int	s)	{//creates	a	symbol	table	with	size	s	
		table=new Node[s];
		tableSize=s;
	}	
	private	int	hash(String	k)	{	
		 	return	Math.abs(k.hashCode()%tableSize);	
	}
	
	public	boolean	insert(String	k,	int	lineNum)	{	
		//if	k	is	not	in	the	table	add	it	to	the	table	with	lineNum	as	the	first	line	number	on		
		//which	k	is	found	and	return	true	
		//if	k	is	in	the	table,	add	lineNum	to	the	list	of	line	numbers	and	return	false	
		int hashbrowns=hash(k);
		//System.out.println("hashed at "+hashbrowns);
		Node temp=table[hashbrowns];
		if(temp==null) {
			temp=new Node(k,lineNum,null);
			table[hashbrowns]=temp;
			return true;
		}
		if(temp.key.equals(k)) {
			//System.out.println("herey");
			temp.addLineNum(lineNum);
			return false;
		}
		while(temp.next!=null) {
			if(temp.key.equals(k)) {
				temp.addLineNum(lineNum);
				return false;
			}
			temp=temp.next;
		}
		if(temp.key.equals(k)) {
			//System.out.println("herey");
			temp.addLineNum(lineNum);
			return false;
		}
		temp.next=new Node(k,lineNum,null);
		return true;
	}
	
	public	boolean	find(String	k)	{	
	//return	true	if	k	is	in	the	table	otherwise	return	false
		int hashbrowns=hash(k);
		Node temp=table[hashbrowns];
		while(temp!=null) {
			//System.out.println("temp not null");
			if(temp.key.equals(k)) {
				return true;
			}
			temp=temp.next;
		}
		return false;
	}
	
	public	LinkedList<Integer>	getLines(String	k)	{	
	//if	k	is	in	the	table	return	the	line	numbers	on	which	k	is	found	
	//if	k	is	not	in	the	table	return	null	
		int hashbrowns=hash(k);
		Node temp=table[hashbrowns];
		while(temp!=null) {
			if(temp.key.equals(k)) {
				return temp.lines;
			}
			temp=temp.next;
		}
		return null;
	}
	
	public	class	STIterator	implements	Iterator<String>	{	
		//An	iterator	that	iterates	through	the	keys	in	the	table
		private Node next;
		private int hashed;
		private boolean sameHash;
		
		public	STIterator()	{
			hashed=0;
			sameHash=false;
			locateNext();
			
		}	
		private void locateNext() {
			if(sameHash) {
				next=next.next;
				sameHash=next.next!=null;
			}else{
				while(hashed<tableSize) {
					//System.out.println(hashed);
					if(table[hashed]!=null) {
						//System.out.println("reached");
						next=table[hashed];
						sameHash=next.next!=null;
						hashed++;
						return;
					}
					hashed++;
				}
				next=null;	
			}
			
		}
		public	boolean hasNext()	{
			return next!=null;
		}	
		public	String	next()	{	
		//PRE:	hasNext()	
		//returns	a	string	containing	the	next	key	and	the	line	numbers	associated	
		//with	the	key.	The	string	includes	a	space	between	the	key	and	the	first
		//	line	number	and	between	each	pair	of	line	numbers	
			String text=next.key;
			for(int i=0; i<next.lines.size();i++) {
				text=text+" "+next.lines.get(i);
			}
			locateNext();
			return text;
		}	
		
		public	void	remove()	{	
		//opConal	method	not	implemented	
		}	
	}	
	public	boolean	delete(String k)	{	
		//if	k	is	in	the	table,	delete	the	entry	for	k	and	return	true	
		//if	k	is	not	in	the	table,	return	false	
		int hashbrowns=hash(k);
		Node temp=table[hashbrowns];
		if(temp==null) {
			return false;
		}
		if(temp.key.equals(k)) {
			table[hashbrowns]=temp.next;
			return true;
		}
		while(temp.next!=null) {
			if(temp.next.key.equals(k)) {
				temp.next=temp.next.next;
				return true;
			}
			temp=temp.next;
		}
		if(temp.key.equals(k)) {
			table[hashbrowns]=temp.next;
			return true;
		}
		return false;
		
	}	
	public	Iterator<String>	iterator()	{	
	//return	a	new	iterator	object	
		return new STIterator();
	}	
}	
	