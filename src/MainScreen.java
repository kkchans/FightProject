import java.awt.Font;

import javax.swing.*;

public class MainScreen extends ImagePanel {
	JButton goGameStart = new JButton("게임 시작");	//게임 시작 버튼
	JButton goHowToGame = new JButton("게임 방법");	//게임 방법 보기 버튼
	JButton goRanking = new JButton("랭킹");	//랭킹 버튼
	JButton goExit = new JButton("게임 종료");	//종료 버튼
	
	MainScreen() { // 패널 생성
		//이미지 패널 띄우기(배경 이미지)
		super(new ImageIcon("./img/mainImg.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		//버튼들 위치, 크기 조정
		goGameStart.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+50, 300, 40);
		goHowToGame.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+100, 300, 40);
		goExit.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+150, 300, 40);
		//goExit.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+200, 300, 40);
		
		add(goGameStart);
		add(goHowToGame);
		add(goExit);
	}
	
}