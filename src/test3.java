//test3


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class test3 extends Canvas implements Runnable, KeyListener {

	//test
    public static final int WIDTH = Main.MAIN_WIDTH;
    public static final int HEIGHT = Main.MAIN_HEIGHT;
    public static final int SCALE = 1;
    public static final String NAME = "Game";
	static final int VK_LSHIFT  = 0xA0;
    boolean gameStart = true;

    //움직임 key는 배열? hash? 에 넣어놓고 저장해놨다가 실행시키면 됨.
    
    private JFrame frame;

    public boolean running = false;
    boolean gameStop = false;
    public int tickCount = 0;

    JButton goMainScreen; //뒤로가기 버튼

    JLabel label1; // 타이머
    int addtime = 0; // 추가 시간 
    private ImagePanel timerPanel;
    
    private BufferedImage player1_img;
    private BufferedImage player2_img;
    private BufferedImage image;
    private BufferedImage background_img;
    private BufferedImage p1FistSkill_img, p1NotebookSkill_img, p1MouseSkill_img, p1ChargerSkill_img, p1DefenseSkill_img;
    private BufferedImage p2FistSkill_img, p2NotebookSkill_img, p2MouseSkill_img, p2ChargerSkill_img, p2DefenseSkill_img;
    private int[] pixels;
    private int[] player1_pixel;
    private int[] player2_pixel;
   
    //플레이어 관련
    Player player1;
    Player player2;
    private BufferedImage player1_hpImg;
    private BufferedImage player2_hpImg;
    private BufferedImage p1_mouse_img;
    private BufferedImage p2_mouse_img;
    private int hp_width = 500;
    private int hp_height = 50;
	int imgWidth, imgHeight;  

    public test3() {
    	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    	//Sound.Play("./bgm/background_bgm.wav"); //배경 음악
		try {
			//background이미지 로드
    		background_img = ImageRelation.ImageLoad("./img/stage1.jpg");
    		pixels = ((DataBufferInt) background_img.getRaster().getDataBuffer()).getData();
    		timerPanel = new ImagePanel(new ImageIcon("./img/timer.png").getImage(), 160, 80);
    		
    		//플레이어들 이미지 로드 & 크기 설정//player이미지 로드, 생성
    		player1_img = ImageRelation.ImageLoad("./img/playerTest.png");
    		imgWidth = player1_img.getWidth(); imgHeight = player1_img.getHeight();
    		player1 = new Player(0, Main.MAIN_HEIGHT-imgHeight, imgWidth, imgHeight, hp_width, -1);
    		
    		player2_img = ImageRelation.ImageLoad("./img/playerTest.png");
    		imgWidth = player2_img.getWidth(); imgHeight = player2_img.getHeight();
    		player2 = new Player(Main.MAIN_WIDTH-imgWidth, Main.MAIN_HEIGHT-imgHeight, imgWidth, imgHeight, hp_width, 1);
    		
    		player1.setOtherPlayer(player2);
    		player2.setOtherPlayer(player1);
    		player1_pixel = ((DataBufferInt) player1_img.getRaster().getDataBuffer()).getData();
    		player2_pixel = ((DataBufferInt) player2_img.getRaster().getDataBuffer()).getData();
    		
    		p1FistSkill_img = ImageRelation.ImageLoad("./img./skill_fist.png");
    		p1MouseSkill_img =  ImageRelation.ImageLoad("./img./skill_p1_mouse.png");
    		p1DefenseSkill_img =  ImageRelation.ImageLoad("./img./skill_p1_defense.png");
    		p1ChargerSkill_img  = ImageRelation.ImageLoad("./img./skill_p1_charger.png");
    		p1NotebookSkill_img = ImageRelation.ImageLoad("./img./skill_p1_notebook.png");
    		
    		p2FistSkill_img = ImageRelation.ImageLoad("./img./skill_fist.png");
    		p2MouseSkill_img =  ImageRelation.ImageLoad("./img./skill_p2_mouse.png");
    		p2DefenseSkill_img =  ImageRelation.ImageLoad("./img./skill_p2_defense.png");
    		p2ChargerSkill_img  = ImageRelation.ImageLoad("./img./skill_p2_charger.png");
    		p2NotebookSkill_img = ImageRelation.ImageLoad("./img./skill_p2_notebook.png");
    		
    		//플레이어 전체 이미지 픽셀로 넣어놓기
    		player1.setAllPixels("./img/player1.png");
    		player2.setAllPixels("./img/player2.png");
    		player1.setPixels(player1_pixel);
    		player2.setPixels(player2_pixel);
    		
    		//플레이어 hp바 만들기
		    player1_hpImg = new BufferedImage(hp_width, hp_height, BufferedImage.TYPE_INT_RGB);
		    player2_hpImg = new BufferedImage(hp_width, hp_height, BufferedImage.TYPE_INT_RGB);
    	    for (int x=0; x<hp_width; x++) {
    	        for (int y=0; y<hp_height; y++) {
    	        	player1_hpImg.setRGB(x, y, 16711680);
    	        	player2_hpImg.setRGB(x, y, 16752343);
    	        }
    	    }
    	    
    	    //마우스 로드
    	    p1_mouse_img = ImageRelation.ImageLoad("./img/flyMouse.png");
    	    p2_mouse_img = ImageRelation.ImageLoad("./img/flyMouse.png");
    	    
    	    image = ImageIO.read(new File("./img/flyMouse.png")); //이미지 로드
    		
		} catch (Exception e) {e.printStackTrace(); }
    	    
		//프레임
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE ));

        frame = new JFrame(NAME);

        //타이머 
        label1 = new JLabel("" + 60); 
        timerPanel.setBounds((Main.MAIN_WIDTH/2)-(160 /2), 30, 160, 80);
        timerPanel.add(label1);
        label1.setBounds((Main.MAIN_WIDTH/2)-(160 /2), 30, 160, 80);
        label1.setFont(new Font("Helvetica", Font.BOLD, 25));
        label1.setHorizontalAlignment(JLabel.CENTER); //수평정렬
        label1.setVerticalAlignment(JLabel.CENTER); //수직정렬
        frame.add(label1);
        

        
        goMainScreen = new JButton("←");
		goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30); //이거 하려면 setLayout(null); 해줘야함 
		goMainScreen.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        		System.out.println("메인화면으로 가는 버튼 클릭됨");
	        		if(running) { stop(); } //게임을 멈춤.
	        		frame.dispose(); //프레임 없애기(?)
	        		new MainTest();
	        		/*
	        		 * 게임 함
	        		 * if(running) { stop(); } //게임을 멈춤.
	        		frame.dispose(); //프레임 없애기(?)
	        		new test3().start();
	        		 * */
	        }});
		goMainScreen.setVisible(true);
		frame.add(goMainScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT); // 프레임 크기 설정
        frame.addKeyListener(this);
        frame.setFocusable(true);
        this.setFocusable(false); //캔버스에 focuse가 되지 않도록
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
    	//게임을 멈추거나 나갈때 실행되는..
        running = false; //
        gameStop = false; //게임 멈추기
        //Sound.PlayStop(); //음악 종료
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

        long beforeTime = System.currentTimeMillis();
        long count = 60;
        
        while (running) {
        	 if(player1.getHp() <= 0) {
                 stop();
              }
              else if(player2.getHp() <= 0) {
                 stop();
              }
               now = System.nanoTime();
               delta += (now - lastTime) / nsPerTick;
               lastTime = now;
               shouldRender = true;
               
               // 제한 시간
               long afterTime = System.currentTimeMillis(); 
               count = ((beforeTime+60000) - afterTime)/1000+addtime;
               if(count == 0) { // 시간 끝나면 체력이 똑같냐에 따라 추가 시간을 줌 
                  if(player1.getHp()==player2.getHp()) {
                     addtime+=20;
                  }
                  else stop();
               }
               //System.out.println("시간차이(m) : "+secDiffTime);
               label1.setText(count + "");
//               timerPanel.repaint();
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
                if(player1.getJumping()) {
                	player1.jump();
                }
                if(player2.getJumping()) {
                	player2.jump();
                }
                if(player1.defenseSkill.activate) {
                	player1.defenseSkill();
                }
                if(player2.defenseSkill.activate) {
                	player2.defenseSkill();
                }
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
    	
//        tickCount++;
//    	 
//    	 //노란배경
//    	if(t<=200) {
//	        for (int i = 0; i < pixels.length; i++) {
//	            pixels[i] = pixels[i]-tickCount;
//	        }
//	    }
//    	t++;
    }

    public void render() {
    	player1.render();
    	player2.render();
    	BufferStrategy bs = getBufferStrategy(); //Canvas 클래스와 연계해서 더블 버퍼링을 구현
        if (bs == null) {
            createBufferStrategy(3);//BufferStrategy영역이 세개 생성됨.
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
        
        //이미지들을 그려줌

        g.drawImage(background_img, 0, 0, getWidth(), getHeight(), null);
        //마우스 던지는중에만 마우스 그려줌
        if(player1.getFlyMouse()) g.drawImage(p1_mouse_img, player1.getMouseX(), player1.getMouseY(), null);  //마우스 전체 이미지 그리기
        if(player2.getFlyMouse()) g.drawImage(p2_mouse_img, player2.getMouseX(), player2.getMouseY(), null);
        g.drawImage(player1_img, player1.getX(), player1.getY(), null);
        g.drawImage(player2_img, player2.getX(), player2.getY(), null);
        g.drawImage(player1_hpImg, 30, 30, hp_width, hp_height, null);
        g.drawImage(player2_hpImg, Main.MAIN_WIDTH-hp_width-30, 30, null);

        //스킬들 그려주는 부분;;
        int p1startLoc = 30, interver = 60, y_loc = 90;
        if(player1.isSkill_fistActivate()) g.drawImage(p1FistSkill_img, p1startLoc, y_loc, 50, 50, null);
        if(player1.isSkill_chargerActivate()) g.drawImage(p1ChargerSkill_img, p1startLoc+interver*1, y_loc, 50, 50, null);
        if(player1.isSkill_mouseActivate())  g.drawImage(p1MouseSkill_img, p1startLoc+interver*2, y_loc, 50, 50, null);
        if(player1.defenseSkill.Cooltime()) g.drawImage(p1DefenseSkill_img, p1startLoc+interver*3, y_loc, 50, 50, null);
        if(player1.isSkill_notebookActivate()) g.drawImage(p1NotebookSkill_img, p1startLoc+interver*4, y_loc, 50, 50, null);
        int p2startLoc = Main.MAIN_WIDTH-80;
        if(player2.isSkill_fistActivate()) g.drawImage(p2FistSkill_img, p2startLoc, y_loc, 50, 50, null);
        if(player2.isSkill_chargerActivate())g.drawImage(p2ChargerSkill_img, p2startLoc-interver*1, y_loc, 50, 50, null);
        if(player2.isSkill_mouseActivate())g.drawImage(p2MouseSkill_img, p2startLoc-interver*2, y_loc, 50, 50, null);
        if(player2.defenseSkill.Cooltime())g.drawImage(p2DefenseSkill_img, p2startLoc-interver*3, y_loc, 50, 50, null);
        if(player2.isSkill_notebookActivate())g.drawImage(p2NotebookSkill_img, p2startLoc-interver*4, y_loc, 50, 50, null);

        
        
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
			
			//플레이어 두명 주먹
			case KeyEvent.VK_SHIFT: 
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) { //주먹
					player2.hit(player1.fist());		break;
				}
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) { //주먹
					player1.hit(player2.fist());	break;
				}
			break;
			
			//플레이어1
			case KeyEvent.VK_W:			player1.jumpingStart();		break;
			case KeyEvent.VK_A:			player1.setLeft(true);		break;
			case KeyEvent.VK_D:			player1.setRight(true);		break;
			case KeyEvent.VK_E:			player1.throwMouse();		break; //마우스 던지기
			case KeyEvent.VK_R:			player1.pullOther();		break; //다른 플레이어 내쪽으로 이동
			case KeyEvent.VK_F:			player1.DefenseSkillStart();	break; //방어
			case KeyEvent.VK_Q:			player2.hit(player1.noteBook());	break; //노트북 공격
			
			//플레이어 2
			case KeyEvent.VK_UP:		player2.jumpingStart();		break; //점프
			case KeyEvent.VK_LEFT:		player2.setLeft(true);		break; //왼쪽이동
			case KeyEvent.VK_RIGHT:		player2.setRight(true);		break; //오른쪽이동
			case 47:					player2.throwMouse();		break; //마우스 던지기(/)
			case 222:					player2.pullOther();		break; //다른 플레이어 내쪽으로 이동(따옴표. ')
			case 46:					player2.DefenseSkillStart();	break; //방어
			case KeyEvent.VK_ENTER:		player1.hit(player2.noteBook());	break; //노트북 공격
			}
		}
		System.out.println("KeyPressed"); // 콘솔창에 메소드 이름 출력
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		if(!gameStop) {
			switch (e.getKeyCode()) { //키 코드 알아내기
			//플레이어1
			case KeyEvent.VK_A:			player1.setLeft(false);		break;
			case KeyEvent.VK_D:			player1.setRight(false);	break;

			//플레이어 2
			case KeyEvent.VK_LEFT:		player2.setLeft(false);		break; //왼쪽이동
			case KeyEvent.VK_RIGHT:		player2.setRight(false);		break; //오른쪽이동
			}
		}
		System.out.println("KeyReleased"); // 콘솔창에 메소드 이름 출력
	}
}