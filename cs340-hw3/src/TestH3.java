import java.io.*;
import java.util.*;

public class TestH3 {
	Hashtable<String, H3> h = new Hashtable<>();
	String cmdArgs[];
	H3 list;
	int keySize;
	int dataSize;

	public TestH3(String cmdFile) throws IOException {
		BufferedReader b = new BufferedReader(new FileReader(cmdFile));
		String cmd = b.readLine();
		while (cmd != null) {
	
			cmdArgs = cmd.split(" ");
			keySize = new Integer(cmdArgs[2]);
			dataSize = new Integer(cmdArgs[3]);
			switch (cmdArgs[0]) {
				case "create":	list = new H3(keySize, dataSize, cmdArgs[1]);
						h.put(cmdArgs[1], list);
						break;

				case "reuse":	list = new H3(cmdArgs[1]);
						h.put(cmdArgs[1],list);
						break;

				case "print":	list = h.get(cmdArgs[1]);
						System.out.println("Print "+cmdArgs[1]);
						list.print();
						break;

				case "close":	list = h.get(cmdArgs[1]);
						list.close();
						break;

				case "remove":	list = h.get(cmdArgs[1]);
						remove();
						break;

				case "insert":	list = h.get(cmdArgs[1]);
						insert();
						break;

				case "find":	list = h.get(cmdArgs[1]);
						find();
						break;
			}
			cmd = b.readLine();
		}
	}

	private void remove() throws IOException {
		for (int i = 4; i < cmdArgs.length; i = i+keySize) {
			int key[] = new int[keySize];
			for (int j = 0; j < keySize; j++) {
				key[j] = new Integer(cmdArgs[i+j]);
			}
			list.remove(key);
		}	
	}

	private void find() throws IOException {
		System.out.print("Find ");
		for (int i = 4; i < cmdArgs.length; i = i+keySize) {
			int key[] = new int[keySize];
			for (int j = 0; j < keySize; j++) {
				System.out.print(cmdArgs[i+j]+" ");
				key[j] = new Integer(cmdArgs[i+j]);
			}
			System.out.print(": ");
			String results[] = list.find(key);
			if (results == null) System.out.println("No matches found");
			else {
				for (int k = 0; k < results.length; k++) {
					System.out.print(results[k]+" ");
				}
				System.out.println();
			}
		}	
	}

	private void insert() throws IOException {
		for (int i = 4; i < cmdArgs.length; i = i+keySize+1) {
			int key[] = new int[keySize];
			for (int j = 0; j < keySize; j++) {
				key[j] = new Integer(cmdArgs[i+j]);
			}
			char data[] = new char[dataSize];
			int k;
			String dataString = cmdArgs[i+keySize];
			for (k = 0; k < dataString.length(); k++) {
				data[k] = dataString.charAt(k);
			}
			for (k = dataString.length(); k < dataSize; k++) {
				data[k] = '\0';
			}
			list.insert(key, data);
		}	
	}

	public static void main(String args[]) throws IOException {
		new TestH3(args[0]);
	}

}









