package Main;

import Game.Entities.Dynamic.Player;
import Input.KeyManager;
import Input.MouseManager;
import Worlds.WorldBase;


/**
 * Created by AlexVR on 7/1/2018.
 * Made by the people, for the people...
 */

public class Handler {

    private GameSetUp game;
    private WorldBase world;
	public int counter=0;
	

    public Handler(GameSetUp game){
        this.game = game;
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public GameSetUp getGame() {
        return game;
    }

    public void setGame(GameSetUp game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }

    public WorldBase getWorld() {
        return world;
    }

    public void setWorld(WorldBase world) {
        this.world = world;
    }
    public int getCounter() {
    		return counter;
    }
    public void setCounter(int counter) {
    		this.counter = counter;
    }


}
