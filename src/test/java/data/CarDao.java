package data;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import tests.db.DBConnection;
import ui.dto.cars.CarTestData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

public class CarDao extends DBConnection {

    public static String getSelectAll(String nameTable) {
        return "SELECT * FROM public.%s".formatted(nameTable);
    }

    public static String getSelectCarByID(String carId) {
        return """
                select * from public.car left join public.engine_type
                on car.engine_type_id = engine_type.id
                where car.id = %s""".formatted(carId);
    }

    public static String getSelectCarByModel(CarRequest car) {
        return "SELECT * FROM public.car WHERE car.mark = '%s' and car.model = '%s'"
                .formatted(car.getMark(), car.getModel());
    }

    public static String getSelectCarByModel(CarTestData car) {
        return "SELECT * FROM public.car WHERE car.mark = '%s' and car.model = '%s'"
                .formatted(car.getMark(), car.getModel());
    }

    public void verifyAttributesCar(CarTestData car, ResultSet result) throws SQLException {
        assertEquals(car.getMark(), result.getString("mark"));
        assertEquals(car.getModel(), result.getString("model"));
        assertEquals(Integer.valueOf(car.getPrice()), result.getInt("price"));
        assertEquals(car.getEngineType(), result.getString("type_name"));
    }

    public void verifyAttributesCar(CarResponse car, ResultSet result) throws SQLException {
        assertEquals(car.getMark(), result.getString("mark"));
        assertEquals(car.getModel(), result.getString("model"));
        assertEquals(Integer.valueOf((int) car.getPrice()), result.getInt("price"));
        assertEquals(car.getEngineType(), result.getString("type_name"));
    }
}
