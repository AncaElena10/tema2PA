/**
 * Tema 2 Proiectarea algoritmilor
 * KimLandia
 *
 * @author  Moisa Anca-Elena 321CA
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
	public static int[] parent;
	public static int[] size;
	
	public static int findSet(int node) {
		if (parent[node] == node) {
			return node;
		}
		return findSet(parent[node]);
	}
	
	public static void union(int n1, int n2) {
		n1 = findSet(n1);
		n2 = findSet(n2);
		if (size[n1] < size[n2]) parent[n1] = n2;
		if (size[n1] > size[n2]) parent[n2] = n1;
		if (size[n1] == size[n2]) parent[n2] = n1;
	}
	
	private static void sortEdges(Graph g) {
		Collections.sort(g.getAllEdges(), new Edge());
	}
	
	public static void makeSet(Graph g) {
		int nodesNum = g.getNodeCount();
		parent = new int[nodesNum + 1];
		size = new int[nodesNum + 1];
		for (int i = 0; i < nodesNum; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}
	
	public static long kruskal(Graph g, Edge edge, long initialCost) {
		ArrayList<Edge> muchiiAMA = new ArrayList<Edge>();
		
		/**
		 * fiecare nod e un arbore diferit
		 */
		makeSet(g);
		/**
		 * sortare muchii in ordine crescatoare a costului
		 */
		sortEdges(g);
		
		/**
		 * conditie pentru costurile muchiilor incluse neaparat in graf
		 * daca aceasta este null, inseamna ca algoritmul kruskal poate decurge
		 * in mod normal
		 */
		if (edge != null) {
			g.getAllEdges().get(0).setCost(initialCost);
		}

		for (Edge e : g.getAllEdges()) {
			int node1 = e.getFirstNode();
			int node2 = e.getSecondNode();
			
			/**
			 * capetele muchiei fac parte din subarbori disjuncti
			 */
			if (findSet(node1) != findSet(node2)) {
				muchiiAMA.add(e); // adaug muchia la arbore
				union(node1, node2); // unesc subarborii aferenti lui node1 si node2
			}
		}
		
		/**
		 * calcularea costului minim
		 */
		long thisCost = 0;
		for (int i = 0; i < muchiiAMA.size(); i++) {
			thisCost += muchiiAMA.get(i).getCost();
		}
		return thisCost;
	}
	
	public static void main(String ... args) throws IOException {
		BufferedReader fileIn = new BufferedReader(new FileReader("kim.in"));

		/**
		 * creare graf si citirea datelor
		 */
		Graph g = new Graph();
		g.readData(fileIn);
		long result = kruskal(g, null, 0);
		/**
		 * pun rezultatul functiei intr-un string si il scriu in fisierul de output
		 */
		String stringResult = String.valueOf(result);
		BufferedWriter fileOut = new BufferedWriter(new FileWriter("kim.out"));
		fileOut.write(stringResult);
		String newline = System.getProperty("line.separator");
		fileOut.write(newline);
			
		/**
		 *  extrag muchia cu indicele primit, ii setez costul minim si o trimit ca parametru
		 *  in functia kruskal
		 *  de asemenea, pun rezultatul intr-un string si il scriu in output
		 */
		for (int i = 0; i < g.getIndexesSize(); i++) {
			Edge edge = g.getIndexes().get(i);
			long initialCost = edge.getCost();
			edge.setCost(Integer.MIN_VALUE);
			long result2 = kruskal(g, edge, initialCost);
			String stringResult2 = String.valueOf(result2);
			fileOut.write(stringResult2);
			String newline2 = System.getProperty("line.separator");
			fileOut.write(newline2);
		}
		/**
		 * inchidere fisiere
		 */
		fileIn.close();
		fileOut.close();
	}
}

/**
 *  clasa retine componentele grafului: primul nod, al doilea si costul muchiei formate intre
 *  cele doua noduri
 */
class Edge implements Comparator<Object> {
	private long cost;
	private int firstNode;
	private int secondNode;
	private int edgeIndex;
	
	public Edge(int firstNode, int secondNode, long cost, int edgeIndex) {
		this.firstNode = firstNode;
		this.secondNode = secondNode;
		this.cost = cost;
		this.edgeIndex = edgeIndex;
	}
	
	public Edge() {}

	public int getFirstNode() {
		return firstNode;	
	}
	
	public int getSecondNode() {
		return secondNode;
	}
	
	public long getCost() {
		return cost;
	}
	
	public int getEdgeIndex() {
		return edgeIndex;
	}
	
	public void setCost(long initialCost) {
		this.cost = initialCost;
	}
	
	public String toString() {
		return firstNode + " " + secondNode + " " + cost;
	}

	/**
	 * sortare muchii dupa cost
	 */
	@Override
	public int compare(Object arg0, Object arg1) {
		Edge e1 = (Edge) arg0;
		Edge e2 = (Edge) arg1;
		if (e1.cost < e2.cost)
			return -1;
		if (e1.cost > e2.cost)
			return 1;
		return 0;
	}
}
