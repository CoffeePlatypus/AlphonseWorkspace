/**
 * CS 270: Driver for Assignment 02
 * See assignment document for details.
 */
import java.util.Scanner;

public class Driver {

	/**
	 * Main method for the expression parser.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter an expression: ");
		int val1 = scanner.nextInt();
		String op = scanner.next();
		int val2 = scanner.nextInt();
		scanner.close();

		Solver solver = new Solver();
		char[] binVal1 = solver.decimalToBinary(val1);
		char[] binVal2 = solver.decimalToBinary(val2);
		char[] binResult = solver.evaluateExpression(binVal1, op, binVal2);

		System.out.println(val1 + " " + op + " " + val2
					+ " evaluates to the following in binary:");
		System.out.println(arrayToString(binResult));
	}

	/**
	 * Converts the given array of characters into a String for easy printing.
	 * If the array is null, the String "null" is returned.
	 *
	 * @param arr The array of characters to convert to a String.
	 * @return The characters in the array as a String, or null.
	 */
	public static String arrayToString(char[] arr) {
		if (arr == null) {
			return "null";
		}
		return new String(arr);
	}

}

