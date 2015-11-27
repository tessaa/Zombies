import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Person interface
 * @author Therese Askling & Simon Täcklind
 *
 */
public interface Person {

	public enum direction{
		UP, DOWN, LEFT, RIGHT;
	}
	
	public boolean move(GameContainer container, StateBasedGame sb, direction d, int delta);
	
	public void attack() throws SlickException;
	
	public void die() throws SlickException;
}
