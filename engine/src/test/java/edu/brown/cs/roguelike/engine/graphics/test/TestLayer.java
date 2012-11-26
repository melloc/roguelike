package edu.brown.cs.roguelike.engine.graphics.test;

import org.junit.Test;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.Layer;
import edu.brown.cs.roguelike.engine.graphics.Section;

public class TestLayer implements Layer {

	Vec2i size = null;
	String str = "Not set";
	
	@Override
	public GameAction getActionForKey(Key k) {
		switch (k.getKind()) {
		case NormalKey: return new GameAction(0, 1);
		default: return new GameAction(0, 2);
		}
	}

	@Override
	public void propagateAction(GameAction action) {
		if (action.getContextClassifier() != 0)
			throw new Error("Received an action for a non-testing context.");
		
		switch (action.getActionClassifier()) {
		case 1: str = "You have pressed a normal key..."; break;
		case 2: str = "You did not press a normal key..."; break;
		default: str = "This shouldn't happen. Received a " + action.getActionClassifier() + "...";
		}
	}

	@Override
	public void updateSize(Vec2i newSize) {
		this.size = newSize;
	}

	@Override
	public void doDraw(Section writer) {
        //writer.drawString(0,0,str);
        int x = (this.size.x - str.length()) / 2;
        if (x < 0) x = 0;
        int y = this.size.y / 2;
        if (y < 0) y = 0;
		writer.drawString(x, y, str); 
	}

	@Override
	public void tick(long nanosSincePreviousTick) {
		
	}

    @Test
    public void fakeTest() {
        // This exists so that we can create an interactive test.
    }

}
