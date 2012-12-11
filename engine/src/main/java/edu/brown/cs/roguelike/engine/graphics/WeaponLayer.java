package edu.brown.cs.roguelike.engine.graphics;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EquipType;
import edu.brown.cs.roguelike.engine.entities.ItemType;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.entities.Weapon;
import edu.brown.cs.roguelike.engine.level.Level;

public class WeaponLayer<A extends Application> extends UseItemLayer<A> {

	public WeaponLayer(A app, Vec2i size, Level currentLevel) {
		super(app, size, currentLevel);
	}

	@Override
	public void doDraw(Section s) {
		s.drawString(0, 0, "Wield what?");
	}

	@Override
	protected void applyItemEffect(Stackable item, EntityActionManager target) {
		if(item.getType() == ItemType.WEAPON) {
			Weapon w = (Weapon) item;
			
			Weapon oldWeap = (Weapon) target.getEntity().getEquipment().get(EquipType.WEAPON); 
			if(oldWeap != null) {
				oldWeap.getUnwieldAction().apply(target);
				target.getEntity().getInventory().add(oldWeap);
			}
			w.getWieldAction().apply(target);
			target.getEntity().getEquipment().put(EquipType.WEAPON, w);
			target.getEntity().getInventory().remove(item);
		}
	}

}

