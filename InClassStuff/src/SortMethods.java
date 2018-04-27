import java.util.LinkedList;
import java.util.List;

public class SortMethods {
	
										//  #ops  	#times												Total # ops
	public void bubbleSort(int[] data) {//	  1	 	  1														1		//Sloooooow; // Don't use!! //not reccomeded!!// takes about n^2 operations// n= length of array
		for(int i=data.length; i>0;i--) {// 3/1/2   1/n/n-1												3/n/n-1
			for(int j=0; j<i; j++) {//		1/1/2   n-1/ {[(n^2)/2]+[n/2]+n-1} / {[(n^2)/2]-[n/2]}		n-1/{[(n^2)/2]+[n/2]+n-1}/{[(n^2)/2]-[n/2]}
				if(data[j+1]<data[j]) {//     4		{[(n^2)/2]-[n/2]}									13{[(n^2)/2]-[n/2]}// all below inculued
					int temp=data[j+1];//	  3		best =0 / worst={[(n^2)/2]-[n/2]}								//Worst case analysis/ always true/ count more 
					data[j+1]=data[j];//	  4		best =0 / worst={[(n^2)/2]-[n/2]}
					data[j]=temp;//			  2		best =0 / worst={[(n^2)/2]-[n/2]}
				}																					//(8)n^2 -(3n)-1 
			}																						// squareing bad
		}	//			return sorta			  1
	}
														//#ops		#times			// total 13n+7 // Actully 4(n^2)+9n+7 // DOnt use!!!
	public void selectionSort (int[] data) {			// 1			i
		for(int i=0; i<data.length; i++) {			// 1/2/2  		i/n+1/n
			int min = findIndexOfMinimum(data, i);// 	2				n
		    int tmp = data[min];				//   	2				n
		    data[min] = data[i];				//   	3				n
		    data[i] = tmp;						//   	2				n
		}										//		1				1
	}

		  // returns the index of the smallest element 
		  // in the range of indices [i, data.length-1]
	//precondition- I is [0,data.length-1];
	//posConditon data[minIndex]=data:[i,dataLength-1]		#ops		#times				total= 2+1+2+2n-2i-2+6n-6i-12+1= 8n-8i-8 //Only for valid i
	private int findIndexOfMinimum (int[] data, int i) {//	 2			  1
		int minIndex = i;								//	 1			  1
		for (int j=i+1; j<data.length; j++) {			// 2/2/2	 1/n-i-1/n-i-2/
			if (data[j] < data[minIndex]) {				//   3  		n-i-2
				minIndex = j;							//	 1          0 or n-i-2
			}
		}
		return minIndex;								//   1			 1
	}
													//  #ops 		times		total best= 15n-10
	public void insertionSort1 (int[] data) {//			 1			1			total worst=  (5n^2) +10n -11
	    for (int i=1; i<data.length; i++) {//			1/2/2		1/n/n-1
	    	int currentValue = data[i];//				2			n-1
	        int j = i-1;					//			2			n-1
	        while (j >= 0 && data[j] > currentValue) {//4			n-1 or {[(n^2)/2]+[n/2]-1}
	        	data[j+1] = data[j];			//		4			0 or {[(n^2)/2]-[n/2]}
	        	j--;							//		2			0 or {[(n^2)/2]-[n/2]}
	        }
	        data[j+1] = currentValue;//					3			
	    }											//	1
	}

	//algorithm quickSort(A) 	// recursion
	  //INPUT: List A of integers
	  //OUTPUT: None.  A is sorted

	  //// Base Case
	  //If A contains 0 or 1 items then return
	  //Let PIVOT be a randomly selected element of A

	  //// Divide
	  //Let B be an array that contains all the elements of A <= PIVOT
		//(not including the PIVOT)
	  //Let C be an array that contains all the elements of A >= PIVOT
	    //(not including the PIVOT)

	  //quickSort(B)
	  //quickSort(C)

	  //// Conquer
	  //Let A = [B, PIVOT, C]
	public static int partition(int data[], int left, int right) {
		while(true) {
			while(left < right && data[left] < data[right]) {
		    	right--;
		    }
		    if(left < right) {
		    	swap(data, left++, right);
		    }else{
		    	return left;
		    }

		    while(left < right && data[left] < data[right]) {
		    	left++;
		    }
		    if(left < right) {
		    	swap(data, left, right--);
		    }else {
		    	return right;
		    }
		}
	}

	private static void swap(int[] data, int i, int right) {
		// TODO Auto-generated method stub
		
	}
	public static void quickSort(int data[], int left, int right) {
	  if(left >= right) return;// contains 0 oo 1 thing
	  int pivotLocation = partition(data, left, right);
	  quickSort(data, left, pivotLocation-1);
	  quickSort(data, pivotLocation+1, right);
	}
	
	//precondition: two sorted list and an empty one
	//postcondition: left and right are empty
	public void merge(List<Integer> left, List<Integer> right, List<Integer> result) {
		while(!left.isEmpty() && !right.isEmpty()) {
			Integer n1=left.get(0);
			Integer n2=right.get(0);
			
			if(n1<n2) {
				result.add(left.remove(0));
			}else{
				result.add(right.remove(0));
			}
		}
		while(!left.isEmpty()) {
			result.add(left.remove(0));
		}
		while(!right.isEmpty()) {
			result.add(right.remove(0));
		}	
	}
	
	public void mergeSort(List<Integer> list) {
		//if(list.size()<=1) {
		//	return;
		//}
		//let A be the first half of list
		//let B be the second half of list;
		//list is empty;
			//mergeSort(A);
			//mergeSort(B);
		//merger(A,B,list);
		if(list.size()<=1) {
			return;
		}
		
		List<Integer> left=new LinkedList<Integer>();
		List<Integer> right=new LinkedList<Integer>();
		int half=list.size()/2;
		
		for(int i=0;i<half;i++) {
			left.add(list.remove(0));
		}
		
		while(!list.isEmpty()) {
			right.add(list.remove(0));
		}
		
		mergeSort(left);
		mergeSort(right);
		merge(left, right, list);
	}

	 public void insertionSort (int[] x) {
		    int i, j, currentValue;
		 
		    for (i=1; i<x.length; i++) {
		      currentValue = x[i];
		      j = i-1;
		      while (j>=0 && x[j]<currentValue) {
		        x[j+1] = x[j];
		        j--;
		      }
		      x[j+1] = currentValue;
		    }
		  }


	

}
