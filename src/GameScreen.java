
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameScreen extends ImagePanel implements KeyListener {
	
	Player player1;
	Player player2;
	
	//test
	Image buffImage;
	Graphics buffg;
	Image player;
	Image background;
	Graphics g;
	Toolkit tk = Toolkit.getDefaultToolkit();
	
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
		
		addKeyListener(this);
		setFocusable(true);

	}

	void gameInit() {
		player1 = new Player(0, 300);
		player = tk.getImage("./img/playerTest.png");
		background = tk.getImage("./img/testImg.png");
//		buffg.clearRect(0, 0, f_width, f_height);
//		buffg.drawImage(me_img, x, y, this);
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
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("keyTyped"); // 콘솔창에 메소드 이름 출력
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) { //키 코드 알아내기
		case KeyEvent.VK_UP:
			//점프
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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("keyReleased"); // 콘솔창에 메소드 이름 출력
	}
	

	
}
