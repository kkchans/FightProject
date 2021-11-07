
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameScreen extends ImagePanel {
	
	Player player1;
	Player player2;
	
	boolean b_gameStart;
	
	//test
	Image buffImage;
	Graphics buffg;
	Image player;
	Image background;
	Graphics g;
	Toolkit tk = Toolkit.getDefaultToolkit();
	MyKeyListener myListener = new MyKeyListener(); //�����ӿ��� ����͸� �޵���
	
	JButton goMainScreen = new JButton("��"); //�ڷΰ��� ��ư
	Image[] Player_img; //�÷��̾� �ִϸ��̼� ǥ���� ���� �̹����� �迭�� ����
	
	public GameScreen() {
		//��� �̹��� ����
		super(new ImageIcon("./img/testImg.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		//�ڷΰ��� ��ư ����!
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
		
		gameInit();
		gameStart();
		//Ű

	}

	void gameInit() {
		player1 = new Player(0, 390);
		player = tk.getImage("./img/playerTest.png");
		background = tk.getImage("./img/testImg.png");

	}
	
	protected void paintComponent(Graphics g) {
		buffImage = createImage(Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		buffg = buffImage.getGraphics();
		
		buffg.clearRect(0, 0, Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		buffg.drawImage(background, 0, 0, this);
		buffg.drawImage(player, player1.getX(), player1.getY(), this);
		
		g.drawImage(buffImage, 0, 0, this);
	}
	
	void gameStart() {
		if(b_gameStart) {
			
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
				case KeyEvent.VK_UP:
					//����
					player1.jump();
					break;
				case KeyEvent.VK_DOWN:
					break;
				case KeyEvent.VK_LEFT:
					player1.move(-1);
					break;
				case KeyEvent.VK_RIGHT:
					player1.move(1);
					break;
				}
				System.out.println(player1.getX());
				repaint(); //�������� �׸� �ٽ� �׸���
				System.out.println("KeyPressed"); // �ܼ�â�� �޼ҵ� �̸� ���
			}
		}

		public void keyReleased(KeyEvent e) {
			if(b_gameStart) {
				System.out.println("keyReleased"); // �ܼ�â�� �޼ҵ� �̸� ���
			}
		}
		
	}

	
}
