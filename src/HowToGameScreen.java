import javax.swing.*;

public class HowToGameScreen extends ImagePanel{
	JButton goMainScreen = new JButton("←");
	public HowToGameScreen() {
		super(new ImageIcon("./img/gameStory.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
	}

}
