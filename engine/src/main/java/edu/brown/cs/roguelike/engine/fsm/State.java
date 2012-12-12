package edu.brown.cs.roguelike.engine.fsm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.Entity;
import edu.brown.cs.roguelike.engine.save.Saveable;

/**
 * A State in a FSM.
 * 
 * @author lelberty
 *
 */
public abstract class State<I extends Input> implements Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -2729803608828543111L;

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
		State<I> other = (State<I>) obj;
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
