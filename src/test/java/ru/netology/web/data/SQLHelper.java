package ru.netology.web.data;

import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.*;

public class SQLHelper {

    private SQLHelper() {
    }

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.app");
    private static String password = System.getProperty("db.pass");

    @Getter
    private static final String payTable = "payment_gate";
    @Getter
    private static final String creditTable = "credit_gate";
    @SneakyThrows

    public static void dropTables() {
        String dropPaymentTables = "DROP TABLE IF EXISTS payment_gate;";
        String dropCreditTables = "DROP TABLE IF EXISTS credit_gate;";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        Statement statement = connection.createStatement();
        statement.executeUpdate(dropPaymentTables);
        statement.executeUpdate(dropCreditTables);
    }

    public static void createTablePaymentGate() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE payment_gate (id INT UNIQUE KEY AUTO_INCREMENT, number VARCHAR(19) NOT NULL PRIMARY KEY, status VARCHAR(8) NOT NULL)");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void createTableCreditGate() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE credit_gate ( id INT UNIQUE KEY AUTO_INCREMENT, number VARCHAR(19) NOT NULL PRIMARY KEY, status VARCHAR(8) NOT NULL)");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertApprovedCardPaymentGate() {
        String cardData = "INSERT INTO payment_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "4444 4444 4444 4441");
            preparedStatement.setString(3, "APPROVED");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertDeclinedCardPaymentGate() {
        String cardData = "INSERT INTO payment_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "4444 4444 4444 4442");
            preparedStatement.setString(3, "DECLINED");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertApprovedCardCreditGate() {
        String cardData = "INSERT INTO credit_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "4444 4444 4444 4441");
            preparedStatement.setString(3, "APPROVED");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertDeclinedCardCreditGate() {
        String cardData = "INSERT INTO credit_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "4444 4444 4444 4442");
            preparedStatement.setString(3, "DECLINED");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertEmptyPaymentGateApprovedCard() {
        String cardData = "INSERT INTO payment_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "4444 4444 4444 4441");
            preparedStatement.setString(3, "");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertEmptyCreditGateApprovedCard() {
        String cardData = "INSERT INTO credit_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "4444 4444 4444 4441");
            preparedStatement.setString(3, "");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertEmptyNoCardPaymentGate() {
        String cardData = "INSERT INTO payment_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "");
            preparedStatement.setString(3, "");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static void insertEmptyNoCardCreditGate() {
        String cardData = "INSERT INTO credit_gate (id, number, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             PreparedStatement preparedStatement = connection.prepareStatement(cardData)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "");
            preparedStatement.setString(3, "");
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
    }

    public static String getCardStatusPaymentGate(String payTable) {
        String status = "";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT status FROM " + payTable + " ;")) {
                while (resultSet.next()) status = resultSet.getString("status");
            }
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
        return status;
    }

    public static String getCardStatusCreditGate(String creditTable) {
        String status = "";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT status FROM " + creditTable + " ;")) {
                while (resultSet.next()) status = resultSet.getString("status");
            }
        } catch (SQLException sqlException) {
            sqlException.getErrorCode();
        }
        return status;
    }

}

