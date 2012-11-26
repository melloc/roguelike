package edu.brown.cs.roguelike.engine.graphics;

import java.util.Stack;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;

/**
 * Represents an application that uses Lanterna. Provides the framework for
 * building a console game.
 */
public abstract class Application extends LanternaFrontend {

    /**
     * This stack contains all elements in the game that should be drawn.
     * The element on top of the stack is considered the `active` element,
     * and receives all key presses.
     */
	protected Stack<Layer> layers = new Stack<Layer>();
	
	public Stack<Layer> getLayers() {return layers;}
	
	public Application(String title) {
		super(title);
	}

	@Override
	protected void onTick(long nanosSincePreviousTick) {
		for (Layer layer : layers)
			layer.tick(nanosSincePreviousTick);
	}

	@Override
	protected void onDraw(Section s) {
		for (Layer layer : layers)
			layer.doDraw(s);
	}
	
    /**
     * Takes care of any initialization that is necessary for the game. For 
     * example, any {@link Layer}'s that need to be added to the stack should
     * be added here. Any other preparation that is necessary should also be 
     * done here.
     *
     * @param screenSize The initial size of the screen. This is provided in 
     * case there is a need to determine where things should initially be drawn
     * until the next resize (which may never happen, so passing this in is 
     * sort of important).
     *
     * @return Boolean indicating whether initialization was sucessful.
     */
	protected abstract boolean initialize(Vec2i screenSize);

	@Override
	protected void onKeyPressed(Key k) {
		if (!layers.isEmpty()) {
			Layer active = layers.peek();
			active.propagateAction(active.getActionForKey(k));
		}
	}
	
	private boolean initialized = false;

	@Override
	protected void onResize(Vec2i newSize) {
		if (!initialized)
			initialized = initialize(newSize);
			
		for (Layer layer : layers)
			layer.updateSize(newSize);
	}

}
