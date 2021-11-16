import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;



public class test3 extends Canvas implements Runnable, KeyListener {

	//test

    public static final int WIDTH = Main.MAIN_WIDTH;
    public static final int HEIGHT = Main.MAIN_HEIGHT;
    public static final int SCALE = 1;
    public static final String NAME = "Game";
    boolean gameStart = true;


    //움직임 key는 배열? hash? 에 넣어놓고 저장해놨다가 실행시키면 됨.
    
    private JFrame frame;



    public boolean running = false;
    boolean gameStop = false;
    public int tickCount = 0;

    JButton goMainScreen; //뒤로가기 버튼

    private BufferedImage player1_img;
    private BufferedImage player2_img;
    private BufferedImage image;
    private BufferedImage background_img;
    private int[] pixels;
   
    //플레이어 관련
    Player player1;
    Player player2;
    int player1_width, player1_height;
    int player2_width, player2_height;
    private BufferedImage player1_hpImg;
    private BufferedImage player2_hpImg;
    private int hp_width = 500;
    private int hp_height = 50;
	int imgWidth, imgHeight;    
    public test3() {
    	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

		try {
			//background이미지 로드
			image = ImageIO.read(new File("./img/stage1.jpg"));
    		imgWidth = image.getWidth();
    		imgHeight = image.getHeight();
    		background_img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
    	    pixels = ((DataBufferInt) background_img.getRaster().getDataBuffer()).getData();
    		for (int x=0; x<imgWidth; x++) {
	        for (int y=0; y<imgHeight; y++) {
	        	background_img.setRGB(x, y, image.getRGB(x, y));
	        	//pixels[(x/imgWidth)*y+x] = image.getRGB(x, y);
	        }
	    }
    		
    		//player이미지 로드, 생성
    		image = ImageIO.read( new File("./img/playerTest.png"));
    		player1_width = image.getWidth();
    		player1_height = image.getHeight();
    		player1_img = new BufferedImage(player1_width, player1_height, BufferedImage.TYPE_INT_ARGB);
			for (int x=0; x<player1_width; x++) {
    	        for (int y=0; y<player1_height; y++) {
    	        	//if(image.getRGB(x, y) != 0x00) {
    	        	player1_img.setRGB(x, y, image.getRGB(x, y));
					//}
    	        }
    	    }
			image = ImageIO.read( new File("./img/playerTest.png"));
    		player2_width = image.getWidth();
    		player2_height = image.getHeight();
    		player2_img = new BufferedImage(player1_width, player1_height, BufferedImage.TYPE_INT_ARGB);
    		player2 = new Player(0, Main.MAIN_HEIGHT-player2_height, player2_width, player2_height, hp_width);
			for (int x=0; x<player2_width; x++) {
    	        for (int y=0; y<player2_height; y++) {
    	        	player2_img.setRGB(x, y, image.getRGB(x, y));
    	        }
    	    }
			
			player1 = new Player(0, Main.MAIN_HEIGHT-player1_height, player1_width, player1_height, hp_width);
			player2 = new Player(Main.MAIN_WIDTH-player2_width, Main.MAIN_HEIGHT-player2_height, player2_width, player2_height, hp_width);
    		player1.setOtherPlayer(player2);
    		player2.setOtherPlayer(player1);
    		player1.setPixels(pixels);
    		player2.setPixels(pixels);
			
    		//플레이어 hp바 만들기
		    player1_hpImg = new BufferedImage(hp_width, hp_height, BufferedImage.TYPE_INT_RGB);
		    player2_hpImg = new BufferedImage(hp_width, hp_height, BufferedImage.TYPE_INT_RGB);
    	    for (int x=0; x<hp_width; x++) {
    	        for (int y=0; y<hp_height; y++) {
    	        	player1_hpImg.setRGB(x, y, 16711680);
    	        	player2_hpImg.setRGB(x, y, 16752343);
    	        }
    	    }
			
		} catch (Exception e) {e.printStackTrace(); }
    	    
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE ));

        frame = new JFrame(NAME);

        goMainScreen = new JButton("←");
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30); //이거 하려면 setLayout(null); 해줘야함 
		goMainScreen.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        		System.out.println("메인화면으로 가는 버튼 클릭됨");
	        		if(running) { stop(); } //게임을 멈춤.
	        		frame.dispose(); //프레임 없애기(?)
	        		new MainTest();
	        }});
		goMainScreen.setVisible(true);
		frame.add(goMainScreen);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT); // 프레임 크기 설정
        frame.addKeyListener(this);
        frame.setFocusable(true);

        frame.add(this);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }



    public synchronized void stop() {
        running = false;
        gameStop = false;
    }


    long now;
    boolean shouldRender;
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            shouldRender = true;
            if (delta >= 0.00001) {
                ticks++;
                tick();
                delta -= 0.00001;
                shouldRender = true;
            }
            
            if (shouldRender) {
                frames++;
                player1.update();
                player2.update();
                render();
            }


            if(!gameStop) { //게임을 멈추지 않았을 때(게임을 멈추면 하지 말아야 할 것들)
	            if (System.currentTimeMillis() - lastTimer >= 100) {
	                lastTimer += 100;
	                System.out.println(ticks + " ticks, " + frames + " frames");
	                frames = 0;
	                ticks = 0;
	            }
            }
        }
    }

int t = 0;
    public void tick() {
        //tickCount++;

    	
    	if(t<=200) {
	        for (int i = 0; i < pixels.length; i++) {
	            pixels[i] = pixels[i]-1;
	        }
	    }
    	t++;
    }

    public void render() {
    	 BufferStrategy bs = getBufferStrategy(); //Canvas 클래스와 연계해서 더블 버퍼링을 구현
        if (bs == null) {
            createBufferStrategy(3);//BufferStrategy영역이 두개 생성됨.
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //플레이어 hp바
        if(player1.getHp()>=0) {
	        for (int x=player1.getHp(); x<hp_width; x++) {
	        	for(int y = 0; y < hp_height; y++) {
	        		player1_hpImg.setRGB(x, y, 167);
	        	}
		    }
        }
        if(player2.getHp()>=0) {
	        for (int x=player2.getHp(); x<hp_width; x++) {
	        	for(int y = 0; y < hp_height; y++) {
	        		player2_hpImg.setRGB(x, y, 167); //167은 파란색 hp바 배경
	        	}
		    }
        }
        g.fillRect(0, 0, getWidth(), getHeight());
      //플레이어 hp바 END
        g.drawImage(background_img, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(player1_img, player1.getX(), player1.getY(), player1_width, player1_height, null);
        g.drawImage(player2_img, player2.getX(), player2.getY(), player2_width, player2_height, null);
        g.drawImage(player1_hpImg, 30, 30, hp_width, hp_height, null);
        g.drawImage(player2_hpImg, Main.MAIN_WIDTH-hp_width-30, 30, hp_width, hp_height, null);
 
        g.dispose();
        bs.show();
    }



    //keylistener 구현
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		if(!gameStop) {
			switch (e.getKeyCode()) { //키 코드 알아내기
			//게임 관련 키
			case KeyEvent.VK_F5: 		gameStop = true;  			break;
			case KeyEvent.VK_SHIFT: 
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) { player1.hit(100); }
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) { player2.hit(100); }
			break;
			//플레이어1
			case KeyEvent.VK_F3:		player1.hit(100);			break;
			case KeyEvent.VK_W:			player1.jumpingStart();		break;
			case KeyEvent.VK_A:			player1.setLeft(true);		break;
			case KeyEvent.VK_D:			player1.setRight(true);		break;
			case KeyEvent.VK_S:			player1.setJumping(false);	break;
			//플레이어 2
			case KeyEvent.VK_F4:		player2.hit(100);			break;
			case KeyEvent.VK_UP:		player2.jumpingStart();		break;
			case KeyEvent.VK_LEFT:		player2.setLeft(true);		break;
			case KeyEvent.VK_RIGHT:		player2.setRight(true);		break;
			case KeyEvent.VK_DOWN:		player2.setJumping(false);	break;
			}
		}
		System.out.println("KeyPressed"); // 콘솔창에 메소드 이름 출력
	}

	@Override
	public void keyReleased(KeyEvent e) { }
}