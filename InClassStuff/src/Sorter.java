class Sorter {
  public static <E extends Comparable<E>> void insertionSort(E[] data) {
    for(int i=1; i < data.length; i++ ) {
       E currentValue = data[ i ];
       int j = i-1;
       while(j >= 0 && data[j].compareTo( currentValue ) > 0) {
         data[j+1] = data[j];
         j--;
       }
       data[j+1] = currentValue;
     }
  }
}
