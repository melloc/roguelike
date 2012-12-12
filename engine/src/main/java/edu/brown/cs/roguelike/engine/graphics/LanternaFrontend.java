package edu.brown.cs.roguelike.engine.graphics;

import java.util.Date;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.TerminalSize;

import cs195n.Vec2i;

/**
 * This class provides a framework in which we can develop an application that
 * uses the Lanterna library for drawing on the terminal.
 *
 * @see <a href="http://code.google.com/p/lanterna/">Lanterna's Site</a>
 */
public abstract class LanternaFrontend {
	
    public LanternaFrontend(String title) {


    }

    /**
	 * Use to update any state that changes over time. The amount of time that has
     * passed depends on the length of time that the event loop takes to complete.
	 * 
	 * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
	 *                              	to onTick
	 */
	protected abstract void onTick(long nanosSincePreviousTick);
	
	/**
	 * This function is called every time the screen needs to be updated.
     *
	 * @param s		A {@link Screen} object used for writing characters to the terminal.
	 */
	protected abstract void onDraw(Section s);
	
	/**
     * This method is called with a Lanterna {@link Key} event each time 
     * a key is pressed. Ideally, the key is then turned into a Lanterna
     * agnostic representation that is then passed throughout the game.
     *
	 * @param e		A Lanterna {@link Key} representing the input event.
     *
     * @see {@link edu.brown.cs.roguelike.engine.events.GameAction}
	 */
	protected abstract void onKeyPressed(Key k);
	
	/**
	 * Called when the size of the drawing area changes. Any subsequent calls to onDraw should note
	 * the new size and be sure to fill the entire area appropriately. Guaranteed to be called
	 * before the first call to onDraw.
	 * 
	 * @param newSize	the new size of the drawing area.
	 */
	protected abstract void onResize(Vec2i newSize);

    /**
     * Used to indicate if the game should be running right now. Controls the
     * event loop, too. When this is set to false, the loop will end, and any
     * necessary cleanup should be performed.
     */
	private boolean running = false;
	
    /**
	 * Begin processing events. None of the <code>on</code>*** methods will be called before this is
	 * called.
	 */
	public final void startup() {
		if (!running) {
			running = true;
			doStartup();
		}
	}

	/**
	 * Stop processing events. This causes the event loop to stop once it makes 
     * it through the current loop. Because of this, some events may still get 
     * called after this method is called.
	 * <br><br>
	 * Calling this method is not strictly necessary, but useful if you want to close the front-end
	 * without exiting the program (e.g. if you write a map editor that directly launches the game).
	 */
	public final void shutdown() {
		if (running) {
			running = false;
		}
	}

	protected boolean isTTY = false;

    /**
     * Contains the main event loop that drives the game. This method will not 
     * return until {@link LanternaFrontend#shutdown} is called.
     */
    final void doStartup() {
		Vec2i screenSize = getSize();
		if (isTTY) System.out.print("\033[8;" + screenSize.y + ";" + screenSize.x + "t");
        Screen screen = TerminalFacade.createScreen();
        screen.startScreen();
        {
            TerminalSize size = screen.getTerminalSize();
            onResize(new Vec2i(size.getColumns(),size.getRows()));
        }


        long time = new Date().getTime();
        while (running) {
			try {
				// First do anything that we need to do for updating screen size.
				if (screen.resizePending()) {
					screen.refresh();
					TerminalSize size = screen.getTerminalSize();
					onResize(new Vec2i(size.getColumns(),size.getRows()));
				}

				// First do anything that we need to do for updating screen size.
				// Then we draw the screen.
				screen.clear();
				onDraw(new Section(screen, new Vec2i(0,0), screenSize));
				screen.refresh();

				// We can now give the program any key presses
				Key key = screen.readInput();
				if (key != null)
					onKeyPressed(key);

				// Then we take care of ticks.
				long currentTime = new Date().getTime();
				onTick(currentTime - time);
				time = currentTime;
			} catch (NullPointerException e) {
				// For now, ignore it.
			}
        }
        screen.stopScreen();
    }
    
    /**
     * This method turns on debug mode, which is useful for development.
     */
    final void doSetDebugMode() {
        // Nothing is done here currently.
        // If logging is added at some point (such as log4j), then this should
        // set the logging level to something proper.
    }

	public abstract Vec2i getSize();

}
