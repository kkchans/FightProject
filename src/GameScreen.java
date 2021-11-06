
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameScreen extends ImagePanel {
	
	Player player1;
	Player player2;
	
	JButton goMainScreen = new JButton("←"); //뒤로가기 버튼
	Image[] Player_img; //플레이어 애니메이션 표현을 위해 이미지를 배열로 받음
	
	public GameScreen() {
		//배경 이미지 띄우기
		super(new ImageIcon("./img/testImg.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		//뒤로가기 버튼 세팅!
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
		
		gameInit();
	}

	void gameInit() {
		
	}
	
}
