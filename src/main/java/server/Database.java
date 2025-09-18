package server;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public  class Database {
    private static final String DB_URL = "jdbc:sqlite:kolos.db"; // tu wpisz ścieżkę do pliku bazy SQLite

    public Database() {
        // Możesz tu sprawdzić czy tabela istnieje i ewentualnie ją utworzyć
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String createTable = """
                CREATE TABLE IF NOT EXISTS users (
                    login TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    points INTEGER DEFAULT 0
                )
                """;
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String login, String password) {
        String sql = "SELECT 1 FROM users WHERE login = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true jeśli użytkownik istnieje
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateLeaderboard(String winner, String loser) {
        String incPoints = "UPDATE users SET points = points + 1 WHERE login = ?";
        String decPoints = "UPDATE users SET points = points - 1 WHERE login = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);

            try (PreparedStatement winStmt = conn.prepareStatement(incPoints);
                 PreparedStatement loseStmt = conn.prepareStatement(decPoints)) {

                winStmt.setString(1, winner);
                winStmt.executeUpdate();

                loseStmt.setString(1, loser);
                loseStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Map<String, Integer> getLeaderboard() {
        String sql = "SELECT login, points FROM users ORDER BY points DESC";
        Map<String, Integer> leaderboard = new LinkedHashMap<>(); // zachowa kolejność sortowania

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                leaderboard.put(rs.getString("login"), rs.getInt("points"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }
}