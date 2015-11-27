import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * The inventory class keeps a record of
 * the items in the player's possession.
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Inventory {
	private HashSet<Weapon> weapons;
	private ArrayList<HealthPack> healthPack;

	/**
	 * Constructor for the Inventory-class.
	 */
	public Inventory(){
		weapons = new HashSet<Weapon>();
		healthPack = new ArrayList<HealthPack>();
	}
	
	/**
	 * Add an HealthPack-object to the healthpack arraylist
	 * @param a The object we want to add.
	 */
	public void addHealthPack(HealthPack h){
		healthPack.add(h);
	}
	
	/**
	 * add a weapon to the weapons hashset.
	 * @param w
	 */
	public void addWeapon(Weapon w){
			weapons.add(w);
	}
	
	public boolean hasHealthPack(){
		if(healthPack.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public HealthPack getHealthPack(){
		if(hasHealthPack()){
			Iterator<HealthPack> h = healthPack.iterator();
			return h.next();
		}
		return null;
	}
	
	public Weapon getWeapon(String s){
		Iterator<Weapon> w = weapons.iterator();
		while(w.hasNext()){
			Weapon weapon = w.next();
			if(weapon.getName().equals(s)){
				return weapon;
			}
		}
		return null;
	}
	
	public void haveWeapon(Weapon weapon){
		weapon.pickUp();
	}
	
	public void dropWeapon(Weapon weapon){
		weapon.drop();
	}


	public HashSet<Item> getItems() {
		HashSet<Item>items = new HashSet<Item>();
		if(hasHealthPack()){
			for(HealthPack h: healthPack){
				items.add(h);
			}
		}
		if(!weapons.isEmpty()){
			for(Weapon w: weapons){
				items.add(w);
			}
		}
		return items;
	}


	public void removeItem(Item item) {
		if(item.getClass() == Weapon.class){
			weapons.remove((Weapon)item);
		}else if(item.getClass() == HealthPack.class){
			healthPack.remove((HealthPack)item);
		}
	}
}
