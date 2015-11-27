import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The class for the mainState
 * @author Therese Askling & Simon Täcklind
 *
 */
public class MainMenuState extends BasicGameState{
	int stateID = -1;
	
	Image menuScreen, play, help, quit, resume, current;
	float currentScale= 1, helpScale = 1, quitScale =1, scaleStep = 0.0001f;
	private static final int menuX = 340, menuY = 100;
	/**
	 * Constructor for the MainScreenState class.
	 * @param stateID
	 */
	public MainMenuState(int stateID){
		this.stateID = stateID;
	}
	
	/**
	 * Initializes the MainMenuState.
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		menuScreen = new Image("data/menuscreen.jpg");
		play = new Image("data/play.png");
		help = new Image("data/help.png");
		quit = new Image("data/quit.png");
		resume = new Image("data/resume.png");
		current = play;
	}

	/**
	 * Renders The MainMenuState.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		menuScreen.draw(0,0);
		current.draw(menuX, menuY, currentScale);
		help.draw(menuX, menuY + 80, helpScale);
		quit.draw(menuX, menuY + 160, quitScale);
		
		
	}

	/**
	 * Updates the MainMenuState.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		if(Game.hasStarted){
			current = resume;
		}else{
			current = play;
		}
		Input input = gc.getInput();
		
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		boolean insideCurrent = false;
		boolean insideHelp = false;
		boolean insideQuit = false;
		
		if((mouseX >= menuX && mouseX <= menuX + current.getWidth()) &&
				(mouseY >= menuY && mouseY <= menuY + current.getHeight())){
			insideCurrent = true;
		}else if((mouseX >= menuX && mouseX <= menuX + help.getWidth()) &&
					(mouseY >= menuY+80 && mouseY <= menuY+80 + help.getHeight())){
			insideHelp = true;
		}else if((mouseX >= menuX && mouseX <= menuX + quit.getWidth()) &&
					(mouseY >= menuY+160 && mouseY <= menuY+160 + quit.getHeight())){
			insideQuit = true;
		}

		if(insideCurrent){
			if(currentScale < 1.1f){
				currentScale += scaleStep * delta;
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					sb.enterState(Game.GAMEPLAYSTATE);
				}
			}
		}else if(currentScale > 1.0f){
				currentScale -= scaleStep * delta; 
		}
		
		if(insideHelp){
			if(helpScale < 1.1f){
				helpScale +=  scaleStep * delta;
				if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
					sb.enterState(Game.HELPSCREENSTATE);
				}
			}
		}else if(helpScale > 1.0f){
				helpScale -= scaleStep * delta;
		}
		
		if(insideQuit){
			if(quitScale < 1.1f){
				quitScale +=  scaleStep * delta;
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					gc.exit();
				}
			}
		}else if(quitScale > 1.0f){
				quitScale -= scaleStep * delta;
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
