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
 * The class for the winningState
 * @author Therese Askling & Simon Täcklind
 *
 */
@SuppressWarnings("deprecation")
public class WinScreenState extends BasicGameState{
	int stateID = -1;
	Image endgamescreen;
	TrueTypeFont trueTypeFont;
	TrueTypeFont trueTypeFont2;
	private int counter = 0;
	private int cooldown = 0;
	
	private int killScore = 0;
	private int timeScore = 0;
	private int pointsEarned = 0;
	/**
	 * Constructor for the WinScreenState class.
	 * @param stateID
	 */
	public WinScreenState(int stateID){
		this.stateID = stateID;
	}
	
	/**
	 * Initializes the WinScreenState.
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		endgamescreen = new Image("data/winscreen.jpg");
		Font font = new Font("Verdana", Font.BOLD, 20);
		Font font2 = new Font("Verdana", Font.BOLD, 40);
        trueTypeFont = new TrueTypeFont(font, true);
        trueTypeFont2 = new TrueTypeFont(font2, true);
	}

	/**
	 * Renders The WinScreenState.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		endgamescreen.draw(0,0);
		trueTypeFont.drawString(100, 100,
				"Monster points earned: " + String.valueOf(killScore), Color.white);
		if(this.killScore == Game.killScore){
			trueTypeFont.drawString(100, 150,
					"Time points earned: " + String.valueOf(timeScore), Color.white);
		}
		if(this.timeScore == Game.timeScore
				&& this.killScore == Game.killScore){
			trueTypeFont.drawString(100, 200,
					"Total points earned: " + String.valueOf(pointsEarned), Color.white);
			trueTypeFont2.drawString(100, 250,
					"Current Score: " + String.valueOf(Game.totalScore), Color.white);
			trueTypeFont2.drawString(50, 500,
					"Press enter to continue!", Color.yellow);
		}
			
	}

	/**
	 * Updates the WinScreenState.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		if(this.killScore < Game.killScore){
			this.killScore += delta * 0.1;
		}else if(this.timeScore < Game.timeScore){
			this.timeScore += delta * 0.1;
		}
		pointsEarned = this.killScore + this.timeScore;
		
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ENTER)){
			if(this.cooldown <= 0){
				this.cooldown = 500;
				counter++;
			}
		}
		if(this.cooldown > 0){
			this.cooldown -= delta;
		}
		switch(counter){
			case 0:
				if(this.killScore == Game.killScore){
					counter++;
				}
				break;
			case 1:
				this.killScore = Game.killScore;
				if(this.timeScore == Game.timeScore){
					counter++;
				}
				break;
			case 2:
				this.timeScore = Game.timeScore;
				break;
			case 3:
				this.killScore = 0;
				this.timeScore = 0;
				this.pointsEarned = 0;
				this.counter = 0;
				sb.enterState(Game.GAMEPLAYSTATE);
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
