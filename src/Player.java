import java.awt.image.DataBufferInt;

public class Player {
	private String playerName;
	private float speed;
	private int mouseSpeed = 15;
	private long score = 0; //플레이어 랭킹에 나오는 스코어
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
	private int mouseX=0, mouseY=500;//마우스 좌표
	private Player otherPlayer;
	private int location; //왼쪽이면 -1, 1
	private int attackRange = 200;
	private int[] playerAllPixels;
	private int[] playerPixels;
	private int hp;
	private boolean movable = true;
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
	
	Skill noteBookSkill = new Skill(0.3f, 3.0f);
	Skill fistSkill = new Skill(0.1f, 0.1f);
	Skill mouseSkill = new Skill(0.1f, 2.0f);
	Skill defenseSkill = new Skill(3.0f, 5.0f);
	Skill pullOtherSkill = new Skill(0f, 5.0f);
	
	//내가 스킬을 쓸 때 상대방은 스킬을 못쓰도록 해야하고
	//pullOther 스킬은 상대방을 내쪽으로 오게 한 후 0.5초가량 공격을 하지 못하도록 한다. 
	
	//생성될때
	Player(float x, float y, int width, int height, int hp, int location, String playerName){
		posture = playerState.basic1;
		jumping = false;
		this.hp = hp;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		speed = 5;
		this.location = location;
		this.playerName = playerName;
	}
	   
	
	public long getScore() {
		return score;
	}

	public void plusScore(long score) {
		this.score += score;
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
	   
	   public void setHp(int hp) {
	      this.hp = hp;
	   }
	   
	   public void setY(int y) {
	      this.y = y;
	   }
	   
	   public void setX(int x) {
	      this.x = x;
	   }
	   
	   public void setbasic() {
	      posture = playerState.basic1;
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
	
	int jumpHeight = 100;
	//점프
	void jump() {
//		if(!collisionCheck(x-location, otherPlayer.x)) { //충돌 안했을때
			if(y >= Main.MAIN_HEIGHT-height-jumpHeight && up && jumping)  {//점프
				jumpEndSec = System.currentTimeMillis();
				if(jumpEndSec - jumpStartSec >= 0.5) //올라감
				{
					yMove(-8);
					jumpStartSec = System.currentTimeMillis();
					if(y == Main.MAIN_HEIGHT-height-jumpHeight) { up = false; down = true; }
				}
			}else if(y <= Main.MAIN_HEIGHT-height && down) { //낙하
				jumpEndSec = System.currentTimeMillis();
				if(jumpEndSec - jumpStartSec >= 0.1) //내려옴
				{
					yMove(8);
					jumpStartSec = System.currentTimeMillis();
					if(y >= Main.MAIN_HEIGHT-height) {
						//점프가 끝날때
						down = false; jumping = false;
						if(posture==playerState.jump)
							posture = playerState.basic1;
					}
				}
			}
//		}
		
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
	

	private boolean collisionCheck(float x, float otherPX) {
    	int space = 100;
    	// 점프시 충돌은 아직. 좌우만..
    	//현재 플레이어가 왼쪽에 있을 때 충돌 여부 검사
    	
    	if(x+space <= otherPX+space && x+width-space >= otherPX+space) { //충돌됨.
    		return true;
    	}
    	if(x+space <= otherPX+otherPlayer.width-space && x+width-space >= otherPX+otherPlayer.width-space) { //충돌됨. (플러이어가 오른쪽일떄)
    		return true;
    	}
    	if(x <= -space*2 || x+width >= Main.MAIN_WIDTH+space*2) { //충돌됨. (플러이어가 오른쪽일떄)
    		return true;
    	}
    	
    	return false;
    }
	
	long preTime = System.currentTimeMillis();
	void update() {
		if(movable) {
			if(System.currentTimeMillis() - preTime >= 300) { //0.3초마다
				if(posture == playerState.basic1) { posture = playerState.basic2; }
				else if(posture == playerState.basic2) { posture = playerState.basic1; }
				preTime = System.currentTimeMillis();
			}
			if(jumping) {
	        	jump();
	        }
			if(mouseSkill.isActivate()) {
				flyMouse();
			}
			if((System.currentTimeMillis()-mouseSkillEndT)>=300 && mouseSkillEnd) { //마우스 맞고 x초 지난 후
				basicPosture();
				mouseSkillEnd = false;
			}
			if(fistSkill.isActivate()) {
				fisting();
			}
			if(noteBookSkill.isActivate()) {
				noteBook_ing();
			}
			if(pullOtherSkill.isActivate()) {
				pullingOther();
			}
			if(left && !collisionCheck(x-1, otherPlayer.x)) { //가려는 곳과 충돌 안했고 왼쪽으로 이동
				xMove(-1);
			}
			if(right && !collisionCheck(x+1, otherPlayer.x)) { //충돌 안했고 오른으로 이동
				xMove(1);
			}
	
			if(!left && !right && (posture == playerState.run1||posture == playerState.run2)) { posture = playerState.basic1; } //안움직이면 basic으로
			if(!defenseSkill.isActivate() && posture == playerState.defense) { posture = playerState.basic1;  }
		}
		//다른 플레이어가 
		
		//스킬들의 시작과 끝 관련
		//시작할때 시간을 기록하는 함수가 하나씩 있다.
		//그리고 그 스킬들을 실행시키는 함수가 있고
		//각 duration 시간이 지나지 않은 동안
		
		
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
	

	boolean pBasicPstreChk() {
		if(posture == playerState.basic1 ||posture == playerState.basic2 || 
				posture == playerState.run1 || posture==playerState.run2) {
			return true;
		}
		return false;
	}
	
	void jumpingStart() {
		if(movable&& !jumping) { 
			//기본
			if(pBasicPstreChk()) {
				posture = playerState.jump;
			}
			jumpHeight = 200;
			jumping = true;
			up = true;
			down = false;
			jumpStartSec = System.currentTimeMillis();
			//this.playerY -=100;
		}
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		if(movable) {
			this.left = left;
			if(left && pBasicPstreChk()) { 	
				posture = playerState.run1;
				}
		}
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		if(movable) {
			this.right = right;
			if(right && pBasicPstreChk()) { 
				posture = playerState.run1; 
			}
		}
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
		if(defenseSkill.Cooltime() && movable) {
			posture = playerState.defense;
			defenseSkill.skillStart();
			System.out.println("방어 시작");
		}
	}
	void defenseSkillProcess() {
		if(!defenseSkill.skillIng()) { 
			posture = playerState.basic1;
			defenseSkill.skillEnd();
			System.out.println("방어 끝남");
		}else {
			
		}
	}
	
	void basicPosture() {
		posture = playerState.basic1;
		otherPlayer.posture = playerState.basic1;
	}
	
	//주먹질
	int fist() {
		System.out.println("fist 함");
		if(fistSkill.Cooltime() && movable) {
			if(otherPlayer.defenseSkill.isActivate() == true) return 0; //다른 플레이어가 방어중이면 주먹 효과 0
	
			if(x + attackRange > otherPlayer.getX() && x - attackRange < otherPlayer.getX()) {
				fistSkill.skillStart();
				posture = playerState.fist;
				System.out.println("맞음");
				otherPlayer.posture = playerState.beHit;
				Sound.fistBgm();
				return 5;
			}
		}
		return 0;
	}
	
	void fisting(){
		if(!fistSkill.skillIng()) {
			basicPosture();
			fistSkill.skillEnd();
		}
	}
	
	int noteBook() {
		System.out.println("noteBook 함");
		if(noteBookSkill.Cooltime()&& movable) {
			if(otherPlayer.book == true) return 0;
			if(x + attackRange > otherPlayer.getX() && x - attackRange < otherPlayer.getX()) {
				noteBookSkill.skillStart();
				System.out.println("맞음");
				posture = playerState.notebookHit;
				otherPlayer.posture = playerState.beHit;
				Sound.fistBgm();
				return 20;
			}
		}
		return 0;
	}
	void noteBook_ing(){
		if(!noteBookSkill.skillIng()) {
			basicPosture();
			noteBookSkill.skillEnd();
		}
	}
	public int getMouseX() {
		return mouseX;
	}


	public int getMouseY() {
		return mouseY;
	}

	private boolean mouseCollisionChk() {
		int space = 40;
    	// 점프시 충돌은 아직. 좌우만..
    	//현재 플레이어가 왼쪽에 있을 때 충돌 여부 검사
    	
    	if(mouseX <= otherPlayer.x+otherPlayer.width-space && mouseX >= otherPlayer.x+space &&
    			mouseY <= otherPlayer.y+otherPlayer.height-space && mouseY >= otherPlayer.y+space) { //충돌됨.
    		Sound.fistBgm();
    		return true;
    	}
 
    	return false;
		
    }

	void pullingOther() {
		//상대방 내쪽으로 끌어오는중
		
		if(pullOtherSkill.isActivate() && !collisionCheck(x-location, otherPlayer.x+(location*4))) { //끌어오는중이고 충돌 X
			otherPlayer.setMovable(false);
			otherPlayer.xMove(location*2);//빠르게 오도록 함
		}else if(pullOtherSkill.isActivate() && collisionCheck(x-location, otherPlayer.x+(location*4))) { //끌어오는중이고 충돌 
			otherPlayer.setMovable(true);
			pullOtherSkill.skillEnd();
		}	
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}


	void pullOther() {
		//상대방 내쪽으로 끌어오기
		//둘의 거리가 좁을때만 가능하게 하기
//		pullOther = true;
		if(pullOtherSkill.Cooltime()&& movable) {
			pullOtherSkill.skillStart();
		}
	}
	
	boolean getFlyMouse() {
		return flyMouse;
	}
	
	void throwMouse() {
		if(mouseSkill.Cooltime() && movable) {
			mouseSkill.skillStart();
			//마우스 던지기 시작
			flyMouse = true;
			//마우스 위치 세팅
	        mouseX = (int)x+(60*location);
	        if(location==-1) {
	        	//왼쪽 사람이면
	        	mouseX+=width;
	        }
		}
		
	}
	
	boolean mouseSkillEnd;
	long mouseSkillEndT;
	public void flyMouse() {
		//마우스 던지는중
		if(flyMouse) {
			if(mouseCollisionChk()) { //상대방한테 마우스 닿았을때
	    		otherPlayer.posture = playerState.beHit;
				//충돌
				flyMouse = false;
				//상대방 피 깎기
				otherPlayer.hit(10);
				mouseSkill.skillEnd();
				mouseSkillEndT = System.currentTimeMillis();
				mouseSkillEnd = true;
			}
			else{
				mouseX+=((location*-1)*mouseSpeed); //location으로 플레이어의 위치에 따라 달라지도록 함
			}
		}
	}
	
	
	
}