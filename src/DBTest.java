import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBTest {
	
	 public static void main(String[] args) {
		 	Scanner scan = new Scanner(System.in);
	    	DBTest bDao = new DBTest();
	    	int num;
	    	while(true) {
	    	System.out.print("0. ����, 1. ������ ����, 2: ������ ��� : ");
	    	num = scan.nextInt();
	    	switch(num) {
	    	case 0: return;
	    	case 1:
	    		String id, title, pass, combo, name, text;
	    		System.out.print("id(����) : "); id = scan.next();
	    		System.out.print("title : "); title = scan.next();
	    		System.out.print("pass : "); pass = scan.next();
	    		System.out.print("combo : "); combo = scan.next();
	    		System.out.print("name : "); name = scan.next();
	    		System.out.print("text : "); text = scan.next();
	    		bDao.insertBoard(id, title, pass, combo, name, text); break;
	    	case 2:
	    		bDao.selectOne();
	    		break;
	    	}
	    	
	    	}
	 }
	
    private Connection conn; //DB Ŀ�ؼ� ���� ��ü
    private static final String USERNAME = "root";//DBMS���� �� ���̵�
    private static final String PASSWORD = "0407";//DBMS���� �� ��й�ȣ
    private static final String URL = "jdbc:mysql://localhost:3306/test_boarddb";//DBMS������ db��
    
    public DBTest() {
        try {
            System.out.println("������");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("����̹� �ε� ����");
        } catch (Exception e) {
            System.out.println("����̹� �ε� ���� ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
    }
    
    public void insertBoard(String id, String title, String pass, String combo, String name, String text){
        //������ �غ�
        String sql = "insert into board values(?,?,?,?,?,?)";
        
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, pass);
            pstmt.setString(4, combo);
            pstmt.setString(5, name);
            pstmt.setString(6, text);
            
            int result = pstmt.executeUpdate();
            if(result==1) {
                System.out.println("Board������ ���� ����!");
            }
            
        } catch (Exception e) {
            System.out.println("Board������ ���� ����!");
        }    finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
	             }
	          } catch (Exception e2) {}
	     }
    }

        public void selectOne() {
            String sql = "select * from board";
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while((rs.next())) {
                    System.out.println("id: " + rs.getInt("id"));
                    System.out.println("boardTitle: " + rs.getString("boardTitle"));
                    System.out.println("boardPassword: " + rs.getString("boardPassword"));
                    System.out.println("comboPublic: " + rs.getString("comboPublic"));
                    System.out.println("writerName: " + rs.getString("writerName"));
                    System.out.println("textContent: " + rs.getString("textContent"));
                    
                }
                
                
                
            } catch (Exception e) {
                System.out.println("select �޼��� ���ܹ߻�");
            }    finally {
                try {
                    if(pstmt!=null && !pstmt.isClosed()) {
                        pstmt.close();
                    }
                } catch (Exception e2) {}
            }
        }
        
        
    //���ǿ� �´� ���� DB���� ����(����)    �ϴ� �޼���
        public void updateBoard(int id) {
            String sql = "update board set boardPassword =?, writerName =? where id=?";
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,"7777");
                pstmt.setString(2,"������");
                pstmt.setInt(3,id);
                pstmt.executeUpdate();
                System.out.println("������ id: " + id);
                
            } catch (Exception e) {
                System.out.println("update ���� �߻�");
            }    finally {
                try {
                    if(pstmt!=null && !pstmt.isClosed()) {
                        pstmt.close();
                    }
                } catch (Exception e2) {}
            }
        
    }    
    
}
 