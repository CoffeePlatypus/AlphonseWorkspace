
public class SomeCodeSomewhere {

	SinglyLinkedList<Integer> list= new SinglyLinkedList<Integer>();
	// |SinglyLinkedListNode|
	//-----------------------
	// |head--> becomes a node-->
	// |size:0 -> 1
	list.add(13);
	list.add(-6);
	list.add(9);
	/////////
	for(int i=0; i<list.size(); i++) {
		sum +=list.get(i);
	}
	
	
	
	//I like to keep this in case I need it later;
	//char[][] grid={{'_','#','_','_','_','_','#','^','_','_'},
				   //{'_','_','#','_','$','#','_','_','_','_'},
				   //{'_','_','_','!','_','_','_','_','_','!'},
				   //{'_','_','_','_','_','!','_','#','_','^'},
				   //{'_','_','_','_','_','_','#','#','_','^'}};
	//Map map=new Map(grid);
}
