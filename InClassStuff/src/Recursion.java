
public class Recursion {
	
	public static void main(String[] args) {
		for(int i=0; i<50; i++) {
			System.out.println(fib2(i));
		}
	}
	
	public static int gcd(int m, int n) { //greatest common divisor
		if(n==0) {//base case
			return m;
		}else{
			return gcd(n,m%n);//reduction step[makes input smaller]
		}
	}
	
	public static String toBinaryString(int n) {
		if(n==0) {
			return "";
		}else if(n%2==1){
			return toBinaryString(n/2)+"1";
		}else{
			return toBinaryString(n/2)+"0";
		}
	}
	
	public static long fib(int n) { 						//time is n!	/tenique is repetive so you do more work than nessicary
		if(n==0 || n==1) {
			return n;
		}else{
			return fib(n-2)+fib(n-1);
		}
	}
	
	public static long fib2(int n) { 										//time is n	//not recurrisve
		if(n==0 || n==1) {
			return n;
		}else{
			long back1=1;
			long back2=0;
			while(n>=2) {
				long sum=back1+back2;
				back2=back1;
				back1=sum;
				n--;
			}
			return back1;
		}
	}
	
	public void printBackwards(String txt) {
		if(txt.length()==0) return;
		System.out.println(txt.charAt(txt.length()-1));
		printBackwards(txt.substring(0,txt.length()-2));
	}
	
	public void reverse(Object[] data, int left, int right) {
		if(left>=right) return;
		Object temp=data[left];
		data[left]=data[right];
		data [right]=temp;
		reverse(data,left++,right--);
	}
	
	public void reverse(Object[] data) {
		reverse(data,0,data.length-1);
	}
	
	public void print(int n) {
		if(n==0) {
			System.out.println(0);
			return;
		}
		System.out.println(n);
		print(n-1);
	}

}
