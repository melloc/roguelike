package edu.brown.cs.roguelike.engine.fsm;

import java.util.UUID;

import edu.brown.cs.roguelike.engine.save.Saveable;

/**
 * A simple FSM
 * 
 * @author lelberty
 *
 */
public class FSM<I extends Input> implements Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -777482188164096235L;
	
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
		curState.onActive();
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
		FSM<I> other = (FSM<I>) obj;
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
