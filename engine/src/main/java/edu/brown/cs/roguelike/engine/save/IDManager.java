package edu.brown.cs.roguelike.engine.save;


/**
 * Responsible for generating globally unique ids and
 * maintaining hygiene 
 * 
 * @author lelberty
 *
 */
public class IDManager {

	private static long gid = 0;
	
	/**
	 * Should be used when loading objects, and called
	 * by every object that is loaded. This will guarantee
	 * that the next id will be the maximum loaded id +1 
	 * @param gid
	 */
	public static void update(long id) {
		gid = Math.max(gid, id);
	}
	
	/**
	 * Current gid is in use (with the exception of gid 0), 
	 * so increment gid before returning;
	 * @return
	 */
	public static long getNext() {
		gid++;
		return gid;
	}
}
