package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
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
            System.out.println("データベース接続成功：" + con);

            // postsテーブルの作成
            stmt = con.createStatement();
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS posts (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    posted_at DATE,
                    post_content VARCHAR(255),
                    likes INT
                );
                """;
            stmt.executeUpdate(createTableSQL);
            System.out.println("postsテーブルを作成しました");

            // データ追加用のSQLクエリを準備
            String SQL = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL);

            // 投稿データ
            Object[][] postsData = {
                {1003, "2023-02-08", "昨日の夜は徹夜でした・・", 13},
                {1002, "2023-02-08", "お疲れ様です！", 12},
                {1003, "2023-02-09", "今日も頑張ります！", 18},
                {1001, "2023-02-09", "無理は禁物ですよ！", 17},
                {1002, "2023-02-10", "明日から連休ですね！", 20}
            };

            // データ追加
            System.out.println("レコード追加を実行します");
            int totalAdded = 0;
            for (Object[] row : postsData) {
                pstmt.setInt(1, (Integer) row[0]);
                pstmt.setDate(2, Date.valueOf((String) row[1]));
                pstmt.setString(3, (String) row[2]);
                pstmt.setInt(4, (Integer) row[3]);
                totalAdded += pstmt.executeUpdate();
            }
            System.out.println(totalAdded + "件のレコードが追加されました");

            // データ検索
            String selectSQL = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
            rs = stmt.executeQuery(selectSQL);
            System.out.println("ユーザーIDが1002のレコードを検索しました");

            // 結果の表示
            int count = 0;
            while (rs.next()) {
                count++;
                Date postedAt = rs.getDate("posted_at");
                String content = rs.getString("post_content");
                int likes = rs.getInt("likes");
                System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容=" + content + "／いいね数=" + likes);
            }
        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException ignore) {
            }
        }
    }
}
