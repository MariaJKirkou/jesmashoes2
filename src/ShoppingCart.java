import java.util.List;
import java.util.Scanner;
//hanterar shoppingflödet i ett skosystem där användaren kan logga in,
// välja skor och lägga till dem i en kundvagn innan de eventuellt går till kassan.

public class ShoppingCart {
    public void startShopping(String username, String password) {
        int customerId = Customer.authenticateUser(username, password);
        if (customerId == -1) {
            System.out.println("Invalid username or password, please try again!");
            return;
        }
        System.out.println("Login successful! Welcome " + username + "!");

        Scanner scanner = new Scanner(System.in);
        boolean shopping = true; //boolean för användaren kan fortsätta handla tills hen bestämmer sig för att sluta

        while (shopping) {
            System.out.println("Available brands:");
            List<String> brands = ShoeService.getAvailableBrands();
            for (int i = 0; i < brands.size(); i++) {
                System.out.println((i + 1) + ". " + brands.get(i));
            }
            System.out.print("Select a brand: ");
            int brandChoice = scanner.nextInt();
            String selectedBrand = brands.get(brandChoice - 1);

            System.out.println("Available colors for " + selectedBrand + ":");
            List<String> colors = ShoeService.getAvailableColors(selectedBrand);
            for (int i = 0; i < colors.size(); i++) {
                System.out.println((i + 1) + ". " + colors.get(i));
            }
            System.out.print("Select a color: ");
            int colorChoice = scanner.nextInt();
            String selectedColor = colors.get(colorChoice - 1);

            System.out.println("Available sizes for " + selectedBrand + " in " + selectedColor + ":");
            List<Integer> sizes = ShoeService.getAvailableSizes(selectedBrand, selectedColor);
            for (int i = 0; i < sizes.size(); i++) {
                System.out.println((i + 1) + ". Size " + sizes.get(i));
            }
            System.out.print("Select a size: ");
            int sizeChoice = scanner.nextInt();
            int selectedSize = sizes.get(sizeChoice - 1);

            ShoeInformation selectedShoe = ShoeService.getShoe(selectedBrand, selectedColor, selectedSize);
            if (selectedShoe == null) {
            } else {
                CustomerService.addToCart(customerId, selectedShoe.id);
            }

            System.out.print("Do you want to add more items (y/n)? ");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("n")) {
                shopping = false;
            }
        }

        System.out.print("Do you want to check out? (y/n): ");
        String checkoutChoice = scanner.next();
        if (checkoutChoice.equalsIgnoreCase("y")) {
            CustomerService.checkout(customerId);
        }
        System.out.println("\nThank you for your purchase!");
    }
}