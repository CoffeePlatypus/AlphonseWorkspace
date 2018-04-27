import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Evaluator {
	
	private HashMap<String,String> environment;
	private Stack<HashMap<String,String>> env = new Stack<HashMap<String,String>>();
	private String exp;
	
	public Evaluator(String e) {
		exp = e;
		environment = new HashMap<String,String>() ;
	}
	
	public Evaluator(String e, HashMap<String,String> en) {
		exp = e;
		environment = en;
	}
	
	public Evaluator(int i) {
		exp = i+"";
	}
		
	public String evaluate() {
		Scanner s = new Scanner(exp);
		return evaluate(s.next(),s);
	}
	
	//recursive 
	private String evaluate(String previous, Scanner scanner) {
		String tolken = scanner.next();
//		System.out.println("tolken: "+tolken);
//		System.out.println("previous: "+previous);
		if(tolken.equals("(")) {
			
			if(previous.equals("(")) {
				saveEnvironment();
				makeEnviroment(scanner);
				return evaluate(")",scanner);
			}else{
				return evaluate("(",scanner);
			}
		}else if(isOperand(tolken)) {
			String exp1 = evaluate(tolken,scanner);
			String exp2 = evaluate(")",scanner);		//hiojoji
			//System.out.println(exp1+tolken+exp2);
			String hold = operate(exp1, exp2,tolken);
			//loadEnvironment();
			return hold;
		}else if(tolken.equals(")")) {
			loadEnvironment();
			if(scanner.hasNext()) {
				return scanner.next();					//huihoiguuio
			}
			return "";
		}else {
			//System.out.println(tolken);
			return tolken;
		}
	}
	
	private boolean isOperand(String tolken) {
		return tolken.equals("+") || tolken.equals("-") || tolken.equals("*") || tolken.equals("/") || tolken.equals("%") || tolken.equals("^");
	}

	private void makeEnviroment(Scanner scanner) {
		boolean madeNew = false;
		while(scanner.next().equals("(")) {
			String var = scanner.next();
			String val = scanner.next();
			//System.out.println(var+ " = "+ val);
//			if(environment.containsKey(var) && !madeNew) {
//				saveEnvironment();
//				madeNew = true;
//			}
			environment.put(var, val);
			scanner.next();
		}
	}
	
	private void saveEnvironment() {
		//System.out.println("save");
		HashMap<String,String> temp = new HashMap<String,String>();
		environment.putAll(temp);
		env.push(temp);
	}
	
	private void loadEnvironment() {
		//System.out.print("load?");
		if(!env.isEmpty()) {
			System.out.println(" yes");
			environment = env.pop();
		}
		//System.out.println();
	}
	
	private HashMap<String,String> getEnvironment() {
		return environment;
	}

	private String operate(String e1, String e2, String op) {
		int exp1,exp2;
		try {
			exp1 = Integer.parseInt(e1);
		}catch (Exception e) {
			e1 = environment.get(e1);
			if(e1 == null) {
				return "UNDEFINED";
			}
			exp1 = Integer.parseInt(e1);
		}
		
		try {
			exp2 = Integer.parseInt(e2);
		}catch (Exception e) {
			e2 = environment.get(e2);
			if(e2 == null) {
				return "UNDEFINED";
			}
			exp2 = Integer.parseInt(e2);
		}
		//System.out.println(exp1+" "+op+" "+exp2);
		int output = 0;
		switch(op) {
			case "+": output = exp1 + exp2;					break;
			case "-": output = exp1 - exp2;					break;
			case "^": output = (int)Math.pow(exp1, exp2); 	break;
			case "%": output = exp1 % exp2;					break;
			case "/": output = exp1 / exp2;					break;
			case "*": output = exp1 * exp2;					break;
		}
		return output+"";
	}

}
