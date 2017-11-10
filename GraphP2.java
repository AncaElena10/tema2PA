
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphP2 {
	// numarul total de noduri
	private int numNodes;
	// lista de adiacenta
	private Map<Integer, List<Integer>> adjList;
	private int[] nodes;
	private BufferedReader fileIn;
	
	/*
	 * initializare lista de adiacenta
	 */
	public GraphP2(int nodesNum) {
		adjList = new HashMap<Integer, List<Integer>>();

		for (int i = 1; i <= nodesNum; i++) {
			adjList.put(i, new LinkedList<Integer>());
		}
	}
	
	/**
	 * Creare o muchie intre node1 si node2
	 * 
	 * @param node1
	 * @param node2
	 */
	public void setEdge(int node1, int node2) {
		List<Integer> list = adjList.get(node1);
		list.add(node2);
	}
	
	/**
	 * 
	 * @param node
	 * @return vecinii nodului primit
	 */
	public List<Integer> getNeighbors(int node) {
		if (node > adjList.size()) {
			return null;
		}
		return adjList.get(node);
	}

	/**
	 * 
	 * @return lista de adiacenta
	 */
	public Map<Integer, List<Integer>> getAdjList() {
		return adjList;
	}

	/**
	 * 
	 * @return numarul de noduri
	 */
	public int getNodesNum() {
		return numNodes;
	}

	/**
	 * 
	 * @return noduri
	 */
	public int[] getNodes() {
		return nodes;
	}

	/**
	 * 
	 * @param node
	 * @return true daca nodul primit nu are vecini
	 */
	public boolean isAloneVertex(int node) {
		if (getNeighbors(node).size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Functie care citeste toate testele
	 * @param fileIn2
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void readData(BufferedReader fileIn2) throws NumberFormatException, IOException {
		fileIn = new BufferedReader(new FileReader("portal.in"));
		int numEdges = 0;
		String line = fileIn.readLine();
		String[] remove = line.split("\\s+"); // eliminare spatii

		try {
			numNodes = Integer.parseInt(remove[0]);
			numEdges = Integer.parseInt(remove[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		nodes = new int[numNodes];
		for (int i = 1, cnt = 0; i <= numNodes; i++) {
			nodes[cnt++] = i;
		}
		
		for (int i = 0; i < numEdges; i++) {
			line = fileIn.readLine();
			remove = line.split("\\s+");
			int node1 = Integer.parseInt(remove[0]);
			int node2 = Integer.parseInt(remove[1]);

			setEdge(node1, node2);
			setEdge(node2, node1);
		}
	}
}
