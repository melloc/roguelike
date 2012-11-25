package edu.brown.cs.roguelike.engine.entities;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;

public abstract class Combatable extends Entity implements Movable {
	
	protected int HP;
	protected int team;
	protected Stats stats;
	protected EntityActionManager manager;
	protected Tile location;
	
	/** #Dies **/
	protected abstract void die();
	
	/**Processes an Enemy kill, gaining xp etc**/
	protected abstract void onKillEntity(Combatable combatable);
	
	/**Attacks an opponent**/
	public void attack(Combatable opp) {
		if(tryHit(opp)) {
			dealDamage(opp);
		}
	}
	
	/**
	 * Does a roll to see whether the this can hit opp
	 */
	private boolean tryHit(Combatable opp) {
		return Math.random() < (((double) this.stats.getAttack()) / (this.stats.getAttack() + opp.stats.getDefense()));
	}
	
	/**
	 * Deals damage to opponent.
	 */
	private void dealDamage(Combatable opp) {
		int attackPower = (int) Math.round(Math.random()*stats.attack);
		
		System.out.println(attackPower);
		
		opp.takeDamage(new Attack(this,attackPower));
	}

	/**
	 * Takes damage, applies attack effects, checks to see if dead
	 */
	public void takeDamage(Attack attack) {
		changeHP(-attack.power);
		if(HP <= 0) {
			die();
			attack.opponent.onKillEntity(this);
		}
		//TODO: Attack effects
	}

	/**
	 * @return the hP
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
	}

	/**
	 * @return the manager
	 */
	public EntityActionManager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(EntityActionManager manager) {
		this.manager = manager;
	}


	public void changeHP(int delta) {
		this.HP += delta;
	}
	
	/**
	 * Gets the {@link Combatable}'s location on the map.
	 * @return The location of this {@link Combatable} on the {@link Level}
	 */
	@Override
	public Tile getLocation() {
		return location;
	}

	/**
	 * Sets the {@link Combatable}'s location on the map.
	 * @param location The new location of this {@link Combatable}.
	 */
	public void setLocation(Tile location) {
		this.location = location;
	}

	public void move(Direction dir) {
		Vec2i next = location.getLocation().plus(dir.getDelta());
		Tile[][] tiles = location.getLevel().getTiles();
		Tile nextTile = null;
		if (tiles.length > next.x && next.x >= 0 && tiles[0].length > next.y && next.y >= 0)
			nextTile = tiles[next.x][next.y];
		if (nextTile != null && nextTile.isPassable()) {
			location.setEntity(null);
			nextTile.setEntity(this);
		} else if(nextTile != null && nextTile.getEntity() != null && (nextTile.getEntity() instanceof Combatable)) {
			Combatable opp = (Combatable) nextTile.getEntity();
			if(opp.team != this.team) {
				this.attack(opp);
			}
		}

	}

	public void moveTo(Tile tile) {

	}
}
