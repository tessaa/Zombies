import java.awt.geom.Line2D;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The SuperClass for all the nasty monsters
 * we want to kill. 
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Monster implements Person{	
	public Animation sprite, north, west, east, south;
	public Room currentRoom;
	public Person.direction direction;
	public float x , y;
	public Player player;
	public double health;
	public double attackDamage;
	public double attackRange;
	public float senseRange;
	public float speed;
	public int cooldown = 0;
	public int monsterSpecificCooldown;
	public int monsterSpecificValue;
	private Random random;
	private Line2D line = new Line2D.Float();
	
	public static final int PSIZE = 24;
	
	/**
	 * Constructor for the monster class
	 * @param room The room the monsters are in
	 */
	public Monster(Room room, Player p){
		health = 100;
		random = new Random();
	}
	
	
	/**
	 * Making the zombies move in random ways
	 * until the player is close. Then it attacks.
	 * @param container The engine which powers our game
	 * @param sb The game we are playing
	 * @param delta WTF is this??
	 */
	public void monsterMovement(GameContainer container, StateBasedGame sb, int delta){
		if(playerClose()){
			persuePlayer(container, sb, delta);
		}else{
			randomMovement();
		}
	}

	private void randomMovement() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Persues the player and attacks 
	 * if the player is close enough.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing.
	 * @param delta
	 */
	private void persuePlayer(GameContainer container, StateBasedGame sb, int delta) {
		float u = player.getPlayerPos()[0];
		float v = player.getPlayerPos()[1];
		changeSenseRange(20);
		if((u-x)<0){
			move(container, sb, Person.direction.LEFT, delta);
		}if((u-x)>0){
			move(container, sb, Person.direction.RIGHT, delta);
		}if((v-y)<0){
			move(container, sb, Person.direction.UP, delta);
		}if((v-y)>0){
			move(container, sb, Person.direction.DOWN, delta);
		}
		if(((u-x)<attackRange&&(v-y)<attackRange)&&((u-x)>(-attackRange)&&(v-y)>(-attackRange))
				&& cooldown <= 0){
			cooldown = monsterSpecificCooldown;
			attack();
		}
	}


	/**
	 * Checks if the player is close enough for
	 * the monster to notice.
	 * @return True if the player is close enough, 
	 * False if it isn't.
	 */
	public boolean playerClose() {
		float pos[] = player.getPlayerPos();
		float u = pos[0];
		float v = pos[1];
		if(((u-x)<senseRange&&(v-y)<senseRange)
				&&((u-x)>(-senseRange)&&(v-y)>(-senseRange))){
			return true;
		}
		return false;
	}


	/**
	 * Making the monster move in different directions.
	 */
	@Override
	public boolean move(GameContainer container, StateBasedGame sb, Person.direction d, int delta) {
		direction = d;
		switch(direction){
		// Moves the monster up. Changes the image of the player
		// to suit that direction.
		case UP:
		sprite = north;
		sprite.update(delta);
		if (!currentRoom.isBlocked(x, y - delta * 0.1f)
				&& !currentRoom.isBlocked(x + PSIZE, y -delta * 0.1f)) {
				y -= delta * 0.05f;
				return true;
			}
		if (currentRoom.isExit(x, y)){
			move(container, sb, Person.direction.DOWN, delta);
		}
			break;
		// Moves the monster down. Changes the image of the player
		// to suit that direction.
		case DOWN:
			sprite = south;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x, y + PSIZE + delta * 0.1f)
					&& !currentRoom.isBlocked(x + PSIZE, y + PSIZE - 10 + delta + 0.1f)) {
				y += delta * 0.05f;
				return true;
			}if (currentRoom.isExit(x, y)){
				move(container, sb, Person.direction.UP, delta);
			}
			break;
		// Moves the monster to the left. Changes the image of the player
		// to suit that direction.
		case LEFT:
			sprite = west;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x - delta * 0.1f, y)
					&& !currentRoom.isBlocked(x -delta * 0.1f, y + PSIZE)) {
				x -= delta * 0.05f;
				return true;
			}
			if (currentRoom.isExit(x, y)){
				move(container, sb, Person.direction.RIGHT, delta);
			}
			break;
		// Moves the monster to the right. Changes the image of the player
		// to suit that direction.
		case RIGHT:
			sprite = east;
			sprite.update(delta);
			if (!currentRoom.isBlocked(x + PSIZE + delta * 0.1f, y)
					&& !currentRoom.isBlocked(x + PSIZE + delta * 0.1f, y + PSIZE)) {
				x += delta * 0.05f;
				return true;
			}
			if (currentRoom.isExit(x, y)){
				move(container, sb, Person.direction.LEFT, delta);
			}
		    break;
		}
		return false;
    }



	/**
	 * Attack the player.
	 */
	@Override
	public void attack() {
		player.setHealth(attackDamage);
	}

	/**
	 * remove the monster from existence.
	 * @throws SlickException 
	 */
	@Override
	public void die() throws SlickException {
		player.killValue = monsterSpecificValue;
		player.score += monsterSpecificValue;
		currentRoom.roundScore += monsterSpecificValue;
		currentRoom.removeMonster(this);
		int i = random.nextInt(10)+1;
		if(i >5){
			currentRoom.addItem(x, y);
			GamePlayState.itemCount++;
		}
	}
	
	/**
	 * Initializes the monster with all the different 
	 * images that is to be shown during the game.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing
	 * @throws SlickException
	*/
	public void initMonster(GameContainer container, StateBasedGame sb, int v, int u) throws SlickException {
		x = (float) v;
		y = (float) u;
		// Puts all the images in an array.
		Image [] movementNorth = {new Image("data/hero_north.png"), new Image("data/hero_north2.png")};
		Image [] movementSouth = {new Image("data/hero_south.png"), new Image("data/hero_south2.png")};
		Image [] movementWest = {new Image("data/hero_west.png"), new Image("data/hero_west2.png")};
		Image [] movementEast = {new Image("data/hero_east.png"), new Image("data/hero_east2.png")};
		// WFT does this mean??
		int [] duration = {300, 300};         

		// Assigns images to all the different directions
		// the monster could possibly be turned in.
		north= new Animation(movementNorth, duration, false);
		south = new Animation(movementSouth, duration, false);
		west = new Animation(movementWest, duration, false);
		east = new Animation(movementEast, duration, false);
		
		// Makes the first image that we see of 
		// the monster to the one where it faces the
		// east.
		sprite = east;
		}
	
	/**
	 * Render the monster on the map.
	 */
	public void renderMonster(){
		sprite.draw(-player.getPlayerPos()[0] + 320 + x,
				-player.getPlayerPos()[1] + 320 + y);
	}

	/**
	 * Changes the health-status of the monster.
	 * @param d The change in health.
	 * @throws SlickException 
	 */
	public void setHealth(double d) throws SlickException{
		health += d;
		if(health <= 0){
			die();
		}
	}

	/**
	 * returns the position of the monster.
	 * @return float array.
	 */
	public float[] getMonsterPos(){
		float[] i = new float[2];
		i[0] = -player.getPlayerPos()[0] + 320 + x;
		i[1] = -player.getPlayerPos()[1] + 320 + y;
		return i;
	}
	
	/**
	 * 
	 */
	public void changeSenseRange(int change){
		if(senseRange < 300){
			senseRange += change;
		}
	}
	
}
