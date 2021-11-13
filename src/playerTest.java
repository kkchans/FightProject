
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class playerTest extends JFrame {
	GamePanel gameP;
	
	boolean keyUp = false;
	boolean keyLeft = false;
	boolean keyRight = false;

	Thread th;
	
	public static void main(String args[]) {
		new playerTest();
	}
	
	public playerTest() {
		
		gameP = new GamePanel();
	
		add(gameP);
		addKeyListener(gameP.myListener);
		setFocusable(true);
		
		setTitle("test"); // ������ �̸�
		setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT); // ������ ũ�� ����
		setLocationRelativeTo(null); //�������� ȭ���� �߰����� ����
		setResizable(false); // ���Ƿ� ������ ũ�� ���� x. â ũ�⸦ ����.
		//setUndecorated(true); //��ܹ� ���ֱ�
		setVisible(true); // ������ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�� ������ �ؾ� Ŭ�����Ҷ� ���α׷��� ����. ���ϸ� ��� ���ư�
	}

	public class GamePanel extends JPanel implements Runnable {
		
		Player player1;
		Player player2;
		
		boolean b_gameStart;
		
		//�̹���
		Graphics2D graphics;
		BufferedImage buffImage;
		BufferedImage player1_img;
		BufferedImage player2_img;
		BufferedImage background_img;
		BufferedImage mainImg;
		BufferedImage rgbImage;
		int[] img_pixels;
		
		//test
		
		//Graphics g;
		Toolkit tk = Toolkit.getDefaultToolkit();
		MyKeyListener myListener = new MyKeyListener(); //�����ӿ��� ����͸� �޵���
		
		JButton goMainScreen = new JButton("��"); //�ڷΰ��� ��ư
		Image[] Player_img; //�÷��̾� �ִϸ��̼� ǥ���� ���� �̹����� �迭�� ����
		
		public GamePanel() {
			init();
			b_gameStart = true;
			gameInit();
			gameStart();
			//Ű
	
		}
	
		void init() {
			setLayout(null);
			goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30); //�̰� �Ϸ��� setLayout(null); ������� 
			goMainScreen.addActionListener(new ActionListener(){
		        public void actionPerformed(ActionEvent e) {
		        		System.out.println("����ȭ������ ���� ��ư Ŭ����");
		        		dispose(); //������ ���ֱ�(?)
		        		new Main();
		        }});
			add(goMainScreen);
		}
		
		void gameInit() {
			player1 = new Player(0, 390);
			player2 = new Player(Main.MAIN_WIDTH-300, 390);
			File imageFile = new File("./img/playerTest.png");
			if(!imageFile.exists()) {System.out.println("���� �ȿ���");}
			try {
				player1_img = ImageIO.read(imageFile);
			} catch (IOException e) {e.printStackTrace(); }
			//imageFile = new File("./img/background.png");
			mainImg = new BufferedImage(Main.MAIN_WIDTH, Main.MAIN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			img_pixels = ((DataBufferInt)mainImg.getRaster().getDataBuffer()).getData();
			rgbImage = new BufferedImage(player1_img.getWidth(), player1_img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			for(int x = 0; x < player1_img.getWidth(); x++) {
				for(int y = 0; y < player1_img.getHeight(); y++) {
					rgbImage.setRGB(x, y, player1_img.getRGB(x, y));
				}
			}
			for(int i = 0; i < img_pixels.length; i++) {
				img_pixels[i] = rgbImage.getRGB(0, 0);
				img_pixels[i] = rgbImage.getRGB(1, 0);
				img_pixels[i] = rgbImage.getRGB(2, 0);
			}
		}
		
//		public void paint(Graphics2D g) {
//			graphics = (Graphics2D)buffImage.getGraphics();
//		}
		
		protected void paintComponent(Graphics g) {

			
			
//			buffImage = createImage(Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
//			buffg = buffImage.getGraphics();
//			
//			buffg.clearRect(0, 0, Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
//			buffg.drawImage(background, 0, 0, this);
//			buffg.drawImage(player1_img, player1.getX(), player1.getY(), this);
//			//buffg.drawImage(player2_img, player2.getX(), player2.getY(), this);
//			
			//g.drawImage(buffImage, 0, 0, this);
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
					
				  BufferStrategy bs = getBufferStrategy();

			        Graphics g = bs.getDrawGraphics();

			        g.setColor(Color.BLACK);
			        g.fillRect(0, 0, getWidth(), getHeight());

			        g.drawImage(mainImg, 0, 0, getWidth(), getHeight(), null);

			        g.dispose();
			        bs.show();
					//this.getGraphics().drawImage(mainImg, 0, 0, null);
					//repaint(); //�׸� �ٽ� �׸���
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
}
