import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class test {
	public static void main(String[] ar) {
		game_Frame fms = new game_Frame();
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable {
	int f_width;
	int f_height;

	int x, y;
	boolean KeyUp = false;
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;
	boolean KeySpace = false;

	int cnt; // 각종 타이밍 조절을 위해 무한 루프를 카운터할 변수

	Thread th;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image me_img;
	Image Missile_img;
	Image Enemy_img; // 적 이미지를 받아들일 이미지 변수
	ArrayList Missile_List = new ArrayList();
	ArrayList Enemy_List = new ArrayList();
//다수의 적을 등장 시켜야 하므로 배열을 이용.

	Image buffImage;
	Graphics buffg;

	Missile ms;
	Enemy en; // 에너미 클래스 접근 키

	game_Frame() {
		init();
		start();

		setTitle("슈팅 게임 만들기");
		setSize(f_width, f_height);
		Dimension screen = tk.getScreenSize();

		int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2);

		setLocation(f_xpos, f_ypos);
		setResizable(false);
		setVisible(true);
	}

	public void init() {
		x = 100;
		y = 100;
		f_width = 800;
		f_height = 600;

		me_img = tk.getImage("img/playerTest.png");

		Missile_img = tk.getImage("Missile.png");
		Enemy_img = tk.getImage("enemy.png");// 적 이미지 생성
	}

	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);

		th = new Thread(this);
		th.start();

	}

	public void run() {
		try {
			while (true) {
				KeyProcess();
				EnemyProcess();// 적 움직임 처리 메소드 실행
				MissileProcess();

				repaint();

				Thread.sleep(20);
				cnt++;// 무한 루프 카운터
			}
		} catch (Exception e) {
		}
	}

	public void MissileProcess() {
		if (KeySpace) {
			ms = new Missile(x, y);
			Missile_List.add(ms);
		}
	}

	public void EnemyProcess() {// 적 행동 처리 메소드

		for (int i = 0; i < Enemy_List.size(); ++i) {
			en = (Enemy) (Enemy_List.get(i));
//배열에 적이 생성되어있을 때 해당되는 적을 판별
			en.move(); // 해당 적을 이동시킨다.
			if (en.x < -200) { // 적의 좌표가 화면 밖으로 넘어가면
				Enemy_List.remove(i); // 해당 적을 배열에서 삭제
			}
		}

		if (cnt % 300 == 0) { // 루프 카운트 300회 마다
			en = new Enemy(f_width + 100, 100);
			Enemy_List.add(en);
//﻿각 좌표로 적을 생성한 후 배열에 추가한다.
			en = new Enemy(f_width + 100, 200);
			Enemy_List.add(en);
			en = new Enemy(f_width + 100, 300);
			Enemy_List.add(en);
			en = new Enemy(f_width + 100, 400);
			Enemy_List.add(en);
			en = new Enemy(f_width + 100, 500);
			Enemy_List.add(en);
		}

	}

	public void paintComponent(Graphics g) {
		buffImage = createImage(f_width, f_height);
		buffg = buffImage.getGraphics();

		update(g);
	}

	public void update(Graphics g) {
		Draw_Char();

		Draw_Enemy(); // 그려진 적 이미지를 가져온다.

		Draw_Missile();

		g.drawImage(buffImage, 0, 0, this);
	}

	public void Draw_Char() {
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(me_img, x, y, this);
	}

	public void Draw_Missile() {
		for (int i = 0; i < Missile_List.size(); ++i) {
			ms = (Missile) (Missile_List.get(i));
			buffg.drawImage(Missile_img, ms.pos.x + 150, ms.pos.y + 30, this);
			ms.move();
			if (ms.pos.x > f_width) {
				Missile_List.remove(i);
			}
		}
	}

	public void Draw_Enemy() { // 적 이미지를 그리는 부분
		for (int i = 0; i < Enemy_List.size(); ++i) {
			en = (Enemy) (Enemy_List.get(i));
			buffg.drawImage(Enemy_img, en.x, en.y, this);
//배열에 생성된 각 적을 판별하여 이미지 그리기
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			KeyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			KeyDown = true;
			break;
		case KeyEvent.VK_LEFT:
			KeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = true;
			break;

		case KeyEvent.VK_SPACE:
			KeySpace = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		System.out.println("sdfds");
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			KeyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			KeyDown = false;
			break;
		case KeyEvent.VK_LEFT:
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = false;
			break;

		case KeyEvent.VK_SPACE:
			KeySpace = false;
			break;

		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void KeyProcess() {

		if (KeyUp == true)
			y -= 5;
		if (KeyDown == true)
			y += 5;
		if (KeyLeft == true)
			x -= 5;
		if (KeyRight == true)
			x += 5;
	}
}

class Missile {

	Point pos;

	Missile(int x, int y) {
		pos = new Point(x, y);
	}

	public void move() {
		pos.x += 10;
	}
}

class Enemy { // 적 위치 파악 및 이동을 위한 클래스
	int x;
	int y;

	Enemy(int x, int y) { // 적좌표를 받아 객체화 시키기 위한 메소드
		this.x = x;
		this.y = y;
	}

	public void move() { // x좌표 -3 만큼 이동 시키는 명령 메소드
		x -= 3;
	}
}