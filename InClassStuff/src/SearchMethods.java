
public class SearchMethods {
	
	public int search(E[] data, E key) {// best O(1) worst O(N)
		for(int i=0; i<data.length; i++) {
			if(data[i].equals(key)) {
				return i;
			}
		}
	return -1;
	}
	
	public int binarySearch(int[] data, int value) {
		int intervalMin = 0;
		int intervalMax = data.length-1;
		while(intervalMin <= intervalMax) {
			int middle = (intervalMin + intervalMax)/2;
			if(data[middle] == value) { 
				return middle;
			}else if(data[middle] < value) {
				intervalMin = middle+1;
			}else intervalMax = middle-1;
		}
		return -1;
	}
}
