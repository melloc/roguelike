package edu.brown.cs.roguelike.engine.fsm;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A State in a FSM.
 * 
 * @author lelberty
 *
 */
public abstract class State<I extends Input> {
  
  private ArrayList<Transition<I>> transitions;
  
  public State() {
    this.transitions = new ArrayList<Transition<I>>();
  }
  
  /**
   * Adds a transition to this State, and re-sorts the ArrayList
   * so that higher-priority States come first 
   * 
   * @param t
   */
  public void addTransition(Transition<I> t) {
    transitions.add(t);
    Collections.sort(transitions);
  }
  
  public abstract void onActive();
  public abstract void onEnter();
  public abstract void onExit();

  
  /**
   * Gets the next State, traversing the Transitions in
   * order of highest to lowest priority. This order is guaranteed
   * by addTransition.
   * 
   * If transition, calls onExit on this and onEnter on next state.
   * Otherwise returns this. 
   * 
   * @param i (Input) input condition used to determine the next State
   * @return
   */
  public State<I> nextState(I i) {
    
    for (Transition<I> t : transitions) {
      
      if (t.isReady(i)) {
        State<I> next = t.getTargetState();
        this.onExit();
        next.onEnter();
        return next;
      }
    }
    
    // if no Transitions were succesful, just loop back to self
    return this;
  }


}
