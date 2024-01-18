package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static ContactDAO contactDAO = new ContactDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput;

        do {
            showMenu();
            System.out.print("Choisissez une option : ");
            userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    addContact(scanner);
                    break;
                case "2":
                    viewContacts();
                    break;
                case "3":
                    // Ajouter d'autres cas pour mettre à jour et supprimer
                    break;
                case "4":
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        } while (!userInput.equals("4"));
    }

    private static void showMenu() {
        System.out.println("1. Ajouter un contact");
        System.out.println("2. Afficher les contacts");
        System.out.println("3. Mettre à jour un contact");
        System.out.println("4. Supprimer un contact");
        System.out.println("5. Quitter");
    }

    private static void addContact(Scanner scanner) {
        System.out.println("Entrer le nom :");
        String name = scanner.nextLine();
        System.out.println("Entrer l'email :");
        String email = scanner.nextLine();
        System.out.println("Entrer le numéro de téléphone :");
        String phoneNumber = scanner.nextLine();
        System.out.println("Entrer l'adresse :");
        String address = scanner.nextLine();

        Contact newContact = new Contact(0, name, email, phoneNumber, address);
        contactDAO.createContact(newContact);
    }

    private static void viewContacts() {
        List<Contact> contacts = contactDAO.getAllContacts();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}
