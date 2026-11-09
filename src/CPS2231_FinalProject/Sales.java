package CPS2231_FinalProject;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Sales extends Database {
    private int sales_id;
    private int sales_customer_id;
    private double sales_amount;
    private String sales_type;
    private String sales_description;

    public Sales(int id, int customerId, double amount, String type, String description) {
        this.sales_id = id;
        this.sales_customer_id = customerId;
        this.sales_amount = amount;
        this.sales_type = type;
        this.sales_description = description;
    }

    public void addSales(int id, int customerId, double amount, String type, String description) {
        String insertQuery = "INSERT INTO sales (sales_id, sales_customer_id, sales_amount, sales_type, sales_description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, customerId);
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
                int updatedCustomerId = getIntInput(scanner);
                System.out.println("Enter New Amount:");
                double updatedAmount = getDoubleInput(scanner);
                scanner.nextLine(); // consume the newline
                System.out.println("Enter New Type:");
                String updatedType = getStringInput(scanner);
                System.out.println("Enter New Description:");
                String updatedDescription = getStringInput(scanner);

                updateSales(id, updatedCustomerId, updatedAmount, updatedType, updatedDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSales(int id, int updatedCustomerId, double updatedAmount, String updatedType, String updatedDescription) {
        String updateQuery = "UPDATE sales SET sales_customer_id = ?, sales_amount = ?, sales_type = ?, sales_description = ? WHERE sales_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, updatedCustomerId);
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
                int customerId = resultSet.getInt("sales_customer_id");
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
                int customerId = resultSet.getInt("sales_customer_id");
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
        Sale example = new Sale(0, 0, 0.0, "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        int id;
        int customerId;
        double amount;
        String type;
        String description;

        do {
            System.out.println("1. Add Sales");
            System.out.println("2. Edit Sales");
            System.out.println("3. Delete Sales");
            System.out.println("4. Search Sales");
            System.out.println("5. Display Sales");
            System.out.println("6. Back to Main Menu");
            System.out.println("Enter your choice:");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Enter ID to add:");
                    id = getIntInput(scanner);
                    System.out.println("Enter Customer ID:");
                    customerId = getIntInput(scanner);
                    System.out.println("Enter Amount:");
                    amount = getDoubleInput(scanner);
                    scanner.nextLine(); // consume the newline
                    System.out.println("Enter Type:");
                    type = getStringInput(scanner);
                    System.out.println("Enter Description:");
                    description = getStringInput(scanner);

                    example.addSales(id, customerId, amount, type, description);
                    break;
                case 2:
                    System.out.println("Enter ID to edit:");
                    id = getIntInput(scanner);
                    example.editSales(id);
                    break;
                case 3:
                    System.out.println("Enter ID to delete:");
                    id = getIntInput(scanner);
                    example.deleteSales(id);
                    break;
                case 4:
                    System.out.println("Enter ID to search:");
                    id = getIntInput(scanner);
                    example.searchSales(id);
                    break;
                case 5:
                    example.displaySales();
                    break;
                case 6:
                    System.out.println("Returning to Main Menu....");
                    break;
                default:
                    System.out.println("Invalid choice. Returning to Main Menu....");
            }
        } while (choice != 6);

        scanner.close();
    }

    // Utility methods to get valid inputs
    private static int getIntInput(Scanner scanner) {
        int input;
        while (true) {
            try {
                input = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // clear the invalid input
            }
        }
        return input;
    }

    private static double getDoubleInput(Scanner scanner) {
        double input;
        while (true) {
            try {
                input = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a double.");
                scanner.next(); // clear the invalid input
            }
        }
        return input;
    }

    private static String getStringInput(Scanner scanner) {
        return scanner.nextLine().trim();
    }
}
