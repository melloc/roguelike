package edu.brown.cs.roguelike.engine.graphics;

import java.util.List;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.level.Level;
public abstract class UseItemLayer<A extends Application> implements Layer  {
	private A app;
	private Vec2i size;
	private Level currentLevel;

	public UseItemLayer(A app, Vec2i size, Level currentLevel) {
		this.app = app;
		this.size = size;
		this.currentLevel = currentLevel;
	}

	@Override
	public GameAction getActionForKey(Key k) {
		if (k.getCharacter() >= 'a' && k.getCharacter() <= 'z') {
			int slot = k.getCharacter() - 'a';
			return new GameAction(2,slot); //Quaff potion in slot 
		}
		else {
			return new GameAction(2,-1); //Not valid key
		}
	}

	@Override
	public void propagateAction(GameAction action) {
		if (action.getContextClassifier() != 2)
			throw new Error("Received an action for a non-gameplay context.");

		if(action.getActionClassifier() == -1) {
			//TODO: Display announcement of incorrect key
			app.getLayers().pop(); //Remove this layer from stack
		}
		else if(isValidSlot(action.getActionClassifier())){
			useItem(action.getActionClassifier());
			app.getLayers().pop(); //Remove this layer from stack
		}
		else {
			throw new Error("This shouldn't happen and indicates an unhandled case. Received: "
					+ action.toString()
					+ "..."); }
	}

	private void useItem(int slot) {

		System.out.println(slot);

		List<EntityActionManager> mainCharList = currentLevel.getManager().getEntity("main");
		if(mainCharList.size() < 1) {
			return;
		}

		Combatable main = mainCharList.get(0).getEntity();

		Stackable[] inventory = main.getInventory().toArray(new Stackable[0]);
		if(inventory.length < 1 || inventory.length-1 < slot) {
			return;
		}

		applyItemEffect(inventory[slot], mainCharList.get(0));
	}


	protected abstract void applyItemEffect(Stackable item, EntityActionManager target);

	private boolean isValidSlot(int slot) {
		return (slot >= 0 && slot <= 25);
	}


	@Override
	public void updateSize(Vec2i newSize) {
		this.size = newSize;
	}


	@Override
	public void tick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub

	}

}


