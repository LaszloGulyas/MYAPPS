import java.nio.file.Paths;
import java.sql.*;

public class DBoperations {

    private Connection conn;
    private Portfolio pf;

    public DBoperations(Portfolio pf) {
        this.pf = pf;
        openDatabase();
    }

    private void openDatabase() {
        System.out.println("Opening database...");
        String path = "jdbc:sqlite:" + Paths.get("").toAbsolutePath().toString() + "\\accounts.db";
        System.out.println(path);
        try {
            conn = DriverManager.getConnection(path);
            Statement statement = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, null,null);
            if (tables.next()) {
                System.out.println("Database found...");
                this.loadDataFromDB();
            } else {
                System.out.println("Create new database...");
                statement.execute("CREATE TABLE accounts (bank TEXT, account TEXT, balance INTEGER)");
                System.out.println("New database created: accounts");
            }

        } catch (SQLException e) {
            System.out.println("error " + e);
        }
    }

    private void loadDataFromDB() {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM accounts");
            int i=0;
            while (rs.next()) {
                Bank b = pf.getOrAddBank(rs.getString("bank"));
                b.addAccount(rs.getString("account"), rs.getInt("balance"));
                i++;
            }
            System.out.println("Loaded accounts: " + i);
        }
        catch (SQLException e) {
            System.out.println("error " + e);
        }
    }

    public void updateDB() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM accounts");
            for (Bank b : pf.getBankArray()) {
                for (Account a : b.getAccountArray()) {
                    statement.execute("INSERT INTO accounts VALUES ('"+b.getBankName()+"'," +
                            "'"+a.getAccountName()+"',"+a.getBalance()+")");
                }
            }
        }
        catch (SQLException e) {
            System.out.println("error " + e);
        }
    }
}