package edu.brown.cs.roguelike.engine.level.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import edu.brown.cs.roguelike.engine.level.Hallway;
import edu.brown.cs.roguelike.engine.level.Room;

public class HallwayTest {
	
	private Hallway h1;
	private Hallway h2;
	private Room r1;
	private Room r2;
	
	@Before
	public void setUp() {
		h1 = new Hallway(new Vec2i(0,0), new Vec2i(1,1));
		h2 = new Hallway(new Vec2i(2,2), new Vec2i(0,2));
		r1 = new Room(new Vec2i(0,0), new Vec2i(10,10));
		r2 = new Room(new Vec2i(10,10), new Vec2i(20,20));
		
		r1.connectToHallway(h1);
		r2.connectToHallway(h1);
		r1.connectToHallway(h2);
		r2.connectToHallway(h2);
	}
	
	@After
	public void tearDown() {
		h1 = null;
		h2 = null;
		r1 = null;
		r2 = null;
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
		
		oos.writeObject(r2);
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
		
		oos.writeObject(h2);
		oos.close();
		
		byte[] raw = out.toByteArray();
		InputStream in = new ByteArrayInputStream(raw);
		ObjectInputStream ois = new ObjectInputStream(in);
		
		Object o = ois.readObject();
		ois.close();

		assertTrue(o instanceof Hallway);
		
		Hallway h2_ds = (Hallway)o;
		
		assertEquals(h2_ds, h2); 
	}
}
