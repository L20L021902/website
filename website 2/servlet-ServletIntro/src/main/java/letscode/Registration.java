package letscode;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registration {

    public static void mockRegister() {
        try {
            Connection c = Database.getConnection();
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
}
