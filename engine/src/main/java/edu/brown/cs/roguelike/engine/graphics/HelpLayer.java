package edu.brown.cs.roguelike.engine.graphics;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.ItemType;
import edu.brown.cs.roguelike.engine.entities.Potion;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.game.Announcer;
import edu.brown.cs.roguelike.engine.game.TurnManager;
import edu.brown.cs.roguelike.engine.level.Level;

public class HelpLayer<A extends Application> implements Layer{

	private Vec2i size;
	private A app;

	public HelpLayer( A app, Vec2i size) {
		this.app = app;
		this.size = size;
	}

	@Override
	public void doDraw(Section s) {
		s.drawString(1, 0, "- Move with the arrow keys or hjkl");
		s.drawString(1, 1, "- The enemies are letters! Move into them to fight them");
		s.drawString(1, 2, "- < and > are up and down stairs,");
		s.drawString(1, 3, "- Stand on stairs and press the correspsonding key to use them");
		s.drawString(1, 4, "- Other characters are items, walk on them to pick them up");
		s.drawString(1, 5, "- Use 'o' to display a cursor to observe the world");
		s.drawString(1, 6, "- Quaff potions with q");
		s.drawString(1, 7, "- Wield weapons with w");
		s.drawString(1, 8, "- Drop items with d");
	}

	@Override
	public GameAction getActionForKey(Key k) {
		return new GameAction(0,0); //Always pop
	}

	@Override
	public void propagateAction(GameAction action) {
		app.getLayers().pop(); 
	}

	@Override
	public void updateSize(Vec2i newSize) {
		this.size = newSize;
	}

	@Override
	public void tick(long nanosSincePreviousTick) {}



}

