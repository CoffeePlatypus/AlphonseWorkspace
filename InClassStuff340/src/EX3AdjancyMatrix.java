import java.io.IOException;
import java.util.LinkedList;

public class EX3AdjancyMatrix {
	
	private int g[][];
	private int numVertices;
	private String vertexNames[];
	private int indegree[];
	private LinkedList<Integer> queue;
	
	public EX3AdjancyMatrix(String vertices) {
		vertexNames=vertices.split("\\s+");
		numVertices=vertexNames.length;
		g=new int[numVertices][numVertices];
	}
	
	private int getVertexNum(String n) {
		for(int i=0; i<numVertices; i++) {
			if(vertexNames[i].equals(n)) {
				return i;
			}
		}
		return -1;
	}
	
	public void addEdge(String v1, String v2) {
		g[getVertexNum(v1)][getVertexNum(v2)]=1;
	}
	
	public String topoSort() {
		// count indegree  //Algorythm same for different langues
		// find verticies with indegree of zero and add to queue
		// while(there are verticies with an indegree of zero){ //while queue is not empty
		//		add new vertex(with indegree 0) to topo
		indegree=new int[numVertices];
		queue=new LinkedList<>();
		
		calcIndegrees();//Initlize indegree 
		intQueue();//put zeros in queue
		String topo="";
		int topoCount=0;
		while(queue.size()!=0) {
			int i=queue.remove();
			topo=topo+" "+vertexNames[i];
			updateIndegree(i);
			topoCount++;
		}
		if(topoCount==numVertices) {
			return topo;
		}
		return null;
	}
	
	private void updateIndegree(int vertex) {
		for(int j=0;j<numVertices;j++) {
			if(g[vertex][j]==1) {
				indegree[j]--;
				if(indegree[j]==0){
					queue.add(j);
				}
			}
		}
	}

	private void intQueue() {
		for(int i=0;i<numVertices;i++) {
			if(indegree[i]==0) {
				queue.add(i);
			}
		}
		
		
	}

	private void calcIndegrees() {
		for(int i=0;i<numVertices;i++) indegree[i]=0;
		for(int j=0;j<numVertices;j++) {
			for(int i=0;i<numVertices;i++) {
				indegree[i]=indegree[i]+g[i][j];
			}
		}	
	}

	public static void main(String args[]) throws IOException {
		
	}

}
