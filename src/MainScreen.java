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
				main.MAIN_WIDTH, main.MAIN_HEIGHT);
		
		//��ư�� ��ġ, ũ�� ����
		goGameStart.setBounds(main.MAIN_WIDTH/2-150, main.MAIN_HEIGHT/2+50, 300, 40);
		goHowToGame.setBounds(main.MAIN_WIDTH/2-150, main.MAIN_HEIGHT/2+100, 300, 40);
		goRanking.setBounds(main.MAIN_WIDTH/2-150, main.MAIN_HEIGHT/2+150, 300, 40);
		goExit.setBounds(main.MAIN_WIDTH/2-150, main.MAIN_HEIGHT/2+200, 300, 40);
		
		add(title);
		add(goGameStart);
		add(goHowToGame);
		add(goRanking);
		add(goExit);
	}
	
}
