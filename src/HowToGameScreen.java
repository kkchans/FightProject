import javax.swing.*;

public class HowToGameScreen extends ImagePanel{
	JButton goMainScreen = new JButton("��");
	public HowToGameScreen() {
		//��� �̹��� ����
		super(new ImageIcon("./img/howToGameBackground.png").getImage(),
				main.MAIN_WIDTH, main.MAIN_HEIGHT);
		
		goMainScreen.setBounds(main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
	}

}
