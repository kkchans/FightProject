import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.desktop.ScreenSleepEvent;
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


public class GameScreen extends Canvas implements Runnable, KeyListener {
   
   //stage 관련
   boolean stage_fun = false;
   long stage_start;
   
   int player1_score = 0;
   int player2_score = 0;
   
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

    JLabel timerLable; // 타이머
    int addtime = 0; // 추가 시간
    
    JLabel player1_scoreLabel; //스코어
    JLabel player2_scoreLabel;
    
    int round = 1; // 라운드
    
    boolean win1_im = false; 
    boolean win2_im = false;
    
    private ImagePanel timerPanel;
    
    private BufferedImage player1_img;
    private BufferedImage player2_img;
    private BufferedImage image;
    private BufferedImage background_img;
    private BufferedImage win1_img;
    private BufferedImage win2_img;
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
    private long count = 60;
    int imgWidth, imgHeight;  
   
   long beforeTime;

    public GameScreen() {
       image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
       Sound.Play("./bgm/background_bgm.wav"); //배경 음악
      try {
         //background이미지 로드
          background_img = ImageRelation.ImageLoad("./img/stage1.jpg");
          pixels = ((DataBufferInt) background_img.getRaster().getDataBuffer()).getData();
          timerPanel = new ImagePanel(new ImageIcon("./img/timer.png").getImage(), 160, 80);
          
          //플레이어들 이미지 로드 & 크기 설정//player이미지 로드, 생성
          player1_img = ImageRelation.ImageLoad("./img/playerTest.png");
          imgWidth = player1_img.getWidth(); imgHeight = player1_img.getHeight();
          player1 = new Player(0, Main.MAIN_HEIGHT-imgHeight, imgWidth, imgHeight, hp_width, -1, "player1");
          
          player2_img = ImageRelation.ImageLoad("./img/playerTest.png");
          imgWidth = player2_img.getWidth(); imgHeight = player2_img.getHeight();
          player2 = new Player(Main.MAIN_WIDTH-imgWidth, Main.MAIN_HEIGHT-imgHeight, imgWidth, imgHeight, hp_width, 1, "player2");
          
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
          
          // 윈 루즈 로드
           win1_img = ImageRelation.ImageLoad("./img./win_player1.png");
          win2_img = ImageRelation.ImageLoad("./img./win_player2.png");
          
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
        timerLable = new JLabel("" + 60); 
        timerLable.setOpaque(true);
        timerLable.setBackground(new Color(200, 255, 0));
        timerLable.setBounds((Main.MAIN_WIDTH/2)-(140 /2), 30, 140, 70);
        timerLable.setFont(new Font("Helvetica", Font.BOLD, 25));
        timerLable.setHorizontalAlignment(JLabel.CENTER); //수평정렬
        timerLable.setVerticalAlignment(JLabel.CENTER); //수직정렬
        frame.add(timerLable);
        
        
        //player1 score 
        player1_scoreLabel = new JLabel("" + 0); 
        player1_scoreLabel.setBounds(450, 100, 50, 50);
        player1_scoreLabel.setOpaque(true);
        player1_scoreLabel.setBackground(new Color(0, 255, 0));
        player1_scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        player1_scoreLabel.setHorizontalAlignment(JLabel.CENTER); //수평정렬
        player1_scoreLabel.setVerticalAlignment(JLabel.CENTER); //수직정렬
        frame.add(player1_scoreLabel);
        
        //2
        player2_scoreLabel = new JLabel("" + 0); 
        player2_scoreLabel.setBounds(800, 100, 50, 50);
        player2_scoreLabel.setOpaque(true);
        player2_scoreLabel.setBackground(new Color(0, 255, 155));
        player2_scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        player2_scoreLabel.setHorizontalAlignment(JLabel.CENTER); //수평정렬
        player2_scoreLabel.setVerticalAlignment(JLabel.CENTER); //수직정렬
        frame.add(player2_scoreLabel);
        
        
        goMainScreen = new JButton("←");
      goMainScreen.setBounds(Main.MAIN_WIDTH-63, 0, 50, 30); //이거 하려면 setLayout(null); 해줘야함 
      goMainScreen.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                 System.out.println("메인화면으로 가는 버튼 클릭됨");
                 if(running) { stop(); } //게임을 멈춤.
                 frame.dispose(); //프레임 없애기(?)
                 new Main();
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
        Sound.PlayStop(); //음악 종료
    }

    public void gameStop() {
       running = false;
    }
    
    public void stageChange() {
       System.out.println("stageChange");
       
       //플레이어들의 랭킹스코어 저장
       player1.plusScore(player1.getHp()*count);
       player2.plusScore(player2.getHp()*count);
       
       win1_im = false;
       win2_im = false;
       
       beforeTime = System.currentTimeMillis();
       round+=1;
       if(round == 4) { //게임이 끝날때
          stop();
          frame.dispose(); //프레임 없애기(?)
          if(player1.getScore() >= player2.getScore()) {
        	  new Main().visibleRangking(player1.getScore());
          }
          else {
        	  new Main().visibleRangking(player2.getScore());
          }
       }
       player1.setHp(500);
       player1.setX(0);
       imgHeight = player1_img.getHeight();
       player1.setY(Main.MAIN_HEIGHT-imgHeight);
       player1.setbasic();
       
       player2.setHp(500);
       imgWidth = player2_img.getWidth(); imgHeight = player2_img.getHeight();
       player2.setX(Main.MAIN_WIDTH-imgWidth);
       player2.setY(Main.MAIN_HEIGHT-imgHeight);
       player2.setbasic();
       
       for (int x=0; x<hp_width; x++) {
             for (int y=0; y<hp_height; y++) {
                player1_hpImg.setRGB(x, y, 16711680);
                player2_hpImg.setRGB(x, y, 16752343);
             }
         }
       try {
          if(round == 2) {
             background_img = ImageRelation.ImageLoad("./img/stage2.jpg");
          }
          else background_img = ImageRelation.ImageLoad("./img/stage3.jpg");
       }catch(Exception e) {
       }
       stage_fun = false;
    }
    long now;
    boolean shouldRender;
    long prevTick = System.currentTimeMillis();
    
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D;

        int frames = 0;
        int ticks = 0;
        

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        beforeTime = System.currentTimeMillis();
        count = 60;
        
        while (running) {
        	delta = (System.currentTimeMillis() - prevTick)/180000;//180프레임
           if(stage_fun) {
              System.out.println("stage_fun");
              if(System.currentTimeMillis() - stage_start >  3000) { 
                  stageChange();
                 gameStop = false;                 
              }
           }
           

               
               
//               timerPanel.repaint();

               if (System.currentTimeMillis() > delta*1000) {//0.1초마다
                   player1.update();
                   player2.update();
                   if(player1.getJumping()) {
                      player1.jump();
                   }
                   if(player2.getJumping()) {
                      player2.jump();
                   }
                   if(player1.defenseSkill.isActivate()) {
                      player1.defenseSkillProcess();
                   }
                   if(player2.defenseSkill.isActivate()) {
                      player2.defenseSkillProcess();
                   }
                   render();
               }

            if(!gameStop) { //게임을 멈추지 않았을 때(게임을 멈추면 하지 말아야 할 것들)
               if(player1.getHp() <= 0 || player2.getHp() <= 0) {
                    //if(running) { stop(); } //게임을 멈춤.
                    //round++;
                    //if(round == 4) stop();
                   stage_fun = true;
                    stage_start = System.currentTimeMillis();
                    if(player2.getHp() <= 0) {
                       win1_im = true;
                       player1_score+=1;
                    }
                    else {
                       win2_im = true;
                       player2_score+=1;
                    }
                     gameStop = true;
                  
                  }
               
               // 제한 시간
               long afterTime = System.currentTimeMillis(); 
               count = ((beforeTime+61000) - afterTime)/1000+addtime;
               if(count == 0) { // 시간 끝나면 체력이 똑같냐에 따라 추가 시간을 줌 
                  if(player1.getHp()==player2.getHp()) {
                     addtime+=20;
                  }
                  else { 
                     stage_fun = true;
                      stage_start = System.currentTimeMillis();
                      
                      if(player1.getHp() > player2.getHp()) {
                         win1_im = true;
                         player1_score+=1;
                      }   
                      else {
                         win2_im = true; 
                         player2_score+=1;
                      }
                       gameStop = true;
                  }
               }
               
               //System.out.println("시간차이(m) : "+secDiffTime);
               timerLable.setText(count + "");
               player1_scoreLabel.setText(player1_score + "");
               player2_scoreLabel.setText(player2_score + "");
               
            }
           
            prevTick = System.currentTimeMillis();
        }//while end
       
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

        if(player1.getHp() >= 0) {
	        for (int x=player1.getHp(); x<hp_width; x++) {
	           for(int y = 0; y < hp_height; y++) {
	              player1_hpImg.setRGB(x, y, 167);
	           }
	       }
        }
        if(player2.getHp() >= 0) {
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
        g.drawImage(player1_img, player1.getX(), player1.getY(), null);
        g.drawImage(player2_img, player2.getX(), player2.getY(), null);
        if(player1.getFlyMouse()) g.drawImage(p1_mouse_img, player1.getMouseX(), player1.getMouseY(), null);  //마우스 전체 이미지 그리기
        if(player2.getFlyMouse()) g.drawImage(p2_mouse_img, player2.getMouseX(), player2.getMouseY(), null);
        g.drawImage(player1_hpImg, 30, 30, hp_width, hp_height, null);
        g.drawImage(player2_hpImg, Main.MAIN_WIDTH-hp_width-30, 30, null);

        //스킬들 그려주는 부분
        int p1startLoc = 30, interver = 60, y_loc = 90;
        if(player1.fistSkill.Cooltime()) g.drawImage(p1FistSkill_img, p1startLoc, y_loc, 50, 50, null);
        if(player1.pullOtherSkill.Cooltime()) g.drawImage(p1ChargerSkill_img, p1startLoc+interver*1, y_loc, 50, 50, null);
        if(player1.mouseSkill.Cooltime())  g.drawImage(p1MouseSkill_img, p1startLoc+interver*2, y_loc, 50, 50, null);
        if(player1.defenseSkill.Cooltime()) g.drawImage(p1DefenseSkill_img, p1startLoc+interver*3, y_loc, 50, 50, null);
        if(player1.noteBookSkill.Cooltime()) g.drawImage(p1NotebookSkill_img, p1startLoc+interver*4, y_loc, 50, 50, null);
        int p2startLoc = Main.MAIN_WIDTH-80;
        if(player2.fistSkill.Cooltime()) g.drawImage(p2FistSkill_img, p2startLoc, y_loc, 50, 50, null);
        if(player2.pullOtherSkill.Cooltime())g.drawImage(p2ChargerSkill_img, p2startLoc-interver*1, y_loc, 50, 50, null);
        if(player2.mouseSkill.Cooltime())g.drawImage(p2MouseSkill_img, p2startLoc-interver*2, y_loc, 50, 50, null);
        if(player2.defenseSkill.Cooltime())g.drawImage(p2DefenseSkill_img, p2startLoc-interver*3, y_loc, 50, 50, null);
        if(player2.noteBookSkill.Cooltime())g.drawImage(p2NotebookSkill_img, p2startLoc-interver*4, y_loc, 50, 50, null);

        
        if(win1_im) {
          g.drawImage(win1_img, 50, 0, null);
        }
        if(win2_im) {
           System.out.println("win2");
           g.drawImage(win2_img, 0, 0, null);
        }
        
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
               player2.hit(player1.fist());      break;
            }
            if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) { //주먹
               player1.hit(player2.fist());   break;
            }
         break;
         
         //플레이어1
         case KeyEvent.VK_W:         player1.jumpingStart();      break;
         case KeyEvent.VK_A:         player1.setLeft(true);      break;
         case KeyEvent.VK_D:         player1.setRight(true);      break;
         case KeyEvent.VK_E:         player1.throwMouse();      break; //마우스 던지기
         case KeyEvent.VK_R:         player1.pullOther();      break; //다른 플레이어 내쪽으로 이동
         case KeyEvent.VK_F:         player1.DefenseSkillStart();   break; //방어
         case KeyEvent.VK_Q:         player2.hit(player1.noteBook());   break; //노트북 공격
         
         //플레이어 2
         case KeyEvent.VK_UP:		player2.jumpingStart();      break; //점프
         case KeyEvent.VK_LEFT:     player2.setLeft(true);      break; //왼쪽이동
         case KeyEvent.VK_RIGHT:    player2.setRight(true);      break; //오른쪽이동
         case 47:               	player2.throwMouse();      break; //마우스 던지기(/)
         case 222:               	player2.pullOther();      break; //다른 플레이어 내쪽으로 이동(따옴표. ')
         case 46:               	player2.DefenseSkillStart();   break; //방어
         case KeyEvent.VK_ENTER:    player1.hit(player2.noteBook());   break; //노트북 공격
         
         case KeyEvent.VK_F2: player2.hit(500);   break;
         case KeyEvent.VK_F1: player1.hit(500);   break;
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent e) { 
      if(!gameStop) {
         switch (e.getKeyCode()) { //키 코드 알아내기
         //플레이어1
         case KeyEvent.VK_A:         player1.setLeft(false);      break;
         case KeyEvent.VK_D:         player1.setRight(false);   break;

         //플레이어 2
         case KeyEvent.VK_LEFT:      player2.setLeft(false);      break; //왼쪽이동
         case KeyEvent.VK_RIGHT:      player2.setRight(false);      break; //오른쪽이동
         }
      }
   }
}