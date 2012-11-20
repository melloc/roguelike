package edu.brown.cs.roguelike.engine.level.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;

public class LevelTest {
	
	private Level l1;
	
	@Before
	public void setUp() throws ConfigurationException {
		BSPLevelGenerator rg = new BSPLevelGenerator();
		l1 = rg.generateLevel(new Vec2i(100,150));
	}
	
	@After
	public void tearDown() {
		l1 = null;
	}
	
	/**
	 * Serialize succeeds and output is non-empty
	 * 
	 * @author liam
	 * @throws IOException
	 */
	@Test
	public void testIsSerializable() throws IOException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		
		oos.writeObject(l1);
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
	public void testRoundTripSerialization() 
			throws IOException, ClassNotFoundException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		
		oos.writeObject(l1);
		oos.close();
		
		byte[] raw = out.toByteArray();
		InputStream in = new ByteArrayInputStream(raw);
		ObjectInputStream ois = new ObjectInputStream(in);
		
		Object o = ois.readObject();
		ois.close();

		assertTrue(o instanceof Level);
		
		Level l1_ds = (Level)o;
		
		assertEquals(l1_ds, l1);
		// TODO(liam) figure out why this assertEquals is deprecated...
		assertEquals(l1_ds.getTiles(), l1.getTiles());
		assertEquals(l1_ds.getRooms(), l1.getRooms());
		assertEquals(l1_ds.getHallways(), l1.getHallways());
		
		
	}

}
