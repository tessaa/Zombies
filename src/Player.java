import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The player class. Handles everything about 
 * the player and the player's movements.
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Player implements Person{
	
	// Inventory of all the player's Items.
	public Inventory inventory;
	
	// The player's current weapon of choice.
	private Weapon currentWeapon;
	
	// The direction the player is currently facing.
	private Person.direction currentDirection;
	
	// The different animations
	private Animation sprite, north, south, east, west, wAttack,
						eAttack, nAttack, sAttack;
	
	// The current position of the player.
	private float x = 576f, y = 576f;
	
	// The room/map the player is in at the moment.
	private Room currentRoom;
	
	// The health of the player
	public double health;
	
	// The game we are playing
	private StateBasedGame SB;
	
	
	// The size of the playersprite
	private static final int PSIZE = 24;
	
	// Stores the points gained by the
	// last monster killed by the player.
	public int killValue;
	
	// The current score
	public float score = 0;
	
	// The direction the player is facing
	private String direction;
	
	/**
	 * Constructor for the player class.
	 * @param room The room that the player is in.
	 */
	public Player(Room room){
		inventory = new Inventory();
		currentRoom = room;
		// Decides whether or not the player
		// has a weapon in his or her possession
		// at the start of the game.
		createInventory();
		// The initial health-status of the player,
		health = 100;
	}

	@Override
	/**
	 * Attack any monster close to the player.
	 */
	public void attack() throws SlickException{
		if(direction == "west"){
			sprite = wAttack;
		}else if(direction == "east"){
			sprite = eAttack;
		}else if(direction == "north"){
			sprite = nAttack;
		}else if(direction == "south"){
			sprite = sAttack;
		}
		currentWeapon.setAmmo(-1);
		for(Monster m: currentRoom.getMonsters()){
			if(m == null){
				continue;  
			}
			if(m.playerClose()){
				attackMonster(m);
			}
		}
	}

	/**
	 * Attack the monster who is trying to kill you.
	 * @param m The monster we want to attack.
	 * @throws SlickException 
	 */
	private void attackMonster(Monster m) throws SlickException {
		currentWeapon.use(m, getPlayerPos(), this);
	}

	/**
	 * End the game when the player dies.
	 */
	@Override
	public void die() {
		SB.enterState(Game.LOSESCREENSTATE);
	}
	
	/**
	 * Moves the player in different directions 
	 * depending on input from the user.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing.
	 * @param d The direction the player is
	 * going to move.
	 * @param delta How far the player is set to move.
	 */
	@Override
	public boolean move(GameContainer container, StateBasedGame sb, Person.direction d, int delta) {
		currentDirection = d;
		switch(currentDirection){
		// Moves the player up. Changes the image of the player
		// to suit that direction.
		case UP:
			direction = "north";
			sprite = north;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x, y - delta * 0.1f)
					&& !currentRoom.isBlocked(x + PSIZE, y -delta * 0.1f)) {
				y -= delta * 0.1f;
			}
			break;
		// Moves the player down. Changes the image of the player
		// to suit that direction.
		case DOWN:
			direction = "south";
			sprite = south;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x, y + PSIZE + delta * 0.1f)
					&& !currentRoom.isBlocked(x + PSIZE, y + PSIZE - 10 + delta + 0.1f)) {
				y += delta * 0.1f;
			}
			break;
		// Moves the player to the left. Changes the image of the player
		// to suit that direction.
		case LEFT:
			direction = "west";
			sprite = west;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x - delta * 0.1f, y)
					&& !currentRoom.isBlocked(x -delta * 0.1f, y + PSIZE)) {
				x -= delta * 0.1f;
			}
			break;
		// Moves the player to the right. Changes the image of the player
		// to suit that direction.
		case RIGHT:
			direction = "east";
			sprite = east;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x + PSIZE + delta * 0.1f, y)
					&& !currentRoom.isBlocked(x + PSIZE + delta * 0.1f, y + PSIZE)) {
				x += delta * 0.1f;
			}
		    break;
		}
		if (currentRoom.isExit(x, y)){
			GamePlayState.won = true;
			GamePlayState.difficulty++;
			sb.enterState(Game.WINSCREENSTATE);
		}
		
		Item i = currentRoom.standOnItem(x, y);
		if(i != null){
			if(i instanceof Weapon){
				String iN = i.getName();
				if(inventory.getWeapon(iN) != null){
					if(iN == "Gun"){
						inventory.getWeapon(iN).setAmmo(50);
					}
					if(iN == "Shotgun"){
						inventory.getWeapon(iN).setAmmo(20);
					}
					
				}else{
					inventory.addWeapon((Weapon)i);
					inventory.haveWeapon((Weapon)i);
				}
			}
			if(i instanceof HealthPack){
				HealthPack h = (HealthPack) i;
				h.use(this);
			}
		}
		return true;
	}
	
	/**
	 * Switches the weapon that the player uses
	 * to kill the evil monsters.
	 * @param w The weapon the user wants to switch to.
	 */
	public void switchWeapon(String weapon){
		Weapon w = inventory.getWeapon(weapon);
		if(w != null && w.checkHasWeapon()){
			currentWeapon = w;
			wAttack = w.getAnimations()[0];
			eAttack = w.getAnimations()[1];
			nAttack = w.getAnimations()[2];
			sAttack = w.getAnimations()[3];
		}	
	}
	
	/**
	 * Puts the different weapons in a hashmap and 
	 * declares whether or not the player actually has it.
	 */
	private void createInventory(){
		inventory.addWeapon(new Weapon(10, 500, -10, "Knife"));
		inventory.haveWeapon(inventory.getWeapon("Knife"));
	}
	
	/**
	 * Initializes the player with all the different 
	 * images that is to be shown during the game.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing
	 * @throws SlickException
	 */
	public void initPlayer(GameContainer container, StateBasedGame sb) throws SlickException {
		SB = sb;
		inventory.getWeapon("Knife").initItem();
		switchWeapon("Knife");
		
		// Puts all the images in an array.
		Image[] movementNorth = {new Image("data/hero_north.png"), new Image("data/hero_north2.png")};
		Image[] movementSouth = {new Image("data/hero_south.png"), new Image("data/hero_south2.png")};
		Image[] movementWest = {new Image("data/hero_west.png"), new Image("data/hero_west2.png")};
		Image[] movementEast = {new Image("data/hero_east.png"), new Image("data/hero_east2.png")};
		// How long each image is shown.
		int[] duration = {300, 300};

		// Assigns images to all the different directions
		// the player could possibly be turned in.
		north= new Animation(movementNorth, duration, false);
		south = new Animation(movementSouth, duration, false);
		west = new Animation(movementWest, duration, false);
		east = new Animation(movementEast, duration, false);
		
		sprite = east;
	}
	
	/**
	 * Renders the image of the player on the map.
	 */
	public void renderPlayer(){
		sprite.draw(320, 320);
	}
	
	
	/**
	 * @return The player's position
	 * on the x- and y-axis as a
	 * float array.
	 */
	public float[] getPlayerPos(){
		float[] i = new float[2];
		i[0] = x;
		i[1] = y;
		return i;
	}
	/**
	 * Change the player's health status
	 * by a certain amount.
	 * @param i The amount to change by.
	 */
	public void setHealth(double i){
		health += i;
		if(health <= 0){
			die();
		}
	}
	
	public void pickup(Item item){
		item.pickUp();
	}

	public void setPos(float f, float g) {
		x = f;
		y = g;
	}
	
	public void setCurrentRoom(Room r){
		currentRoom = r;
	}
	
	public Weapon getCurrentWeapon(){
		return currentWeapon;
	}
	
	public Room getCurrentRoom(){
		return currentRoom;
	}
	
	public String getDirection(){
		return direction;
	}
	
	public void updateSprite(){
		sprite.update(currentWeapon.getCooldown());
	}
}
