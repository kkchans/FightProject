
class Skill {
	//스킬 클래스
	
	long startTime; //스킬 시작 시간
	float coolTime; //스킬 쿨타임
	float duration; //스킬 지속 시간
	boolean activate; //스킬이 활성화 되어있는지.(스킬이 실행중인지)
	
	Skill(){
		activate = false; // 처음에는 활성화가 안되어 있음.
	}
	
	boolean isActivate() {
		return activate;
	}
	
	
	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	void skillStart(){
		//스킬을 시작
		startTime = System.currentTimeMillis();	//쿨타임 체크용
		activate = true; //모션용
	}
	
	void skillEnd(){
		//스킬을 종료
		activate = false;
	}
	
	boolean skillIng() {
		//스킬 지속 시간 안지났으면 true, 지났으면 false
		if((System.currentTimeMillis()-this.startTime)>=this.duration*1000){
			return true;
		}
		else { return false; }
	}
	
	//쿨타임
	boolean Cooltime() {
		   //쿨타임이면 false, 쿨타임이 지났다면 true
	      if((System.currentTimeMillis()-this.startTime)>= this.coolTime*1000 ) {
	    	  return true; //쿨타임이 지났음.(사용 가능)
	      }
	      else { return false; }
	}
	
}