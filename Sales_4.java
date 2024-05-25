import java.sql.*;
import java.util.Scanner;

public class Sales_4 extends Database {
    private int sales_id;
    private String sales_customer_id;
    private double sales_amount;
    private String sales_type;
    private String sales_description;

    public Sales_4(int id, String customerId, double amount, String type, String description) {
        this.sales_id = id;
        this.sales_customer_id = customerId;
        this.sales_amount = amount;
        this.sales_type = type;
        this.sales_description = description;
    }

    public void addSales(int id, String customerId, double amount, String type, String description) {
        String insertQuery = "INSERT INTO sales (sales_id, sales_customer_id, sales_amount, sales_type, sales_description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, customerId);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, description);
            preparedStatement.executeUpdate();
            System.out.println("Sales record created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editSales(int id) {
        String checkIdQuery = "SELECT * FROM sales WHERE sales_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery)) {
            checkIdStatement.setInt(1, id);
            ResultSet result = checkIdStatement.executeQuery();

            if (!result.next()) {
                System.out.println("ID " + id + " does not exist. Unable to update sales information.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter New Customer ID:");
                String updatedCustomerId = scanner.next();
                System.out.println("Enter New Amount:");
                double updatedAmount = scanner.nextDouble();
                System.out.println("Enter New Type:");
                String updatedType = scanner.next();
                System.out.println("Enter New Description:");
                String updatedDescription = scanner.next();

                updateSales(id, updatedCustomerId, updatedAmount, updatedType, updatedDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSales(int id, String updatedCustomerId, double updatedAmount, String updatedType, String updatedDescription) {
        String updateQuery = "UPDATE sales SET sales_customer_id = ?, sales_amount = ?, sales_type = ?, sales_description = ? WHERE sales_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedCustomerId);
            preparedStatement.setDouble(2, updatedAmount);
            preparedStatement.setString(3, updatedType);
            preparedStatement.setString(4, updatedDescription);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

            System.out.println("Sales record with ID " + id + " updated successfully.");

            System.out.println("Changes made:");
            System.out.println("Customer ID: " + updatedCustomerId);
            System.out.println("Amount: " + updatedAmount);
            System.out.println("Type: " + updatedType);
            System.out.println("Description: " + updatedDescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSales(int id) {
        String deleteQuery = "DELETE FROM sales WHERE sales_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Sales record with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No sales record found with ID " + id + ". Deletion unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchSales(int id) {
        String selectQuery = "SELECT * FROM sales WHERE sales_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String customerId = resultSet.getString("sales_customer_id");
                double amount = resultSet.getDouble("sales_amount");
                String type = resultSet.getString("sales_type");
                String description = resultSet.getString("sales_description");
                System.out.println("ID: " + id + ", Customer ID: " + customerId + ", Amount: " + amount + ", Type: " + type + ", Description: " + description);
            } else {
                System.out.println("No sales record found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displaySales() {
        String selectQuery = "SELECT * FROM sales";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("sales_id");
                String customerId = resultSet.getString("sales_customer_id");
                double amount = resultSet.getDouble("sales_amount");
                String type = resultSet.getString("sales_type");
                String description = resultSet.getString("sales_description");
                System.out.println("ID: " + id + ", Customer ID: " + customerId + ", Amount: " + amount + ", Type: " + type + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        Sales_4 example = new Sales_4(0, "", 0.0, "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        int id;
        String customerId;
        double amount;
        String type;
        String description;

        do {
            System.out.println("1. Add Sales");
            System.out.println("2. Edit Sales");
            System.out.println("3. Delete Sales");
            System.out.println("4. Search Sales");
            System.out.println("5. Display Sales");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter ID to add:");
                    id = getValidIntInput(scanner);
                    System.out.println("Enter Customer ID:");
                    customerId = scanner.next();
                    System.out.println("Enter Amount:");
                    amount = scanner.nextDouble();
                    System.out.println("Enter Type:");
                    type = scanner.next();
                    System.out.println("Enter Description:");
                    description = scanner.next();

                    example.addSales(id,  customerId, amount, type, description);
                    break;
                case 2:
                    System.out.println("Enter ID to edit: ");
                    id = scanner.nextInt();
                    example.editSales(id);
                    break;
                case 3:
                    System.out.println("Enter ID to delete:");
                    id = scanner.nextInt();
                    example.deleteSales(id);
                    break;
                case 4:
                    System.out.println("Enter ID to search:");
                    id = scanner.nextInt();
                    example.searchSales(id);
                    break;
                case 5:
                    example.displaySales();
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
