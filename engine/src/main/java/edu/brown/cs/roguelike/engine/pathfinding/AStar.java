package edu.brown.cs.roguelike.engine.pathfinding;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;


/**
 * Implementation of the A* graph pathfinding algorithm
 * 
 * @author lelberty
 *
 */
public class AStar<N extends AStarNode<N>> {
  
  private HashMap<N,N> cameFrom;
  private ArrayList<N> path;
  
  public AStar() {
    reset();
  }

  private void reset() {
    path = null;
    cameFrom = null;
    path = new ArrayList<N>();
    cameFrom = new HashMap<N,N>();
  }
  
  /**
   * A* pathfinding. Returns an ArrayList of nodes representing the calculated
   * path from the start to the goal
   */
  public ArrayList<N> computePath(N start, N goal) {

    reset(); // reset path and pathMap
    
    // open and closed sets
    ArrayList<N> closed = new ArrayList<N>();
    PriorityQueue<N> open = new PriorityQueue<N>();
    
    open.add(start);
    
    int totalDistance = 0;

    start.update(totalDistance, goal);
    
    // init now for efficiency
    int neighborGScore = 0;
    
    while(!open.isEmpty()) {
      N current = open.poll();
      
      if (current.equals(goal)) {
        buildPath(goal); // reconstruct and return
        return this.path;
      } else { // otherwise keep going
        
        closed.add(current);
        
        for (N neighbor : current.getNeighbors()) {
          
          if (!closed.contains(neighbor)) {

            neighborGScore = current.getGScore() + current.distance(neighbor); 
            
            // If we haven't visited neighbor yet, or if this is a better path to
            // neighbor, then update it and add to open
            if (!open.contains(neighbor) || neighborGScore < neighbor.getGScore()) {
              
              // update the path to neighbor
              this.cameFrom.put(neighbor, current);
              
              // update neighbor node and add to open
              neighbor.update(neighborGScore, goal);
              open.add(neighbor);
            }
          }
        }
      }
    }
    
    // BAD. Could not find path.
    return null;
  }
  
  /**
   * Helper. Reconstructs path from pathMap
   * 
   * @param from
   * @param cur
   * @return
   */
  private void buildPath(N cur) {
    if (cur != null) { // icky null base case
      this.path.add(0,cur);
      buildPath(this.cameFrom.get(cur));
    }
  }
    
}    
