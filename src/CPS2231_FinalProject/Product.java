package CPS2231_FinalProject;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Product extends Database {
    private String product_name;
    private int product_customer_id;
    private String product_items;
    private int product_number;
    private String product_type;
    private String product_description;

    public Product(String name, int pc_id, String items, int p_num, String type, String description){
        this.product_name = name;
        this.product_customer_id = pc_id;
        this.product_items = items;
        this.product_number = p_num;
        this.product_type = type;
        this.product_description = description;
    }

    // Add/create customer
    public void addProduct(String name, int pc_id, String items, int p_num, String type, String description) {
        String insertQuery = "INSERT INTO products (product_name , product_customer_id, product_items, product_number, product_type, product_description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, pc_id);
            preparedStatement.setString(3, items);
            preparedStatement.setInt(4, p_num);
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, description);
            preparedStatement.executeUpdate();
            System.out.println("CPS2231_FinalProject.Product created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit customer information
    public void editProduct(String name) {
        String checkIdQuery = "SELECT * FROM product" + "WHERE product_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery)) {
            checkIdStatement.setString(1, name);
            ResultSet result = checkIdStatement.executeQuery();

            if (!result.next()) {
                System.out.println("ID " + name + " does not exist. Unable to update product information.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter New ID:");
                int updatedID = scanner.nextInt();
                System.out.println("Enter New Items:");
                String updatedItems = scanner.next();
                System.out.println("Enter New Number of Items:");
                int updatedNumber = scanner.nextInt();
                System.out.println("Enter New Type:");
                String updatedType = scanner.next();
                System.out.println("Enter New Description:");
                String updatedDescription = scanner.next();

                // Update customer information in the database
                updateProduct(name, updatedID, updatedItems, updatedNumber, updatedType, updatedDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Delete a customer
    public void deleteProduct(String name) {
        String deleteQuery = "DELETE FROM product WHERE product_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update customer information
    public void updateProduct(String name, int newPc_id, String newItems, int newP_num, String newType, String newDescription) {
        try {
            // Get current customer information
            String getCurrentInfoQuery = "SELECT * FROM product WHERE product_name = ?";
            try (Connection connection = getConnection();
                 PreparedStatement getCurrentInfoStatement = connection.prepareStatement(getCurrentInfoQuery)) {
                getCurrentInfoStatement.setString(1, name);
                ResultSet currentInfoResult = getCurrentInfoStatement.executeQuery();

                if (currentInfoResult.next()) {
                    String currName = currentInfoResult.getString("product_name");
                    String currMobile = currentInfoResult.getString("product_customer_id");
                    String currEmail = currentInfoResult.getString("product_items");
                    String currUsername = currentInfoResult.getString("product_number");
                    String currPassword = currentInfoResult.getString("product_type");
                    String currAddress = currentInfoResult.getString("product_description");

                    // Update the customer information
                    String updateQuery = "UPDATE products SET product_customer_id = ?, product_items = ?, product_number = ?, product_type = ?, product_description = ?, WHERE product_name = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setString(1, name);
                        preparedStatement.setInt(2, newPc_id);
                        preparedStatement.setString(3, newItems);
                        preparedStatement.setInt(4, newP_num);
                        preparedStatement.setString(5, newType);
                        preparedStatement.setString(6, newDescription);


                        System.out.println("CPS2231_FinalProject.Product " + name + " updated successfully.");

                        // Display changes made
                        System.out.println("Changes made:");
                        System.out.println("Name: " + currName + " -> " + name);
                        System.out.println("Mobile: " + currMobile + " -> " + newPc_id);
                        System.out.println("Email: " + currEmail + " -> " + newItems);
                        System.out.println("Username: " + currUsername + " -> " + newP_num);
                        System.out.println("Password: " + currPassword + " -> " + newType);
                        System.out.println("Address: " + currAddress + " -> " + newDescription);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search and display all customers
    public void searchProduct(String name) {
        String selectQuery = "SELECT * FROM product WHERE product_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("product_customer_id");
                String items = resultSet.getString("product_items");
                String number = resultSet.getString("product_number");
                String type = resultSet.getString("product_type");
                String description = resultSet.getString("product_description");
                System.out.println("CPS2231_FinalProject.Product name: " + name + "productID: " + id + ", items: " + items + ", product number: " + number + ", product type: " + type + ", description: " + description );
            } else {
                System.out.println("No product found with name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayProduct () {
        String selectQuery = "SELECT * FROM product";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                String name = resultSet.getString("product_name");
                int id = resultSet.getInt("product_customer_id");
                String items = resultSet.getString("product_items");
                int number = resultSet.getInt("product_number");
                String type = resultSet.getString("product_type");
                String description = resultSet.getString("product_description");
                System.out.println("Name : " + name + ", ID: " + id + ", Items: " + items + ", Number: " + number + ", Type: " + type + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void menu() {
        Product example = new Product("", 0, "", 0, "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        String name;
        int id;
        String items;
        int num;
        String type;
        String description;

            System.out.println("1. Add Product");
            System.out.println("2. Edit Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Search Product");
            System.out.println("5. Display Product");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter Name to add:");
                    name = scanner.next();
                    System.out.println("Enter ID:");
                    id = scanner.nextInt();
                    System.out.println("Enter Items:");
                    items = scanner.next();
                    System.out.println("Enter Number of Items:");
                    num = scanner.nextInt();
                    System.out.println("Enter Type:");
                    type = scanner.next();
                    System.out.println("Enter Description:");
                    description = scanner.next();


                    example.addProduct(name, id, items, num, type, description);
                    break;
                case 2:
                    System.out.println("Enter name to edit: ");
                    name = scanner.next();
                    example.editProduct(name);
                    break;
                case 3:
                    System.out.println("Enter name to delete:");
                    name = scanner.next();
                    example.deleteProduct(name);
                    break;
                case 4:
                    System.out.println("Enter name to search:");
                    name = scanner.next();
                    example.searchProduct(name);
                    break;
                case 5:
                    example.displayProduct();
                    break;
                case 6:
                    System.out.println("Returning to Main Menu....");
                    break;
                default:
                    System.out.println("Invalid choice. Returning to Main Menu....");
            }
    }
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

    private static String getStringInput(Scanner scanner) {
        return scanner.nextLine().trim();
    }

}
