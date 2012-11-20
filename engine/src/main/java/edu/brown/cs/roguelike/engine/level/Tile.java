package edu.brown.cs.roguelike.engine.level;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.entities.Entity;
import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;
import static edu.brown.cs.roguelike.engine.level.TileType.*;

/**
 * A tile object to make up each physical level
 *
 * @author jte
 *
 */
public class Tile implements Saveable, Drawable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -4972149313921847289L;

	public Tile(TileType type, boolean passable) {
		this.type = type;
		this.passable = passable;
	}

	private Entity entity;

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		entity.setLocation(this);
		this.entity = entity;
	}

	private boolean passable; //

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	private TileType type;

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	/*** BEGIN Saveable ***/

	private long id;

	/**
	 * init block for assigning id
	 */
	{
		this.id = IDManager.getNext();
	}

	private void writeObject(ObjectOutputStream os) throws IOException {
		os.defaultWriteObject();
	}

	private void readObject(ObjectInputStream os) throws IOException,
			ClassNotFoundException {
		os.defaultReadObject();
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (id == other.id) {
			return true;
		}
		return false;
	}

	public char getCharacter() {
		return getCurrent().getCharacter();
	}

	public Color getColor() {
		return getCurrent().getColor();
	}

	protected Drawable getCurrent() {
		if (this.entity != null)
			return entity;
		else
			return getType();
	}

	/*** END Saveable ***/

}
