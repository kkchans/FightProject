public class Player {
	private float speed;
	private float x;
	private float y;
	private int width;
	private int height;
	private long jumpStartSec;
	private long jumpEndSec;
	private long bookStartSec;
	private long bookEndSec;
	private boolean jumping;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean book;
	private boolean flyMouse;
	private int mouseX=0, mouseY=400;//마우스 좌표
	private int mouseSpeed = 15;
	private Player otherPlayer;
	private int[] mousePixels, preMousePixels;
	private boolean pullOther;
	private int location; //왼쪽이면 -1, 1
	
	private int hp;
	
	//생성될때
	Player(float x, float y, int width, int height, int hp, int location){
		jumping = false;
		this.hp = hp;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		speed = 30;
		this.location = location;
	}
	
	void setOtherPlayer(Player player) {
		this.otherPlayer = player;
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
	void setMousePixels(int[] pixels, int[] prePixels) {
		this.mousePixels = pixels;
		this.preMousePixels = prePixels;
	}
	
	

	//좌우 이동
	void xMove(float playerX) {
		this.x += playerX*speed;
	}
	
	//상하 이동
	void yMove(float playerY) {
		this.y += playerY;
	}
	
	//점프
	void jump() {
		if(!collisionCheck()) { //충돌 안했을때
			if(y >= Main.MAIN_HEIGHT-height-100 && up && jumping)  {//점프
				jumpEndSec = System.currentTimeMillis();
				if(jumpEndSec - jumpStartSec >= 0.00005) //올라감
				{
					yMove(-2);
					jumpStartSec = System.currentTimeMillis();
					if(y == Main.MAIN_HEIGHT-height-100) { up = false; down = true; }
				}
			}else if(y <= Main.MAIN_HEIGHT-height && down) { //낙하
				jumpEndSec = System.currentTimeMillis();
				if(jumpEndSec - jumpStartSec >= 0.00001) //내려옴
				{
					yMove(2);
					jumpStartSec = System.currentTimeMillis();
					if(y == Main.MAIN_HEIGHT-height) { down = false; jumping = false;}
				}
			}
		}
		
	}
	
	int getX() {
		return (int)x;
	}
	
	int getY() {
		return (int)y;
	}
	
	boolean getJumping() {
		return jumping;
	}
	
	private boolean collisionCheck() {
    	int space = 30;
    	// 점프시 충돌은 아직. 좌우만..
    	//현재 플레이어가 왼쪽에 있을 때 충돌 여부 검사
    	if(x+space <= otherPlayer.x+space && x+width-space >= otherPlayer.x+space) { //충돌됨.
    		return true;
    	}else if(x+space > otherPlayer.x+space && x+space <= otherPlayer.x+space+otherPlayer.width-space) { //충돌됨. (플러이어가 오른쪽일떄)
    		return true;
    	}
 
    	return false;
    }

	void update() {
		
		if(jumping) {
        	jump();
        }
		flyMouse();
		if(pullOther) {
			pullingOther();
		}
		if(left && !collisionCheck()) { //충돌 안했고 왼쪽으로 이동
			xMove(-1);
		}else if(left&& collisionCheck()) {
			xMove(3);
		}
		if(right && !collisionCheck()) { //충돌 안했고 오른으로 이동
			xMove(1);
		}else if(right&& collisionCheck()) {
			xMove(-3);
		}
		right = false;
		left = false;
	}
	
	void jumpingStart() {
		jumping = true;
		up = true;
		down = false;
		jumpStartSec = System.currentTimeMillis();
		//this.playerY -=100;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
		if(!jumping) {
			this.down = true;
			this.up = false;
		}
	}
	public boolean getBook() {
		return book;
	}
	void bookskilStart() {
		book = true;
		bookStartSec = System.currentTimeMillis();
		System.out.println("book 활성화");
	}
	void bookskil() {
		bookEndSec = System.currentTimeMillis();
		if((bookEndSec - bookStartSec)/1000 >= 1) { 
			book = false;
			System.out.println("book 끝남");
		}
	}
	
	int fist(int x) {
		System.out.println("fist 함");
		if(this.x + 200 > x && this.x - 200 < x) {
			System.out.println("맞음");
			return 5;
		}
		return 0;
	}
	
	int noteBook(int x) {
		System.out.println("noteBook 함");
		if(this.x + 200 > x && this.x - 200 < x) {
			System.out.println("맞음");
			return 15;
		}
		return 0;
	}

	public int getMouseX() {
		return mouseX;
	}


	public int getMouseY() {
		return mouseY;
	}

	private boolean mouseCollisionChk() {
    	int space = 30;
    	// 마우스랑 상대방 닿았는지
    	if(mouseX <= otherPlayer.x+space && mouseX+width >= otherPlayer.x+space) { //충돌됨.
    		return true;
    	}else if(mouseX > otherPlayer.x+space && mouseX <= otherPlayer.x+space+otherPlayer.width-space) { //충돌됨. (플러이어가 오른쪽일떄)
    		return true;
    	}
 
    	return false;
    }

	void pullingOther() {
		//상대방 내쪽으로 끌어오는중
		
		if(pullOther && !collisionCheck()) { //끌어오는중이고 충돌 X
			otherPlayer.xMove(location);
		}
		if(pullOther && collisionCheck()) { //끌어오는중이고 충돌 
			pullOther = false;
		}	
	}
	
	void pullOther() {
		//상대방 내쪽으로 끌어오기
		//둘의 거리가 좁을때만 가능하게 하기
		pullOther = true;
		
	}
	
	void throwMouse() {
		//마우스 던지기 시작
		flyMouse = true;
		//마우스 위치 세팅
        mouseX = (int)x;
		//마우스 보이게 함
		for (int i = 0; i < mousePixels.length; i++) {
            mousePixels[i] = preMousePixels[i];
        }
	}
	
	public void flyMouse() {
		//마우스 던지는중
		if(flyMouse) {
			if(mouseCollisionChk()) { //상대방한테 마우스 닿았을때
				//충돌
				flyMouse = false;
				//마우스 없애기.
		        for (int i = 0; i < mousePixels.length; i++) {
		            mousePixels[i] =  0;
		        }
				//상대방 피 깎기
				otherPlayer.hit(100);
			}
			else{
				mouseX+=((location*-1)*mouseSpeed); //location으로 플레이어의 위치에 따라 달라지도록 함
			}
		}
	}
	
}