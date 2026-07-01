package tests.db;

import lombok.extern.log4j.Log4j2;
import tests.ui.base.BaseTest;
import ui.dto.cars.CarTestData;

import java.sql.*;

import static org.testng.Assert.assertEquals;

@Log4j2
public class DBConnection extends BaseTest {

    private Connection connection; // класс нужен для того, чтобы подключаться к БД
    private Statement statement;// класс нужен для того, чтобы в БД отправлять запрос
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

    public static String getSelectAll(String nameTable) {
        return "SELECT * FROM public.%s".formatted(nameTable);
    }

    public static String getSelectCarByID(String carId) {
        return ("select * from public.car left join public.engine_type " +
                "on car.engine_type_id = engine_type.id " +
                "where car.id = %s").formatted(carId);
    }

    public void verifyAttributesCar(CarTestData car, ResultSet result) throws SQLException {
        assertEquals(car.getMark(), result.getString("mark"));
        assertEquals(car.getModel(), result.getString("model"));
        assertEquals(Integer.valueOf(car.getPrice()), result.getInt("price"));
        assertEquals(car.getEngineType(), result.getString("type_name"));
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