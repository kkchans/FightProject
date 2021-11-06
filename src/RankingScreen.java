import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RankingScreen extends ImagePanel{
	JButton goMainScreen = new JButton("ก็");
	public RankingScreen() {
		super(new ImageIcon("./img/rankingBackgroud.png").getImage(),
				main.MAIN_WIDTH, main.MAIN_HEIGHT);
		
		goMainScreen.setBounds(main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
		
	}

}
