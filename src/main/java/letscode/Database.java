package letscode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
                    " PHONE              INTEGER  NOT NULL, " +
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

            // Goods
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS GOODS" +
                    "(ID              INTEGER PRIMARY KEY," +
                    " GOODS_ID        INTEGER    NOT NULL UNIQUE, " +
                    " NAME            TEXT    NOT NULL, " +
                    " CATEGORY        TEXT NOT NULL, " +
                    " BUY_PRICE       INTEGER NOT NULL, " +
                    " SELL_PRICE      INTEGER NOT NULL, " +
                    " STOCK           INTEGER NOT NULL, " +
                    " STATUS          TEXT NOT NULL, " +
                    " UPDATE_DATE     INTEGER NOT NULL, " +
                    " CHECK (STATUS in ('有货', '却贷')))";
            stmt.executeUpdate(sql);
            stmt.close();

            // Sales
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS SALES" +
                    "(ID              INTEGER PRIMARY KEY," +
                    " ORDER_ID        INTEGER    NOT NULL UNIQUE, " +
                    " CLIENT_ID       INTEGER    NOT NULL, " +
                    " AMOUNT          INTEGER NOT NULL, " +
                    " STATUS          TEXT NOT NULL, " +
                    " ORDER_GOODS     TEXT NOT NULL, " +
                    " UPDATE_DATE    INTEGER NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();

            // Clients
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS SALES" +
                    "(ID              INTEGER PRIMARY KEY," +
                    " CLIENT_ID       INTEGER    NOT NULL UNIQUE, " +
                    " NAME            TEXT NOT NULL, " +
                    " SEX             TEXT    NOT NULL, " +
                    " ADDRESS         TEXT    NOT NULL, " +
                    " PHONE           INTEGER  NOT NULL, " +
                    " CHECK (SEX in (\"男\",\"女\")));";
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

    public static String getGoods() {
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT ID,GOODS_ID,NAME,CATEGORY,BUY_PRICE,SELL_PRICE,STATUS,UPDATE_DATE FROM GOODS");

            JSONArray goodsArray = new JSONArray();
            while (rs.next()) {
                JSONObject goods = new JSONObject();

                goods.put("id", rs.getInt("ID"));
                goods.put("goods_id", rs.getInt("GOODS_ID"));
                goods.put("name", rs.getString("NAME"));
                goods.put("category", rs.getString("CATEGORY"));
                goods.put("buy_price", rs.getString("BUY_PRICE"));
                goods.put("sell_price", rs.getString("SELL_PRICE"));
                goods.put("status", rs.getString("STATUS"));
                goods.put("update_date", LocalDateTime.ofEpochSecond(rs.getLong("UPDATE_DATE"), 0, ZoneOffset.UTC).toString());

                goodsArray.add(goods);
            }

            if (goodsArray.isEmpty()) {
                stmt.close();
                c.close();
                return "";
            } else {
                stmt.close();
                c.close();
                return goodsArray.toJSONString();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
