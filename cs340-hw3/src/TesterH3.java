import java.io.IOException;

public class TesterH3 {
	public static void main(String [] args) throws IOException {
		boolean isNew=true;
		int[] k1={2,4,8};
		int[] k2={4,7,4};
		int[] k3={6,8,2};
		int[] k4={7,9,1};
		int[] k5={10,3,8};
		char[] d1={'s','a','m'};
		char[] d2={'m','a','t'};
		char[] d3={'j','o','n'};
		char[] d4={'r','i','n'};
		char[] d5={'s','i','m'};
		
		if(isNew) {
			H3 cList=new H3(3,3,"C:\\temp\\list1.txt");
			cList.insert(k1, d1);
			cList.insert(k1, d2);
			cList.insert(k1, d3);
			cList.insert(k1, d4);
			cList.insert(k5, d5);
			cList.print();
			String[] hold=  cList.find(k1);
			for(int i=0;i<hold.length; i++) {
				System.out.println(hold[i]);
			}
			//System.out.println();
			//cList.remove(k2);
			//cList.print();
			cList.close();
		}else{
			H3 cList=new H3("C:\\temp\\list1.txt");
			//cList.remove(k1);
			//cList.remove(k2);
			cList.insert(k1, d1);
			cList.insert(k3, d1);
			cList.insert(k5, d2);
			cList.insert(k2, d3);
			cList.insert(k5, d4);
			cList.insert(k4, d5);
			cList.print();
			cList.close();
		}
	}
}
