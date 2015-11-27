import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The class for all the nasty zombies
 * we want to kill. 
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Zombie extends Monster{	
	/**
	 * Constructor for the zombie class
	 * @param room The room the zombies are in.
	 * @param player The player the zombies want to kill.
	 */
	public Zombie(Room room, Player player){
		super(room, player);
		attackDamage = (-5);
		attackRange = 10;
		senseRange = 90;
		speed = 0.05f;
		currentRoom = room;
		this.player = player;
		monsterSpecificCooldown = 2000;
		monsterSpecificValue = 250;
	}
	
	public void initMonster(GameContainer container, StateBasedGame sb, int v, int u) throws SlickException {
		x = (float) v;
		y = (float) u;
		// Puts all the images in an array.
		Image [] movementNorth = {new Image("data/zombie_north.png"), new Image("data/zombie_north2.png")};
		Image [] movementSouth = {new Image("data/zombie_south.png"), new Image("data/zombie_south2.png")};
		Image [] movementWest = {new Image("data/zombie_west.png"), new Image("data/zombie_west2.png")};
		Image [] movementEast = {new Image("data/zombie_east.png"), new Image("data/zombie_east2.png")};
		// How long each image is shown.
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
	
}
