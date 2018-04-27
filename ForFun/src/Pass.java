
public class Pass {
	public static void main(String args[]) {
		String pass="";
		while(pass.length()!=10) {
			int i=(int)(Math.random()*(126-33)+33);
			//System.out.println(i);
			pass=pass+((char) i);
		}
		System.out.println(pass);
	}

}
