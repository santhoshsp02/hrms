package com.hrms;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Create new Organization");
            System.out.println("2. Login to Organization");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createOrganization();
                    break;
                case 2:
                    loginMenu();
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void createOrganization() {
        System.out.println("\n--- Create New Organization ---");
        System.out.print("Enter Organization Name: ");
        String orgName = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter PAN: ");
        String pan = scanner.nextLine();
        System.out.print("Enter GST: ");
        String gst = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Email (for login access): ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Organization Employees Count: ");
        int employeeCount = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO organizations (org_name, address, pan, gst, department, email, password, employee_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orgName);
            pstmt.setString(2, address);
            pstmt.setString(3, pan);
            pstmt.setString(4, gst);
            pstmt.setString(5, department);
            pstmt.setString(6, email);
            pstmt.setString(7, password);
            pstmt.setInt(8, employeeCount);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Organization created successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error creating organization: " + e.getMessage());
        }
    }

    private static void loginMenu() {
        while (true) {
            System.out.println("\n--- Organization Login Menu ---");
            System.out.println("1. Enter email and password for login");
            System.out.println("2. Forget password");
            System.out.println("3. Back to main menu");
            System.out.println("4. Exit");
            System.out.print("Select the options: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleLogin();
                    return;
                case 2:
                    handleForgotPassword();
                    break;
                case 3:
                    return; // Back to main menu
                case 4:
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void handleLogin() {
        System.out.print("Enter email -> ");
        String email = scanner.nextLine();
        System.out.print("Enter password -> ");
        String password = scanner.nextLine();

        String sql = "SELECT org_name FROM organizations WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String orgName = rs.getString("org_name");
                System.out.println("\nWelcome to " + orgName);
                organizationDashboard(orgName);
            } else {
                System.out.println("Invalid email or password!");
            }
        } catch (SQLException e) {
            System.out.println("Database error during login: " + e.getMessage());
        }
    }

    private static void handleForgotPassword() {
        System.out.print("Enter email -> ");
        String email = scanner.nextLine();
        System.out.print("Enter new password for login -> ");
        String newPassword = scanner.nextLine();

        String sql = "UPDATE organizations SET password = ? WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully! Redirecting to login options...");
            } else {
                System.out.println("Email not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }

    private static void organizationDashboard(String orgName) {
        while (true) {
            System.out.println("\n--- Dashboard: " + orgName + " ---");
            System.out.println("1. Employee -> add, view, update, delete");
            System.out.println("2. Attendance -> create, view, update, delete");
            System.out.println("3. Salary -> for particular employee -> create, update, delete, view");
            System.out.println("4. Notice or holiday -> create, view, delete, update for all employee");
            System.out.println("5. Logout -> redirect to main menu");
            System.out.print("Select the options: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("[Feature] Employee module placeholder (Add, View, Update, Delete).");
                    break;
                case 2:
                    System.out.println("[Feature] Attendance module placeholder (Create, View, Update, Delete).");
                    break;
                case 3:
                    System.out.println("[Feature] Salary module placeholder (Create, Update, Delete, View).");
                    break;
                case 4:
                    System.out.println("[Feature] Notice or Holiday module placeholder.");
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option! Please choose between 1 to 5.");
            }
        }
    }
}