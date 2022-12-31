package letscode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Database {

    public static boolean initTables(String username) {
        Connection c;
        Statement stmt;
        try {
            c = getConnection(username);

            // Goods
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS GOODS" +
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
            sql = "CREATE TABLE IF NOT EXISTS CLIENTS" +
                    "(ID              INTEGER PRIMARY KEY," +
                    " CLIENT_ID       INTEGER    NOT NULL UNIQUE, " +
                    " NAME            TEXT NOT NULL, " +
                    " SEX             TEXT    NOT NULL, " +
                    " ADDRESS         TEXT    NOT NULL, " +
                    " PHONE           INTEGER  NOT NULL, " +
                    " CHECK (SEX in ('男','女','')));";
            stmt.executeUpdate(sql);
            stmt.close();

            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean initTablesMain() {
        Connection c;
        Statement stmt;
        try {
            c = getConnectionToMain();

            // Users
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(ID INTEGER PRIMARY KEY," +
                    " USERNAME           TEXT    NOT NULL UNIQUE, " +
                    " PASSWORD           BLOB    NOT NULL, " + // Hashed password
                    " REALNAME           TEXT, " +
                    " SEX                TEXT, " +
                    " ADDRESS            TEXT, " +
                    " PHONE              INTEGER, " +
                    " CHECK (SEX in ('男','女','')));";
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

    public static Connection getConnection(String username) throws ClassNotFoundException, SQLException {
        // get username hash
        String usernameHash;
        try {
            usernameHash = Helpers.bytesToHex(MessageDigest.getInstance("MD5")
                    .digest(username.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(String.format("jdbc:sqlite:userdatabases/%s.db", usernameHash));
    }

    public static Connection getConnectionToMain() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:main.db");
    }

    public static boolean updateUserInfo(String username, String realname, String sex, String address, long phone) {
        try {
            Connection c = getConnectionToMain();
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

    public static String getGoods(String username) {
        try {
            Connection c = getConnection(username);
            Statement stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT ID,GOODS_ID,NAME,CATEGORY,BUY_PRICE,SELL_PRICE,STATUS,UPDATE_DATE FROM GOODS");

            JSONArray goodsArray = new JSONArray();
            while (rs.next()) {
                JSONObject goods = new JSONObject();

                goods.put("id", rs.getInt("ID"));
                goods.put("goods_id", rs.getInt("GOODS_ID"));
                goods.put("name", rs.getString("NAME"));
                goods.put("category", rs.getString("CATEGORY"));
                goods.put("buy_price", rs.getInt("BUY_PRICE"));
                goods.put("sell_price", rs.getInt("SELL_PRICE"));
                goods.put("status", rs.getString("STATUS"));
                goods.put("update_date", LocalDateTime.ofEpochSecond(rs.getLong("UPDATE_DATE"), 0, ZoneOffset.UTC).toString());

                goodsArray.add(goods);
            }

            if (goodsArray.isEmpty()) {
                stmt.close();
                c.close();
                return "[]";
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

    public static boolean addGoods(String username, int goodsID, String name, String category, int buyPrice, int sellPrice, int stock,
                                   String status, long updateDate) {

        if (goodsID == 0 || name == null || category == null || buyPrice == 0 || sellPrice == 0 || status == null || updateDate == 0) {
            return false;
        }

        try (Connection c = Database.getConnection(username)) {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO GOODS VALUES (?,?,?,?,?,?,?,?,?)");

            stmt.setInt(2, goodsID);
            stmt.setString(3, name);
            stmt.setString(4, category);
            stmt.setInt(5, buyPrice);
            stmt.setInt(6, sellPrice);
            stmt.setInt(7, stock);
            stmt.setString(8, status);
            stmt.setLong(9, updateDate);

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateGoods(String username, int goodsID, String name, String category, int buyPrice, int sellPrice, int stock,
                                   String status, long updateDate) {

        if (goodsID == 0 || name == null || category == null || buyPrice == 0 || sellPrice == 0 || status == null || updateDate == 0) {
            return false;
        }

        try (Connection c = Database.getConnection(username)) {
            PreparedStatement stmt = c.prepareStatement("UPDATE GOODS SET NAME = ?, CATEGORY = ?, BUY_PRICE = ?, SELL_PRICE = ?," +
                    "STOCK = ?, STATUS = ?, UPDATE_DATE = ? WHERE GOODS_ID is ?");

            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setInt(3, buyPrice);
            stmt.setInt(4, sellPrice);
            stmt.setInt(5, stock);
            stmt.setString(6, status);
            stmt.setLong(7, updateDate);
            stmt.setInt(8, goodsID);

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteGoods(String username, int goodsID) {

        if (goodsID == 0) {
            return false;
        }

        try (Connection c = Database.getConnection(username)) {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM GOODS WHERE GOODS_ID is ?");

            stmt.setInt(1, goodsID);

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getClients(String username) {
        try (
                Connection c = getConnection(username);
                Statement stmt = c.createStatement()
        ){
            ResultSet rs = stmt.executeQuery("SELECT CLIENT_ID,NAME,SEX,PHONE,ADDRESS FROM CLIENTS");

            JSONArray clientsArray = new JSONArray();
            while (rs.next()) {
                JSONObject client = new JSONObject();

                client.put("client_id", rs.getInt("CLIENT_ID"));
                client.put("name", rs.getString("NAME"));
                client.put("sex", rs.getString("SEX"));
                client.put("address", rs.getString("ADDRESS"));
                client.put("phone", rs.getLong("PHONE"));

                clientsArray.add(client);
            }

            if (clientsArray.isEmpty()) {
                return "";
            } else {
                return clientsArray.toJSONString();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addClient(String username, int clientID, String name, String sex, String address, long phone) {

        if (clientID == 0 || name == null || sex == null || address == null || phone == 0) {
            return false;
        }

        try (Connection c = Database.getConnection(username)) {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO CLIENTS VALUES (?,?,?,?,?,?)");

            stmt.setInt(2, clientID);
            stmt.setString(3, name);
            stmt.setString(4, sex);
            stmt.setString(5, address);
            stmt.setLong(6, phone);

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateClient(String username, int clientID, String name, String sex, String address, long phone) {

        if (clientID == 0 || name == null || sex == null || address == null || phone == 0) {
            return false;
        }

        try (Connection c = Database.getConnection(username)) {
            PreparedStatement stmt = c.prepareStatement("UPDATE CLIENTS SET NAME = ?, SEX = ?, ADDRESS = ?, PHONE = ? WHERE CLIENT_ID = ?");

            stmt.setString(1, name);
            stmt.setString(2, sex);
            stmt.setString(3, address);
            stmt.setLong(4, phone);
            stmt.setInt(5, clientID);

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getSales(String username) {
        try (
                Connection c = getConnection(username);
                Statement stmt = c.createStatement()
        ){
            ResultSet rs = stmt.executeQuery("SELECT ID,ORDER_ID,CLIENT_ID,AMOUNT,STATUS,UPDATE_DATE FROM SALES");

            JSONArray salesArray = new JSONArray();
            while (rs.next()) {
                JSONObject sale = new JSONObject();

                sale.put("id", rs.getInt("ID"));
                sale.put("order_id", rs.getInt("ORDER_ID"));
                sale.put("client_id", rs.getInt("CLIENT_ID"));
                sale.put("amount", rs.getLong("AMOUNT"));
                sale.put("status", rs.getString("STATUS"));
                sale.put("update_date", LocalDateTime.ofEpochSecond(rs.getLong("UPDATE_DATE"), 0, ZoneOffset.UTC).toString());

                salesArray.add(sale);
            }

            if (salesArray.isEmpty()) {
                return "";
            } else {
                return salesArray.toJSONString();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean registerUser(String username, String password) {
        try (
                Connection c = getConnectionToMain();
                PreparedStatement stmt = c.prepareStatement("INSERT INTO USERS VALUES (?,?,?,?,?,?,?)")
        ){
            stmt.setString(2, username);
            stmt.setBytes(3, Helpers.getPasswordHash(password));

            stmt.executeUpdate();

            return initTables(username);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String fillWithUserData(String username, String webpage) {
        try (
                Connection c = getConnectionToMain();
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM USERS WHERE USERNAME is ?")
        ){
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) { return null; }

            StringBuilder filledWebpage = new StringBuilder(webpage);

            Helpers.replaceOnce(filledWebpage, "$(username)", username); // first place
            Helpers.replaceOnce(filledWebpage, "$(username)", username); // second place
            Helpers.replaceOnce(filledWebpage, "$(username)", username); // third place

            Helpers.replaceOnce(filledWebpage, "$(id)", Integer.toString(rs.getInt("ID")));

            Helpers.replaceOnce(filledWebpage, "$(realname)", rs.getString("REALNAME"));

            String sex = rs.getString("SEX");
            Helpers.replaceOnce(filledWebpage, "$(nosex)", sex == null ? "selected" : "");
            Helpers.replaceOnce(filledWebpage, "$(male)", sex != null  && sex.equals("男") ? "selected" : "");
            Helpers.replaceOnce(filledWebpage, "$(female)", sex != null  && sex.equals("女") ? "selected" : "");

            Helpers.replaceOnce(filledWebpage, "$(address)", rs.getString("ADDRESS"));

            Helpers.replaceOnce(filledWebpage, "$(phone)", Long.toString(rs.getLong("PHONE")));

            return filledWebpage.toString();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addSale(String username, int orderID, int clientID, JSONArray orderContents, long updateTime) {
        if (orderID == 0 || clientID == 0 || orderContents == null || orderContents.isEmpty() || updateTime == 0) {
            return false;
        }

        try (Connection c = Database.getConnection(username)) {
            // checking that client exists
            PreparedStatement stmt = c.prepareStatement("SELECT ID FROM CLIENTS WHERE CLIENT_ID is ?");

            stmt.setInt(1, clientID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                // the client doesn't exist
                return false;
            }

            stmt.close();

            // checking that all goods exist
            StringBuilder orderContentString = new StringBuilder();
            long totalCost = 0;
            for (Object item: orderContents) {
                JSONObject position = (JSONObject) item;
                int goodsID = (int) position.get("goods_id");
                int goodsAmount = (int) position.get("goods_amount");
                if (goodsAmount <= 0) {
                    // bad value
                    return false;
                }

                stmt = c.prepareStatement("SELECT SELL_PRICE FROM GOODS WHERE GOODS.GOODS_ID is ?");

                stmt.setInt(1, goodsID);

                rs = stmt.executeQuery();
                if (!rs.next()) {
                    // no such goods
                    stmt.close();
                    return false;
                }

                // adding to total order cost
                totalCost += (long) goodsAmount * rs.getInt("SELL_PRICE");

                // adding to order content string
                orderContentString.append(String.format("%d:%d,", goodsID, goodsAmount));

                stmt.close();
            }

            // adding order to the database
            stmt = c.prepareStatement("INSERT INTO SALES VALUES (?,?,?,?,?,?,?)");

            stmt.setInt(2, orderID);
            stmt.setInt(3, clientID);
            stmt.setLong(4, totalCost);
            stmt.setString(5, "未保存");
            stmt.setString(6, orderContentString.toString());
            stmt.setLong(7, updateTime);

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
