package edu.brown.cs.roguelike.engine.entities;

public abstract class Combatable extends Entity{
	
	protected int HP;
	protected Stats stats;
	protected EntityActionManager manager;
	
	protected abstract void die();
	
	/**Processes an Enemy kill, gaining xp etc**/
	protected abstract void onKillEntity(Combatable combatable);
	
	/**Attacks an opponent**/
	public void attack(Combatable opp) {
		if(tryHit(opp)) {
			dealDamage(opp);
		}
	}
	
	/**Does a roll to see whether the this can hit opp**/
	private boolean tryHit(Combatable opp) {
		return Math.random() < (this.stats.getAttack() / (this.stats.getAttack() + opp.stats.getDefense()));
	}
	
	/**Deals damage to opp**/
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
	
}
