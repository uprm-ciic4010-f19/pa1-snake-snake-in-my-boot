package Game.Entities.Static;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private static Handler handler;

    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        Apple.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    static public boolean isGood() {
    		if(handler.getCounter() > handler.getWorld().GridWidthHeightPixelCount) {
    			
    			return false;
    		}
    		else {
    			
    			return true;
    		}
    }


}
