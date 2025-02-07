import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Customer {
    // Autentiserar användaren
    public static int authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT id FROM Customer WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return -1; //fel lr nget ide
    }
}
