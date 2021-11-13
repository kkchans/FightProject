
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class GameScreen extends JFrame implements Runnable {
	Player player1;
	Player player2;
	
	JPanel ui;
	boolean b_gameStart;
	
	//test
	Image buffImage;
	Graphics buffg;
	Image player;
	Image background;
	Graphics g;
	Toolkit tk = Toolkit.getDefaultToolkit();
	MyKeyListener myListener = new MyKeyListener(); //�����ӿ��� ����͸� �޵���
	
	JButton goMainScreen; //�ڷΰ��� ��ư
	Image[] Player_img; //�÷��̾� �ִϸ��̼� ǥ���� ���� �̹����� �迭�� ����
	
	boolean keyUp = false;
	boolean keyLeft = false;
	boolean keyRight = false;

	Thread th;
	
	public GameScreen() {
		addKeyListener(myListener);
		setFocusable(true);
		
		setTitle("test"); // ������ �̸�
		setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT); // ������ ũ�� ����
		setLocationRelativeTo(null); //�������� ȭ���� �߰����� ����
		setResizable(false); // ���Ƿ� ������ ũ�� ���� x. â ũ�⸦ ����.
		//setUndecorated(true); //��ܹ� ���ֱ�
		setVisible(true); // ������ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�� ������ �ؾ� Ŭ�����Ҷ� ���α׷��� ����. ���ϸ� ��� ���ư�
		
		init();
		b_gameStart = true;
		gameInit();
		gameStart();
		
		//Ű
	}
	
	void init() {
		setLayout(null);
		ui = new ImagePanel(tk.getImage("./img/stage1.png"), Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		ui.setLayout(null);
		goMainScreen = new JButton("��");
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30); //�̰� �Ϸ��� setLayout(null); ������� 
		goMainScreen.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        		System.out.println("����ȭ������ ���� ��ư Ŭ����");
	        		dispose(); //������ ���ֱ�(?)
	        		new Main();
	        }});
		goMainScreen.setVisible(true);
		add(goMainScreen);
		//add(ui);
		ui.setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		ui.setVisible(true);
	}
	
	void gameInit() {
		player1 = new Player(0, 390);
		player = tk.getImage("./img/playerTest.png");
		background = tk.getImage("./img/stage1.jpg");
	}
	
	public void paint(Graphics g) {
		buffImage = createImage(Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		buffg = buffImage.getGraphics();
		buffg.clearRect(0, 0, Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		buffg.drawImage(background, 0, 0, this);
		buffg.drawImage(player, player1.getX(), player1.getY(), this);
		
		g.drawImage(buffImage, 0, 0, this);
		
	}
	protected void paintComponent(Graphics g) {
//		buffImage = createImage(Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
//		buffg = buffImage.getGraphics();
//		buffg.clearRect(0, 0, Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
//		buffg.drawImage(background, 0, 0, this);
//		buffg.drawImage(player, player1.getX(), player1.getY(), this);
//		
//		g.drawImage(buffImage, 0, 0, this);
//		ui.setVisible(true);
//		super.paintComponents(g);
	}
	
	void gameStart() {
		th = new Thread(this);
		th.start();
	}


	@Override
	public void run() {
		if(b_gameStart) {
			while(true) {
				KeyProcess();
				if(player1.getJumping()) {
					player1.jump();
				}
				repaint(); //�׸� �ٽ� �׸���
			}
		}
	}
	
	void KeyProcess() {
		if(keyUp) {
			//����
			//���� �Ŀ� ���ٴ�����
			player1.jumpingStart();
			keyUp = false;
		}
		if(keyLeft) {
			player1.xMove(-1);
			keyLeft = false;
		}
		if(keyRight) {
			player1.xMove(1);
			keyRight = false;
		}
	}
			
	public class MyKeyListener extends KeyAdapter{
		
		public void keyTyped(KeyEvent e) {
			if(b_gameStart) {
				System.out.println("keyTyped"); // �ܼ�â�� �޼ҵ� �̸� ���
			}
		}

		public void keyPressed(KeyEvent e) {
			if(b_gameStart) {
				switch (e.getKeyCode()) { //Ű �ڵ� �˾Ƴ���
					case KeyEvent.VK_UP:	keyUp = true;		break;
					case KeyEvent.VK_LEFT:	keyLeft = true;		break;
					case KeyEvent.VK_RIGHT:	keyRight = true;	break;
				}
				System.out.println("KeyPressed"); // �ܼ�â�� �޼ҵ� �̸� ���
			}
		}

		public void keyReleased(KeyEvent e) {
			if(b_gameStart) {
				System.out.println("keyReleased"); // �ܼ�â�� �޼ҵ� �̸� ���
			}
		}//keyReleased END
		
	}//MyKeyListener CLASS END
}
