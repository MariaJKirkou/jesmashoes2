import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//har hand om skon å tar hand om de i shoppingen,dvs  används för att interagera med databasen som innehåller information om skor

public class ShoeService {
    // lista m brand fr tabell shoe, distinct för att undvika dubblicering. returnerar list av strängar brands
    public static List<String> getAvailableBrands() {
        List<String> brands = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT DISTINCT brand FROM Shoe";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                brands.add(rs.getString("brand"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return brands;
    }


    public static List<String> getAvailableColors(String brand) {
        //Hämtar alla unika färger,som finns för ett specifikt brand.
        List<String> colors = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT DISTINCT colour FROM Shoe WHERE brand = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, brand);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                colors.add(rs.getString("colour"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return colors;
    }

    // Hämtar alla size som finns för en viss färg, varumärke. Returnerar en lista av heltal list-integer, med skostorlekar.
    //används EFTER att anv. valt färg å varumärke.
    public static List<Integer> getAvailableSizes(String brand, String color) {
        List<Integer> sizes = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT DISTINCT size FROM Shoe WHERE brand = ? AND colour = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, brand);
            pstmt.setString(2, color);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sizes.add(rs.getInt("size"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return sizes;
    }

    // Hämtar en specifik sko baserat på dess info, kontroll i lagersaldo (inventory) returnerar null om den är slutsåld.
    public static ShoeInformation getShoe(String brand, String color, int size) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT s.id, s.brand, s.colour, s.size, s.price, i.stock " +
                    "FROM Shoe s " +
                    "JOIN Inventory i ON s.id = i.shoeId " +  // Ser till att Inventory-tabellen används
                    "WHERE s.brand = ? AND s.colour = ? AND s.size = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, brand);
            pstmt.setString(2, color);
            pstmt.setInt(3, size);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");  // Hämtar lagersaldot
                if (stock <= 0) { //kollar om skon finns å följer kriterierna
                    System.out.println("ERROR: The product is out of sale!"); //felhanteringen som kommer om skon ej finns i lager.
                    return null;
                }

                ShoeInformation shoe = new ShoeInformation();
                shoe.id = rs.getInt("id");
                shoe.brand = rs.getString("brand");
                shoe.colour = rs.getString("colour");
                shoe.size = rs.getInt("size");
                shoe.price = rs.getInt("price");
                return shoe;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
