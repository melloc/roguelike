package edu.brown.cs.roguelike.engine.pathfinding;

import java.util.ArrayList;

public class GraphNode<N extends GraphNode<N>>  {
  
  private ArrayList<N> neighbors;
  
  public GraphNode() {
    this.neighbors = new ArrayList<N>();
  }

  public void addNeighbor(N neighbor) {
    this.neighbors.add(neighbor);
  }
  
  public ArrayList<N> getNeighbors() {
    return neighbors;
  }


}
