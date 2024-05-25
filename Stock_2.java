import java.sql.*;
import java.util.Scanner;

public class Stock_2 extends Database {
    private int stock_id;
    private String stock_items;
    private int stock_number;
    private String stock_type;
    private String stock_description;

    public Stock_2(int id, String items, int number, String type, String description) {
        this.stock_id = id;
        this.stock_items = items;
        this.stock_number = number;
        this.stock_type = type;
        this.stock_description = description;
    }
    public void addStock(int id, String items, int number, String type, String description) {
        String insertQuery = "INSERT INTO stock (stock_id, stock_items, stock_number, stock_type, stock_description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, items);
            preparedStatement.setInt(3, number);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, description);
            preparedStatement.executeUpdate();
            System.out.println("Stock created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editStock(int id) {
        String checkIdQuery = "SELECT * FROM stock WHERE stock_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery)) {
            checkIdStatement.setInt(1, id);
            ResultSet result = checkIdStatement.executeQuery();

            if (!result.next()) {
                System.out.println("ID " + id + " does not exist. Unable to update stock information.");
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

                updateStock(id, updatedItems, updatedNumber, updatedType, updatedDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateStock(int id, String updatedItems, int updatedNumber, String updatedType, String updatedDescription) {
        String updateQuery = "UPDATE stock SET stock_items = ?, stock_number = ?, stock_type = ?, stock_description = ? WHERE stock_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedItems);
            preparedStatement.setInt(2, updatedNumber);
            preparedStatement.setString(3, updatedType);
            preparedStatement.setString(4, updatedDescription);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

            System.out.println("Stock with ID " + id + " updated successfully.");

            System.out.println("Changes made:");
            System.out.println("Items: " + updatedItems);
            System.out.println("Number of Stock: " + updatedNumber);
            System.out.println("Type: " + updatedType);
            System.out.println("Description: " + updatedDescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStock(int id) {
        String deleteQuery = "DELETE FROM stock WHERE stock_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Stock with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No stock found with ID " + id + ". Deletion unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchStock(int id) {
        String selectQuery = "SELECT * FROM stock WHERE stock_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String items = resultSet.getString("stock_items");
                int number = resultSet.getInt("stock_number");
                String type = resultSet.getString("stock_type");
                String description = resultSet.getString("stock_description");
                System.out.println("ID: " + id + ", Items: " + items + ", Number of Items: " + items + ", Type: " + type + ", Description: " + description);
            } else {
                System.out.println("No stock found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayStock() {
        String selectQuery = "SELECT * FROM stock";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("stock_id");
                String items = resultSet.getString("stock_items");
                int number = resultSet.getInt("stock_number");
                String type = resultSet.getString("stock_type");
                String description = resultSet.getString("stock_description");
                System.out.println("ID: " + id +  ", Items: " + items + ", Number of Items: " + number + ", Type: " + type + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void menu() {
        Stock_2 example = new Stock_2(0, "", 0, "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        int id;
        String items;
        int number;
        String type;
        String description;

        do {
            System.out.println("1. Add Stock");
            System.out.println("2. Edit Stock");
            System.out.println("3. Delete Stock");
            System.out.println("4. Search Stock");
            System.out.println("5. Display Stock");
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

                    example.addStock(id, items, number, type, description);
                    break;
                case 2:
                    System.out.println("Enter ID to edit: ");
                    id = scanner.nextInt();
                    example.editStock(id);
                    break;
                case 3:
                    System.out.println("Enter ID to delete:");
                    id = scanner.nextInt();
                    example.deleteStock(id);
                    break;
                case 4:
                    System.out.println("Enter ID to search:");
                    id = scanner.nextInt();
                    example.searchStock(id);
                    break;
                case 5:
                    example.displayStock();
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