import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The HealthPack class provides fast healthcare 
 * for the player.
 * @author Therese Askling & Simon Täcklind
 *
 */
public class HealthPack implements Item {
	
	private int x, y;
	private int heal;
	private Image sprite;
	private String name = "Health Pack";

	/**
	 * Constructor for the HealthPack class.
	 */
	public HealthPack(int heal){
		this.heal = heal;
		this.name += "(+" + String.valueOf(heal) + ")";
	}

	@Override
	public void pickUp() {
		// TODO Auto-generated method stub
		
	}

	public int[] getPos() {
		int[] i = {x, y};
		return i;
	}

	public void use(Player player) {
		player.setHealth(heal);
	}

	@Override
	public void renderItem(Player player) {
		sprite.draw(-player.getPlayerPos()[0] + 320 + x,
				-player.getPlayerPos()[1] + 320 + y);
		
	}

	@Override
	public void initItem() throws SlickException {
		sprite = new Image("data/HealthPack.png");
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	public String getName(){
		return name;
	}
}
