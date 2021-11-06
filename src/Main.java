
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	static final int MAIN_WIDTH = 1280; // ����ȭ�� ����
	static final int MAIN_HEIGHT = 650; // ����ȭ�� ����
	
	MainScreen mainScreen; //���� ȭ�� �г�
	GameScreen gameScreen; //���� ȭ�� �г�
	HowToGameScreen howGameScreen; //���� ��� ȭ�� �г�
	RankingScreen rankingScreen; //��ŷ ȭ�� �г�
	
	public static void main(String args[])
	{
		new Main();
		System.out.println("Hello!!");
	}
	
	Main() { // ������ ����
		init(); // �����ӿ� �� ������Ʈ ���� �޼ҵ�
		start(); // ���� ��� ó�� �κ�
		setTitle("Fight Project"); // ������ �̸�
		setSize(MAIN_WIDTH, MAIN_HEIGHT); // ������ ũ�� ����
		setLocationRelativeTo(null); //�������� ȭ���� �߰����� ����
		setResizable(false); // ���Ƿ� ������ ũ�� ���� x. â ũ�⸦ ����.
		//setUndecorated(true); //��ܹ� ���ֱ�
		setVisible(true); // ������ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�� ������ �ؾ� Ŭ�����Ҷ� ���α׷��� ����. ���ϸ� ��� ���ư�
	}
	public void init() {
		
		mainScreen = new MainScreen(); //���� ȭ�� �г�
		gameScreen = new GameScreen(); //���� ȭ�� �г�
		howGameScreen = new HowToGameScreen(); //���� ��� ȭ�� �г�
		rankingScreen = new RankingScreen(); //��ŷ ȭ�� �г�
		
		add(mainScreen); //���� �����ӿ� ���� ȭ�� �г��� ����ش�.
		//������ �гε��� ������ �ʰ� �����صд�.
		add(gameScreen); gameScreen.setVisible(false);
		add(howGameScreen); howGameScreen.setVisible(false);
		add(rankingScreen); rankingScreen.setVisible(false);

		//��� ��ư�鿡 �׼� �����ʸ� �޾��ش�.
		mainScreen.goGameStart.addActionListener(buttonClick);
		mainScreen.goHowToGame.addActionListener(buttonClick);
		mainScreen.goRanking.addActionListener(buttonClick);
		mainScreen.goExit.addActionListener(buttonClick);
		//���� ȭ�� �̵� ��ư�鿡 �׼� ������ �޾��ֱ�
		gameScreen.goMainScreen.addActionListener(buttonClick);
		howGameScreen.goMainScreen.addActionListener(buttonClick);
		rankingScreen.goMainScreen.addActionListener(buttonClick);
	}
	public void start() {
		
	}


	//�׼� ������ �������̽� ������ ���� ��ü�� ���� �����Ѵ�. (��ư�� Ŭ�������ÿ� �� ����� �����Ų��.)
	public ActionListener buttonClick = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        	JButton button = (JButton)e.getSource();
        	if(button == mainScreen.goGameStart) {
        		System.out.println("���� ���� ��ư Ŭ����");
        		//���� �г��� ������ �ʰ� �ϰ� ���� �г��� ���̰� ��
        		mainScreen.setVisible(false); gameScreen.setVisible(true);
        	}
        	if(button == mainScreen.goHowToGame) {
        		System.out.println("���� ��� ��ư Ŭ����");
        		mainScreen.setVisible(false); howGameScreen.setVisible(true);
        	}
        	if(button == mainScreen.goRanking) {
        		System.out.println("��ŷ ��ư Ŭ����");
        		mainScreen.setVisible(false); rankingScreen.setVisible(true);
        	}
        	if(button == mainScreen.goExit) {
        		System.out.println("���� ���� ��ư Ŭ����");
        		System.exit(0);
        	}
        	//����ȭ�� ���� ��ư �̺�Ʈ
        	if(button == gameScreen.goMainScreen) {
        		System.out.println("����ȭ������ ���� ��ư Ŭ����");
        		gameScreen.setVisible(false); mainScreen.setVisible(true);
        	}
        	if(button == rankingScreen.goMainScreen) {
        		System.out.println("����ȭ������ ���� ��ư Ŭ����");
        		rankingScreen.setVisible(false); mainScreen.setVisible(true);
        	}
        	if(button == howGameScreen.goMainScreen) {
        		System.out.println("����ȭ������ ���� ��ư Ŭ����");
        		howGameScreen.setVisible(false); mainScreen.setVisible(true);
        	}
        	
        }    
    };
	
}
