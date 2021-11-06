import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

//�̹��� �г��� ����
public class ImagePanel extends JPanel{
	private Image img;
	public ImagePanel(Image img, int width, int height) {
		this.img = img;
		
		//�г� �̹��� ũ�� ����
		setSize(width, height);
		setPreferredSize(new Dimension(width, height)); //dimension�� ������ ������ demention�� �ޱ� ����
		
		setLayout(null); //���̾ƿ� ���� x
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//�̹����� �׷���
		g.drawImage(img, 0, 0, null);//�̹����� �ҷ��´�.
	}
}
