package edu.brown.cs.roguelike.engine.fsm;

import java.util.UUID;

import edu.brown.cs.roguelike.engine.save.Saveable;

public abstract class Transition<I extends Input> 
  implements Comparable<Transition<I>>, Saveable {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -2375741298470957438L;
private State<I> targetState;
  
  public Transition(State<I> targetState) {
    this.targetState = targetState;
  }
  
  public State<I> getTargetState() { return this.targetState; } 
  
  public abstract boolean isReady(I i);
  
  public int compareTo(Transition<I> o) {
    return 0;
  }
  
  
	/*** BEGIN Saveable ***/

	private UUID id;
	
	/** initialize id **/
	{
		this.id = UUID.randomUUID();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		// TODO: figure out how to safely handle this cast
		Transition<I> other = (Transition<I>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (id.equals(other.id))
			// return true if ids are the same
			return true;
		return false;
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	/*** END Saveable ***/
}
