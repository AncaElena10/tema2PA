/**
 * Tema 2 Proiectarea algoritmilor
 * Portal
 *
 * @author  Moisa Anca-Elena 321CA
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class MainP2 {
	private static Map<Integer, List<Integer>> adjList;

	/**
	 * 
	 * @param g
	 * @param array
	 * @return numaul total de noduri izolate
	 */
	public static int dfs(GraphP2 g, ArrayList<Integer> arrayFrunze) {
		int nodesNum = g.getNodesNum();
		int[] nodes = g.getNodes();
		int countAlone = 0;

		/*
		 * in variabila countAlone retin acele noduri din care nu porneste
		 * sau nu ajunge niciun drum, fiind si ele considerate noduri izolate
		 */
		for (int i = 0; i < nodesNum; i++) {
			if (g.isAloneVertex(nodes[i]) == true) {
				countAlone++;
			}
		}

		Stack<Integer> stack = new Stack<Integer>();
		boolean[] wasVisited = new boolean[adjList.size()];
		int[] parent = new int[adjList.size()];
		int ok = 0;
		int finalCnt = 0;
		int countIsolated = 0;

		/**
		 * functia DFS cu o mica modificare
		 * in cazul in care se gaseste un ciclu, algoritmul poate continua
		 * in cazul in care nu se gaseste un ciclu, rezulta ca exista un nod izolat
		 */
		for (int i = 0; i < arrayFrunze.size(); i++) {
			if (wasVisited[arrayFrunze.get(i)] == false) {
				stack.push(arrayFrunze.get(i));
				wasVisited[arrayFrunze.get(i)] = true;
				ok = 0;

				while (!stack.isEmpty()) {
					int node = stack.pop();
					for (int j = 0; j < g.getNeighbors(node).size(); j++) {
						int neighbour = g.getNeighbors(node).get(j);
						if (wasVisited[neighbour] == true && neighbour != parent[node]) {
							ok = 1;
						}
						if (wasVisited[neighbour] == false) {
							wasVisited[neighbour] = true;
							parent[neighbour] = node;
							stack.push(neighbour);
						}
					}
				}
				/**
				 * daca nu exista un ciclu, atunci numarul de noduri izolate creste
				 */
				if (ok == 0) {
					countIsolated++;
				}
			}
		}
		finalCnt = countIsolated + countAlone;
		return finalCnt;
	}

	public static void main(String... args) throws IOException {
		BufferedReader fileIn = new BufferedReader(new FileReader("portal.in"));

		// creare graf
		GraphP2 g = new GraphP2(200000);
		g.readData(fileIn);
		adjList = g.getAdjList();
		int[] nodes = g.getNodes();
		int nodesNum = g.getNodesNum();
		int[] frunza = new int[nodesNum];
		int cnt = 0;

		for (int i = 0; i < nodesNum; i++) {
			if (g.getNeighbors(nodes[i]).size() == 1) {
				frunza[cnt++] = nodes[i];
			}
		}

		int cnt1 = 0;
		for (int i = 0; i < frunza.length; i++) {
			if (frunza[i] != 0) {
				cnt1++;
			}
		}

		int[] frunze = new int[cnt1];
		int cnt2 = 0;
		for (int i = 0; i < frunza.length; i++) {
			if (frunza[i] != 0) {
				frunze[cnt2++] = frunza[i];
			}
		}
		
		/**
		 * arrayFrunze contine toate frunzele din graf, adica nodurile care au un singur vecin
		 */
		ArrayList<Integer> arrayFrunze = new ArrayList<Integer>(frunze.length);
		for (int i = 0; i < frunze.length; i++) {
			arrayFrunze.add(frunze[i]);
		}

		/**
		 * pun rezultatul intr-un string si il scriu in fisierul de output
		 */
		int result = dfs(g, arrayFrunze);
		String stringResult = String.valueOf(result);
		BufferedWriter fileOut = new BufferedWriter(new FileWriter("portal.out"));
		fileOut.write(stringResult);
		/**
		 * inchidere fisiere
		 */
		fileIn.close();
		fileOut.close();
	}
}
