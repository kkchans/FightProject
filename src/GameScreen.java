
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameScreen extends ImagePanel {
	
	Player player1;
	Player player2;
	
	JButton goMainScreen = new JButton("��"); //�ڷΰ��� ��ư
	Image[] Player_img; //�÷��̾� �ִϸ��̼� ǥ���� ���� �̹����� �迭�� ����
	
	public GameScreen() {
		//��� �̹��� ����
		super(new ImageIcon("./img/testImg.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		//�ڷΰ��� ��ư ����!
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
		
		gameInit();
	}

	void gameInit() {
		
	}
	
}
