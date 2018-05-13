package flowGraph;

//import gui1.NodeGui;

import java.util.ArrayList;

public class Node {

	private int ID; //number of node
	private ArrayList<Edge> edges;// edges for the current node

	private int currentIndex;// used for looping throw edges of the node
//	private NodeGui nodeGui;

	// used inBFS
	private boolean visited;
	private Edge parent;

	
	//constructors 
	private Node() {

	}

	public Node(int id) {
		this.ID = id;
		edges = new ArrayList<Edge>();
//		nodeGui = new NodeGui(ID);
	}

	// for setting edges of the current node
	public void addEdge(Edge e) {
		edges.add(e);
	}

	public void addEdge(int weight, Node to) {
		edges.add(new Edge(weight, this, to));
	}

	public void removeEdge(Edge e) {
		edges.remove(e);
	}

	// use these for looping throw edges
	///////////////////////////////////////////////////////////////////////////
	public void startLooping() {
		currentIndex = 0;
	}

	public Edge getNextEdge() {

		Edge r;
		if (currentIndex == edges.size())
			r = null;
		else
			r = edges.get(currentIndex);

		currentIndex++;
		return r;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || this.ID != ((Node) obj).ID)
			return false;
		return true;
	}

	// getters and setters
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public Edge getParent() {
		return parent;
	}

	public void setParent(Edge parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		String r = "" + ID;

		if (edges.size() == 0)
			return r + "()";

		r += "(";
		int i;
		for (i = 0; i < edges.size() - 1; i++)
			r += edges.get(i).getTo().getID() + ",";
		r += edges.get(i).getTo().getID() + ")";

		return r;
	}

//	public NodeGui getGUI() {
//		return nodeGui;
//	}

}
