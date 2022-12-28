package letscode;

import java.sql.*;

public class Database {

    public static boolean initTables() {
        Connection c;
        Statement stmt;
        try {
            c = getConnection();

            // Users
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(ID INTEGER PRIMARY KEY," +
                    " USERNAME           TEXT    NOT NULL UNIQUE, " +
                    " PASSWORD           BLOB    NOT NULL, " + // Hashed password
                    " REALNAME           TEXT    NOT NULL, " +
                    " SEX                TEXT    NOT NULL, " +
                    " ADDRESS            TEXT    NOT NULL, " +
                    " PHONE              NUMBER  NOT NULL, " +
                    " CHECK (SEX in (\"男\",\"女\")));";
            stmt.executeUpdate(sql);
            stmt.close();

            // Logged in tokens
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS TOKENS" +
                    "(ID INTEGER PRIMARY KEY," +
                    " USERNAME        TEXT    NOT NULL UNIQUE, " +
                    " TOKEN           TEXT    NOT NULL, " +
                    " VALID_UNTIL     INTEGER NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();

            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:database.db");
    }

    public static boolean updateUserInfo(String username, String realname, String sex, String address, long phone) {
        try {
            Connection c = getConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE USERS SET REALNAME = ?, SEX = ?, ADDRESS = ?, PHONE = ? WHERE USERNAME is ?");

            stmt.setString(1, realname);
            stmt.setString(2, sex);
            stmt.setString(3, address);
            stmt.setLong(4, phone);
            stmt.setString(5, username);

            stmt.executeUpdate();

            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
