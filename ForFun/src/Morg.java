
public class Morg {
	
	public static void main(String[]args) {
		double m=100000;
		double r=.05;
		int time=1;
		double monthlyPayment=280;
		int endTime=30*12;
		double totalCost=0;
		while(time<endTime) {
			m=m+((r/12)*m)-monthlyPayment;
			totalCost=totalCost+monthlyPayment;
			time++;
		}
		System.out.println(m);
		System.out.println(totalCost);
		
	}

}
