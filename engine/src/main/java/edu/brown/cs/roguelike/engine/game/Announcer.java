package edu.brown.cs.roguelike.engine.game;

import java.util.ArrayList;

import edu.brown.cs.roguelike.engine.graphics.Application;


public class Announcer {
	private final static Announcer instance = new Announcer();
	public static Announcer getAnnouncer() {return instance;}
	
	private ArrayList<String> announcements;
	private Announcer() {
		announcements = new ArrayList<String>();
	}
	
	/**Adds the announcement to the announcement queue
	 * 
	 * @param s - The announcement to add
	 */
	public static void announce(String s){
		getAnnouncer().announcements.add(s);
	}
	
	public void display(Application app) {
		app.getLayers().push(new AnnounceLayer(app, announcements));
	}
	
	public static void displayAnnouncements(Application app) {
		if(getAnnouncer().announcements.size() > 0)
			getAnnouncer().display(app);
	}
}
