public class Player {
	private float playerSpeed;
	private float playerX;
	private float playerY;
	private int width;
	private int height;
	private long jumpStartSec;
	private long jumpEndSec;
	private boolean jumping;
	private boolean up;
	private boolean down;
	private int hp;
	
	//생성될때
	Player(float playerX, float playerY, int width, int height, int hp){
		jumping = false;
		this.hp = hp;
		this.width = width;
		this.height = height;
		this.playerX = playerX;
		this.playerY = playerY;
	}
	public int getHp() {
		return hp;
	}
	public void hit(int hit) {
		hp-=hit;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	//좌우 이동
	void xMove(float playerX) {
		this.playerX += playerX*15;
	}
	
	//상하 이동
	void yMove(float playerY) {
		this.playerY += playerY;
	}
	
	//점프
	void jump() {

		if(playerY >= Main.MAIN_HEIGHT-height-100 && up) {//점프
			jumpEndSec = System.currentTimeMillis();
			if(jumpEndSec - jumpStartSec >= 0.00005) //올라감
			{
				yMove(-2);
				jumpStartSec = System.currentTimeMillis();
				if(playerY == Main.MAIN_HEIGHT-height-100) { up = false; down = true; }
			}
		} 
		
		if(playerY <= Main.MAIN_HEIGHT-height && down) { //낙하
			jumpEndSec = System.currentTimeMillis();
			if(jumpEndSec - jumpStartSec >= 0.00001) //내려옴
			{
				yMove(2);
				jumpStartSec = System.currentTimeMillis();
				if(playerY == Main.MAIN_HEIGHT-height) { down = false; }
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