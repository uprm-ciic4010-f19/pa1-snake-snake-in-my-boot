package Worlds;

import java.awt.Graphics;
import java.util.Random;

import Game.Entities.Static.Apple;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class WorldOne extends WorldBase{

	//public boolean good = true;
	public WorldOne (Handler handler) {
		super(handler);

		//has to be a number bigger than 20 and even
		//pixel count 
		GridWidthHeightPixelCount = 60;
		GridPixelsize = (900/GridWidthHeightPixelCount);
		playerLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];
		appleLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];

	}

	@Override
	public void tick() {
		super.tick();
		player.tick();
		if(!appleOnBoard){
			appleOnBoard=true;
			int appleX = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);
			int appley = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);

			//change coordinates till one is selected in which the player isnt standing
			boolean goodCoordinates=false;
			do{
				if(!handler.getWorld().playerLocation[appleX][appley]){
					goodCoordinates=true;
				}
			}while(!goodCoordinates);

			apple = new Apple(handler,appleX,appley);
			appleLocation[appleX][appley]=true;
		}
	}




	@Override
	public void render(Graphics g){
		super.render(g);
		player.render(g,playerLocation);
	}
}
