package letscode;

import java.sql.*;

public class Debug {

    public static void listAllTokens() {
        System.out.println("Listing all tokens in the database:");

        try (
                Connection c = Database.getConnectionToMain();
                Statement stmt = c.createStatement()
        ){

            ResultSet rs = stmt.executeQuery("SELECT * FROM TOKENS");

            while(rs.next()) {
                System.out.printf("%d: username: %s, token: %s, valid untill: %d%n",
                        rs.getInt("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("TOKEN"),
                        rs.getLong("VALID_UNTIL"));
            }

            System.out.println("Finished listing all tokens");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void listAllUsers() {
        System.out.println("Listing all users in the database:");

        try (
                Connection c = Database.getConnectionToMain();
                Statement stmt = c.createStatement()
        ){

            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

            while(rs.next()) {
                System.out.printf("%d: username: %s%n",
                        rs.getInt("ID"),
                        rs.getString("USERNAME"));
            }

            System.out.println("Finished listing all users");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
