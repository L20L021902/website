package letscode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class Mock {

    public static void mockGoods() {
        try {
            Connection c = Database.getConnection();
            PreparedStatement stmt;

            stmt = c.prepareStatement("INSERT OR IGNORE INTO GOODS VALUES (?,?,?,?,?,?,?,?,?)");

            stmt.setInt(2, 567001231);
            stmt.setString(3, "Apple");
            stmt.setString(4, "电子产品");
            stmt.setInt(5, 5999);
            stmt.setInt(6, 6399);
            stmt.setInt(7, 0);
            stmt.setString(8, "却贷");
            stmt.setLong(9, Instant.parse("2022-10-23T16:15:00.00Z").getEpochSecond());

            stmt.executeUpdate();
            stmt.close();

            stmt = c.prepareStatement("INSERT OR IGNORE INTO GOODS VALUES (?,?,?,?,?,?,?,?,?)");

            stmt.setInt(2, 456782345);
            stmt.setString(3, "圆规");
            stmt.setString(4, "文化用品");
            stmt.setInt(5, 3);
            stmt.setInt(6, 6);
            stmt.setInt(7, 100);
            stmt.setString(8, "有货");
            stmt.setLong(9, Instant.parse("2022-10-24T17:15:00.00Z").getEpochSecond());

            stmt.executeUpdate();
            stmt.close();

            stmt = c.prepareStatement("INSERT OR IGNORE INTO GOODS VALUES (?,?,?,?,?,?,?,?,?)");

            stmt.setInt(2, 667512945);
            stmt.setString(3, "布熊");
            stmt.setString(4, "玩具");
            stmt.setInt(5, 50);
            stmt.setInt(6, 100);
            stmt.setInt(7, 150);
            stmt.setString(8, "有货");
            stmt.setLong(9, Instant.parse("2022-10-24T17:18:00.00Z").getEpochSecond());

            stmt.executeUpdate();
            stmt.close();

            c.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
