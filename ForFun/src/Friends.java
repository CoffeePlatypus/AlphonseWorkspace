import java.util.LinkedList;

public class Friends {
	public static void main(String [] args){
		myFriends();
	}

	private static void myFriends() {
		LinkedList<Object> friends = getFriends();
		while(!friends.isEmpty()) {
			System.out.println(friends.removeFirst());
		}
		
	}

	private static LinkedList<Object> getFriends() {
		LinkedList<Object> friends = null;
		friends.isEmpty();
		return friends;
	}
}
