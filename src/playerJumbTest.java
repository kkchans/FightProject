
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class playerJumbTest extends JFrame {
	GameScreen gameScreen;
	
	boolean keyUp = false;
	boolean keyLeft = false;
	boolean keyRight = false;

	Thread th;
	
	public static void main(String args[]) {
		new playerJumbTest();
	}
	public playerJumbTest() {
		gameScreen = new GameScreen();
		add(gameScreen);
		addKeyListener(gameScreen.myListener);
		setFocusable(true);
		
		setTitle("test"); // 프레임 이름
		setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT); // 프레임 크기 설정
		setLocationRelativeTo(null); //프레임을 화면의 중간으로 설정
		setResizable(false); // 임의로 프레임 크기 설정 x. 창 크기를 고정.
		//setUndecorated(true); //상단바 없애기
		setVisible(true); // 프레임 띄우기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//이 설정을 해야 클로즈할때 프로그램이 꺼짐. 안하면 계속 돌아감
	}

	
	public class GameScreen extends JPanel implements Runnable {
		
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
		MyKeyListener myListener = new MyKeyListener(); //프레임에서 어댑터를 달도록
		
		JButton goMainScreen = new JButton("←"); //뒤로가기 버튼
		Image[] Player_img; //플레이어 애니메이션 표현을 위해 이미지를 배열로 받음
		
		public GameScreen() {
			
			b_gameStart = true;
			gameInit();
			gameStart();
			//키
	
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
			th = new Thread(this);
			th.start();
		}
	

		@Override
		public void run() {
			if(b_gameStart) {
				while(true) {
					KeyProcess();
					repaint(); //그림 다시 그리기
					if(player1.getJumping()) {
						player1.jump();
					}
				}
			}
		}
		
		void KeyProcess() {
			if(keyUp) {
				//점프
				//몇초 후에 땅바닥으로
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
					System.out.println("keyTyped"); // 콘솔창에 메소드 이름 출력
				}
			}
	
			public void keyPressed(KeyEvent e) {
				if(b_gameStart) {
					switch (e.getKeyCode()) { //키 코드 알아내기
						case KeyEvent.VK_UP:	keyUp = true;		break;
						case KeyEvent.VK_LEFT:	keyLeft = true;		break;
						case KeyEvent.VK_RIGHT:	keyRight = true;	break;
					}
					System.out.println("KeyPressed"); // 콘솔창에 메소드 이름 출력
				}
			}
	
			public void keyReleased(KeyEvent e) {
				if(b_gameStart) {
					System.out.println("keyReleased"); // 콘솔창에 메소드 이름 출력
				}
			}//keyReleased END
			
		}//MyKeyListener CLASS END

	
		
	}
}
