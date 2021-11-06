import javax.swing.*;

public class HowToGameScreen extends ImagePanel{
	JButton goMainScreen = new JButton("←");
	public HowToGameScreen() {
		//배경 이미지 설정
		super(new ImageIcon("./img/howToGameBackground.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
	}

}
