import java.io.IOException;
import java.sql.*;

class CustomerService {
    public static void addToCart(int customerId, int shoeId) {
        try (Connection conn = DatabaseConnector.getConnection()) {

            // Hämta befintlig order om den finns via en sql fråga som anropas
            String getOrderQuery = "SELECT id FROM CustomerOrder WHERE customerId = ? AND OrderStatus = 1 ORDER BY id DESC LIMIT 1";
            PreparedStatement getOrderStmt = conn.prepareStatement(getOrderQuery);
            getOrderStmt.setInt(1, customerId);
            ResultSet rs = getOrderStmt.executeQuery();

            int customerOrderId = -1;
            if (rs.next()) {
                customerOrderId = rs.getInt("id");
            } else {
                // Skapa en ny order om ingen öppen order finns
                String createOrderQuery = "INSERT INTO CustomerOrder (customerId, OrderStatus) VALUES (?, 1)";
                PreparedStatement createOrderStmt = conn.prepareStatement(createOrderQuery, Statement.RETURN_GENERATED_KEYS); //hämtar det nyss skapade ID:t
                createOrderStmt.setInt(1, customerId);
                createOrderStmt.executeUpdate();
                ResultSet newOrderRs = createOrderStmt.getGeneratedKeys();// för att läsa det nya ID:t
                if (newOrderRs.next()) {
                    customerOrderId = newOrderRs.getInt(1);
                }
            }

            // Kolla om produkten redan finns i varukorgen
            String checkCartQuery = "SELECT quantity FROM Cart WHERE customerOrderId = ? AND shoeId = ?";
            PreparedStatement checkCartStmt = conn.prepareStatement(checkCartQuery);
            checkCartStmt.setInt(1, customerOrderId);
            checkCartStmt.setInt(2, shoeId);
            ResultSet cartRs = checkCartStmt.executeQuery();

            if (cartRs.next()) {
                // Om produkten redan finns i varukorgen, uppdatera kvantiteten istället för att lägga till en ny post
                String updateCartQuery = "UPDATE Cart SET quantity = quantity + 1 WHERE customerOrderId = ? AND shoeId = ?";
                PreparedStatement updateCartStmt = conn.prepareStatement(updateCartQuery);
                updateCartStmt.setInt(1, customerOrderId);
                updateCartStmt.setInt(2, shoeId);
                updateCartStmt.executeUpdate();
            } else {
                // Lägg till produkten i varukorgen om den inte redan finns
                CallableStatement stmt = conn.prepareCall("{CALL addToCart(?, ?, ?)}"); //ropar på callable statment t addtocart
                stmt.setInt(1, customerId);
                stmt.setInt(2, customerOrderId);
                stmt.setInt(3, shoeId);
                stmt.execute();
            }

            System.out.println("The product has been added to your cart.");
        } catch (SQLException | IOException e) {
            System.out.println("Error adding product to cart."); //felhanteringen som kastar ut ett stacktrace om skon inte finns.
            e.printStackTrace();
        }
    }

    // Slutför köpet och uppdaterar orderstatus
    public static void checkout(int customerId) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String updateOrderStatusQuery = "UPDATE CustomerOrder SET OrderStatus = 2 WHERE customerId = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateOrderStatusQuery);
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            System.out.println("\nYour order has been confirmed and is now paid!");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}