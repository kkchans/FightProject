import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
   
    private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0407";
    private static final String URL = "jdbc:mysql://localhost:3306/fightproject_db";
    
    public DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
 
        } catch (Exception e) {
            System.out.println("오류 발생 ");

        }
    }
    
    public void insertBoard(String name, long score){
     
        String sql = "insert into ranking values(?,?)";
        
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setLong(2, score);
            
            int result = pstmt.executeUpdate();
            if(result==1) {
                System.out.println("실행 됨!");
            }
            
        } catch (Exception e) {
            System.out.println("오류!");
        }    finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                    try {
                        conn.close();
                    } catch (SQLException e1) {    }
                }
             } catch (Exception e2) {}
        }
    }

    	String resStr = "";
        public String selectOne() {
        	 resStr = "";
            String sql = "SELECT name, score, (@rank := @rank + 1) AS rank FROM dbtest AS a, "
                  + "(SELECT @rank := 0) AS b ORDER BY a.score DESC limit 10";
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while((rs.next())) {
                	resStr += "name : " + rs.getString("name")+"\n";
                	resStr += "score : " + rs.getString("score")+"\n\n";
                }
                
            } catch (Exception e) {
                System.out.println("오류");
            }    finally {
                try {
                    if(pstmt!=null && !pstmt.isClosed()) {
                        try {
                            conn.close();
                        } catch (SQLException e1) {    }
                        pstmt.close();
                    }
                } catch (Exception e2) {}
            }
            return resStr;
        }
    
}
 