package edu.brown.cs.roguelike.game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.Layer;
import edu.brown.cs.roguelike.engine.graphics.Section;
import edu.brown.cs.roguelike.engine.level.Level;

/**
 * Displays a list of items an the corresponding key for each items.
 * The user can view pages of items. 
 * 
 * @author Jake
 *
 */
public class InventoryLayer implements Layer{

	private int page = 0;
	private Vec2i size;
	private GUIApp app;
	private Level currentLevel;

	public InventoryLayer(GUIApp guiApp, Vec2i size, Level currentLevel) {
		this.app = guiApp;
		this.size = size;
		this.currentLevel = currentLevel;
	}
	
	@Override
	public GameAction getActionForKey(Key k) {
		switch (k.getKind()) {
		case NormalKey: 
			switch (k.getCharacter()) {
			case ' ':
				return new GameAction(2, 1); //next page
			default:
				return new GameAction(2, 0); // do nothing
			}
		default:
			return new GameAction(2, 0); // do nothing
		}
	}

	@Override
	public void propagateAction(GameAction action) {
		if (action.getContextClassifier() != 2)
			throw new Error("Received an action for a non-gameplay context.");
		
		switch (action.getActionClassifier()) {
		case 0:
			break; // do nothing
		case 1:
			closeInventory();
			break;
		default:
			throw new Error("This shouldn't happen and indicates an unhandled case. Received: "
					+ action.toString()
					+ "...");
		}
	}


	private void closeInventory() {
		app.getLayers().pop();
	}

	@Override
	public void updateSize(Vec2i newSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub

	}


	@Override
	public void doDraw(Section s) {
		
		s = s.moveUpperLeft(new Vec2i(size.x - 50, 0));
		
		Set<Stackable> inventory;
		List<EntityActionManager> mains = currentLevel.getManager().getEntity("main");
		if (mains.size() > 0)
			inventory =	mains.get(0).getEntity().getInventory();
		else
			inventory = new HashSet<Stackable>();
		Iterator<Stackable> iter = inventory.iterator();
		int i;
		for(i = 0; i < Math.min(size.y-1,inventory.size()); i++) {
			String line = "";
			line += "(";
			line += getLetter(i);
			line += ") ";
			line +=  iter.next().getDescription();
			s.drawString(0, i, line);
		}
		s.drawString(0, i, "Press Space to continue");
	}

	private char getLetter(int i) {
		return (char) ('a' + i);
	}

}
