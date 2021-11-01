

public class FightFrame extends JFrame {
	int f_width = 1280; // 프레임 넓이
	int f_height = 650; // 프레임 높이
	FightFrame() { // 프레임 생성
		init(); // 프레임에 들어갈 컴포넌트 세팅 메소드
		start(); // 시작 명령 처리 부분
		setTitle("Fight Project"); // 프레임 이름
		setSize(f_width, f_height); // 프레임 크기 설정
		setLocationRelativeTo(null);
		setResizable(false); // 임의로 프레임 크기 설정 x
		setVisible(true); // 프레임을 눈에 보이게 띄우기
		
		Image img = new ImageIcon("rpg.png").getImage();
		
	}
	public void init() {
		
	}
	public void start() {
		
	}
}
