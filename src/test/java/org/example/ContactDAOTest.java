package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ContactDAOTest {
    @Test
    void createContact() {
        ContactDAO dao = new ContactDAO();
        Contact contact = new Contact(0, "John Doe", "john@example.com", "1234567890", "123 Street");
        dao.createContact(contact);

        Contact retrieved = dao.getContact(contact.getId());
        assertEquals("John Doe", retrieved.getName());
        // Ajoutez plus d'assertions ici pour les autres attributs
    }
    @Test
    void testDatabaseConnection() {
        ContactDAO dao = new ContactDAO();
        Contact contact = new Contact(0, "Test Name", "test@example.com", "123456789", "Test Address");
        dao.createContact(contact);

        // Tentez de récupérer le contact ajouté
        Contact retrieved = dao.getContact(contact.getId());
        assertNotNull(retrieved, "La connexion à la base de données a échoué ou le contact n'a pas été ajouté.");
    }

    @Test
    void deleteContact() {
        ContactDAO dao = new ContactDAO();
        int idToDelete = 1; // Utilisez un ID valide
        dao.deleteContact(idToDelete);

        // Vérifiez que le contact a été supprimé
        assertNull(dao.getContact(idToDelete));
    }
    @Test
    void updateContact() {
        ContactDAO dao = new ContactDAO();
        // Mise à jour d'un contact existant
        Contact contact = new Contact(1, "Jane Doe", "jane@example.com", "0987654321", "321 Avenue"); // Utilisez un ID valide
        dao.updateContact(contact);

        // Vérifiez que les informations ont été mises à jour
    }

}

