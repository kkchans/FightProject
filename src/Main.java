
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	static final int MAIN_WIDTH = 1280; // 메인화면 넓이
	static final int MAIN_HEIGHT = 650; // 메인화면 높이
	
	MainScreen mainScreen; //메인 화면 패널
	GameScreen gameScreen; //게임 화면 패널
	HowToGameScreen howGameScreen; //게임 방법 화면 패널
	RankingScreen rankingScreen; //랭킹 화면 패널
	
	public static void main(String args[])
	{
		new Main();
		System.out.println("Hello!!");
	}
	
	Main() { // 프레임 생성
		init(); // 프레임에 들어갈 컴포넌트 세팅 메소드
		start(); // 시작 명령 처리 부분
		setTitle("Fight Project"); // 프레임 이름
		setSize(MAIN_WIDTH, MAIN_HEIGHT); // 프레임 크기 설정
		setLocationRelativeTo(null); //프레임을 화면의 중간으로 설정
		setResizable(false); // 임의로 프레임 크기 설정 x. 창 크기를 고정.
		//setUndecorated(true); //상단바 없애기
		setVisible(true); // 프레임 띄우기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//이 설정을 해야 클로즈할때 프로그램이 꺼짐. 안하면 계속 돌아감
	}
	public void init() {
		
		mainScreen = new MainScreen(); //메인 화면 패널
		gameScreen = new GameScreen(); //게임 화면 패널
		howGameScreen = new HowToGameScreen(); //게임 방법 화면 패널
		rankingScreen = new RankingScreen(); //랭킹 화면 패널
		
		add(mainScreen); //메인 프레임에 메인 화면 패널을 띄워준다.
		//나머지 패널들은 보이지 않게 설정해둔다.
		add(gameScreen); gameScreen.setVisible(false);
		add(howGameScreen); howGameScreen.setVisible(false);
		add(rankingScreen); rankingScreen.setVisible(false);

		//모든 버튼들에 액션 리스너를 달아준다.
		mainScreen.goGameStart.addActionListener(buttonClick);
		mainScreen.goHowToGame.addActionListener(buttonClick);
		mainScreen.goRanking.addActionListener(buttonClick);
		mainScreen.goExit.addActionListener(buttonClick);
		//메인 화면 이동 버튼들에 액션 리스너 달아주기
		gameScreen.goMainScreen.addActionListener(buttonClick);
		howGameScreen.goMainScreen.addActionListener(buttonClick);
		rankingScreen.goMainScreen.addActionListener(buttonClick);
	}
	public void start() {
		
	}


	//액션 리스너 인터페이스 변수에 구현 객체를 만들어서 대입한다. (버튼을 클릭했을시에 이 기능을 실행시킨다.)
	public ActionListener buttonClick = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        	JButton button = (JButton)e.getSource();
        	if(button == mainScreen.goGameStart) {
        		System.out.println("게임 시작 버튼 클릭됨");
        		//메인 패널을 보이지 않게 하고 게임 패널을 보이게 함
        		mainScreen.setVisible(false); gameScreen.setVisible(true);
        	}
        	if(button == mainScreen.goHowToGame) {
        		System.out.println("게임 방법 버튼 클릭됨");
        		mainScreen.setVisible(false); howGameScreen.setVisible(true);
        	}
        	if(button == mainScreen.goRanking) {
        		System.out.println("랭킹 버튼 클릭됨");
        		mainScreen.setVisible(false); rankingScreen.setVisible(true);
        	}
        	if(button == mainScreen.goExit) {
        		System.out.println("게임 종료 버튼 클릭됨");
        		System.exit(0);
        	}
        	//메인화면 가기 버튼 이벤트
        	if(button == gameScreen.goMainScreen) {
        		System.out.println("메인화면으로 가는 버튼 클릭됨");
        		gameScreen.setVisible(false); mainScreen.setVisible(true);
        	}
        	if(button == rankingScreen.goMainScreen) {
        		System.out.println("메인화면으로 가는 버튼 클릭됨");
        		rankingScreen.setVisible(false); mainScreen.setVisible(true);
        	}
        	if(button == howGameScreen.goMainScreen) {
        		System.out.println("메인화면으로 가는 버튼 클릭됨");
        		howGameScreen.setVisible(false); mainScreen.setVisible(true);
        	}
        	
        }    
    };
	
}
