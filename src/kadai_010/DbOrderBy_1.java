package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOrderBy_1 {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "Versas13$"
            );
            System.out.println("データベース接続成功");

            // 武者小路勇気さんの点数を更新
            String updateSql = "UPDATE scores SET score_math = ?, score_english = ? WHERE name = ?";
            pstmt = con.prepareStatement(updateSql);
            pstmt.setInt(1, 95); // 数学の点数
            pstmt.setInt(2, 80); // 英語の点数
            pstmt.setString(3, "musyanokouziyuuki"); // 生徒の氏名
            System.out.println("レコード更新を実行します");
            int updateCount = pstmt.executeUpdate();
            System.out.println(updateCount + "件のレコードが更新されました");

            // scoresテーブルのレコードを取得し、点数順に並べ替える
            String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";
            stmt = con.createStatement();
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            rs = stmt.executeQuery(selectSql);

            // 結果を出力
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int scoreMath = rs.getInt("score_math");
                int scoreEnglish = rs.getInt("score_english");
                System.out.println(count + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + scoreMath + "／英語=" + scoreEnglish);
            }
        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
            if (con != null) try { con.close(); } catch (SQLException ignore) {}
        }
    }
}
