
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The class for the losingState
 * @author Therese Askling & Simon Täcklind
 *
 */
public class HelpScreenState extends BasicGameState{
	int stateID = -1;
	Image helpscreen;
	/**
	 * Constructor for the HelpScreenState class.
	 * @param stateID
	 */
	public HelpScreenState(int stateID){
		this.stateID = stateID;
	}
	
	/**
	 * Initializes the HelpScreenState.
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		helpscreen = new Image("data/helpscreen.jpg");
	}

	/**
	 * Renders The HelpScreenState.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		helpscreen.draw(0,0);
	}

	/**
	 * Updates the HelpScreenState.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			sb.enterState(Game.MAINMENUSTATE);
		}
	}

	/**
	 * Return the stateID
	 */
	@Override
	public int getID() {
		return stateID;
	}

}
