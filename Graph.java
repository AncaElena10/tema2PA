
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {
	
	// numarul total de noduri
	private int numNodes;
	// indexul muchiei
	private ArrayList<Edge> indexes = new ArrayList<>();
	ArrayList<Edge> allEdges;
	private BufferedReader fileIn;

	public Graph() {
		allEdges = new ArrayList<Edge>();
	}

/**
 * Creare muchie intre doua noduri 
 * @param fromNodeIdx
 * @param toNodeIdx
 * @param cost
 * @param index
 */
	public void insertEdge(int fromNodeIdx, int toNodeIdx, int cost, int index) {
		allEdges.add(new Edge(fromNodeIdx, toNodeIdx, cost, index));
	}

	/**
	 * 
	 * @return numarul total de noduri
	 */
	public int getNodeCount() {
		return numNodes;
	}
	
	/**
	 * 
	 * @return lungimea array-ului de indecsi primiti din fisier
	 */
	public int getIndexesSize() {
		return indexes.size();
	}
	
	/**
	 * 
	 * @return muchiile care compun graful
	 */
	public ArrayList<Edge> getAllEdges() {
		return allEdges;
	}
	
	/**
	 * 
	 * @return indicii primiti din fisier
	 */
	public ArrayList<Edge> getIndexes() {
		return indexes;
	}
	
	/**
	 * Functia face corelatia intre index-ul primit din fisier si muchia la care
	 * se refera
	 * @param position
	 * @return
	 */
	public Edge returnIndex(int position) {
		for (int i = 0; i < allEdges.size(); i++) {
			if (allEdges.get(i).getEdgeIndex() == position) {
				return allEdges.get(i);
			}
		}
		return new Edge();
	}

	/**
	 * functie care citeste toate testele
	 * @param fileIn2
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public void readData(BufferedReader fileIn2) throws NumberFormatException, IOException {
		fileIn = new BufferedReader(new FileReader("kim.in"));
		int numEdges = 0;
		
		String line = fileIn.readLine();
		String[] remove = line.split("\\s+"); // eliminare spatii
		
		try {
			numNodes = Integer.parseInt(remove[0]);		
			numEdges = Integer.parseInt(remove[1]);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		int cnt = 1;
		for (int i = 0; i < numEdges; i++) {
			line = fileIn.readLine();
			remove = line.split("\\s+");
			int node1 = Integer.parseInt(remove[0]);
			int node2 = Integer.parseInt(remove[1]);
			int cost = Integer.parseInt(remove[2]);

			Edge edge = new Edge();
			int firstNode = edge.getFirstNode();
			int secondNode = edge.getSecondNode();
			long thisCost = edge.getCost();
			firstNode = node1;
			secondNode = node2;
			thisCost = cost;

			insertEdge(node1, node2, cost, i);
		}
		// citire cele Q muchii de rezerva
		while ((line = fileIn.readLine()) != null) {
			int x = Integer.parseInt(line);
			indexes.add(returnIndex(x - 1));
		}
	}
}
