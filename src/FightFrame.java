

public class FightFrame extends JFrame {
	int f_width = 1280; // ������ ����
	int f_height = 650; // ������ ����
	FightFrame() { // ������ ����
		init(); // �����ӿ� �� ������Ʈ ���� �޼ҵ�
		start(); // ���� ��� ó�� �κ�
		setTitle("Fight Project"); // ������ �̸�
		setSize(f_width, f_height); // ������ ũ�� ����
		setLocationRelativeTo(null);
		setResizable(false); // ���Ƿ� ������ ũ�� ���� x
		setVisible(true); // �������� ���� ���̰� ����
		
		Image img = new ImageIcon("rpg.png").getImage();
		
	}
	public void init() {
		
	}
	public void start() {
		
	}
}
