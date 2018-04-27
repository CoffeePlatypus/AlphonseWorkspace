import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Evaler {
	private String exp;
	
	public Evaler(String e) {
		exp = e;
	}
	
	public  String eval() {
		HashMap<String,String> environment = new HashMap<String,String>();
		return eval(exp, environment);
	}

	private  String  eval(String exp, HashMap<String, String> environment) {
		if(isNum(exp)) {
			return exp;
		}else if(isOperand(exp)) {
			return operate()
		}else if(isOperand(exp)) {
			
	
		}else if(isSymbol(exp)) {
			
		}
		
		
	}
	
	private boolean isSymbol(String exp2) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isOperand(String exp) {
		char c = exp.charAt(0);
		return (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '%') || (c == '^');
	}

	private boolean isNum(String exp) {
		try{
			Integer.parseInt(exp);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
}
