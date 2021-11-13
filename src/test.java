import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class test extends Canvas implements Runnable, KeyListener {



    private static final long serialVersionUID = 1L;



    public static final int WIDTH = Main.MAIN_WIDTH;
    public static final int HEIGHT = Main.MAIN_HEIGHT;
    public static final int SCALE = 1;
    public static final String NAME = "Game";



    private JFrame frame;



    public boolean running = false;
    public int tickCount = 0;



    private BufferedImage player1_img;
    private BufferedImage player2_img;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage rgbImage;
    private BufferedImage hp_img;
    private int[] pixels;



    public test() {
    	 File imageFile = new File("./img/stage1.jpg");
    		if(!imageFile.exists()) {System.out.println("파일 안열림");}
    		try {
    			image = ImageIO.read(imageFile);
    		} catch (IOException e) {e.printStackTrace(); }
    		int width = image.getWidth();
    	    int height = image.getHeight();
    	    rgbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    	    player1_img = new BufferedImage(242, 303, BufferedImage.TYPE_INT_ARGB);
    	    hp_img = new BufferedImage(300, 100, BufferedImage.TYPE_INT_RGB);
    	    for (int x=0; x<300; x++) {
    	        for (int y=0; y<100; y++) {
    	        	hp_img.setRGB(x, y, 16711680);
    	        }
    	    }
    	    for (int x=0; x<width; x++) {
    	        for (int y=0; y<height; y++) {
    	            rgbImage.setRGB(x, y, image.getRGB(x, y));
    	        }
    	    }
    	    try {
    			image = ImageIO.read( new File("./img/playerTest.png"));
    		} catch (IOException e) {e.printStackTrace(); }
    	    for (int x=0; x<242; x++) {
    	        for (int y=0; y<303; y++) {
    	        	if(image.getRGB(x, y) != 0x00) {
    	        		player1_img.setRGB(x, y, image.getRGB(x, y));
					}
    	            //rgbImage.setRGB(x, y, image.getRGB(x, y));
    	        }
    	    }
    	    pixels = ((DataBufferInt) rgbImage.getRaster().getDataBuffer()).getData();
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE ));



        frame = new JFrame(NAME);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Main.MAIN_WIDTH, Main.MAIN_HEIGHT); // 프레임 크기 설정
        frame.addKeyListener(this);

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
    }



    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;



        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;
            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }



            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            if (shouldRender) {
                frames++;
                render();
            }



            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }



    public void tick() {
        //tickCount++;

    	

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] + tickCount;
        }
    }

    int hpbar = 300;
    public void render() {
        BufferStrategy bs = getBufferStrategy(); //Canvas 클래스와 연계해서 더블 버퍼링을 구현(
        if (bs == null) {
            createBufferStrategy(2);//BufferStrategy영역이 두개 생성됨.
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if(hpbar>=0) {
	        for (int x=hpbar; x<300; x++) {
	        	for(int y = 0; y < 100; y++) {
		        	hp_img.setRGB(x, y, 167);
	        	}
		    }
        }
        
        g.drawImage(rgbImage, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(player1_img, 100, 0, 242, 303, null);
        g.drawImage(hp_img, 300, 200, 200, 30, null);

        g.dispose();
        bs.show();
    }



    public static void main(String[] args) {
        new test().start();
    }



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) { //키 코드 알아내기
		case KeyEvent.VK_F4:	hpbar-=100;		break;
		
	}
	System.out.println("KeyPressed"); // 콘솔창에 메소드 이름 출력
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}