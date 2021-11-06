import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

//이미지 패널을 만듬
public class ImagePanel extends JPanel{
	private Image img;
	public ImagePanel(Image img, int width, int height) {
		this.img = img;
		
		//패널 이미지 크기 설정
		setSize(width, height);
		setPreferredSize(new Dimension(width, height)); //dimension을 생성한 이유는 demention을 받기 때문
		
		setLayout(null); //레이아웃 설정 x
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//이미지를 그려줌
		g.drawImage(img, 0, 0, null);//이미지를 불러온다.
	}
}
