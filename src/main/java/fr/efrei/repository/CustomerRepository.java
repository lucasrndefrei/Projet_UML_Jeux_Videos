package fr.efrei.repository;

import fr.efrei.domain.Customer;
import fr.efrei.factory.CustomerFactory;
import fr.efrei.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private Connection getConnection() {
        return DatabaseConnection.getInstance().getConnection();
    }

    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers (id, name, contact_number, password, role, credits) VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name = VALUES(name), contact_number = VALUES(contact_number), password = VALUES(password), role = VALUES(role), credits = VALUES(credits)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getContactNumber());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getRole().name());
            if (customer.getCredits() == null) stmt.setNull(6, java.sql.Types.INTEGER);
            else stmt.setInt(6, customer.getCredits());
            stmt.executeUpdate();
            return customer;
        } catch (SQLException e) {
            System.err.println("Error while saving customer: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Customer findByContact(String contactNumber) {
        String sql = "SELECT * FROM customers WHERE contact_number = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, contactNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String roleStr = rs.getString("role");
                fr.efrei.domain.Role role = (roleStr != null) ? fr.efrei.domain.Role.valueOf(roleStr) : fr.efrei.domain.Role.CUSTOMER;
                Integer credits = rs.getObject("credits") != null ? (Integer) rs.getObject("credits") : null;
                return new Customer.Builder()
                    .setId(rs.getString("id"))
                    .setName(rs.getString("name"))
                    .setContactNumber(rs.getString("contact_number"))
                    .setPassword(rs.getString("password"))
                    .setRole(role)
                    .setCredits(credits)
                    .build();
            }
        } catch (SQLException e) {
            System.err.println("Error while searching for customer: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Customer findById(String id) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String roleStr = rs.getString("role");
                fr.efrei.domain.Role role = (roleStr != null) ? fr.efrei.domain.Role.valueOf(roleStr) : fr.efrei.domain.Role.CUSTOMER;
                Integer credits = rs.getObject("credits") != null ? (Integer) rs.getObject("credits") : null;
                return new Customer.Builder()
                    .setId(rs.getString("id"))
                    .setName(rs.getString("name"))
                    .setContactNumber(rs.getString("contact_number"))
                    .setPassword(rs.getString("password"))
                    .setRole(role)
                    .setCredits(credits)
                    .build();
            }
        } catch (SQLException e) {
            System.err.println("Error while searching for customer: " + e.getMessage());
        }
        return null;
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY name";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String roleStr = rs.getString("role");
                fr.efrei.domain.Role role = (roleStr != null) ? fr.efrei.domain.Role.valueOf(roleStr) : fr.efrei.domain.Role.CUSTOMER;
                Integer credits = rs.getObject("credits") != null ? (Integer) rs.getObject("credits") : null;
                customers.add(new Customer.Builder()
                    .setId(rs.getString("id"))
                    .setName(rs.getString("name"))
                    .setContactNumber(rs.getString("contact_number"))
                    .setPassword(rs.getString("password"))
                    .setRole(role)
                    .setCredits(credits)
                    .build());
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving customers: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM customers WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void addCredits(String customerId, int creditsToAdd) {
        String sql = "UPDATE customers SET credits = credits + ? WHERE id = ? AND (role IS NULL OR role = 'CUSTOMER')";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, creditsToAdd);
            stmt.setString(2, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding credits: " + e.getMessage());
        }
    }
}
