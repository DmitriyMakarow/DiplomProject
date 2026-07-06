package tests.db;

import lombok.extern.log4j.Log4j2;
import tests.ui.base.BaseTest;

import java.sql.*;

@Log4j2
public class DBConnection extends BaseTest {

    private Connection connection; // класс нужен для того, чтобы подключаться к БД
    static Statement statement;// класс нужен для того, чтобы в БД отправлять запрос
    private ResultSet result; //объект этого класса хранит результаты селект

    public void connect() {
        try {
            connection = DriverManager.getConnection(urlDB, userDB, passwordDB);
            statement = connection.createStatement();
            log.info("Соединение с БД выполнено");
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

    public boolean emptySelect(String query) throws SQLException {
        try (ResultSet rs = select(query)) {
            log.info("Найдены записи в БД!");
            return !rs.next();
        }
    }

    public void close() {
        try {
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            log.info("Подключение к БД закрыто");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения", e);
        }
    }
}