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

import edu.brown.cs.roguelike.engine.level.Room;

public class RoomTest {
	
	private Room r1;
	private Room r2;
	private Room r3;
	
	@Before
	public void setUp() {
		r1 = new Room(new Vec2i(0,0), new Vec2i(10,10));
		r2 = new Room(new Vec2i(15,15), new Vec2i(25, 25));
		r3 = new Room(new Vec2i(30,30), new Vec2i(40,40));
		
		r1.addRoom(r2);
		r2.addRoom(r1);
		r1.addRoom(r3);
		r3.addRoom(r2);
	}
	
	@After
	public void tearDown() {
		r1 = null;
		r2 = null;
		r3 = null;
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
		
		oos.writeObject(r1);
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
		
		oos.writeObject(r1);
		oos.close();
		
		byte[] raw = out.toByteArray();
		InputStream in = new ByteArrayInputStream(raw);
		ObjectInputStream ois = new ObjectInputStream(in);
		
		Object o = ois.readObject();
		ois.close();

		assertTrue(o instanceof Room);
		
		Room r1_ds = (Room)o;
		
		assertEquals(r1_ds.getConnectedRooms(), r1.getConnectedRooms()); 
	}
	

}
