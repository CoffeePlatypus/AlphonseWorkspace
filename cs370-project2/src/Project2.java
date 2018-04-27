import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Project2 {
	private int numOffset, numIndex, setsNum;
	private String writePolicy, allocationPolicy;
	private int rhits = 0;
	private int whits = 0;
	private int rmisses = 0;
	private int wmisses = 0;
	private int wb = 0;
	private int wt = 0;
	private int count = 0;
	private LinkedList<Way>[] sets;
	private boolean test = false;

	public Project2() throws IOException { // Project main
		getParams();
		if (test) {
			testParams();
		} else {
			sets = new LinkedList[(int) Math.pow(2, numIndex) + 1];
			readInstructions();
			writeStats();
		}

	}

	private void testParams() throws IOException{
		numOffset = 8;
		numIndex = 4;
		for (int i = 0; i < 5; i++) {
			sets = new LinkedList[(int) Math.pow(2, numIndex) + 1];
			rhits = 0;
			whits = 0;
			rmisses = 0;
			wmisses = 0;
			wb = 0;
			wt = 0;
			count = 0;
			readInstructions();
			writeStats();
			numOffset--;
			numIndex++;
		}

	}

	public static void main(String[] args) {
		try {
			new Project2();
		} catch (IOException e) {
			System.out.println("booo");
		}
	}

	public void getParams() throws IOException {
		BufferedReader param = new BufferedReader(new FileReader("parameters.txt"));
		setsNum = Integer.parseInt(param.readLine());
		numOffset = Integer.parseInt(param.readLine());
		numIndex = Integer.parseInt(param.readLine());
		allocationPolicy = param.readLine();
		writePolicy = param.readLine();
		param.close();
		// System.out.println("sets " + setsNum );
		// System.out.println("off " +numOffset);
		// System.out.println("index "+ numIndex);
		// System.out.println(writePolicy);
		// System.out.println(allocationPolicy);
		// System.out.println();
	}

	public void readInstructions() throws IOException {
		BufferedReader instructions = new BufferedReader(new FileReader("accesses1.txt"));
		String line = instructions.readLine();
		while (line != null) {
			Scanner parse = new Scanner(line);
			processInstruction(parse.next(), Long.parseLong(parse.next(), 16));
			parse.close();
			line = instructions.readLine();
		}
		instructions.close();
	}

	public void processInstruction(String type, long instruction) {
		// System.out.println("ins: "+Long.toHexString(instruction));
		int index = getIndex(instruction);
		long tag = getTag(instruction);
		// System.out.println("offset: " +
		// Long.toHexString(getOffset(instruction)));
		// System.out.println("index: "+Integer.toHexString(index));
		// System.out.println("tag: "+Long.toHexString(tag));
		// System.out.println();

		count++;

		if (sets[index] == null) {
			// cant hit if gets here
			if (type.equals("w") && allocationPolicy.equals("wna")) {
				wmisses++;
				return;
			}
			sets[index] = new LinkedList<Way>();
			sets[index].addFirst(new Way(tag));
			if (type.equals("w")) {
				wmisses++;
			} else {
				rmisses++;
			}
			return;
		}

		Way temp = sets[index].getFirst();
		if (temp.getTag() == tag) {
			if (type.equals("w")) {
				whits++;
				if (writePolicy.equals("wb")) {
					if (temp.isDirty()) {
						wb++;
					} else {
						wt++;
					}
					temp.update();
				}
			} else {
				rhits++;
			}
			return;
		}

		if (setsNum == 2) {
			if (sets[index].size() == 2) {
				temp = sets[index].getLast();
				if (temp.getTag() == tag) {
					if (type.equals("w")) {
						whits++;
						if (writePolicy.equals("wb")) {
							if (temp.isDirty()) {
								wb++;
							} else {
								wt++;
							}
							temp.update();
						}

					} else {
						rhits++;
					}
					return;
				}
			}
		}

		if (type.equals("w") && allocationPolicy.equals("wna")) {
			wmisses++;
			return;
		}
		sets[index].addFirst(new Way(tag));
		if (sets[index].size() > setsNum) {
			sets[index].removeLast();
		}

		if (type.equals("w")) {
			wmisses++;
		} else {
			rmisses++;
		}

	}

	public int getIndex(long instuction) {
		long i = instuction >> numOffset;
		return (int) (i & ((1 << numIndex) - 1));
	}

	public int getOffset(long instruction) {
		return (int) (instruction & ((1 << numOffset - 1) - 1)); // may not need
	}

	public long getTag(long instruction) {
		return instruction >> 13;// (numOffset+numIndex);
	}

	private void writeStats() throws IOException {
		BufferedWriter wout = new BufferedWriter(new FileWriter("statistics.txt"));
		wout.write("rhits : " + rhits);
		wout.newLine();
		wout.write("whits : " + whits);
		wout.newLine();
		wout.write("rmisses : " + rmisses);
		wout.newLine();
		wout.write("wmisses : " + wmisses);
		wout.newLine();
		wout.write("hit rate : " + (double) (rhits + whits) / count);
		System.out.println("Hit rate for off: " + numOffset + " and in: " + numIndex + ": " + ((double) (rhits + whits) / count)+"    breaker: "+(wb+wt)) ;
		wout.newLine();
		wout.write("wb : " + wb);
		wout.newLine();
		wout.write("wt : " + wt);
		wout.newLine();
		wout.close();
	}
}
