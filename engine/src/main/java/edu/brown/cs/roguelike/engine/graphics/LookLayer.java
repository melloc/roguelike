package edu.brown.cs.roguelike.engine.graphics;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal.Color;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Tile;


/**
 * A layer that allows the player to look at tiles, getting descriptions of the monsters 
 * 
 * @author jte
 *
 * @param <A> the main Application of the Roguelike
 */
public class LookLayer<A extends Application> implements Layer {
	
	private final int OFFSET = 30;
	
	protected Level currentLevel;

	protected Vec2i size;
	protected A app;
	protected Tile loc;

	public LookLayer(A app, Vec2i size,Level currentLevel) {
		this.app = app;
		this.size = size;
		this.currentLevel = currentLevel;

		//Set loc to player
		EntityActionManager player = currentLevel.getManager().getPlayer(0);
		if(player == null) {
			loc = currentLevel.tiles[0][0];
		}
		else {
			loc = player.getLocation();
		}		
	}

	@Override
	public void updateSize(Vec2i newSize) {
		this.size = newSize;
	}

	@Override
	public void doDraw(Section sw) {
		sw.setForegroundColor(Color.YELLOW);
		sw.drawString(loc.getLocation().x, loc.getLocation().y + DefaultMainLayer.ANNOUNCE_OFFSET, "X");
		
		int startX = size.x-OFFSET;
		int currentY = 0;
		if(loc.getLocation().x > size.x - OFFSET) {
			startX = 0;
		}
		
		sw.setForegroundColor(Color.DEFAULT);
		if(loc.getEntity() != null){
			sw.drawString(startX, currentY, loc.getEntity().getDescription());
			currentY++;
		}
		
		for(Stackable s : loc.getStackables()) {
			sw.drawString(startX, currentY, s.getDescription());
			currentY++;
		}
		
		if(loc.getType().getDescription() != null && loc.getReveal()) {
			sw.drawString(startX, currentY, loc.getType().getDescription());
			currentY++;
		}
		
		if(currentY == 0) {
			sw.drawString(startX, currentY, "Nothing here");
		}
		
		
	}

	public GameAction getActionForKey(Key k) {
		switch (k.getKind()) {
		case NormalKey: {
			switch (k.getCharacter()) {
			case 'h':
				return new GameAction(1, 1); // Move left
			case 'j':
				return new GameAction(1, 2); // Move down
			case 'k':
				return new GameAction(1, 3); // Move up
			case 'l':
				return new GameAction(1, 4); // Move right
			case ' ':
				return new GameAction(1, 5); // Close
			default:
				return new GameAction(1, 0); // do nothing
			}
		}
		case ArrowLeft:
			return new GameAction(1, 1); // Move left
		case ArrowDown:
			return new GameAction(1, 2); // Move down
		case ArrowUp:
			return new GameAction(1, 3); // Move up
		case ArrowRight:
			return new GameAction(1, 4); // Move right
		case Escape:
			return new GameAction(1,5); //Close
		default:
			return new GameAction(1, 0); // do nothing
		}
	}

	public  void propagateAction(GameAction action) {
		if (action.getContextClassifier() != 1)
			throw new Error("Received an action for a non-gameplay context.");

		Direction dir = null;

		switch (action.getActionClassifier()) {
		case 1: //Move Left
			dir = Direction.LEFT;
			break;
		case 2: //Move Down
			dir = Direction.DOWN;
			break;
		case 3: //Move Up
			dir = Direction.UP;
			break;
		case 4: //Move Right
			dir = Direction.RIGHT;
			break;
		case 5:
			app.getLayers().pop(); //Remove this layer from stack
			return;
		case 0:
			return;
		}

		Vec2i next = loc.getLocation().plus(dir.getDelta());
		Tile[][] tiles = currentLevel.getTiles();
		if (tiles.length > next.x && next.x >= 0 && tiles[0].length > next.y && next.y >= 0)
			loc = tiles[next.x][next.y];

	}


	public  void tick(long nanosSincePreviousTick){}

}
