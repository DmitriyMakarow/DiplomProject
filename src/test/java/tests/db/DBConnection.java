package tests.db;

import java.sql.*;

public class DBConnection {

    private final String URL = "jdbc:postgresql://82.142.167.37:4832/pflb_trainingcenter";
    private final String USER = "pflb-at-read";
    private final String PASSWORD = "PflbQaTraining2354";

    private Connection connection; // класс нужен для того, чтобы подключаться к БД
    private Statement statement;// класс нужен для того, чтобы в БД отправлять запрос
    private ResultSet resultSet; // объект этого класс хранит результаты селект

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            System.out.println("Соединение с БД выполнено");
        } catch (SQLException e) {
            throw new RuntimeException("Соединение не выполнено", e);
        }
    }

    public ResultSet select(String query) {
        try {
            return statement.executeQuery(query); //executeQuery только для селектов
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (resultSet!= null) {
                resultSet.close();
            }
            if (statement!= null) {
                statement.close();
            }
            if (connection!= null) {
                connection.close();
            }
            System.out.println("Подключение к БД закрыто");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения", e);
        }
    }
}