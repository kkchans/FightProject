import java.awt.Graphics;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private ImagePanel img;
	public ImagePanel() {
		this.img = img;
		setSize(215, 341);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
