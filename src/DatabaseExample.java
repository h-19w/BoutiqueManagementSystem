

// CPS2231_FinalProject.Boutique Project
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class DatabaseExample {
    // CPS2231_FinalProject.Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root"; // Default WAMP server username
    private static final String PASSWORD = ""; // Default WAMP server password (usually blank)

    // Load the JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get a CPS2231_FinalProject.connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

//    public static void main(String[] args) {
//        DatabaseExample c1 = new attr.CPS2231_FinalProject.Customer(1233456, "rob", "98765432", "rob21@gmail.com", "G0liverp00l", "idkADDRESS");
////        String name, String mobile, String email, String username, String password, String address
    }


class Boutiques {
    private int boutique_id;
    private int boutique_owner_id;
    private String boutique_name;
    private String boutique_type;
    private String boutique_description;
    private String boutique_place;

    public void addBoutique() {
        // Implement logic to add a new boutique
    }

    public void editBoutique() {
        // Implement logic to edit an existing boutique
    }

    public void deleteBoutique() {
        // Implement logic to delete a boutique
    }

    public void updateBoutique() {
        // Implement logic to update a boutique
    }

    public void saveBoutique() {
        // Implement logic to save a boutique
    }

    public void searchBoutique() {
        // Implement logic to search for a boutique
    }
}

// CPS2231_FinalProject.Stock Class
class Stocks {
    private int stock_id;
    private ArrayList<String> stock_items;
    private int stock_number;
    private String stock_type;
    private String stock_description;

    public void addStock() {
        // Implement logic to add new stock
    }

    public void editStock() {
        // Implement logic to edit existing stock
    }

    public void deleteStock() {
        // Implement logic to delete stock
    }

    public void updateStock() {
        // Implement logic to update stock
    }

    public void saveStock() {
        // Implement logic to save stock
    }

    public void searchStock() {
        // Implement logic to search for stock
    }
}

// Products Class
class Products {
    private int product_id;
    private int product_customer_id;
    private ArrayList<String> product_items;
    private int product_number;
    private String product_type;
    private String product_description;

    public void addProducts() {
        // Implement logic to add new products
    }

    public void editProducts() {
        // Implement logic to edit existing products
    }

    public void deleteProducts() {
        // Implement logic to delete products
    }

    public void updateProducts() {
        // Implement logic to update products
    }

    public void saveProducts() {
        // Implement logic to save products
    }

    public void searchProducts() {
        // Implement logic to search for products
    }
}

// Sales Class
class S {
    private int sales_id;
    private int sales_customer_id;
    private double sales_amount;
    private String sales_type;
    private String sales_description;

    public void addSales() {
        // Implement logic to add new sales
    }

    public void editSales() {
        // Implement logic to edit existing sales
    }

    public void deleteSales() {
        // Implement logic to delete sales
    }

    public void updateSales() {
        // Implement logic to update sales
    }

    public void saveSales() {
        // Implement logic to save sales
    }

    public void searchSales() {
        // Implement logic to search for sales
    }
}

// Inventory Class
class Inventory {
    private int inventory_id;
    private ArrayList<String> inventory_items;
    private int inventory_number;
    private String inventory_type;
    private String inventory_description;

    public void addInventory() {
        // Implement logic to add new inventory
    }

    public void editInventory() {
        // Implement logic to edit existing inventory
    }

    public void deleteInventory() {
        // Implement logic to delete inventory
    }

    public void updateInventory() {
        // Implement logic to update inventory
    }

    public void saveInventory() {
        // Implement logic to save inventory
    }

    public void searchInventory() {
        // Implement logic to search for inventory
    }
}

// Customers Class
class Customers extends DatabaseExample {
    private int customer_id;
    private String customer_name;
    private String customer_mobile;
    private String customer_email;
    private String customer_username;
    private String customer_password;
    private String customer_address;

    public void Customers(int id, String name, String mobile, String email, String username, String password, String address) {
        this.customer_id = id;
        this.customer_name = name;
        this.customer_mobile = mobile;
        this.customer_email = email;
        this.customer_username = username;
        this.customer_password = password;
        this.customer_address = address;
    }

    // Add/create customers
    public void createCustomers(int id, String name, String mobile, String email, String username, String password, String address) {
        String insertQuery = "INSERT INTO customers (name, mobile, email, username, password, address) VALUES (?, ?, ?, ?, ?, ?)";
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
            System.out.println("CPS2231_FinalProject.Customer created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // View customers from the database
    public void viewCustomers() {
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
    // Update a customer
    public void updateCustomers(int id, String name, String mobile, String email, String username, String password, String address) {
        String updateQuery = "UPDATE customers SET customer_name = ?, customer_mobile = ?, customer_email = ?, customer_username = ?, customer_password = ?, customer_address = ? WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, mobile);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, address);
            preparedStatement.setInt(7, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a customer
    public void deleteCustomers(int id) {
        String deleteQuery = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCustomers() {
        // Implement logic to edit existing customers
    }

    public void saveCustomers() {
        // Implement logic to save customers
    }

    public void searchCustomers() {
        // Implement logic to search for customers
    }

    // Assume getConnection is defined in DatabaseExample
//    protected Connection getConnection() {
//        // Implementation of the database CPS2231_FinalProject.connection
//        return null;
//    }

    public static void main(String[] args) {
        // Initialize DatabaseExample with CPS2231_FinalProject.connection details
        Customers example = new Customers();

        // Create a new user
        example.createCustomers(1233456, "rob", "98765432", "rob21@gmail.com", "robbie", "G0liverp00l", "idkADDRESS");
        example.viewCustomers();
    }
}


