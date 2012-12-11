package edu.brown.cs.roguelike.engine.fsm;

/**
 * A simple FSM
 * 
 * @author lelberty
 *
 */
public class FSM<I extends Input> {
  
  protected State<I> curState;
  
  public FSM() {
  }
  
  public void setCurState(State<I> s) {
    curState = s;
  }
  
  public void setStartState(State<I> s) {
    this.setCurState(s);
    s.onEnter();
  }
    
  /** 
   * Get next state based on input
   * 
   * @param i
   */
  public void update(I i) {
    curState = curState.nextState(i);
  }
  
}
