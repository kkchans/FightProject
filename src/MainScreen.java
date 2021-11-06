import java.awt.Font;

import javax.swing.*;

public class MainScreen extends ImagePanel {
	JLabel title = new JLabel("Fight Project");
	JButton goGameStart = new JButton("���� ����");	//���� ���� ��ư
	JButton goHowToGame = new JButton("���� ���");	//���� ��� ���� ��ư
	JButton goRanking = new JButton("��ŷ");	//��ŷ ��ư
	JButton goExit = new JButton("���� ����");	//���� ��ư
	
	MainScreen() { // �г� ����
		//�̹��� �г� ����(��� �̹���)
		super(new ImageIcon("./img/background.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		title.setBounds(Main.MAIN_WIDTH/2-200, 100, 400, 100);
		title.setHorizontalAlignment(JLabel.CENTER); //��� ����
		title.setFont(new Font("Helvetica", Font.BOLD, 40));//Font font = new Font(��Ʈ �̸�, ��Ʈ ��Ÿ��, ��Ʈ ũ��);
		
		//��ư�� ��ġ, ũ�� ����
		goGameStart.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+50, 300, 40);
		goHowToGame.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+100, 300, 40);
		goRanking.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+150, 300, 40);
		goExit.setBounds(Main.MAIN_WIDTH/2-150, Main.MAIN_HEIGHT/2+200, 300, 40);
		
		add(title);
		add(goGameStart);
		add(goHowToGame);
		add(goRanking);
		add(goExit);
	}
	
}
