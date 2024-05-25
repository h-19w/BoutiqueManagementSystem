import java.sql.*;
import java.util.Scanner;

public class Customer_6 extends Database {
    private int customer_id;
    private String customer_name;
    private String customer_mobile;
    private String customer_email;
    private String customer_username;
    private String customer_password;
    private String customer_address;

    public Customer_6(int id, String name, String mobile, String email, String username, String password, String address) {
        this.customer_id = id;
        this.customer_name = name;
        this.customer_mobile = mobile;
        this.customer_email = email;
        this.customer_username = username;
        this.customer_password = password;
        this.customer_address = address;
    }

    public void addCustomer(int id, String name, String mobile, String email, String username, String password, String address) {
        String insertQuery = "INSERT INTO customers (customer_id, customer_name, customer_mobile, customer_email, customer_username, customer_password, customer_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, mobile);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, username);
            preparedStatement.setString(6, password);
            preparedStatement.setString(7, address);
            preparedStatement.executeUpdate();
            System.out.println("Customer created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCustomer(int id) {
        String checkIdQuery = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery)) {
            checkIdStatement.setInt(1, id);
            ResultSet result = checkIdStatement.executeQuery();

            if (!result.next()) {
                System.out.println("ID " + id + " does not exist. Unable to update customer information.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter New Name:");
                String updatedName = scanner.next();
                System.out.println("Enter New Mobile:");
                String updatedMobile = scanner.next();
                System.out.println("Enter New Email:");
                String updatedEmail = scanner.next();
                System.out.println("Enter New Username:");
                String updatedUsername = scanner.next();
                System.out.println("Enter New Password:");
                String updatedPassword = scanner.next();
                System.out.println("Enter New Address:");
                String updatedAddress = scanner.next();

                updateCustomer(id, updatedName, updatedMobile, updatedEmail, updatedUsername, updatedPassword, updatedAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCustomer(int id, String updatedName, String updatedMobile, String updatedEmail, String updatedUsername, String updatedPassword, String updatedAddress) {
        String updateQuery = "UPDATE customers SET customer_name = ?, customer_mobile = ?, customer_email = ?, customer_username = ?, customer_password = ?, customer_address = ? WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedName);
            preparedStatement.setString(2, updatedMobile);
            preparedStatement.setString(3, updatedEmail);
            preparedStatement.setString(4, updatedUsername);
            preparedStatement.setString(5, updatedPassword);
            preparedStatement.setString(6, updatedAddress);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            System.out.println("Customer with ID " + id + " updated successfully.");

            System.out.println("Changes made:");
            System.out.println("Name: " + updatedName);
            System.out.println("Mobile: " + updatedMobile);
            System.out.println("Email: " + updatedEmail);
            System.out.println("Username: " + updatedUsername);
            System.out.println("Password: " + updatedPassword);
            System.out.println("Address: " + updatedAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {
        String deleteQuery = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No customer found with ID " + id + ". Deletion unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchCustomer(int id) {
        String selectQuery = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("customer_name");
                String mobile = resultSet.getString("customer_mobile");
                String email = resultSet.getString("customer_email");
                String username = resultSet.getString("customer_username");
                String password = resultSet.getString("customer_password");
                String address = resultSet.getString("customer_address");
                System.out.println("ID: " + id + ", Name: " + name + ", Mobile: " + mobile + ", Email: " + email + ", Username: " + username + ", Password: " + password + ", Address: " + address);
            } else {
                System.out.println("No customer found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayCustomer() {
        String selectQuery = "SELECT * FROM customers";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String mobile = resultSet.getString("customer_mobile");
                String email = resultSet.getString("customer_email");
                String username = resultSet.getString("customer_username");
                String password = resultSet.getString("customer_password");
                String address = resultSet.getString("customer_address");
                System.out.println("ID: " + id + ", Name: " + name + ", Mobile: " + mobile + ", Email: " + email + ", Username: " + username + ", Password: " + password + ", Address: " + address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        Customer_6 example = new Customer_6(0, "", "", "", "", "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        int id;
        String name;
        String mobile;
        String email;
        String username;
        String password;
        String address;

        do {
            System.out.println("1. Add Customer");
            System.out.println("2. Edit Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Search Customer");
            System.out.println("5. Display Customer");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter ID to add:");
                    id = getValidIntInput(scanner);
                    System.out.println("Enter Name:");
                    name = scanner.next();
                    System.out.println("Enter Mobile:");
                    mobile = scanner.next();
                    System.out.println("Enter Email:");
                    email = scanner.next();
                    System.out.println("Enter Username:");
                    username = scanner.next();
                    System.out.println("Enter Password:");
                    password = scanner.next();
                    System.out.println("Enter Address:");
                    address = scanner.next();

                    example.addCustomer(id, name, mobile, email, username, password, address);
                    break;
                case 2:
                    System.out.println("Enter ID to edit: ");
                    id = scanner.nextInt();
                    example.editCustomer(id);
                    break;
                case 3:
                    System.out.println("Enter ID to delete:");
                    id = scanner.nextInt();
                    example.deleteCustomer(id);
                    break;
                case 4:
                    System.out.println("Enter ID to search:");
                    id = scanner.nextInt();
                    example.searchCustomer(id);
                    break;
                case 5:
                    example.displayCustomer();
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