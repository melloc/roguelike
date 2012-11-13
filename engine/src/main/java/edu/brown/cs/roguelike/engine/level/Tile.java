package edu.brown.cs.roguelike.engine.level;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A tile object to make up each physical level
 * @author jte
 *
 */
public class Tile implements Serializable {
	
	/**
	 * Generated 
	 */
	private static final long serialVersionUID = -4972149313921847289L;
	
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
	
	
	
	
	/*** BEGIN Serialization ***/

	
	/**
	 * Generated hashCode
	 * 
	 * @author liam
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (passable ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	/**
	 * Generated equals
	 * 
	 * @author liam
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (passable != other.passable)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	/**
	 * Custom writeObject
	 * @param os the ObjectOutputStream
	 * @throws IOException 
	 */
	private void writeObject(ObjectOutputStream os) throws IOException {
		os.defaultWriteObject();
	}

	/**
	 * Custom readObject
	 * @param os the ObjectInputStream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream os) 
			throws IOException, ClassNotFoundException {
		os.defaultReadObject();
	}
	
	/*** END Serialization ***/
	
}
