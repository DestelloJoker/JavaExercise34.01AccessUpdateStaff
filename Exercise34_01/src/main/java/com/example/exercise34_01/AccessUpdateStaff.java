/*Program Name: AccessUpdateStaff.java
 * Authors: Austin P
 * Date last Updated: 9/29/2024
 * Purpose: This program uses both javaFX and mySQL to make a program that
 * connects this GUI to a SQL database by changing the final Strings of DB_URL,
 * DB_USER, and DB_PASSWORD, and in the GUI what you input in the input boxes, you have
 * the choice to insert it directly into the mySQL database at the ID provided since the
 * ID is the primary key, update the data at the ID, or view the specific data with the ID provided
 */


package com.example.exercise34_01;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
// Used to Log errors that may occur
import java.util.logging.Logger;
import java.util.logging.Level;
// Imports all parts of java.sql
import java.sql.*;

public class AccessUpdateStaff extends Application {

    // The details for connection to the database
    private static final String DB_URL = "jdbc:mysql://yourhostname:port number usually 3306/yourschema";
    private static final String DB_USER = "username usually root";
    private static final String DB_PASSWORD = "password to your database";

    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(AccessUpdateStaff.class.getName());

    // The UI Components for GUI, the text boxes and buttons
    private final TextField tfId = new TextField();
    private final TextField tfLastName = new TextField();
    private final TextField tfFirstName = new TextField();
    private final TextField tfMI = new TextField();
    private final TextField tfAddress = new TextField();
    private final TextField tfCity = new TextField();
    private final TextField tfState = new TextField();
    private final TextField tfTelephone = new TextField();

    private final Button btnView = new Button("View");
    private final Button btnInsert = new Button("Insert");
    private final Button btnUpdate = new Button("Update");
    private final Button btnClear = new Button("Clear");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a grid layout for the input fields
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Adds the input fields and labels to the grid and adjust the values
        // to fit them on the same or their own 'level'
        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(tfId, 1, 0);

        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(tfLastName, 1, 1);

        gridPane.add(new Label("First Name:"), 2, 1);
        gridPane.add(tfFirstName, 3, 1);

        gridPane.add(new Label("MI:"), 0, 3);
        gridPane.add(tfMI, 1, 3);

        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(tfAddress, 1, 4);

        gridPane.add(new Label("City:"), 0, 5);
        gridPane.add(tfCity, 1, 5);

        gridPane.add(new Label("State:"), 2, 5);
        gridPane.add(tfState, 3, 5);

        gridPane.add(new Label("Telephone:"), 0, 6);
        gridPane.add(tfTelephone, 1, 6);

        // Adds buttons below the telephone input box
        gridPane.add(btnView, 0, 7);
        gridPane.add(btnInsert, 1, 7);
        gridPane.add(btnUpdate, 2, 7);
        gridPane.add(btnClear, 3, 7);

        // Set actions to each button
        btnView.setOnAction(e -> viewRecord());
        btnInsert.setOnAction(e -> insertRecord());
        btnUpdate.setOnAction(e -> updateRecord());
        btnClear.setOnAction(e -> clearFields());

        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setTitle("Staff Database App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method that allows you to view a record by its ID in the SQL Database
    private void viewRecord() {
        String id = tfId.getText();
        // Change this line here from exercise34_01.userdata to whatever your schema_name.table_name is
        String query = "SELECT * FROM exercise34_01.userdata WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tfLastName.setText(rs.getString("lastName"));
                tfFirstName.setText(rs.getString("firstName"));
                tfMI.setText(rs.getString("mi"));
                tfAddress.setText(rs.getString("address"));
                tfCity.setText(rs.getString("city"));
                tfState.setText(rs.getString("state"));
                tfTelephone.setText(rs.getString("telephone"));
            } else {
                showAlert("Record not found!");
            }
            // Logs any errors that may occur during the runtime
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
    }

    // Method that inserts a new record into the SQL Database at the ID you're adding it at
    private void insertRecord() {
        // Change this line here from exercise34_01.userdata to whatever your schema_name.table_name is
        String query = "INSERT INTO exercise34_01.userdata (id, lastName, firstName, mi, address, city, state, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tfId.getText());
            stmt.setString(2, tfLastName.getText());
            stmt.setString(3, tfFirstName.getText());
            stmt.setString(4, tfMI.getText());
            stmt.setString(5, tfAddress.getText());
            stmt.setString(6, tfCity.getText());
            stmt.setString(7, tfState.getText());
            stmt.setString(8, tfTelephone.getText());
            stmt.executeUpdate();
            showAlert("Record inserted successfully!");
            // Logs any errors that may occur during the runtime
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
    }

    // Method for updating a record in the SQL Database based on ID
    private void updateRecord() {
        // Change this line here from exercise34_01.userdata to whatever your schema_name.table_name is
        String query = "UPDATE exercise34_01.userdata SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tfLastName.getText());
            stmt.setString(2, tfFirstName.getText());
            stmt.setString(3, tfMI.getText());
            stmt.setString(4, tfAddress.getText());
            stmt.setString(5, tfCity.getText());
            stmt.setString(6, tfState.getText());
            stmt.setString(7, tfTelephone.getText());
            stmt.setString(8, tfId.getText());
            stmt.executeUpdate();
            showAlert("Record updated successfully!");
            // Logs any errors that may occur during the runtime
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
    }

    // Method that clears all fields inputted on the GUI, not inside the database at a current id
    private void clearFields() {
        tfId.clear();
        tfLastName.clear();
        tfFirstName.clear();
        tfMI.clear();
        tfAddress.clear();
        tfCity.clear();
        tfState.clear();
        tfTelephone.clear();
    }

    // Helper method that shows alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
