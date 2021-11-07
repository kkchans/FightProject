
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
	MyKeyListener myListener = new MyKeyListener(); //프레임에서 어댑터를 달도록
	
	JButton goMainScreen = new JButton("←"); //뒤로가기 버튼
	Image[] Player_img; //플레이어 애니메이션 표현을 위해 이미지를 배열로 받음
	
	public GameScreen() {
		//배경 이미지 띄우기
		super(new ImageIcon("./img/testImg.png").getImage(),
				Main.MAIN_WIDTH, Main.MAIN_HEIGHT);
		
		//뒤로가기 버튼 세팅!
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30);
		add(goMainScreen);
		
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
		if(b_gameStart) {
			
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
				case KeyEvent.VK_UP:
					//점프
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
				repaint(); //눌렸으면 그림 다시 그리기
				System.out.println("KeyPressed"); // 콘솔창에 메소드 이름 출력
			}
		}

		public void keyReleased(KeyEvent e) {
			if(b_gameStart) {
				System.out.println("keyReleased"); // 콘솔창에 메소드 이름 출력
			}
		}
		
	}

	
}
