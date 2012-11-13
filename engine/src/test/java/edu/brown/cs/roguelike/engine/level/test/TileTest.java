package edu.brown.cs.roguelike.engine.level.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.level.TileType;

public class TileTest {

	/**
	 * Serialize succeeds and output is non-empty
	 * 
	 * @author liam
	 * @throws IOException 
	 */
	@Test
	public void testIsSerializable() throws IOException {
		
		Tile t1 = new Tile(TileType.FLOOR, true);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		
		oos.writeObject(t1);
		oos.close();
		
		assertTrue(out.toByteArray().length > 0);
	}
	
	/**
	 * Round trip test succeeds
	 * 
	 * @author liam 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testRoundTripSerialization() throws IOException, ClassNotFoundException {
		
		Tile t1 = new Tile(TileType.WALL, true);
		t1.setPassable(false);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		
		oos.writeObject(t1);
		oos.close();
		
		byte[] raw = out.toByteArray();
		InputStream in = new ByteArrayInputStream(raw);
		ObjectInputStream ois = new ObjectInputStream(in);
		
		Object o = ois.readObject();
		ois.close();

		assertTrue(o instanceof Tile);
		
		Tile t1_ds = (Tile)o;
		
		assertEquals(t1_ds.getType(), t1.getType());
		assertEquals(t1_ds.isPassable(), t1.isPassable());
		assertEquals(t1_ds, t1);
	}

}
