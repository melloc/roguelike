package edu.brown.cs.roguelike.engine.proc.cs195n;

import java.awt.Graphics2D;
import cs195n.SwingFrontEnd;
import cs195n.Vec2i;

/**
 * Applications:
 * 
 * The application is responsible for changing screens, as well as passing on I/O, tick and resize events to the screens.
 * 
 * @author Jake
 *
 */
public abstract class Application extends SwingFrontEnd{

	Vec2i currentSize ;
	
	public Application(String title, boolean fullscreen) {
		super(title, fullscreen);
		currentSize = DEFAULT_WINDOW_SIZE;
		System.out.println("Application Started");
	}


	@Override
	protected void onTick(long nanosSincePreviousTick) {}
	
	

	@Override
	protected abstract void onDraw(Graphics2D g);




}
