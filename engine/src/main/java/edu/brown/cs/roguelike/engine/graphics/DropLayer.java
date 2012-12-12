package edu.brown.cs.roguelike.engine.graphics;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.ItemType;
import edu.brown.cs.roguelike.engine.entities.Potion;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.entities.events.Drop;
import edu.brown.cs.roguelike.engine.game.Announcer;
import edu.brown.cs.roguelike.engine.game.TurnManager;
import edu.brown.cs.roguelike.engine.level.Level;

public class DropLayer<A extends Application> extends UseItemLayer<A> {

	public DropLayer(TurnManager tm, A app, Vec2i size, Level currentLevel) {
		super(tm, app, size, currentLevel);
	}

	@Override
	public void doDraw(Section s) {
		s.drawString(0, 0, "Drop what?");
	}

	@Override
	protected void applyItemEffect(Stackable item, EntityActionManager target) {
			Announcer.announce("You drop the " + item.getDescription()+ ".");
			tm.takeTurn(new Drop(item));
	}

}

