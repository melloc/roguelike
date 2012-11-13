package edu.brown.cs.roguelike.engine.proc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.level.Hallway;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.proc.cs195n.Application;

/**
 * A tester for level generation
 * @author jte
 *
 */


public class LevelGenTester extends Application {

	private final int SIZEX = 80;
	private final int SIZEY = 60;

	RoomGenerator rg;
	Level level;
	int scalex;
	int scaley;
	private boolean drawn = false;

	public LevelGenTester(String title, boolean fullscreen) {
		super(title, fullscreen);
		rg = new RoomGenerator();
		level = rg.generateLevel(new Vec2i(SIZEX,SIZEY));
		scalex = DEFAULT_WINDOW_SIZE.x / SIZEX;
		scaley =  DEFAULT_WINDOW_SIZE.y / SIZEY;
	}

	public static void main(String[] args) {
		LevelGenTester test = new LevelGenTester("DAT LEVELGEN!", false);
		test.startup();
	}

	@Override
	protected void onDraw(Graphics2D g) {

		//if(!drawn)
		//{
			for(int i = 0; i<level.tiles.length; i++) {
				for(int j = 0; j<level.tiles[0].length; j++) {
					Tile t = level.tiles[i][j];
					Rectangle2D rect = new Rectangle2D.Float(i*scalex,j*scaley,(i+1)*scalex,(j+1)*scaley);
					if(t.isPassable()) {
						g.setColor(Color.gray);
					}
					else {
						g.setColor(Color.black);
					}
					g.fill(rect);
					g.setColor(Color.LIGHT_GRAY);
					g.draw(rect);
				}
			}
			for(Room r : level.getRooms()) {
				Rectangle2D rect = new Rectangle2D.Float(r.min.x*scalex,r.min.y*scaley,(r.max.x - r.min.x + 1)*scalex,(r.max.y - r.min.y + 1)*scaley);
				g.setColor(Color.green);
				g.draw(rect);
			}
			for(Hallway h : level.getHallways()) {
				int minX  = Math.min(h.startTile.x, h.endTile.x);
				int minY  = Math.min(h.startTile.y, h.endTile.y);
				int width = Math.abs(h.startTile.x- h.endTile.x) + 1;
				int height = Math.abs(h.startTile.y- h.endTile.y) + 1;
				Rectangle2D rect = new Rectangle2D.Float(minX*scalex,minY*scaley,width*scalex,height*scaley);

				g.setColor(Color.red);
				g.draw(rect);
			}
			
			drawn = true;
			
			
		//}
	}

	@Override
	protected void onKeyTyped(KeyEvent e) {
	}

	@Override
	protected void onKeyPressed(KeyEvent e) {
		level = rg.generateLevel(new Vec2i(SIZEX,SIZEY));
		drawn = false;
	}

	@Override
	protected void onKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResize(Vec2i newSize) {
		// TODO Auto-generated method stub

	}

}
