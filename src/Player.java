
public class Player {
	private float playerSpeed;
	private float playerX;
	private float playerY;
	private long jumpStartSec;
	private long jumpEndSec;
	private boolean jumping;
	private boolean up;
	private boolean down;

	//생성될때
	Player(float playerX, float playerY){
		jumping = false;
		this.playerX = playerX;
		this.playerY = playerY;
	}
	
	//좌우 이동
	void xMove(float playerX) {
		this.playerX += playerX*4;
	}
	
	//상하 이동
	void yMove(float playerY) {
		this.playerY += playerY;
	}
	
	//점프
	void jump() {

		if(playerY >= 390-100 && up) {//점프
			jumpEndSec = System.currentTimeMillis();
			if(jumpEndSec - jumpStartSec >= 1.5) //0.005초마다 내려옴
			{
				yMove(-1);
				jumpStartSec = System.currentTimeMillis();
				if(playerY == 390 - 100) { up = false; down = true; }
			}
		} 
		
		if(playerY <= 390 && down) { //낙하
			jumpEndSec = System.currentTimeMillis();
			if(jumpEndSec - jumpStartSec >= 5) //0.005초마다 내려옴
			{
				yMove(1);
				jumpStartSec = System.currentTimeMillis();
				if(playerY == 390) { down = false; }
			}
		}
		
	}
	
	int getX() {
		return (int)playerX;
	}
	
	int getY() {
		return (int)playerY;
	}
	
	boolean getJumping() {
		return jumping;
	}
	
	void jumpingStart() {
		jumping = true;
		up = true;
		down = false;
		jumpStartSec = System.currentTimeMillis();
		//this.playerY -=100;
	}
	

}
