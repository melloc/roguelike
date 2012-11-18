package edu.brown.cs.roguelike.engine.proc.cs195n;

import java.util.List;
import java.util.Map;

/**
 * A generic interface for a level that contains entities and connections.
 * 
 * You shouldn't implement this interface or try to construct a LevelData. Instead, use
 * {@link CS195NLevelReader} to read a LevelData from a file, then traverse the LevelData you get
 * and convert it to your game's classes.
 * 
 * <strong>DO NOT MODIFY UNDER ANY CIRCUMSTANCES.</strong>
 * 
 * @author zdavis
 */
public interface LevelData {
	/**
	 * @return a list of all entities in the level
	 */
	List<? extends EntityData> getEntities();
	
	/**
	 * @return a list of all connections in the level
	 */
	List<? extends ConnectionData> getConnections();
	
	/**
	 * @return a map of all the properties of the level
	 */
	Map<String, String> getProperties();
	
	/**
	 * A generic interface for a connection between two entities.
	 * 
	 * @author zdavis
	 */
	public interface ConnectionData {
		/**
		 * The source of the connection is most frequently interpreted as the name of an entity, e.g. "button1".
		 * 
		 * @return the source of the connection
		 */
		String getSource();
		
		/**
		 * The source output is any string which identifies the event that should activate this connection, e.g. "onPressed".
		 * 
		 * @return the output of the source that activates this connection
		 */
		String getSourceOutput();

		/**
		 * The target of the connection is most frequently interpreted as the name of an entity, e.g. "door1".
		 * 
		 * @return the target of the connection
		 */
		String getTarget();
		
		/**
		 * The target input is any string which identifies what the target should do as a result of this connection being activated, e.g. "Close".
		 * 
		 * @return the action which the target should perform when the connection is activated
		 */
		String getTargetInput();
		
		/**
		 * @return a map of all the properties of the connection
		 */
		Map<String, String> getProperties();
	}
	
	/**
	 * A generic interface for an entity within a level/world.
	 * 
	 * @author zdavis
	 */
	public interface EntityData {
		/**
		 * @return a map of all the properties of the entity
		 */
		Map<String, String> getProperties();
		
		/**
		 * The name of the entity can be any string. It is most frequently used as the source or target in a connection. 
		 * 
		 * @return the name of this entity
		 */
		String getName();
		
		/**
		 * The "class" of an entity is most frequently interpreted as the actual name of the Java class the entity should be, e.g. "Player".
		 * 
		 * @return the class of this entity
		 */
		String getEntityClass();
		
		/**
		 * @return a list of shapes associated with this entity 
		 */
		List<? extends ShapeData> getShapes();
	}

	/**
	 * A generic interface for a shape within an entity.
	 * 
	 * @author zdavis
	 */
	public interface ShapeData {
		/**
		 * @return the {@link Type} of the shape (what kind of shape it is).
		 */
		Type getType();
		
		/**
		 * CIRCLE: the center of the circle
		 * POLY: the center of the AAB of the polygon
		 * BOX: the center of the box
		 * 
		 * @return the point which is the center of the bounding AAB of the shape
		 */
		Vec2f getCenter();

		/**
		 * CIRCLE: the radius of the circle
		 * 
		 * @return the radius of the shape
		 * @throws UnsupportedOperationException	if this shape is not a CIRCLE
		 */
		float getRadius();
		
		/**
		 * BOX: the vertices of the box in counter-clockwise order (clockwise if screen coordinates not math)
		 * POLY: the vertices of the polygon in counter-clockwise order (clockwise if screen coordinates not math)
		 * 
		 * @return a list of the vertices of the shape in counter-clockwise order
		 * @throws UnsupportedOperationException	if this shape is not a BOX or POLY
		 */
		List<Vec2f> getVerts();
		
		/**
		 * CIRCLE: the point (center.x - radius, center.y - radius)
		 * POLY: the "min" corner of the AAB of the polygon
		 * BOX: the "min" corner of the box
		 * 
		 * @return the point on the bounding AAB of the shape with the least x and y coordinates (usually upper left)
		 */
		Vec2f getMin();
		
		/**
		 * CIRCLE: the point (center.x + radius, center.y + radius)
		 * POLY: the "max" corner of the AAB of the polygon
		 * BOX: the "max" corner of the box
		 * 
		 * @return the point on the bounding AAB of the shape with the greatest x and y coordinates (usually lower right)
		 */
		Vec2f getMax();
		
		/**
		 * @return the width of the bounding AAB of the shape
		 */
		float getWidth();
		
		/**
		 * @return the height of the bounding AAB of the shape
		 */
		float getHeight();
		
		/**
		 * @return a map of all the properties of the shape
		 */
		Map<String, String> getProperties();
		
		/**
		 * An enumeration of the types of shapes that a {@link ShapeData} can represent.
		 * 
		 * The only supported types are Circle, Poly, and Box. Points can be implemented as Circles with a "point: true" property.
		 * 
		 * @author zdavis
		 */
		public enum Type {
			CIRCLE, POLY, BOX;
		}
	}
	
}
