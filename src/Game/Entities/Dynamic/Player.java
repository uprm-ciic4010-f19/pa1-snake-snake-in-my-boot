package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.GameState;
import Game.GameStates.State;
import Main.Handler;
import Game.Entities.Static.Apple;

/**
 * Created by AlexVR on 7/2/2018.
 */

public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;

	public double score=0.0;
	public double tempScore;
	public String stringScore="Score: 0";
	

	public int xCoord;
	public int yCoord;
	double spd = 5;
	

	public double moveCounter;
//private void int Change;
	
	public String direction;//is your first name one?

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;
		direction= "Right";
		justAte = false;
		lenght= 1;

	}

	public void tick(){
		handler.getWorld().playerLocation[xCoord][yCoord]=true;
		int x = xCoord;
		int y = yCoord;
		moveCounter++;
		if(moveCounter>=spd) {
			checkCollisionAndMove();
			moveCounter=1;
			handler.setCounter(handler.getCounter()+1);
			
			//Change(moveCounter);

		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){
			direction="Up";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction != "Up"){
			direction="Down";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
			direction="Left";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction != "Left"){
			direction="Right";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			Eat();
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {	
			if (spd<=5&&spd>0) {
				spd = spd- 0.25;
			};
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {				
			if (spd<5&&spd>=0) {
				spd = spd + 0.25;
			}
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			State.setState(handler.getGame().pauseState);
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
			handler.setCounter(0);
			handler.getGame().restartState = new GameState(handler);
			State.setState(handler.getGame().restartState);
		}
	}

	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;

		switch (direction){
		case "Left":
			if(xCoord==0){
				xCoord = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}

		handler.getWorld().playerLocation[xCoord][yCoord]=true;
		for(int tailLength=1; lenght > tailLength; tailLength++) {
			if(xCoord == handler.getWorld().body.get(tailLength-1).x && yCoord == handler.getWorld().body.get(tailLength-1).y) {
				State.setState(handler.getGame().gameOverState);
			}
		}
		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			
			
			handler.getWorld().body.addFirst(new Tail(x, y,handler));	
		}

	}

	
	public void render(Graphics g,Boolean[][] playeLocation){
		
		
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {	
				if(playeLocation[i][j]){
					g.setColor(Color.green);
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}		
				
				
				if(handler.getWorld().appleLocation[i][j]) {
					if(Apple.isGood()) {
						g.setColor(Color.red);
						g.fillRect((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);				
					}else {
						g.setColor(Color.GRAY);
						g.fillRect((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);
					}
				}
				
				if(isJustAte()) {
					if(Apple.isGood()) {
						setJustAte(false);
						score = Math.sqrt((2*score)+1)+score;
						tempScore = Math.round(score* 100.0) / 100.0;
						stringScore = "Score: " + Double.toString(tempScore);
						
						handler.setCounter(0);
						
					}else {
						setJustAte(false);
						score = score - Math.sqrt((2*score)+1);
						if (score < 0){
							score = 0.0;
						}
						tempScore = Math.round(score* 100.0) / 100.0;
						stringScore = "Score: " + Double.toString(tempScore);					
						
						handler.setCounter(0);
					}
				}
			}
		}
		g.setColor(Color.white);
		g.drawString(stringScore, handler.getWorld().GridWidthHeightPixelCount/2, handler.getWorld().GridWidthHeightPixelCount/2);
	}


	public void Eat(){
		setJustAte(true);
		lenght++;
		if (spd<=5&&spd>0) {
			spd=spd-0.25;
		}
		Tail tail= null;
		
		if (!handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			handler.getWorld().appleLocation[xCoord][yCoord]=false;
			handler.getWorld().appleOnBoard=false;
		}
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);
					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;

	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
