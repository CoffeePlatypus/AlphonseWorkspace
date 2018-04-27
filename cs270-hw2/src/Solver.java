/**
 * CS 270: Solver template for Assignment 02
 * See assignment document for details.
 */
public class Solver {

	private int[] powersOf2;

	/**
	 * Testing method for the Solver class that allows it to be run by itself
	 * without the Driver. This code may be helpful for testing and debugging.
	 * Feel free to modify the method for your own testing purposes.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Solver s = new Solver();
		char[] s1=s.decimalToBinary(12);
		char[] s2=s.decimalToBinary(423);
		char[] c=s.evaluateExpression(s1, "+", s2);
//		c=s.signExtend(c, 6);
		//System.out.println("check bin");
		s.printNum(s1);
		s.printNum(s2);
		s.printNum(c);
		System.out.println(s.binaryToDecimal(c));
		//s1=s.twosComplementNegate(s1);
		//s.twosComplementNegate(s1);
				//System.out.println(c.length+" "+s1.length);
//		if(c[0]!='*') {
//		System.out.println(s.binaryToDecimal(s1)+" + "+s.binaryToDecimal(s2)+" = "+s.binaryToDecimal(c));
//		}
//		for(int i=0;i<c.length;i++) {
//			System.out.println(s1[i]+"     "+s2[i]+"     "+c[i]);
//		}
//		int min = -8;
//		int max = +7;
//		for (int i = min; i <= max; ++i) {
//			char[] binaryStr = s.decimalToBinary(i);
//			System.out.format("%5d: %10s%n", i, Driver.arrayToString(binaryStr));
//		}
	}

	/**
	 * Constructor for the Solver. It should allocate memory for and initialize
	 * the powersOf2 member array.
	 */
	public Solver() {
		// TODO: Initialize the powersOf2 array with the first 16 powers of 2.
		powersOf2=new int[16];
		setPowersOfTwo();
	}
	
	private void setPowersOfTwo() {
		int temp=1;
		for(int i=0;i<16;i++) {
			powersOf2[i]=temp;
			temp=temp*2;
		}
	}

	/**
	 * Determines the number of bits needed to represent the given value in
	 * two's complement binary.
	 *
	 * @param value The value to represent.
	 * @return The number of bits needed.
	 */
	public int howManyBits(int value) {
		int count=1;
		while(value!=0) {
			value=value/2;
			count++;
		}
		return count++;
	}

	/**
	 * Converts the given value into a two's complement binary number
	 * represented as an array of char. The array should use the minimal
	 * number of bits that is necessary.
	 *
	 * @param value The value to convert.
	 * @return A char array of 0's and 1's representing the number in two's
	 *         complement binary. The most significant bit should be stored in
	 *         position 0 in the array.
	 */
	public char[] decimalToBinary(int value) {
		char[] binNum=new char[howManyBits(value)];
		binNum=calcuateNum(binNum,value);
		return binNum;
	}
	private char[] calcuateNum(char[] binNum,int value) {
		if(value==0) {
			binNum[0]='0';
			return binNum;
		}else {
			int temp=value;
			for(int i=binNum.length-1;i>=0;i--) {
				if(temp%2==0) {
					binNum[i]='0';
				}else{
					binNum[i]='1';
				}
				temp=temp/2;
			}
			if(value<0) {
				binNum=twosComplementNegate(binNum);
			}
		}
		return binNum;
	}
	
	public int binaryToDecimal(char[] binNum) {
		int sign=1;
		if(binNum[0]=='1') {
			binNum=twosComplementNegate(binNum);
			sign=-1;
		}
		int num=0;
		for(int i=binNum.length-1,j=0;i>=0;i--,j++) {
			if(binNum[i]=='1') {
				//System.out.println(j);
				num=num+powersOf2[j];
			}
		}
		return sign*num;
	}
	
	public void printNum(char[] binNum) {
		for(int i=0;i<binNum.length;i++) {
			System.out.print(binNum[i]);
		}
		System.out.println();
	}

	

	/**
	 * Computes the two's complement (negation) of the given two's complement
	 * binary number and returns the result.
	 *
	 * @param binaryStr The binary number to negate.
	 * @return The negated number in two's complement binary.
	 */
	public char[] twosComplementNegate(char[] binaryStr) {
		//System.out.println("before comp");
		//printNum(binaryStr);
		char[] complemnt= new char[binaryStr.length];
		for(int i=0;i<binaryStr.length;i++) {
			if(binaryStr[i]=='0') {
				complemnt[i]='1';
			}else{
				complemnt[i]='0';
			}
		}
		complemnt=evaluateExpression(complemnt,"+", new char[]{'0','1'});
		//System.out.println("after comp");
		//printNum(complemnt);
		return complemnt;
	}

	/**
	 * Applies sign extension to the given two's complement binary number so
	 * that it is stored using the given number of bits. If the number of bits
	 * is smaller than the length of the input array, the input array itself
	 * should be returned.
	 *
	 * @param binaryStr The binary number to sign-extend.
	 * @param numBits The number of bits to use.
	 * @return The sign-extended binary number.
	 */
	public char[] signExtend(char[] binaryStr, int numBits) {
		char extender=binaryStr[0];
		char[] bigBinNum=new char[numBits];
		for(int i=bigBinNum.length-1,j=binaryStr.length-1;i>=0;i--,j--) {
			if(j<=0) {
				bigBinNum[i]=extender;
			}else {
				bigBinNum[i]=binaryStr[j];
			}
		}
		return bigBinNum;
	}

	/**
	 * Evaluates the expression given by the two's complement binary numbers
	 * and the specified operator.
	 *
	 * @param binaryStr1 The first number.
	 * @param op The operator to apply (either "+" or "-").
	 * @param binaryStr2 The second number.
	 * @return The result from evaluating the expression, in two's complement
	 *         binary. Note that a '*' should be appended to the returned
	 *         result if overflow occurred.
	 */
	//how long should the resulting char be? because that determies when it overflows?
	public char[] evaluateExpression(char[] binaryStr1, String op, char[] binaryStr2) {
		if(op.equals("-")) {
			return evaluateExpression(binaryStr1, "+", twosComplementNegate(binaryStr2));
		}else{
			int longer=0;
			if(binaryStr1.length>binaryStr2.length) {
				longer=binaryStr1.length;
				binaryStr2=signExtend(binaryStr2,longer);
			}else if(binaryStr1.length<binaryStr2.length) {
				longer=binaryStr2.length;
				binaryStr1=signExtend(binaryStr1,longer);
			}else{
				longer=binaryStr1.length;
			}
			//System.out.println(op+" on following");
			//printNum(binaryStr1);
			//printNum(binaryStr2);
			//System.out.println("--------------------");
			//if(longer>=16) return new char[]{'*'};
			char[] sum=new char[longer];
			//char[] carry=new char[]{'0','0'};
			char carry='0';
			for(int i=longer-1;i>=0;i--) {	
				if(binaryStr1[i]=='0') {			
					if(binaryStr2[i]=='0') {							
						if(carry=='0') {
							sum[i]='0';
						}else{
							sum[i]='1';
							carry='0';
						}
					}else{
						if(carry=='0') {
							sum[i]='1';
						}else{
							sum[i]='0';
						}
					}
				}else{//
					if(binaryStr2[i]=='0') {
						if(carry=='0') {
							sum[i]='1';
						}else{
							sum[i]='0';
						}
					}else{
						if(carry=='0') {
							sum[i]='0';
							carry='1';
						}else{
							sum[i]='1';
						}
					}
				}
			}
			if(binaryStr1[0]==binaryStr2[0] && binaryStr1[0]!=sum[0]) {
				if(sum.length!=16) {
					sum=signExtend(sum,sum.length+1);
					sum[0]='*';
				}else {
					sum[0]='*';
				}
			}
			//printNum(sum);
			return sum;
			
		}
		
		//return binaryStr1;
	}

}
