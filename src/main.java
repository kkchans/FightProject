import javax.swing.*; // JFrame
import java.awt.*; // Dimension, Toolkit
public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame_make fms = new Frame_make();
		System.out.println("Hello!!");
	}
}

class Frame_make extends JFrame { // ������ �����
	int f_width = 1280; // ������ ����
	int f_height = 650; // ������ ����
	Frame_make() { // ������ ����
		init(); // �����ӿ� �� ������Ʈ ���� �޼ҵ�
		start(); // ���� ��� ó�� �κ�
		setTitle("Fight Project"); // ������ �̸�
		setSize(f_width, f_height); // ������ ũ�� ����
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// �����쿡 ǥ�õ� �� ��ġ ������ ���� ���� ������� �ػ� ���� ������
		
		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);
		// �������� ����� ȭ�� ���߾ӿ� ��ġ��Ű�� ���� ��ǥ �� ���
		
		setLocation(f_xpos, f_ypos); //�������� ȭ�鿡 ��ġ
		setResizable(false); // ���Ƿ� ������ ũ�� ���� x
		setVisible(true); // �������� ���� ���̰� ����
	}
	public void init() {
		
	}
	public void start() {
		
	}
}
