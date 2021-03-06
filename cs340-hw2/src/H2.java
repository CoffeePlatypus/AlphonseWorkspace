// Julia Froegel
import java.io.*;
import java.util.*;


public class H2 {
	//Adjaceny list represntation
	private VertexNode vertices; //head
	private int numVertices;
	private int[] indegree;
	private LinkedList<String> queue;
	
	public H2() {
		vertices=null;
		numVertices=0;
	}
	
	public void addVertex(String name) {
		numVertices++;
		if(vertices==null || name.compareTo(vertices.name)<0) { //Short circuting so no null pointer exception
			vertices=new VertexNode(name,vertices);
			return;
		}
		VertexNode temp=vertices;
		while(temp.nextV!=null && name.compareTo(temp.nextV.name)>0) {
			temp=temp.nextV;
		}
		temp.nextV=new VertexNode(name,temp.nextV);
	}
	
	public String toString() {
		return printNodes(vertices);
	}
		
	private String printNodes(VertexNode n) {
		if(n==null) {
			return "";
		}else if(n.nextV==null) {
			return n.name+"-"+printEdges(n.edges);
		}
		return n.name+"-" + printEdges(n.edges)+"\n"+ printNodes(n.nextV);		
	}
	
	private String printEdges(EdgeNode e) {
		if(e==null) {
			return "N";
		}else if(e.nextE==null) {
			return "("+e.vertex1.name+","+e.vertex2.name+")";
		}
		return "("+e.vertex1.name+","+e.vertex2.name+")" +", " + printEdges(e.nextE);
	}
	
	public void addEdge(String n1, String n2) {
		VertexNode temp=findNode(n1);
		temp.edges=new EdgeNode(temp,findNode(n2),temp.edges);
	}
	
	private VertexNode findNode(String name) {
		VertexNode temp=vertices;
		while(temp!=null) {
			if(temp.name.equals(name)) {
				return temp;
			}
			temp=temp.nextV;
		}
		return null;
	}
	
	public String topoSort() {
		//Calculate in degrees	
		//Add vertces with in degrees of 0 to the queue	
		//while the queue is not empty	
		//let i be the vertex removed from the queue	
		//add i to the topological ordering	
		//decrement the in degree of vertices adjacent to I by 1	
		//add new vertices with in degree of 0 to the queue	
		//increment the number of items in the topological order by 1	
		//If the number of vertices in the topological order equals the number of vertices in the graph	
		//return topological order	
		//otherwise return null	
		calculateIndegree();
		queue=new LinkedList<>();
		String topo="";
		int topoCount=0;
		addZeroDegrees();
		while(!queue.isEmpty()){
			String name=queue.removeFirst();
			topo=topo+name+" ";
			//System.out.println(topoCount+" "+name);
			//System.out.println(topo);
			updateIndegree(name);
			addZeroDegrees();
			topoCount++;
			//System.out.println("Size- "+queue.size());
		}
		//System.out.println(topo);
		if(topoCount==numVertices) {
			return topo;
		}
		return null;
	}
	
	private void updateIndegree(String name) {
		int i=0;
		VertexNode temp=vertices;
		while(temp!=null) {
			if(temp.name.equals(name)) {
				indegree[i]--;
				EdgeNode ed=temp.edges;
				while(ed!=null) {
					//System.out.println(ed.vertex2.name+" subtracted");
					indegree[getIndex(ed.vertex2)]--;
					ed=ed.nextE;
				}
			}
			i++;
			temp=temp.nextV;
		}
	}
	

	private int getIndex(VertexNode vertex2) {
		VertexNode temp=vertices;
		int i=0;
		while(temp!=null && !temp.equals(vertex2)) {
			i++;
			temp=temp.nextV;
		}
		if(temp==null) {
			return -1;
		}
		return i;
	}

	private boolean contains(EdgeNode edges, String name) {
		while(edges!=null) {
			if(edges.vertex1.name.equals(name)) {
				//System.out.println("true");
				return true;
			}
			edges=edges.nextE;
		}
		//System.out.println("no");
		return false;
	}

	private void addZeroDegrees() {
		VertexNode v=vertices;
		int i=0;
		while(v!=null) {
			if(indegree[i]==0 && !queue.contains(v.name)){
				queue.add(v.name);
				//System.out.println(v.name+" added");
			}
			i++;
			v=v.nextV;
		}
		
	}

	private void calculateIndegree() {
		indegree=new int[numVertices];
		int i=0;
		VertexNode v=vertices;
		while(v!=null) {
			indegree[i]=countVertices(v.name);
			i++;
			v=v.nextV;
		}
		//printIndegree();
	}

	private void printIndegree() {
		for(int i=0;i<numVertices;i++){
			System.out.print(indegree[i]);
		}
		System.out.println();
		
	}

	private int countVertices(String name) {
		int count=0;
		VertexNode temp=vertices;
		while(temp!=null) {
			EdgeNode edgy=temp.edges;
			while(edgy!=null) {
				if(edgy.vertex2.name.equals(name)) {
					count++;
				}
				edgy=edgy.nextE;
			}
			temp=temp.nextV;
		}
		return count;
	}

	public static void main(String args[]) throws IOException {
		H2 adjancyList=new H2();
		File f= new File(args[0]);
		BufferedReader rin=new BufferedReader(new FileReader(f));
		String verticesNames=rin.readLine();
		Scanner scanVertices=new Scanner(verticesNames);
		while(scanVertices.hasNext()) {
			adjancyList.addVertex(scanVertices.next());
		}
		scanVertices.close();
		String line=rin.readLine();
		while(line!=null) {
			Scanner scanLine=new Scanner(line);
			adjancyList.addEdge(scanLine.next(), scanLine.next());
			scanLine.close();
			line=rin.readLine();
		}
		rin.close();
		//System.out.println(adjancyList);
		String topo=adjancyList.topoSort();
		if(topo==null) {
			System.out.println("Graph contains a cycle");
		}else{
			System.out.println(topo);
		}
	}
	
	public static void test(H2 adjancyList) {
		adjancyList.addVertex("b");
		adjancyList.addVertex("g");
		adjancyList.addVertex("f");
		adjancyList.addVertex("d");
		adjancyList.addVertex("e");
		adjancyList.addVertex("a");
		adjancyList.addVertex("c");
		adjancyList.addEdge("a", "f");
		adjancyList.addEdge("b", "f");
		adjancyList.addEdge("b", "g");
		adjancyList.addEdge("c", "a");
		adjancyList.addEdge("c", "b");
		adjancyList.addEdge("c", "d");
		adjancyList.addEdge("c", "e");
		adjancyList.addEdge("d", "a");
		adjancyList.addEdge("e", "b");
		adjancyList.addEdge("e", "d");
		adjancyList.addEdge("f", "g");
	}
	
	private class VertexNode{
		private String name;
		private VertexNode nextV;
		private EdgeNode edges;
		
		private VertexNode(String n, VertexNode v) {
			name=n;
			nextV=v;
			edges=null;
		}
	}
	
	private class EdgeNode {
		private VertexNode vertex1;
		private VertexNode vertex2;
		private EdgeNode nextE;
		
		private EdgeNode(VertexNode v1, VertexNode v2, EdgeNode e) {
			vertex1=v1;
			vertex2=v2;
			nextE=e;
		}
		
	}

}
