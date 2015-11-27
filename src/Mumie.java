import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * The mumie class is a Subclass of Monster 
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Mumie extends Monster{

	/**
	 * Constructor for Mumie class
	 * @param room The room the mumie is in.
	 * @param p The player the mumie wants to kill.
	 */
	public Mumie(Room room, Player p) {
		super(room, p);
		attackDamage = (-10);
		attackRange = 10;
		senseRange = 70;
		speed = 0.005f;
		currentRoom = room;
		player = p;
		monsterSpecificCooldown = 1000;
		monsterSpecificValue = 750;
	}
	
	public void initMonster(GameContainer container, StateBasedGame sb, int v, int u) throws SlickException {
		x = (float) v;
		y = (float) u;
		// Puts all the images in an array.
		Image [] movementNorth = {new Image("data/mumie_north.png"), new Image("data/mumie_north2.png")};
		Image [] movementSouth = {new Image("data/mumie_south.png"), new Image("data/mumie_south2.png")};
		Image [] movementWest = {new Image("data/mumie_west.png"), new Image("data/mumie_west2.png")};
		Image [] movementEast = {new Image("data/mumie_east.png"), new Image("data/mumie_east2.png")};
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
