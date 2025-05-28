import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/employer_name";  // ← Fixed line
        String user = "root";
        String password = "02162005me";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
