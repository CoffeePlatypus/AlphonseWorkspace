import java.util.Scanner;
import java.util.Stack;


public class ExpressionTree {
	
	private class Bnode {
		private Bnode left;
		private Bnode right;
		private String data;
		
		private Bnode(Bnode l,Bnode r,String d) {
			left=l;
			right=r;
			data=d;
		}
	}
	public static final int INFIX=1;
	public static final int POSTFIX=2;
	private final String gtExp="!";
	private final String gtMult="!^*/%";
	private final String gtAdd="!^*/%+-";
	private Bnode root;
		
	public ExpressionTree(String exp, int format) {
		if(format==2) {
			buildPostfix(exp);
		}else{
			bulidInfix(exp);
		}
		
	}
		
	private void buildPostfix(String exp) {
		Stack<Bnode> operands=new Stack<>();
		Scanner s=new Scanner(exp);
		Bnode x,y;
		while(s.hasNext()) {
			String token=s.next();
			if(Character.isDigit(token.charAt(0))) {
				operands.push(new Bnode(null,null,token));
			}else{
				if(token.charAt(0)!='!') {
					x=operands.pop();
				}else{
					x=null;
				}
				y=operands.pop();
				operands.push(new Bnode(y,x, token));
			}
		}
		s.close();
		root=operands.pop();
	}
		
	private void bulidInfix(String exp) {
		Stack<Bnode> operators=new Stack<>();
		Stack<Bnode> operands=new Stack<>();
		Scanner s=new Scanner(exp);
		//Bnode x,y;
		while(s.hasNext()) {
			String token=s.next();
			if(Character.isDigit(token.charAt(0))) {
				operands.push(new Bnode(null,null, token));
			}else{
				doOperator(operators,operands,token);
			}
		}
		doOperator(operators, operands,"");
		root=operators.pop();
	}
		
	private	void	doOperator(Stack<Bnode>	operators,	Stack<Bnode>	operands,	String	op)	{	
	 	while	(!operators.empty()	&&	!operators.peek().data.equals("(")		
									&&	precLess(op,	operators.peek().data))	{	
			Bnode	oper	=	operators.pop();	
			if	(oper.data.charAt(0)	!=	'!')		
				oper.right	=	operands.pop();	
			oper.left	=	operands.pop();	
			operands.push(oper);	
	 	}	
	 	if	(op.equals(")"))		operators.pop();	
	 	if	(!op.equals(")"))	operators.push(new Bnode(null, null,op));	
}

	private boolean precLess(String op, String ch) {
		switch(op) {
		case"(": return false;
		case")": return true;
		case"!": return false;
		case"^": return gtExp.indexOf(ch)!=-1;
		case"*":;
		case"/":;
		case"%": return gtMult.indexOf(ch)!=-1;
		case"-":;
		case"+": return gtAdd.indexOf(ch)!=1;
		case"": return true;
				
		}
		return true;// not reachable
	}
	
	private boolean isLeaf(Bnode r) {
		return	r.left	==	null	&&	r.right	==	null;
	}
	public int evaluate() {
		return evaluate(root);
	}

	private int evaluate(Bnode r) {
		int	x;	
	 	int	y	=	0;	
	 	if	(isLeaf(r))	return	Integer.parseInt(r.data);	
	 	x	=	evaluate(r.left);	
	 	if	(r.data.charAt(0)	!=	'!')	
			y	=	evaluate(r.right);	
	 	switch	(r.data)	{	
			case	"!": return	-x;	
			case	"^": return	(int)	Math.pow(x,y);	
			case	"*": return	x*y;	
			case	"/": return	x/y;	
			case	"%": return	x%y;	
			case	"+": return	x+y;	
			case	"-": return	x-y;	
	 	}	
		return 0; //never reached
	}
	
	public String toInfix() {
		return	toInfix(root);	
	}

	private String toInfix(Bnode r) {
		String	x;	
	 	String	y;	
	 	if	(isLeaf(r))	return	r.data;	
	 	x	=	toInfix(r.left);	
	 	if	(r.data.charAt(0)	!=	'!')	{	
			y	=	toInfix(r.right);	
			return	"("+x+""+r.data+""+y+")";	
	 	}	
	 	return	"("	+r.data+""+x+")";		//unary	minus	case
	}
	
	public	String	toPosUix()	{	
	 	return	toPosfix(root);	
	}	
	private	String	toPosfix(Bnode	r)	{	
	 	String	x;	
	 	String	y;	
	 	if	(isLeaf(r))	return	r.data;	
	 	x	=	toPosfix(r.left);	
	 	if	(r.data.charAt(0)	!=	'!')	{	
			y	=	toPosfix(r.right);	
			return	x+" "+y+" "+r.data;	
	 	}	
	 	return	x+" "+r.data;		//unary	minus	case	
}	
	

}
