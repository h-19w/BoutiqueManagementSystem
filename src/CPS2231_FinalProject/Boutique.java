package CPS2231_FinalProject;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
//boutique_id, boutique_owner_id, boutique_name, boutique_type,boutique_description, boutique_place

public class Boutique extends Database {
    private int boutique_id;
    private int boutique_owner_id;
    private String boutique_name;
    private String boutique_type;
    private String boutique_description;
    private String boutique_place;

    public Boutique(int id, int owner_id, String name, String type, String description, String place) {
        this.boutique_id = id;
        this.boutique_owner_id = owner_id;
        this.boutique_name = name;
        this.boutique_type = type;
        this.boutique_description = description;
        this.boutique_place = place;
    }

    public void addBoutique(int id, int owner_id, String name, String type, String description, String place) {
        String insertQuery = "INSERT INTO boutique (boutique_id, boutique_owner_id, boutique_name, boutique_type, boutique_description, boutique_place) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, owner_id);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, place);
            preparedStatement.executeUpdate();
            System.out.println("Boutique created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBoutique(int id) {
        String checkIdQuery = "SELECT * FROM boutique WHERE boutique_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery)) {
            checkIdStatement.setInt(1, id);
            ResultSet result = checkIdStatement.executeQuery();

            if (!result.next()) {
                System.out.println("ID " + id + " does not exist. Unable to update boutique information.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter New Owner ID:");
                int updatedOwnerID = scanner.nextInt();
                System.out.println("Enter New Name:");
                String updatedName = scanner.next();
                System.out.println("Enter New Type:");
                String updatedType = scanner.next();
                System.out.println("Enter New Description:");
                String updatedDescription = scanner.next();
                System.out.println("Enter New Place:");
                String updatedPlace = scanner.next();

                // Update boutique information in the database
                updateBoutique(id, updatedOwnerID, updatedName, updatedType, updatedDescription, updatedPlace);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBoutique(int id, int updatedOwnerID, String updatedName, String updatedType, String updatedDescription, String updatedPlace) {
        String updateQuery = "UPDATE boutique SET boutique_owner_id = ?, boutique_name = ?, boutique_type = ?, boutique_description = ?, boutique_place = ? WHERE boutique_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, updatedOwnerID);
            preparedStatement.setString(2, updatedName);
            preparedStatement.setString(3, updatedType);
            preparedStatement.setString(4, updatedDescription);
            preparedStatement.setString(5, updatedPlace);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            System.out.println("CPS2231_FinalProject.Boutique with ID " + id + " updated successfully.");

            // Display changes made
            System.out.println("Changes made:");
            System.out.println("Owner ID: " + updatedOwnerID);
            System.out.println("Name: " + updatedName);
            System.out.println("Type: " + updatedType);
            System.out.println("Description: " + updatedDescription);
            System.out.println("Place: " + updatedPlace);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBoutique(int id) {
        String deleteQuery = "DELETE FROM boutique WHERE boutique_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("CPS2231_FinalProject.Boutique with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No boutique found with ID " + id + ". Deletion unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchBoutique(int id) {
        String selectQuery = "SELECT * FROM boutique WHERE boutique_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String o_id = resultSet.getString("boutique_id");
                String name = resultSet.getString("boutique_name");
                String type = resultSet.getString("boutique_type");
                String description = resultSet.getString("boutique_description");
                String place = resultSet.getString("boutique_place");
                System.out.println("ID: " + id + ", Owner ID: " + o_id +", Name: " + name +  ", Type: " + type + ", Description: " + description + ", Place: " + place);
            } else {
                System.out.println("No boutique found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBoutique() {
        String selectQuery = "SELECT * FROM boutique";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("boutique_id");
                String o_id = resultSet.getString("boutique_id");
                String name = resultSet.getString("boutique_name");
                String type = resultSet.getString("boutique_type");
                String description = resultSet.getString("boutique_description");
                String place = resultSet.getString("boutique_place");
                System.out.println("ID: " + id + ", Owner ID: " + o_id +", Name: " + name +  ", Type: " + type + ", Description: " + description + ", Place: " + place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void menu() {
        Boutique example = new Boutique(0, 0, "", "", "", "");
        Scanner scanner = new Scanner(System.in);

        int choice;
        int id;
        int o_id;
        String name;
        String type;
        String description;
        String place;

            System.out.println("1. Add Boutique");
            System.out.println("2. Edit Boutique");
            System.out.println("3. Delete Boutique");
            System.out.println("4. Search Boutique");
            System.out.println("5. Display Boutique");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Enter ID to add:");
                    id = getIntInput(scanner);
                    System.out.println("Enter Owner ID:");
                    o_id = getIntInput(scanner);
                    System.out.println("Enter Name:");
                    name = getStringInput(scanner);
                    System.out.println("Enter Type:");
                    type = getStringInput(scanner);
                    System.out.println("Enter Description:");
                    description = getStringInput(scanner);
                    System.out.println("Enter Place:");
                    place = getStringInput(scanner);


                    example.addBoutique(id, o_id, name, type, description, place);
                    break;
                case 2:
                    System.out.println("Enter ID to edit: ");
                    id = getIntInput(scanner);
                    example.editBoutique(id);
                    break;
                case 3:
                    System.out.println("Enter ID to delete:");
                    id = getIntInput(scanner);
                    example.deleteBoutique(id);
                    break;
                case 4:
                    System.out.println("Enter ID to search:");
                    id = getIntInput(scanner);
                    example.searchBoutique(id);
                    break;
                case 5:
                    example.displayBoutique();
                    break;
                case 6:
                    System.out.println("Returning to Main Menu....");
                    break;
                default:
                    System.out.println("Invalid choice. Returning to Main Menu....");
            }
    }
    //utility methods
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
