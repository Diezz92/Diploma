package ru.netology.web.data;

import java.sql.DriverManager;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLHelper {

    private SQLHelper() {
    }

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String pass = System.getProperty("db.password");

    public static void clearTables() {
        val deletePaymentEntity = "DELETE FROM payment_entity";
        val deleteCreditEntity = "DELETE FROM credit_request_entity";
        val deleteOrderEntity = "DELETE FROM order_entity";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(
                url, user, pass)
        ) {
            runner.update(conn, deletePaymentEntity);
            runner.update(conn, deleteCreditEntity);
            runner.update(conn, deleteOrderEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        return getPayStatus(statusSQL);
    }

    @SneakyThrows
    public static String getCreditStatus() {
        String statusSQL = "SELECT status FROM credit_request_entity";
        return getCredStatus(statusSQL);
    }

    @SneakyThrows
    private static String getPayStatus(String query) {
        var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        String result = "";
        val runner = new QueryRunner();
        try
                (val conn = DriverManager.getConnection(
                        url, user, pass)
                ) {

            result = runner.query(conn, codeSQL, new ScalarHandler<String>());
            System.out.println(result);
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    private static String getCredStatus(String query) {
        var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        String result = "";
        val runner = new QueryRunner();
        try
                (val conn = DriverManager.getConnection(
                        url, user, pass)
                ) {

            result = runner.query(conn, codeSQL, new ScalarHandler<String>());
            System.out.println(result);
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}