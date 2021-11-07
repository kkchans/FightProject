
public class Player {
	private float playerSpeed;
	private float playerX;
	private float playerY;
	private boolean b_jump;
	private long jumpStart;
	private long jumpEnd;
	
	//�����ɶ�
	Player(float playerX, float playerY){
		this.playerX = playerX;
		this.playerY = playerY;
	}
	
	//�¿� �̵�
	void move(float playerX) {
		this.playerX += playerX*4;
	}
	
	//����
	void jump() {
		jumpStart = System.currentTimeMillis(); 
		this.playerY -=20;
	}
	
	public long getJumpStart() {
		return jumpStart;
	}

	public long getJumpEnd() {
		return jumpEnd;
	}

	public void setJumpEnd(long jumpEnd) {
		this.jumpEnd = jumpEnd;
	}

	int getX() {
		return (int)playerX;
	}
	
	int getY() {
		return (int)playerY;
	}
}
