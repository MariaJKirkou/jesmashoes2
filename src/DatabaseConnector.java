import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static Connection connection;

    public static Connection getConnection() throws IOException, SQLException {
        if (connection == null || connection.isClosed()) {
            Properties p = new Properties();
            p.load(new FileInputStream("C:\\Users\\PC\\IdeaProjects\\DataBaser\\jesmashoes2\\src\\settings.properties"));

            connection = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password")
            );
        }
        return connection;
    }
}