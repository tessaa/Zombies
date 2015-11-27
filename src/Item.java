import org.newdawn.slick.SlickException;

/**
 * Item Interface.
 * @author Therese Askling & Simon Täcklind
 *
 */
public interface Item {
	
	public float x = 1, y = 1;

	public void pickUp();

	public int[] getPos();

	public void renderItem(Player player) throws SlickException;

	public void initItem() throws SlickException;
	
	public void setPosition(int x, int y);
	
	public String getName();
	
}
