package edu.brown.cs.roguelike.engine.entities;

public interface Action {

	public void apply(EntityActionManager queue);

}
