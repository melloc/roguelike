package edu.brown.cs.roguelike.engine.level;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Pathfinder using A* searching. 
 * @author jte
 *
 * @param <T> Any node type
 */
public class Pathfinder {


	/**
	 * A* pathfinder from s to e
	 * @param s Start Node
	 * @param e End Node
	 * @return List of nodes forming the path
	 */
	public  static List<Tile> findPath(Tile s, Tile e, Level level){
		final Hashtable<Tile,Integer> distances = new Hashtable<Tile,Integer>();
		Hashtable<Tile,Tile> parents = new Hashtable<Tile,Tile>();
 
		PriorityQueue<Tile> pq = 
				new PriorityQueue<Tile>(40,new Comparator<Tile>() {
					@Override
					public int compare(Tile t1, Tile t2) {
						int dist1 = distances.get(t1);
						int dist2 = distances.get(t1);
						return dist1 - dist2;
					}
				});

		distances.put(s, 0);
		pq.add(s);

		while(!pq.isEmpty()) {
			Tile low = pq.remove();
			if(low == e) {
				return createPath(e,parents);
			}

			for(Tile neighbor : level.getNeighbors(low))
			{
				if(distances.get(neighbor) > distances.get(low) + 1){
					distances.put(neighbor,distances.get(low) + 1);
					parents.put(neighbor,low);
					pq.add(neighbor);
				}
			}
		}
		//PQ is empty, no path exists
		return null;
	}

	/**
	 * Creates a path using the node's parents
	 * @param e the end node
	 * @return A list of nodes defining the path
	 */
	private static List<Tile> createPath(Tile e, Hashtable<Tile,Tile> parents) {
		LinkedList<Tile> path = new LinkedList<Tile>();
		Tile node = e;
		while(node != null) {
			path.addFirst(node);
			node = parents.get(node);
		}
		return path;
	}

}
