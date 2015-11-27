import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * The room class keeps track of the 
 * map and everything in it.
 * @author Therese Askling & Simon Täcklind
 *
 */
public class Room {
	
	private TiledMap map;
	private boolean[][] blocked;
	private boolean[][] exit;
	static final int SIZE = 32;
	private Monster[] monsters;
	private Inventory items;
	private Player player;
	private Random random;
	
	public int mapWidth;
	public int mapHeight;
	
	public float timeScore;
	public int roundScore;
	
	private int diff;
	public Room(int d){
		monsters = new Monster[4*(d*d)];
		random = new Random();
		diff = d;
		timeScore = 5000 * d;
		items = new Inventory();
	}
	/**
	 * Initializes the game.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing.
	 * @throws SlickException
	 */
	public void initRoom(GameContainer container, StateBasedGame sb) 
				throws SlickException{
		createRoom(container, sb);
		mapWidth = map.getWidth() * 32;
		mapHeight = map.getHeight() * 32;
		createMonsters(container, sb);
		createItems(container, sb);
		initItems();
	}
	
	private void initItems() throws SlickException {
		Item[] i = (Item[]) items.getItems().toArray(new Item[0]);
		int pX = (int)player.getPlayerPos()[0];
		int pY = (int)player.getPlayerPos()[1];
		for(int j = 0; j < i.length; j++){
			int x = random.nextInt(mapWidth-64)+32;
			int y = random.nextInt(mapHeight-64)+32;
			if(!isBlocked(x, y) && !isBlocked(x + 24, y + 24)
					&& !isBlocked(x +24, y) && !isBlocked(x, y +24)){
				if(!(pX - x < 100) || !(pY - y < 100 )
						|| !(pX - x > -100) || !(pY - y > -100)){
					 i[j].setPosition(x, y);
				}else{
					j--;
					continue;
				}
			}else {
				j--;
				continue;
			}	
			i[j].initItem();
		}
		
	}
	/**
	 * Creates the map.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing.
	 * @throws SlickException
	 */
	private void createRoom(GameContainer container, StateBasedGame sb) 
				throws SlickException {
		switch(diff){
			case 1:
				map = new TiledMap("data/map1.tmx");
				break;
			case 2:
				map = new TiledMap("data/map2.tmx");
				break;
			case 3:
				map = new TiledMap("data/map3.tmx");
				break;
		}
		blocked = new boolean[map.getWidth()+1][map.getHeight()+1]; 
		exit = new boolean[map.getWidth()+1][map.getHeight()+1];

		for (int xAxis=0;xAxis<map.getWidth(); xAxis++) {
			for (int yAxis=0;yAxis<map.getHeight(); yAxis++) {
				int tileID = map.getTileId(xAxis, yAxis, 0);
				String value = map.getTileProperty(tileID, "blocked", "false");
				String value2 = map.getTileProperty(tileID, "end", "false");
				if("true".equals(value)) {
					blocked[xAxis][yAxis] = true;
				}else if("false".equals(value)){
					blocked[xAxis][yAxis] = false;
				}
				if("true".equals(value2)){
					exit[xAxis][yAxis] = true;
				}else if("false".equals(value)){
					exit[xAxis][yAxis] = false;
				}
			}
		}
	}

	/** 
	 * Creates the monsters in the room.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing.
	 * @throws SlickException
	 */
	private void createMonsters(GameContainer container, StateBasedGame sb) 
				throws SlickException {
		for(int i=0; i < monsters.length; i++){
			int r = random.nextInt(10)+1;
			if(r<=6){
				monsters[i] = new Zombie(this, player);
			}else{
				monsters[i] = new Mumie(this, player);
			}
		}
		
		int pX = (int)player.getPlayerPos()[0];
		int pY = (int)player.getPlayerPos()[1];
		
		int[] rangeCheck = new int[400];
		for(int i = -200; i < 200; i++){
			rangeCheck[i+200] = i;
		}	
		for(int i = 0; i < monsters.length; i++){
			int x = random.nextInt(mapWidth-64)+32;
			int y = random.nextInt(mapHeight-64)+32;
			for(Monster m : monsters){
				if(m == null){
					continue;
				}else{
					int mX = (int)m.x;
					int mY = (int)m.y;
					boolean done = false;
					
					while(!done){
						int counter = 0;
						for(int a : rangeCheck){
							if(!((mX - x) == a && (mY - y) == a)){
								counter++;
							}
						}
						if(counter == 400){
							done = true;
						}else{
							x = random.nextInt(mapWidth-64)+32;
							y = random.nextInt(mapHeight-64)+32;
							counter = 0;
						}
					}
				}
			}
			
			if(!isBlocked(x, y) && !isBlocked(x + 24, y + 24)
					&& !isBlocked(x +24, y) && !isBlocked(x, y +24)){
				if(!(pX - x < 100) || !(pY - y < 100 )
						|| !(pX - x > -100) || !(pY - y > -100)){
					monsters[i].initMonster(container, sb, x, y);
				}else{
					i--;
				}
			}else {
				i--;
			}
		}
		
	}
	
	private void createItems(GameContainer container, StateBasedGame sb) throws SlickException{
		for(int k = 0; k<diff*2; k++){
			int l = random.nextInt(2);
			switch(l){
			case 0:
				items.addHealthPack(new HealthPack(20));
				break;
			case 1:
				int n = random.nextInt(2);
				switch(n){
				case 0:
					items.addWeapon(new Weapon(150,1000, -10, "Gun"));
					break;
				case 1:
					items.addWeapon(new Weapon(70, 1500, -30, "Shotgun"));
				}
			}
		}
	}
	
	public void addItem(Item item){
		if(item.getClass() == Weapon.class){
			items.addWeapon((Weapon)item);
		}else if(item.getClass() == Weapon.class){
			items.addHealthPack((HealthPack)item); 
		}
	}

	/**
	 * Renders the room.
	 * @throws SlickException 
	 */
	public void renderRoom() throws SlickException{
		map.render(-(int)player.getPlayerPos()[0] + 320,
				-(int)player.getPlayerPos()[1] + 320);
		
		for(Monster m : monsters){
			if(m == null){
				continue;
			}
		    m.renderMonster();
		}

		for(Item j: items.getItems()){
			j.renderItem(player);
		}
		
	}
	
	/**
	 * Check if certain coordinates are the exit.
	 * @param x,y The coordinates we want to check.
	 * @return True if they are the exit, False if 
	 * they are not.
	 */
	public boolean isExit(float x, float y){
		if(x > mapWidth){
			x = mapWidth;
		}
		if(y > mapHeight){
			y = mapWidth;
		}
		int xEnd = (int)x / SIZE;
		int yEnd = (int)y / SIZE;
		return exit[xEnd][yEnd];
	}
	
	/**
	 * Check if certain coordinates are blocked
     * @param x,y The coordinated we want to check.
	 * @return True if they are blocked, False if 
	 * they are not.
	 */
	public boolean isBlocked(float x, float y) {
		if(x > mapWidth){
			x = mapWidth;
		}
		if(y > mapHeight){
			y = mapWidth;
		}
		int xBlock = (int)x / SIZE;
		int yBlock = (int)y / SIZE;
		return blocked[xBlock][yBlock];
	}

	/**
	 * Get the size of each tile in the map.
	 */
	public int getTileSize(){
		return SIZE;
	}
	
	/**
	 * Return a list of the monsters in the room.
	 */
	public Monster[] getMonsters(){
		return monsters;
	}
	
	
	public void setPlayer(Player p){
		player = p;
	}
	
	/**
	 * Move each monster in the map.
	 * @param container The engine that powers the game.
	 * @param sb The game we are playing.
	 * @param delta The distance we want the player to move.
	 */
	public void moveMonsters(GameContainer container, StateBasedGame sb, int delta){
		for(Monster m: monsters){
			if(m == null){
				continue;
			}
			m.monsterMovement(container, sb, delta);
		}
	}

	/**
	 * Remove a monster from the map.
	 * @param monster The monster we want to remove.
	 */
	public void removeMonster(Monster monster) {
		for(int i= 0; i<monsters.length; i++){
			if(monsters[i] == null){
				continue;
			}
			if(monsters[i].equals(monster)){
				monsters[i] = null;
			}
		}
		
	}
	public int getMapWidth(){
		return mapWidth;
	}
	
	public int getMapHeight(){
		return mapHeight;
	}
	
	public int getMonsterCount(){
		int counter = 0;
		for(Monster m : monsters){
			if(m == null){
				continue;
			}else{
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Checks if the player is standning on an item.
	 * @param x & @param y The position of the player.
	 * @return The Item the player is standing on.
	 */
	public Item standOnItem(float x, float y) {
		for(Item i: items.getItems()){
			int[] j = i.getPos();
			if((j[0] <= x+5) && (j[0] >= x-5)&&
					(j[1] <= y+5)&&(j[1] >= y-5)){
				items.removeItem(i);
				Game.lootName = i.getName();
				return i;
			}
		}
		return null;
	}
	public void addItem(float x, float y) throws SlickException {
		int j = random.nextInt(2);
		switch(j){
		case 0:
			HealthPack HP = new HealthPack(20);
			items.addHealthPack(HP);
			HP.initItem();
			HP.setPosition((int)x, (int)y);
			break;
		case 1:
			int k = random.nextInt(10)+1;
			if(k < 7){
				Weapon gun = new Weapon(150, 1000, -10, "Gun");
				items.addWeapon(gun);
				gun.initItem();
				gun.setPosition((int)x, (int)y);
				
			}else if(k >= 7){
				Weapon shotGun = new Weapon(70, 1500, -30, "Shotgun");
				items.addWeapon(shotGun);
				shotGun.initItem();
				shotGun.setPosition((int)x, (int)y);
			}
			break;
		}
		
	}
	
	public int getItemCount(){
		int counter = 0;
		for(Item i : items.getItems()){
			if(i == null){
				continue;
			}else{
				counter++;
			}
		}
		return counter;
	}
}
