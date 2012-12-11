package edu.brown.cs.roguelike.engine.fsm;

public abstract class Transition<I extends Input> implements Comparable<Transition<I>> {
  
  private State<I> targetState;
  
  public Transition(State<I> targetState) {
    this.targetState = targetState;
  }
  
  public State<I> getTargetState() { return this.targetState; } 
  
  public abstract boolean isReady(I i);
  
  public int compareTo(Transition<I> o) {
    return 0;
  }
  
}
