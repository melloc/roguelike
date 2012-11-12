package edu.brown.cs.roguelike.engine.graphics;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.events.GameAction;

/**
 * A {@link Layer} represents something that is on the screen that steals
 * control of the keyboard, screen, etc.
 * 
 * @author cody
 *
 */
public interface Layer {

    /**
     * Converts a {@link Key} into a {@link GameAction}, which is Lanterna 
     * agnostic, and as such can be more reliably passed around the game.
     *
     * @param k Represents the key that was pressed.
     *
     * @return The {@link GameAction} to which the pressed key maps.
     */
	public GameAction getActionForKey(Key k);
	
    /**
     * Passes along an action that was taken. (Note that this need not be from
     * the user. It could be coming across the network, from a unit test, etc.)
     *
     * This layer can decide how it wants to treat the action. It may do 
     * something with it, or decide to pass it further on to someone else.
     *
     * @param action The taken action.
     */
	public void propagateAction(GameAction action);
	
    /**
     * Instructs this {@link Layer} that the size of the screen has changed.
     * The layer might want to know about this in case it needs to change how 
     * it renders itself. For example, if it was automatically wrapping text
     * at a point at which it believed that the terminal stopped, it could now
     * extend itself a bit further.
     */
	public void updateSize(Vec2i newSize);
	
    /**
     * Tell this {@link Layer} that it is time for it to draw itself. A 
     * {@link Screen} object is passed in which should be used to draw this 
     * {@link Layer}.
     */
	public void doDraw(Screen s);
	
    /**
     * Used to alert this layer that time has passed. It can decide if it cares
     * about that or not. (Since roguelikes are primarily driven by the keyboard,
     * this may not be too important.)
     *
     * @param nanosSincePreviousTick The amount of time in nanoseconds since the 
     * last tick.
     */
	public void tick(long nanosSincePreviousTick);
	
}
