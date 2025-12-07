package fr.efrei.repository;

import fr.efrei.domain.Customer;
import fr.efrei.domain.Game;
import fr.efrei.domain.Sale;
import fr.efrei.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository implements ISaleRepository {
    private static SaleRepository instance;
    private final CustomerRepository customerRepository;
    private final GameRepository gameRepository;

    private SaleRepository() {
        this.customerRepository = CustomerRepository.getInstance();
        this.gameRepository = GameRepository.getInstance();
    }

    public static SaleRepository getInstance() {
        if (instance == null) {
            instance = new SaleRepository();
        }
        return instance;
    }

    private Connection getConnection() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Sale save(Sale sale) {
        String sql = "INSERT INTO sales (id, customer_id, game_id, sale_date, price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, sale.getId());
            stmt.setString(2, sale.getCustomer().getId());
            stmt.setString(3, sale.getGame().getId());
            stmt.setDate(4, Date.valueOf(sale.getDate()));
            stmt.setDouble(5, sale.getPrice());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return sale;
            }
        } catch (SQLException e) {
            System.err.println("Error saving sale: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Sale findById(String id) {
        String sql = "SELECT * FROM sales WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = customerRepository.findById(rs.getString("customer_id"));
                    Game game = gameRepository.findById(rs.getString("game_id"));

                    if (customer != null && game != null) {
                        return new Sale(
                                rs.getString("id"),
                                customer,
                                game,
                                rs.getDate("sale_date").toLocalDate(),
                                rs.getDouble("price")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding sale by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Sale> findAll() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales ORDER BY sale_date DESC";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = customerRepository.findById(rs.getString("customer_id"));
                Game game = gameRepository.findById(rs.getString("game_id"));

                if (customer != null && game != null) {
                    Sale sale = new Sale(
                            rs.getString("id"),
                            customer,
                            game,
                            rs.getDate("sale_date").toLocalDate(),
                            rs.getDouble("price")
                    );
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding all sales: " + e.getMessage());
        }

        return sales;
    }

    public List<Sale> findByCustomer(String customerId) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE customer_id = ? ORDER BY sale_date DESC";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = customerRepository.findById(rs.getString("customer_id"));
                    Game game = gameRepository.findById(rs.getString("game_id"));

                    if (customer != null && game != null) {
                        Sale sale = new Sale(
                                rs.getString("id"),
                                customer,
                                game,
                                rs.getDate("sale_date").toLocalDate(),
                                rs.getDouble("price")
                        );
                        sales.add(sale);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding sales by customer: " + e.getMessage());
        }

        return sales;
    }

    public List<Sale> findByGame(String gameId) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE game_id = ? ORDER BY sale_date DESC";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = customerRepository.findById(rs.getString("customer_id"));
                    Game game = gameRepository.findById(rs.getString("game_id"));

                    if (customer != null && game != null) {
                        Sale sale = new Sale(
                                rs.getString("id"),
                                customer,
                                game,
                                rs.getDate("sale_date").toLocalDate(),
                                rs.getDouble("price")
                        );
                        sales.add(sale);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding sales by game: " + e.getMessage());
        }

        return sales;
    }

    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(price), 0) as total FROM sales";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating total revenue: " + e.getMessage());
        }

        return 0.0;
    }

    @Override
    public boolean update(Sale sale) {
        String sql = "UPDATE sales SET customer_id = ?, game_id = ?, sale_date = ?, price = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, sale.getCustomer().getId());
            stmt.setString(2, sale.getGame().getId());
            stmt.setDate(3, Date.valueOf(sale.getDate()));
            stmt.setDouble(4, sale.getPrice());
            stmt.setString(5, sale.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating sale: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM sales WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting sale: " + e.getMessage());
        }

        return false;
    }
}

