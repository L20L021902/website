package letscode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class Mock {

    public static void mockGoods(String username) {
        try {
            Connection c = Database.getConnection(username);
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

    public static void mockClients(String username) {
        try {
            Connection c = Database.getConnection(username);
            PreparedStatement stmt;

            stmt = c.prepareStatement("INSERT OR IGNORE INTO CLIENTS VALUES (?,?,?,?,?,?)");

            stmt.setInt(2, 233454);
            stmt.setString(3, "Jack");
            stmt.setString(4, "男");
            stmt.setString(5, "大道街");
            stmt.setLong(6, 13345676457L);

            stmt.executeUpdate();
            stmt.close();

            stmt = c.prepareStatement("INSERT OR IGNORE INTO CLIENTS VALUES (?,?,?,?,?,?)");

            stmt.setInt(2, 365478);
            stmt.setString(3, "John");
            stmt.setString(4, "男");
            stmt.setString(5, "主体建筑天堂");
            stmt.setLong(6, 13445576483L);

            stmt.executeUpdate();
            stmt.close();

            stmt = c.prepareStatement("INSERT OR IGNORE INTO CLIENTS VALUES (?,?,?,?,?,?)");

            stmt.setInt(2, 263559);
            stmt.setString(3, "Olivia");
            stmt.setString(4, "女");
            stmt.setString(5, "通往大学的主要门户");
            stmt.setLong(6, 13445576583L);

            stmt.executeUpdate();
            stmt.close();

            c.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void mockRegister() {
        try {
            Connection c = Database.getConnectionToMain();
            PreparedStatement stmt = c.prepareStatement("insert or ignore into USERS values (?,?,?,?,?,?,?)");

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest("password".getBytes(StandardCharsets.UTF_8));
            stmt.setString(2, "admin");
            stmt.setBytes(3, hash);
            stmt.setString(4, "Name");
            stmt.setString(5, "男");
            stmt.setString(6, "Address");
            stmt.setInt(7, 100);

            stmt.executeUpdate();

            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void mockSales(String username) {
        try (
                Connection c = Database.getConnection(username)
        ){
            PreparedStatement stmt;

            stmt = c.prepareStatement("INSERT OR IGNORE INTO SALES VALUES (?,?,?,?,?,?,?)");

            stmt.setInt(2, 567001231);
            stmt.setInt(3, 233454);
            stmt.setLong(4, 3750000);
            stmt.setString(5, "未保存");
            stmt.setString(6, "667512945:37500");
            stmt.setLong(7, Instant.parse("2022-10-23T16:15:00.00Z").getEpochSecond());

            stmt.executeUpdate();
            stmt.close();

            stmt = c.prepareStatement("INSERT OR IGNORE INTO SALES VALUES (?,?,?,?,?,?,?)");

            stmt.setInt(2, 456782345);
            stmt.setInt(3, 365478);
            stmt.setLong(4, 3750000);
            stmt.setString(5, "已经交");
            stmt.setString(6, "667512945:37500");
            stmt.setLong(7, Instant.parse("2022-10-24T17:15:00.00Z").getEpochSecond());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
