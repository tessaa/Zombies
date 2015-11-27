import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The class for the losingState
 * @author Therese Askling & Simon Täcklind
 *
 */
@SuppressWarnings("deprecation")
public class LoseScreenState extends BasicGameState{
	int stateID = -1;
	Image endgamescreen;
	TrueTypeFont trueTypeFont = null;
	TrueTypeFont trueTypeFont2 = null;
	/**
	 * Constructor for the LoseScreenState class.
	 * @param stateID
	 */
	public LoseScreenState(int stateID){
		this.stateID = stateID;
	}
	
	/**
	 * Initializes the LoseScreenState.
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		endgamescreen = new Image("data/losescreen.jpg");
		Font font = new Font("Verdana", Font.BOLD, 20);
		Font font2 = new Font("Verdana", Font.BOLD, 40);
        trueTypeFont = new TrueTypeFont(font, true);
        trueTypeFont2 = new TrueTypeFont(font2, true);
	}

	/**
	 * Renders The LoseScreenState.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		endgamescreen.draw(0,0);
		trueTypeFont.drawString(100, 100,
				"Current Score: " + String.valueOf(Game.totalScore), Color.white);
		trueTypeFont2.drawString(50, 500,
				"Press enter to continue!", Color.yellow);
	}

	/**
	 * Updates the LoseScreenState.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		Game.hasStarted = false;
		GamePlayState.difficulty = 1;
		GamePlayState.won = false;
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ENTER)){
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

