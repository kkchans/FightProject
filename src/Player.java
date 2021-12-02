import java.awt.image.DataBufferInt;

public class Player {
	private float speed;
	private int mouseSpeed = 15;
	private float x, y;
	private int width, height;
	private long jumpStartSec;
	private long jumpEndSec;
	private long bookStartSec;
	private long bookEndSec;
	private boolean jumping;
	private boolean up, down, left, right;
	private boolean book;
	boolean flyMouse;
	private int mouseX=0, mouseY=400;//마우스 좌표
	private Player otherPlayer;
	private int location; //왼쪽이면 -1, 1
	private int attackRange = 200;
	private int[] playerAllPixels;
	private int[] playerPixels;
	private int hp;
	playerState posture;
	private static enum playerState{
		basic1,			//기본자세1
		basic2,			//기본자세2
		mouseReady,		//마우스 던질 준비
		throwThing,		//물건 던지기(마우스, 충전기선)
		chargerPull,	//충전기 당기기
		fist,			//주먹질
		notebookHit,	//노트북 공격
		notebooHitRdy,	//노트북 공격 자세
		run1,			//달리기1
		run2,			//달리기2
		beHit,			//맞는자세
		jump,			//점프
		chargerRdy,		//충전기 던질 준비
		beWrapped,		//충전기 선에 감김
		defense,		//방어
		charger,		//충전기 선
		mouse,			//마우스
	}
	
	Skill noteBookSkill = new Skill();
	Skill fistSkill = new Skill();
	Skill mouseSkill = new Skill();
	Skill defenseSkill = new Skill();
	Skill pullOtherSkill = new Skill();
	
	//생성될때
	Player(float x, float y, int width, int height, int hp, int location){
		posture = playerState.basic1;
		jumping = false;
		this.hp = hp;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		speed = 5;
		this.location = location;
		
		defenseSkill.duration = 3.0f;
		
		noteBookSkill.coolTime = 3.0f;
		fistSkill.coolTime = 0.1f;
		mouseSkill.coolTime = 2.0f;
		defenseSkill.coolTime = 5.0f;
		pullOtherSkill.coolTime = 5.0f;
	}
	   
	
	void setAllPixels(String src) throws Exception {
		//플레이어의 전체 이미지를 불러와서 픽셀 데이터로 저장해둔다.
		playerAllPixels = ((DataBufferInt) (ImageRelation.ImageLoad(src)).getRaster().getDataBuffer()).getData();
	}

	void setPixels(int[] pixels) {
		//플레이어의 이미지를 조작하기 위해 저장해둔다.
		playerPixels = pixels;
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
	
	long movePreTime = System.currentTimeMillis();
	//좌우 이동
	void xMove(float playerX) {
		this.x += playerX*speed;
		if(System.currentTimeMillis() - movePreTime >= 100) { //0.3초마다
			if(posture == playerState.run1) { posture = playerState.run2; }
			else if(posture == playerState.run2) { posture = playerState.run1; }
			movePreTime = System.currentTimeMillis();
		}
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
				if(jumpEndSec - jumpStartSec >= 0.5) //올라감
				{
					yMove(-4);
					jumpStartSec = System.currentTimeMillis();
					if(y == Main.MAIN_HEIGHT-height-100) { up = false; down = true; }
				}
			}else if(y <= Main.MAIN_HEIGHT-height && down) { //낙하
				jumpEndSec = System.currentTimeMillis();
				if(jumpEndSec - jumpStartSec >= 0.1) //내려옴
				{
					yMove(4);
					jumpStartSec = System.currentTimeMillis();
					if(y == Main.MAIN_HEIGHT-height) {
						//점프가 끝날때
						down = false; jumping = false;
						posture = playerState.basic1;
					}
				}
			}
		}
		
	}
	
	int getX() {
		return (int)x;
	}
	void setX(float x) {
		this.x = x;
	}
	
	int getY() {
		return (int)y;
	}
	
	boolean getJumping() {
		return jumping;
	}

	private boolean collisionCheck() {
    	int space = 100;
    	// 점프시 충돌은 아직. 좌우만..
    	//현재 플레이어가 왼쪽에 있을 때 충돌 여부 검사
    	
    	if(x+space <= otherPlayer.x+space && x+width-space >= otherPlayer.x+space) { //충돌됨.
    		return true;
    	}
    	if(x+space <= otherPlayer.x+otherPlayer.width-space && x+width-space >= otherPlayer.x+otherPlayer.width-space) { //충돌됨. (플러이어가 오른쪽일떄)
    		return true;
    	}
 
    	return false;
    }

	long preTime = System.currentTimeMillis();
	void update() {
		if(System.currentTimeMillis() - preTime >= 300) { //0.3초마다
			if(posture == playerState.basic1) { posture = playerState.basic2; }
			else if(posture == playerState.basic2) { posture = playerState.basic1; }
			preTime = System.currentTimeMillis();
		}
		if(jumping) {
        	jump();
        }
		flyMouse();
		if(pullOtherSkill.isActivate()) {
			pullingOther();
		}
		if(left && !collisionCheck()) { //충돌 안했고 왼쪽으로 이동
			xMove(-1);
		}
		if(left&& collisionCheck()) { //충돌
			xMove(1);
		}
		if(right && !collisionCheck()) { //충돌 안했고 오른으로 이동
			xMove(1);
		}
		if(right&& collisionCheck()) {//충돌
			xMove(-1);
		}
		if(!left && !right && (posture == playerState.run1||posture == playerState.run2)) { posture = playerState.basic1; } //안움직이면 basic으로
		if(!defenseSkill.isActivate() && posture == playerState.defense) { posture = playerState.basic1;  }
		
	}
	
	void render() {
		
		//플레이어 이미지
		for(int i = 0; i < 310; i++) {
    		for(int j = 0; j < 310; j++) {
    			//ordinal는 enum 값의 리스트 번호를 정수값으로 가져옴(0부터 시작)
    			playerPixels[(width*j)+i] = playerAllPixels[(1860*(j+(height*(posture.ordinal()/6))))+(i+(width*(posture.ordinal()%6)))];
    		}
    	}
	}
	
	
	
	void jumpingStart() {
		posture = playerState.jump;
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
		if(left) { 	posture = playerState.run1; }
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
		if(right) { 	posture = playerState.run1; }
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
	
	
	void DefenseSkillStart() {
		if(defenseSkill.Cooltime()) {
			posture = playerState.defense;
			defenseSkill.skillStart();
			System.out.println("방어 시작");
		}
	}
	void defenseSkillProcess() {
		if(defenseSkill.skillIng()) { 
			posture = playerState.basic1;
			defenseSkill.skillEnd();
			System.out.println("book 끝남");
		}else {
			
		}
	}
	
	//주먹질
	int fist() {
		System.out.println("fist 함");
		if(fistSkill.Cooltime()) {
			fistSkill.skillStart();
			if(otherPlayer.defenseSkill.activate == true) return 0; //다른 플레이어가 방어중이면 주먹 효과 0
	
			if(x + attackRange > otherPlayer.getX() && x - attackRange < otherPlayer.getX()) {
				posture = playerState.fist;
				System.out.println("맞음");
				otherPlayer.posture = playerState.beHit;
				Sound.fistBgm();
				return 5;
			}
		}
		return 0;
	}
	
	int noteBook() {
		System.out.println("noteBook 함");
		if(noteBookSkill.Cooltime()) {
			noteBookSkill.skillStart();
			if(otherPlayer.book == true) return 0;
			if(x + attackRange > otherPlayer.getX() && x - attackRange < otherPlayer.getX()) {
				System.out.println("맞음");
				posture = playerState.notebookHit;
				Sound.fistBgm();
				return 20;
			}
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
    	int space = 50;
    	// 마우스랑 상대방 닿았는지
    	if(mouseX <= otherPlayer.x+space && mouseX+width >= otherPlayer.x+space) { //충돌됨.
    		Sound.fistBgm();
    		return true;
    	}else if(mouseX > otherPlayer.x+space && mouseX <= otherPlayer.x+space+otherPlayer.width-space) { //충돌됨. (플러이어가 오른쪽일떄)
    		Sound.fistBgm();
    		return true;
    	}
 
    	return false;
    }

	void pullingOther() {
		//상대방 내쪽으로 끌어오는중
		
		if(pullOtherSkill.isActivate() && !collisionCheck()) { //끌어오는중이고 충돌 X
			otherPlayer.xMove(location*2);//빠르게 오도록 함
		}
		if(pullOtherSkill.isActivate() && collisionCheck()) { //끌어오는중이고 충돌 
			pullOtherSkill.skillEnd();
		}	
	}
	
	
	
	void pullOther() {
		//상대방 내쪽으로 끌어오기
		//둘의 거리가 좁을때만 가능하게 하기
//		pullOther = true;
		if(pullOtherSkill.Cooltime()) {
			pullOtherSkill.skillStart();
		}
	}
	
	boolean getFlyMouse() {
		return flyMouse;
	}
	
	void throwMouse() {
		if(mouseSkill.Cooltime()) {
			mouseSkill.skillStart();
			//마우스 던지기 시작
			flyMouse = true;
			//마우스 위치 세팅
	        mouseX = (int)x;
		}
	}

	public void flyMouse() {
		//마우스 던지는중
		if(flyMouse) {
			if(mouseCollisionChk()) { //상대방한테 마우스 닿았을때
				//충돌
				flyMouse = false;
				//상대방 피 깎기
				otherPlayer.hit(10);
			}
			else{
				mouseX+=((location*-1)*mouseSpeed); //location으로 플레이어의 위치에 따라 달라지도록 함
			}
		}
	}
	
	
	
}