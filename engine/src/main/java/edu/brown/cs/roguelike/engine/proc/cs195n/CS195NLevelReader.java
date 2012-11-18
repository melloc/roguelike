package edu.brown.cs.roguelike.engine.proc.cs195n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that allows reading of a {@link LevelData} using the support code implementation. See {@link #readLevel(InputStream)}.
 * 
 * @author zdavis
 */
public final class CS195NLevelReader {

	/**
	 * Attempt to read in a {@link LevelData} from a given {@link File}, presumably a .nlf file.
	 * 
	 * @param file   the File object from which to read the level
	 * @return       a valid Level object
	 * @throws InvalidLevelException     if the level could not be read or is invalid
	 * @throws FileNotFoundException     if the given file does not exist
	 */
	public static LevelData readLevel(File file) throws InvalidLevelException, FileNotFoundException {
		return readLevel(new FileInputStream(file));
	}
	
	/**
	 * Attempt to read in a {@link LevelData} from a given {@link InputStream}, presumably from a 
	 * resource stream opened on a .nlf file. 
	 * 
	 * @param in     the InputStream from which to read the level
	 * @return       a valid Level object
	 * @throws InvalidLevelException     if the level could not be read or is invalid
	 */
	public static LevelData readLevel(InputStream in) throws InvalidLevelException {
		if (in == null)
			throw new NullPointerException();
		
		Object object;
		try {
			object = new ObjectInputStream(in).readObject();
		} catch (ClassNotFoundException e) {
			throw new InvalidLevelException("No suitable implementation of Level could be found to decode the level. Please contact a TA.", e);
		} catch (IOException e) {
			throw new InvalidLevelException("An IOException was encountered while decoding the level", e);
		}
		
		if (object == null) {
			throw new InvalidLevelException("Level was null");
		}
		
		LevelData levelData;
		try {
			levelData = (LevelData) object;
		} catch (ClassCastException e) {
			throw new InvalidLevelException("Object read was not a level", e);
		}
		
		return levelData;
	}
}

/*
 * NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS
 * NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS
 * NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS
 * NEVER EDIT THIS #     # ####### #     # ####### ######          ####### ######    ###   #######         ####### #     #   ###    #####    ###   NEVER EDIT THIS
 * NEVER EDIT THIS ##    # #       #     # #       #     #         #       #     #    #       #               #    #     #    #    #     #   ###   NEVER EDIT THIS
 * NEVER EDIT THIS # #   # #       #     # #       #     #         #       #     #    #       #               #    #     #    #    #         ###   NEVER EDIT THIS
 * NEVER EDIT THIS #  #  # #####   #     # #####   ######          #####   #     #    #       #               #    #######    #     #####     #    NEVER EDIT THIS
 * NEVER EDIT THIS #   # # #        #   #  #       #   #           #       #     #    #       #               #    #     #    #          #         NEVER EDIT THIS
 * NEVER EDIT THIS #    ## #         # #   #       #    #          #       #     #    #       #               #    #     #    #    #     #   ###   NEVER EDIT THIS
 * NEVER EDIT THIS #     # #######    #    ####### #     #         ####### ######    ###      #               #    #     #   ###    #####    ###   NEVER EDIT THIS
 * NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS
 * NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS
 * NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS NEVER EDIT THIS
 */
class LevelImpl implements LevelData,Serializable{private static final long serialVersionUID=-5597035496053375008L;private final List<EntityData>entityDatas;private final List<ConnectionData>connectionDatas;private final Map<String,String>properties;public LevelImpl(LevelData levelData){properties=Collections.unmodifiableMap(new HashMap<String,String>(levelData.getProperties()));ArrayList<ConnectionData>conns=new ArrayList<ConnectionData>(levelData.getConnections().size());for(ConnectionData c:levelData.getConnections())conns.add(new ConnectionImpl(c));connectionDatas=Collections.unmodifiableList(conns);ArrayList<EntityData>ents=new ArrayList<EntityData>(levelData.getEntities().size());for(EntityData e:levelData.getEntities())ents.add(new EntityImpl(e));entityDatas=Collections.unmodifiableList(ents);}@Override public List<EntityData>getEntities(){return entityDatas;}@Override public List<ConnectionData>getConnections(){return connectionDatas;}@Override public Map<String,String>getProperties(){return properties;}private static class EntityImpl implements EntityData,Serializable{private static final long serialVersionUID=-1091135647672249902L;private final Map<String,String>properties;private final String name,entityClass;private final List<ShapeData>shapeDatas;EntityImpl(EntityData e){name=e.getName();entityClass=e.getEntityClass();properties=Collections.unmodifiableMap(new HashMap<String,String>(e.getProperties()));List<ShapeData>ss=new ArrayList<ShapeData>(e.getShapes().size());for(ShapeData s:e.getShapes())ss.add(new ShapeImpl(s));shapeDatas=Collections.unmodifiableList(ss);}@Override public Map<String,String>getProperties(){return properties;}@Override public String getName(){return name;}@Override public String getEntityClass(){return entityClass;}@Override public List<ShapeData>getShapes(){return shapeDatas;}}private static class ShapeImpl implements ShapeData,Serializable{private static final long serialVersionUID=-1191716431901112001L;private final Type type;private final Vec2f center,min,max;private final List<Vec2f>verts;private final float radius,width,height;private final Map<String,String>properties;ShapeImpl(ShapeData s){type=s.getType();center=s.getCenter();min=s.getMin();max=s.getMax();width=s.getWidth();height=s.getHeight();properties=Collections.unmodifiableMap(new HashMap<String,String>(s.getProperties()));radius=(type==Type.CIRCLE)?s.getRadius():0;verts=(type==Type.BOX||type==Type.POLY)?Collections.unmodifiableList(new ArrayList<Vec2f>(s.getVerts())):Collections.<Vec2f>emptyList();}@Override public Type getType(){return type;}@Override public Vec2f getCenter(){return center;}@Override public Vec2f getMin(){return min;}@Override public Vec2f getMax(){return max;}@Override public List<Vec2f>getVerts(){if(type!=Type.POLY&&type!=Type.BOX)throw new UnsupportedOperationException("Tried to get the verts of a"+type);return verts;}@Override public float getRadius(){if(type!=Type.CIRCLE)throw new UnsupportedOperationException("Tried to get the radius of a"+type);return radius;}@Override public float getWidth(){return width;}@Override public float getHeight(){return height;}@Override public Map<String,String>getProperties(){return properties;}}private static class ConnectionImpl implements ConnectionData,Serializable{private static final long serialVersionUID=8494751477157651352L;private final String source,sourceOutput,target,targetInput;private final Map<String,String>properties;ConnectionImpl(ConnectionData c){source=c.getSource();sourceOutput=c.getSourceOutput();target=c.getTarget();targetInput=c.getTargetInput();properties=Collections.unmodifiableMap(new HashMap<String,String>(c.getProperties()));}@Override public String getSource(){return source;}@Override public String getSourceOutput(){return sourceOutput;}@Override public String getTarget(){return target;}@Override public String getTargetInput(){return targetInput;}@Override public Map<String,String>getProperties(){return properties;}}}