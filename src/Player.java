
public class Player {
	float playerSpeed;
	float playerX;
	float playerY;
	
	//�����ɶ�
	Player(float playerX, float playerY){
		this.playerX = playerX;
		this.playerY = playerY;
	}
	
	//�¿� �̵�
	void move(float playerX) {
		playerX += playerX;
	}
	
	//����
	void jump() {
		
	}
	
	int getX() {
		return (int)playerX;
	}
	
	int getY() {
		return (int)playerY;
	}
}
