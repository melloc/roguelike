package edu.brown.cs.roguelike.engine.graphics;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.ItemType;
import edu.brown.cs.roguelike.engine.entities.Potion;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.game.Announcer;
import edu.brown.cs.roguelike.engine.game.TurnManager;
import edu.brown.cs.roguelike.engine.level.Level;

public class PotionLayer<A extends Application> extends UseItemLayer<A> {

	public PotionLayer(TurnManager tm, A app, Vec2i size, Level currentLevel) {
		super(tm, app, size, currentLevel);
	}

	@Override
	public void doDraw(Section s) {
		s.drawString(0, 0, "Quaff what?");
	}

	@Override
	protected void applyItemEffect(Stackable item, EntityActionManager target) {
		if(item.getType() == ItemType.POTION) {
			Potion p = (Potion) item;
			Announcer.announce("You quaff the " + p.getDescription()+ ".");
			tm.takeTurnAndAnnounce(p.quaffAction);
			target.getEntity().getInventory().remove(item);
		}
	}

}

