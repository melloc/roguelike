package edu.brown.cs.roguelike.engine.events;

/**
 * This class represents a game action so that we can effectively separate
 * keys from actual actions. By doing this, we can allow the player to remap
 * any actions to whatever keys they would like.
 * 
 * The way that a game action works is by having two different fields that
 * represent the individual action. The first, the layer classifier, is meant
 * identify in which context this action exists, while the second represents
 * the possible action within that context.
 * 
 * For every context classification you create, please place it 
 * <a href="http://phabricator.codymello.com/w/projects/cs195n_final/contexts/">here</a>.
 * 
 * @see <a href="http://phabricator.codymello.com/w/projects/cs195n_final/contexts/">Contexts</a>
 * 
 * @author cody
 *
 */
public class GameAction {
	
	public GameAction(int context, int action) {
		this.contextClassifier = context;
		this.actionClassifier = action;
	}

	private int contextClassifier;
	
	/**
	 * Fetches the contextClassifier from this {@link GameAction}
	 * @return
	 */
	public int getContextClassifier() {
		return contextClassifier;
	}
	
	private int actionClassifier;
	
	/**
	 * Gets the actionClassifier for this {@link GameAction}
	 * @return
	 */
	public int getActionClassifier() {
		return actionClassifier;
	}

	@Override
	public String toString() {
		return "GameAction(context=" + this.contextClassifier + ", action=" + this.actionClassifier + ")";
	}
	
}
