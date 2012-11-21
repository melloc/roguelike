package edu.brown.cs.roguelike.engine.entities;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;

public abstract class Combatable extends Entity implements Movable {
	
	protected int HP;
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
		return Math.random() < (this.stats.getAttack() / (this.stats.getAttack() + opp.stats.getDefense()));
	}
	
	/**
	 * Deals damage to opponent.
	 */
	private void dealDamage(Combatable opp) {
		int attackPower = (int) Math.round(Math.random()*stats.attack);
		opp.takeDamage(new Attack(this,attackPower));
	}

	/**
	 * Takes damage, applies attack effects, checks to see if dead
	 */
	public void takeDamage(Attack attack) {
		changeHP(attack.power);
		if(HP <= 0) {
			die();
			attack.opponent.onKillEntity(this);
		}
		//TODO: Attack effects
	}

	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
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
	 * @return the location
	 */
	@Override
	public Tile getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
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
		}

	}

	public void moveTo(Tile tile) {

	}
}
