import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RankingScreen extends ImagePanel{
	JButton goMainScreen = new JButton("←");
	public RankingScreen() {
		super(new ImageIcon("./img/ranking.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		DBManager dbmg = new DBManager();
		JLabel t = new JLabel(dbmg.selectOne());
	      t.setBounds(500, 200, 100, 100);
	      t.setFont(new Font("Helvetica", Font.BOLD, 25));
	      add(t);
//	      
//	      JLabel t2 = new JLabel("");
//	      t2.setBounds(650, 200, 100, 100);
//	      t2.setFont(new Font("Helvetica", Font.BOLD, 25));
//	      add(t2);
//	      
//	      JLabel t3 = new JLabel("");
//	      t3.setBounds(500, 300, 100, 100);
//	      t3.setFont(new Font("Helvetica", Font.BOLD, 25));
//	      add(t3);
	      
	      goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
	      add(goMainScreen);
		
	}
}


