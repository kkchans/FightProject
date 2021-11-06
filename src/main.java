
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class main extends JFrame{
	static final int MAIN_WIDTH = 1280; // ����ȭ�� ����
	static final int MAIN_HEIGHT = 650; // ����ȭ�� ����
	
	MainScreen mainScreen = new MainScreen(); //���� ȭ�� �г� 
	
	public static void main(String args[])
	{
		// TODO Auto-generated method stub
		new main();
		System.out.println("Hello!!");
	}
	
	
	main() { // ������ ����
		
		add(mainScreen); //���� �����ӿ� ���� ȭ���� ����ش�.

		//��� ��ư�鿡 �׼� �����ʸ� �޾��ش�.
		mainScreen.goGameStart.addActionListener(buttonClick);
		mainScreen.goHowToGame.addActionListener(buttonClick);
		mainScreen.goRanking.addActionListener(buttonClick);
		mainScreen.goExit.addActionListener(buttonClick);

		init(); // �����ӿ� �� ������Ʈ ���� �޼ҵ�
		start(); // ���� ��� ó�� �κ�
		setTitle("Fight Project"); // ������ �̸�
		setSize(MAIN_WIDTH, MAIN_HEIGHT); // ������ ũ�� ����
		setLocationRelativeTo(null); //�������� ȭ���� �߰����� ����
		setResizable(false); // ���Ƿ� ������ ũ�� ���� x. â ũ�⸦ ����.
		setVisible(true); // ������ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�� ������ �ؾ� Ŭ�����Ҷ� ���α׷��� ����. ���ϸ� ��� ���ư�
	}
	public void init() {

	}
	public void start() {
		
	}


	//�׼� ������ �������̽� ������ ���� ��ü�� ���� �����Ѵ�. (��ư�� Ŭ�������ÿ� �� ����� �����Ų��.)
	public ActionListener buttonClick = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        	JButton button = (JButton)e.getSource();
        	if(button == mainScreen.goGameStart) {
        		System.out.println("���� ���� ��ư Ŭ����");
        	}
        	if(button == mainScreen.goHowToGame) {
        		System.out.println("���� ��� ��ư Ŭ����");
        	}
        	if(button == mainScreen.goRanking) {
        		System.out.println("��ŷ ��ư Ŭ����");
        	}
        	if(button == mainScreen.goExit) {
        		System.out.println("���� ���� ��ư Ŭ����");
        		System.exit(0);
        	}
        }    
    };
	
}
