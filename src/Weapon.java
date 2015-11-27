import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The weapons class.
 * Controls the range of the weapon 
 * and the damage it inflicts.
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Weapon implements Item {
	
	private int x, y;
	private double range;
	private double damage;
	private int cooldown;
	private boolean hasWeapon;
	private String name;
	private Image sprite;
	private int ammo;
	
	Animation[] animations = new Animation[4];
	
	public Weapon(float range, int cooldown, float damage, String name){
		this.range = range;
		this.damage = damage;
		hasWeapon = false;	
		this.name = name;
		this.cooldown = cooldown;
		if(name == "Gun"){
			ammo = 50;
		}else if(name == "Shotgun"){
			ammo = 20;
		}
	}

	/**
	 * Use the weapon the way it was 
	 * intended to be used; to kill monsters.
	 * @param m The monster we want to kill.
	 * @param f The player's position on the map.
	 * @throws SlickException 
	 */
	public void use(Monster m, float[] f, Player p) throws SlickException {
		if(inRange(f, m, p)){
				m.setHealth(damage);
		}
	}
	
	/**
	 * Determines if the monster is close enough to hit.
	 * @param f The player's position on the map.
	 * @param m The monster we are trying to kill.
	 * @return boolean True if the monster is within
	 * range, False if it's not.
	 */
	private boolean inRange(float[] f, Monster m, Player p){
		float x = f[0];
		float y = f[1];
		float[] o = m.getMonsterPos();
		float u = o[0] + f[0] - 320;
		float v = o[1] + f[1] - 320;
		if(p.getDirection() == "west"){
			if((x-u <= range && x > u-1) && (v >= y-10 && v <= y+35  )){
				float[] blocked = new float[(int)range];
				for(int i = 0; i < range; i++){
					blocked[i] = x - i;
				}
				for(float a : blocked){
					if(p.getCurrentRoom().isBlocked(a, y)){
						System.out.println("Blocked (west)");
						return false;
					}
				}
				return true;
			}
		}else if(p.getDirection() == "east"){
			if((u-x <= range && u > x-1) && (v >= y-10 && v <= y+35)){
				float[] blocked = new float[(int)range];
				for(int i = 0; i < range; i++){
					blocked[i] = x + i;
				}
				for(float a : blocked){
					if(p.getCurrentRoom().isBlocked(a, y)){
						System.out.println("Blocked (east)");
						return false;
					}
				}
				return true;
			}
		}else if(p.getDirection() == "north"){
			if((y-v <= range && y > v-1) && (u >= x-10 && u <= x+35)){
				float[] blocked = new float[(int)range];
				for(int i = 0; i < range; i++){
					blocked[i] = y - i;
				}
				for(float a : blocked){
					if(p.getCurrentRoom().isBlocked(x, a)){
						System.out.println("Blocked (north)");
						return false;
					}
				}
				return true;
			}
		}else if(p.getDirection() == "south"){
			if((v-y <= range && v > y-1) && (u >= x-10 && u <= x+35)){
				float[] blocked = new float[(int)range];
				for(int i = 0; i < range; i++){
					blocked[i] = y + i;
				}
				for(float a : blocked){
					if(p.getCurrentRoom().isBlocked(x, a)){
						System.out.println("Blocked (south)");
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * drop the weapon.
	 */
	public void drop(){
		hasWeapon = false;
	}
	
	/**
	 * pick up the weapon
	 */
	@Override
	public void pickUp(){
		hasWeapon = true;
	}
	
	/**
	 * Check whether or not the player 
	 * has the weapon.
	 * @return boolean True if the player has
	 * the weapon, False if he/she does not.
	 */
	public boolean checkHasWeapon(){
		return hasWeapon;
	}
	
	/**
	 * @return String The name of the weapon.
	 */
	public String getName(){
		return name;
	}


	public void setHasWeapon(boolean b){
		hasWeapon = b;
	}

	public int[] getPos() {
		int[] i = {x, y};
		return i;
	}
	
	public void initItem() throws SlickException{
		Image weapon = null;
		Image[] attackWest = null;
		Image[] attackEast = null;
		Image[] attackNorth = null;
		Image[] attackSouth = null;
		if(name.equals("Knife")){
			weapon = new Image("data/gun.png");
			Image[] west = {new Image("data/hero_west_attack_knife.png"),
					new Image("data/hero_west_attack_knife2.png")};
			Image[] east = {new Image("data/hero_east_attack_knife.png"),
					new Image("data/hero_east_attack_knife2.png")};
			Image[] north = {new Image("data/hero_north_attack_knife.png"),
					new Image("data/hero_north_attack_knife2.png")};
			Image[] south = {new Image("data/hero_south_attack_knife.png"),
					new Image("data/hero_south_attack_knife2.png")};
			attackWest = west;
			attackEast = east;
			attackNorth = north;
			attackSouth = south;
		}else if(name.equals("Gun")){
			weapon = new Image("data/gun.png");
			Image[] west = {new Image("data/hero_west_attack_gun.png"), 
					new Image("data/hero_west_attack_gun2.png")};
			Image[] east = {new Image("data/hero_east_attack_gun.png"), 
					new Image("data/hero_east_attack_gun2.png")};
			Image[] north = {new Image("data/hero_north_attack_gun.png"), 
					new Image("data/hero_north_attack_gun2.png")};
			Image[] south = {new Image("data/hero_south_attack_gun.png"), 
					new Image("data/hero_south_attack_gun2.png")};
			attackWest = west;
			attackEast = east;
			attackNorth = north;
			attackSouth = south;
		}else if(name.equals("Shotgun")){
			weapon = new Image("data/shotgun.png");
			Image[] west = {new Image("data/hero_west_attack_shotgun.png"), 
					new Image("data/hero_west_attack_shotgun2.png")};
			Image[] east = {new Image("data/hero_east_attack_shotgun.png"), 
					new Image("data/hero_east_attack_shotgun2.png")};
			Image[] north = {new Image("data/hero_north_attack_shotgun.png"), 
					new Image("data/hero_north_attack_shotgun2.png")};
			Image[] south = {new Image("data/hero_south_attack_shotgun.png"), 
					new Image("data/hero_south_attack_shotgun2.png")};
			attackWest = west;
			attackEast = east;
			attackNorth = north;
			attackSouth = south;
		}
		sprite = weapon;
		int[] duration = {300, 100};
		animations[0] = new Animation(attackWest, duration, false);
		animations[1] = new Animation(attackEast, duration, false);
		animations[2] = new Animation(attackNorth, duration, false);
		animations[3] = new Animation(attackSouth, duration, false);
	}

	/**
	 * Renders the image of the weapon on the map.
	 * @param x, y The coordinates of the
	 * weapon on the map.
	 */
	@Override
	public void renderItem(Player player) throws SlickException {
		sprite.draw(-player.getPlayerPos()[0] + 320 + x,
				-player.getPlayerPos()[1] + 320 + y);
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getCooldown(){
		return cooldown;
	}
	
	public Animation[] getAnimations(){
		return animations;
	}
	
	public void setAmmo(int value){
		if(name == "Knife"){
			return;
		}
		ammo += value;
	}
	
	public boolean hasAmmo(){
		if(ammo > 0 || name == "Knife"){
			return true;
		}
		return false;
	}
	
	public int getAmmo(){
		return ammo;
	}
}

