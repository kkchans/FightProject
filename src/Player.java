
public class Player {
	private float playerSpeed;
	private float playerX;
	private float playerY;
	private long jumpStartSec;
	private long jumpEndSec;
	private boolean jumping;
	private boolean up;
	private boolean down;

	//�����ɶ�
	Player(float playerX, float playerY){
		jumping = false;
		this.playerX = playerX;
		this.playerY = playerY;
	}
	
	//�¿� �̵�
	void xMove(float playerX) {
		this.playerX += playerX*4;
	}
	
	//���� �̵�
	void yMove(float playerY) {
		this.playerY += playerY;
	}
	
	//����
	void jump() {

		if(playerY >= 390-100 && up) {//����
			jumpEndSec = System.currentTimeMillis();
			if(jumpEndSec - jumpStartSec >= 1.5) //0.005�ʸ��� ������
			{
				yMove(-1);
				jumpStartSec = System.currentTimeMillis();
				if(playerY == 390 - 100) { up = false; down = true; }
			}
		} 
		
		if(playerY <= 390 && down) { //����
			jumpEndSec = System.currentTimeMillis();
			if(jumpEndSec - jumpStartSec >= 5) //0.005�ʸ��� ������
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
