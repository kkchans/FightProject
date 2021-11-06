import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameScreen extends ImagePanel {
	JButton goMainScreen = new JButton("ก็");
	public GameScreen() {
		super(new ImageIcon("./img/gameplayBackground.png").getImage(),
				main.MAIN_WIDTH, main.MAIN_HEIGHT);
		
		goMainScreen.setBounds(main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
		
	}

}
