package edu.brown.cs.roguelike.engine.game;

import java.util.ArrayList;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.Application;
import edu.brown.cs.roguelike.engine.graphics.Layer;
import edu.brown.cs.roguelike.engine.graphics.Section;

public class AnnounceLayer implements Layer {

	private final static int NUM_LINES = 2;

	private ArrayList<String> announcements;
	private Application app;
	private boolean firstPage;

	public AnnounceLayer(Application app, ArrayList<String> announcements) {
		this.app = app;
		this.announcements = announcements;
		firstPage = true;
	}

	@Override
	public GameAction getActionForKey(Key k) {
		if(k.getKind() == Kind.NormalKey && k.getCharacter() == ' ') {
			return new GameAction(7,1);
		}
		else {
			app.getLayers().pop();
			Layer layerBelow = app.getLayers().peek();
			app.getLayers().push(this);
			return layerBelow.getActionForKey(k);
		}
	}

	@Override
	public void propagateAction(GameAction action) {

		if(action.getContextClassifier() == 7 && action.getActionClassifier() == 1) {
			if(isNextPage())
				loadNextPage();
			else {
				announcements.clear();
				app.getLayers().pop();
			}
		}
		else {
			if(!isNextPage()) {
				announcements.clear();
				app.getLayers().pop();
				app.getLayers().peek().propagateAction(action);
			}	
		}
	}


	private boolean isNextPage() {
		return announcements.size() - NUM_LINES > 0;
	}

	/**
	 * Moves to the next page, sets next page false if there was no more pages to display
	 */
	private void loadNextPage() {
		for(int i = 1; i <= NUM_LINES; i++) {
			if(announcements.size() > 0) {
				announcements.remove(0);
			}
		}
		firstPage = false;
	}

	@Override
	public void updateSize(Vec2i newSize) {}

	@Override
	public void doDraw(Section s) {
		int linesPrinted = 0;
		for(int i = 0; i < NUM_LINES; i++) {
			if(announcements.size() > i) {
				s.drawString(0, i, announcements.get(i));
				linesPrinted++;
			}
		}
		if(isNextPage()) {
			s.drawString(0, linesPrinted, "(Press space for more)");
		}
	}

	@Override
	public void tick(long nanosSincePreviousTick) {}

}
