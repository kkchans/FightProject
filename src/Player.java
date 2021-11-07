
public class Player {
	float playerSpeed;
	float playerX;
	float playerY;
	
	//생성될때
	Player(float playerX, float playerY){
		this.playerX = playerX;
		this.playerY = playerY;
	}
	
	//좌우 이동
	void move(float playerX) {
		playerX += playerX;
	}
	
	//점프
	void jump() {
		
	}
	
	int getX() {
		return (int)playerX;
	}
	
	int getY() {
		return (int)playerY;
	}
}
