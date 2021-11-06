
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class main extends JFrame{
	static final int MAIN_WIDTH = 1280; // 메인화면 넓이
	static final int MAIN_HEIGHT = 650; // 메인화면 높이
	
	MainScreen mainScreen = new MainScreen(); //메인 화면 패널 
	
	public static void main(String args[])
	{
		// TODO Auto-generated method stub
		new main();
		System.out.println("Hello!!");
	}
	
	
	main() { // 프레임 생성
		
		add(mainScreen); //메인 프레임에 메인 화면을 띄워준다.

		//모든 버튼들에 액션 리스너를 달아준다.
		mainScreen.goGameStart.addActionListener(buttonClick);
		mainScreen.goHowToGame.addActionListener(buttonClick);
		mainScreen.goRanking.addActionListener(buttonClick);
		mainScreen.goExit.addActionListener(buttonClick);

		init(); // 프레임에 들어갈 컴포넌트 세팅 메소드
		start(); // 시작 명령 처리 부분
		setTitle("Fight Project"); // 프레임 이름
		setSize(MAIN_WIDTH, MAIN_HEIGHT); // 프레임 크기 설정
		setLocationRelativeTo(null); //프레임을 화면의 중간으로 설정
		setResizable(false); // 임의로 프레임 크기 설정 x. 창 크기를 고정.
		setVisible(true); // 프레임 띄우기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//이 설정을 해야 클로즈할때 프로그램이 꺼짐. 안하면 계속 돌아감
	}
	public void init() {

	}
	public void start() {
		
	}


	//액션 리스너 인터페이스 변수에 구현 객체를 만들어서 대입한다. (버튼을 클릭했을시에 이 기능을 실행시킨다.)
	public ActionListener buttonClick = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        	JButton button = (JButton)e.getSource();
        	if(button == mainScreen.goGameStart) {
        		System.out.println("게임 시작 버튼 클릭됨");
        	}
        	if(button == mainScreen.goHowToGame) {
        		System.out.println("게임 방법 버튼 클릭됨");
        	}
        	if(button == mainScreen.goRanking) {
        		System.out.println("랭킹 버튼 클릭됨");
        	}
        	if(button == mainScreen.goExit) {
        		System.out.println("게임 종료 버튼 클릭됨");
        		System.exit(0);
        	}
        }    
    };
	
}
