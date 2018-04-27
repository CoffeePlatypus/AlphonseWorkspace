import java.util.Arrays;
import java.util.Comparator;

public class ComparatorSortNotes {
	
	public void sortByLength() {
		String[] words=loadDictionary();
		Arrays.sort(words, new CompareByLength()); //want to sort by length; /could write your own sort method?; // how can you specify how to compare
	}

	private String[] loadDictionary() {
		// TODO Auto-generated method stub
		return null;
	}

	public class CompareByLength implements Comparator<String>{
		public int compare(String a, String b) {
			return a.length()-b.length();
		}
	}
	private int findMinimum(E[] data, int i, Comparator<E> c) {
		int min=i;
		for(int j=i+1;j<data.length;j++) {
			if(c.compare(data[j],data[min])<0) {
				min=j;
			}
		}
		return min;
	}
	
	public void something() {
		if(someUsereInput==BY_AVERAGE) {
			Arrays.sort(team,new Comparator<String> c) {
				public int compare(String x, String y) { //anonoymus class
					return x.length()- y.length();
				}
			}
		}
	}

}
