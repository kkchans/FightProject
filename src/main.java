import javax.swing.*; // JFrame
import java.awt.*; // Dimension, Toolkit
public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame_make fms = new Frame_make();
		System.out.println("Hello!!");
	}
}

class Frame_make extends JFrame { // 프레임 만들기
	int f_width = 1280; // 프레임 넓이
	int f_height = 650; // 프레임 높이
	Frame_make() { // 프레임 생성
		init(); // 프레임에 들어갈 컴포넌트 세팅 메소드
		start(); // 시작 명령 처리 부분
		setTitle("Fight Project"); // 프레임 이름
		setSize(f_width, f_height); // 프레임 크기 설정
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// 윈도우에 표시될 때 위치 세팅을 위해 현재 모니터의 해상도 값을 가져옴
		
		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);
		// 프레임을 모니터 화면 정중앙에 배치시키기 위해 좌표 값 계산
		
		setLocation(f_xpos, f_ypos); //프레임을 화면에 배치
		setResizable(false); // 임의로 프레임 크기 설정 x
		setVisible(true); // 프레임을 눈에 보이게 띄우기
	}
	public void init() {
		
	}
	public void start() {
		
	}
}
