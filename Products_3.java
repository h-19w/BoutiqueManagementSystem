import java.sql.*;
import java.util.Scanner;

public class Products_3 extends Database {
    private int product_id;
    private String product_items;
    private int product_number;
    private String product_type;
    private String product_description;

    public Products_3(int id, String items, int number, String type, String description) {
        this.product_id = id;
        this.product_items = items;
        this.product_number = number;
        this.product_type = type;
        this.product_description = description;
    }

    public void addProducts(int id, String items, int number, String type, String description) {
        String insertQuery = "INSERT INTO products (product_id, product_items, product_number, product_type, product_description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, items);
            preparedStatement.setInt(3, number);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, description);
            preparedStatement.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProducts(int id) {
        String checkIdQuery = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery)) {
            checkIdStatement.setInt(1, id);
            ResultSet result = checkIdStatement.executeQuery();

            if (!result.next()) {
                System.out.println("ID " + id + " does not exist. Unable to update product information.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter New Items:");
                String updatedItems = scanner.next();
                System.out.println("Enter New Number:");
                int updatedNumber = scanner.nextInt();
                System.out.println("Enter New Type:");
                String updatedType = scanner.next();
                System.out.println("Enter New Description:");
                String updatedDescription = scanner.next();

                updateProducts(id, updatedItems, updatedNumber, updatedType, updatedDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProducts(int id, String updatedItems, int updatedNumber, String updatedType, String updatedDescription) {
        String updateQuery = "UPDATE products SET product_items = ?, product_number = ?, product_type = ?, product_description = ? WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedItems);
            preparedStatement.setInt(2, updatedNumber);
            preparedStatement.setString(3, updatedType);
            preparedStatement.setString(4, updatedDescription);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

            System.out.println("Product with ID " + id + " updated successfully.");

            System.out.println("Changes made:");
            System.out.println("Items: " + updatedItems);
            System.out.println("Number: " + updatedNumber);
            System.out.println("Type: " + updatedType);
            System.out.println("Description: " + updatedDescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProducts(int id) {
        String deleteQuery = "DELETE FROM products WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No product found with ID " + id + ". Deletion unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProducts(int id) {
        String selectQuery = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String items = resultSet.getString("product_items");
                int number = resultSet.getInt("product_number");
                String type = resultSet.getString("product_type");
                String description = resultSet.getString("product_description");
                System.out.println("ID: " + id + ", Items: " + items +  ", Number: " + number + ", Type: " + type + ", Description: " + description);
            } else {
                System.out.println("No product found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayProducts() {
        String selectQuery = "SELECT * FROM products";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String items = resultSet.getString("product_items");
                int number = resultSet.getInt("product_number");
                String type = resultSet.getString("product_type");
                String description = resultSet.getString("product_description");
                System.out.println("ID: " + id + ", Items: " + items +  ", Number: " + number + ", Type: " + type + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void menu() {
        Products_3 example = new Products_3(0, "", 0, "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        int id;
        String items;
        int number;
        String type;
        String description;

        do {
            System.out.println("1. Add Product");
            System.out.println("2. Edit Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Search Product");
            System.out.println("5. Display Products");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter ID to add:");
                    id = getValidIntInput(scanner);
                    System.out.println("Enter Items:");
                    items = scanner.next();
                    System.out.println("Enter Number of Items:");
                    number = scanner.nextInt();
                    System.out.println("Enter Type:");
                    type = scanner.next();
                    System.out.println("Enter Description:");
                    description = scanner.next();

                    example.addProducts(id, items, number, type, description);
                    break;
                case 2:
                    System.out.println("Enter ID to edit: ");
                    id = scanner.nextInt();
                    example.editProducts(id);
                    break;
                case 3:
                    System.out.println("Enter ID to delete:");
                    id = scanner.nextInt();
                    example.deleteProducts(id);
                    break;
                case 4:
                    System.out.println("Enter ID to search:");
                    id = scanner.nextInt();
                    example.searchProducts(id);
                    break;
                case 5:
                    example.displayProducts();
                    break;
                case 6:
                    System.out.println("Exiting the program. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 6);
    }



    public static int getValidIntInput(Scanner scanner) {
        while (true) {
            System.out.println("Enter an ID (must be an integer):");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid ID. Please enter a valid integer ID.");
                scanner.next();
            }
        }
    }
}
