package edu.brown.cs.roguelike.engine.entities;

public class Attack {
	/**The entity that the performed the attack**/
	public final Combatable opponent; 
	
	/**The Attack's Power**/
	public final int power;
	
	/**Attack Effects**/
	//TODO: Effects
	
	public Attack(Combatable o, int power) {
		this.opponent = o;
		this.power = power;
	}
	
}
