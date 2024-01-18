package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public ContactDAO() {
        // Appel de la méthode pour initialiser la base de données.
        initializeDatabase();
    }
    private Connection connect() {
        // Méthode pour établir la connexion à la base de données
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            // Vous pouvez ajouter ici du code pour initialiser la base de données avec schema.sql si nécessaire
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    private void initializeDatabase() {
        String sqlScriptPath = "src/main/resources/schema.sql"; // Assurez-vous que le chemin est correct

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // Read file content
            String sql = new String(Files.readAllBytes(Paths.get(sqlScriptPath)));
            // Execute SQL script
            stmt.execute(sql);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String url = "jdbc:sqlite:mydatabase.db"; // Assurez-vous que l'URL correspond à votre fichier de base de données SQLite

    // Code pour ajouter un nouveau contact dans la base de données
    public void createContact(Contact contact) {
        String sql = "INSERT INTO Contacts(name, email, phone_number, address) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getEmail());
            pstmt.setString(3, contact.getPhoneNumber());
            pstmt.setString(4, contact.getAddress());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contact.setId(generatedKeys.getInt(1)); // Assigne l'ID généré au contact
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Code pour obtenir un contact par son ID
    public Contact getContact(int id) {
        String sql = "SELECT * FROM Contacts WHERE id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Contact(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Code pour obtenir tous les contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM Contacts";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacts.add(new Contact(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contacts;
    }

    // Code pour mettre à jour un contact existant
    public void updateContact(Contact contact) {
        String sql = "UPDATE Contacts SET name = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getEmail());
            pstmt.setString(3, contact.getPhoneNumber());
            pstmt.setString(4, contact.getAddress());
            pstmt.setInt(5, contact.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Code pour supprimer un contact
    public void deleteContact(int id) {
        String sql = "DELETE FROM Contacts WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
