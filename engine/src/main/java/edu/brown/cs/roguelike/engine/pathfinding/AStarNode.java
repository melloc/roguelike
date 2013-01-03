package edu.brown.cs.roguelike.engine.pathfinding;

public abstract class AStarNode<N extends AStarNode<N>> 
  extends GraphNode<N> implements Comparable<N> {
  
  private int fScore;
  private int gScore;
  
  public AStarNode() {
  }
  
  /**
   * Calculates an admissible heuristic estimate of getting
   * from this node to the goal node
   * @param goal
   * @return
   */
  protected abstract int getHScore(N goal);
  
  public void update(int gScore, N goal) {
    this.fScore = gScore + getHScore(goal);
    this.gScore = gScore;
  }
  
  public int getGScore() {
    return this.gScore;
  }

  public int getFScore() {
    return this.fScore;
  }
  
  /**
   * Calculate the distance between this node and a neighbor
   */
  public abstract int distance(N neighbor);

  public int compareTo(N node1) {
    return this.fScore - node1.getFScore();
  }
  
}
