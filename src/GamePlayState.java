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
 * The gamePlayState class
 * which runs most of the game
 * @author Therese Askling & Simon Täcklind
 *
 */
@SuppressWarnings("deprecation")
public class GamePlayState extends BasicGameState {
	private int stateID = -1;
	private Player player;
	private Room room;
	TrueTypeFont trueTypeFont;
	

	boolean[][] blocked;
	boolean[][] exit;
	static final int SIZE = 32;
	
	private int renderCooldown = 1001;
	private int renderCooldown2 = 1001;
	private float y = 310;
	private int cooldown = 0;
	
	public static int itemCount;
	public static int monsterCount;
	public static int difficulty = 1;
	public static boolean won = false;

	Image statusBar;
	
	/**
	 * Constructor for the gamePlayState class.
	 * @param stateID
	 */
	public GamePlayState(int stateID) {
		this.stateID = stateID;
		room = new Room(difficulty);
		player = new Player(room);
		room.setPlayer(player);
	}
	

	/**
	 * Initializes the game state, the player and the room.
	 */
	@Override
	public void init(GameContainer container, StateBasedGame sb) throws SlickException {
		player.initPlayer(container, sb);
		room.initRoom(container, sb);
		monsterCount = room.getMonsterCount();
		itemCount = room.getItemCount();
		Font font = new Font("Verdana", Font.BOLD, 20);
        trueTypeFont = new TrueTypeFont(font, true);
        statusBar = new Image("data/statusbar.jpg");
	}

	/**
	 * Updates the gamestate.
	 * Keeps track of user input.
	 */
	@Override
	public void update(GameContainer container, StateBasedGame sb, int delta) throws SlickException {
		room.moveMonsters(container, sb, delta); 
		Input input = container.getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			sb.enterState(Game.MAINMENUSTATE);
		}else if (input.isKeyDown(Input.KEY_UP)) {
			player.move(container, sb, Person.direction.UP, delta);
		}else if (input.isKeyDown(Input.KEY_DOWN)) {
			player.move(container, sb, Person.direction.DOWN, delta);
		}else if (input.isKeyDown(Input.KEY_LEFT)) {
			player.move(container, sb, Person.direction.LEFT, delta);
		}else if (input.isKeyDown(Input.KEY_RIGHT)){
			player.move(container, sb, Person.direction.RIGHT, delta);
		}else if (input.isKeyDown(Input.KEY_SPACE)){
			if(cooldown <= 0 && player.getCurrentWeapon().hasAmmo()){
				cooldown = player.getCurrentWeapon().getCooldown();
				player.updateSprite();
				player.attack();
			}
		}else if (input.isKeyDown(Input.KEY_1)){
			player.switchWeapon("Knife");
		}else if (input.isKeyDown(Input.KEY_2)){
			player.switchWeapon("Gun");
		}else if (input.isKeyDown(Input.KEY_3)){
			player.switchWeapon("Shotgun");
		}
		if(cooldown > 0){
			cooldown -= delta;
		}
		if(renderCooldown <= 1000){
			renderCooldown += delta * 2;
			y -= delta * 0.05;
			if(renderCooldown > 1000){
				y = 310;
			}
		}
		if(renderCooldown2 <= 1000){
			renderCooldown2 += delta * 2;
			y -= delta * 0.05;
			if(renderCooldown2 > 1000){
				y = 310;
			}
		}
		for(Monster m : room.getMonsters()){
			if(m == null){
				continue;
			}
			if(m.cooldown > 0){
				m.cooldown -= delta;
			}
		}
		if(room.timeScore > 0){
			room.timeScore -= delta * 0.2;
			if(room.timeScore < 0){
				room.timeScore = 0;
			}
		}
		if(room.getMonsterCount() < monsterCount){
			monsterCount--;
			renderCooldown = 0;
		}
		if(room.getItemCount() < itemCount){
			itemCount--;
			renderCooldown2 = 0;
		}
		if(won){
			player.score += room.timeScore;
			Game.timeScore = (int)room.timeScore;
			Game.killScore = room.roundScore;
			Game.totalScore = (int)player.score;
			if(difficulty == 4){
				sb.enterState(Game.FINALSCREENSTATE);
			}else{
				room = null;
				room = new Room(difficulty);
				room.setPlayer(player);
				player.setCurrentRoom(room);
				room.initRoom(container, sb);
				player.setPos(room.getMapWidth() - 64, room.getMapHeight() - 64);
				monsterCount = room.getMonsterCount();
				won = false;
			}
		}
		if(!Game.hasStarted){
			room = null;
			player = null;
			room = new Room(difficulty);
			player = new Player(room);
			room.setPlayer(player);
			init(container, sb);
		}
		Game.hasStarted = true;
	}

	/**
	 * Renders the room and the player.
	 */
	public void render(GameContainer container, StateBasedGame sb, Graphics g) throws SlickException {
		room.renderRoom();
		player.renderPlayer();
		for(Monster m : room.getMonsters()){
			if(m == null){
				continue;
			}
			trueTypeFont.drawString(m.getMonsterPos()[0],
					m.getMonsterPos()[1]-20,
					"HP:" + String.valueOf((int)m.health), Color.black);
		}
		statusBar.draw(0,600);
		trueTypeFont.drawString(540, 607,
				"HP:" + String.valueOf((int)player.health), Color.black);
		if(player.getCurrentWeapon().getName() == "Knife"){
			trueTypeFont.drawString(247, 607,
					"Weapon:" + player.getCurrentWeapon().getName(), Color.black);
		}else{
			trueTypeFont.drawString(247, 607,
					"Weapon:" + player.getCurrentWeapon().getName()
					+ "(" + String.valueOf(player.getCurrentWeapon().getAmmo()) +")", Color.black);
		}
		trueTypeFont.drawString(17, 607,
				"Score:" + String.valueOf((int)player.score) + " ", Color.black);
		if(renderCooldown < 1000){
			renderPointsGain(player.killValue);
		}
		if(renderCooldown2 < 1000){
			renderItemGain(Game.lootName);
		}
	}
	
	public int getID(){
		return stateID;
	}
	
	public void renderPointsGain(int value){
			trueTypeFont.drawString(320, y,
					"+" + String.valueOf(value), Color.orange);
	}
	public void renderItemGain(String name){
		trueTypeFont.drawString(320, y, name + "!", Color.orange);
	}
}