package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employees_Chapter04 {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "Versas13$"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            stmt = con.createStatement();
            String sql = """
                         CREATE TABLE employees (
                           id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(60) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           age INT(11),
                           address VARCHAR(255)
                         );
                         """;

            // SQLクエリを実行（DBMSに送信）
            int rowCnt = stmt.executeUpdate(sql);
            System.out.println("テーブルを作成: rowCnt=" + rowCnt);
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if(stmt != null) {
                try { stmt.close(); } catch(SQLException ignore) {}
            }
            if(con != null) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
}
