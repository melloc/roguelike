package edu.brown.cs.rouguelike.engine.level;

/**
 * A tile object to make up each physical level
 * @author jte
 *
 */
public class Tile {
	
	public Tile(TileType type, boolean passable) {
		this.type = type;
		this.passable = passable;
	}
	
	private boolean passable; //
	public boolean isPassable() {return passable;}
	public void setPassable(boolean passable) {this.passable = passable;}
	
	private TileType type;
	public TileType getType() {return type;}
	public void setType(TileType type) {this.type = type;} 
}
