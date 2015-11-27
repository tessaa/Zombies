 
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The main class in a Zombie-killing game.
 * @author Therese Askling & Simon Tacklind
 *
 */
public class Game extends StateBasedGame{
	
	public static final int MAINMENUSTATE		= 0;
	public static final int GAMEPLAYSTATE  		= 1;
	public static final int HELPSCREENSTATE		= 2;
    public static final int WINSCREENSTATE 		= 3;
    public static final int LOSESCREENSTATE 	= 4;
    public static final int FINALSCREENSTATE	= 5;
    
    public static boolean hasStarted = false;
    public static String lootName;
    public static int totalScore = 0;
    public static int timeScore = 0;
    public static int killScore = 0;
    /**
     * Constructor for game-class.
     */
	public Game(){
		super("Game");
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
		this.addState(new HelpScreenState(HELPSCREENSTATE));
        this.addState(new WinScreenState(WINSCREENSTATE));
        this.addState(new LoseScreenState(LOSESCREENSTATE));
        this.addState(new FinalScreenState(FINALSCREENSTATE));
        this.enterState(MAINMENUSTATE);
	}
	
	/**
	 * The main method. Gets the game running.
	 * @param argv
	 */
    public static void main(String[] argv) {
		try{
			AppGameContainer app = new AppGameContainer(new Game());
			app.setDisplayMode(640, 640, false);
			app.setTargetFrameRate(99);
			app.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
    }

    /**
     * a list of the different gamestates.
     */
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		this.getState(MAINMENUSTATE).init(gameContainer, this);
		this.getState(HELPSCREENSTATE).init(gameContainer, this);
        this.getState(WINSCREENSTATE).init(gameContainer, this);
        this.getState(LOSESCREENSTATE).init(gameContainer, this);
        this.getState(FINALSCREENSTATE).init(gameContainer, this);
	}
}
