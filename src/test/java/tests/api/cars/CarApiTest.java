package tests.api.cars;

import api.models.CarRequest;
import api.models.CarResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import static org.testng.Assert.assertEquals;
import static ui.pages.base.BasePage.faker;

@Epic("Автомобили. API")
@Feature("Создание автомобиля")
public class CarApiTest extends BaseTest {

    private final Integer id = faker.number().numberBetween(1, Integer.MAX_VALUE);
    private final String
            engineType = "Electric",
            mark = faker.vehicle().manufacturer(),
            model = faker.vehicle().model();
    private final double price = faker.number().numberBetween(1, 1000000);

    CarRequest car = CarRequest.builder()
            .id(id)
            .engineType(engineType)
            .mark(mark)
            .model(model)
            .price(price)
            .build();

    @Test(testName = "Проверка создания автомобиля с валидными параметрами")
    void checkCreateCar() {
        CarResponse carResponse = carAdapter.createApiCar(car);
        Integer idCar = carResponse.id;
        assertEquals(carResponse.engineType, engineType);
        assertEquals(carResponse.model, model);
        assertEquals(carResponse.mark, mark);
        assertEquals(carResponse.price, price);
        carAdapter.deleteApiCar(idCar);
    }

    @Test(testName = "Проверка редактирования автомобиля валидными параметрами")
    void checkEditCar() {
        String newEngineType = "Gasoline",
                newMark = faker.vehicle().manufacturer(),
                newModel = faker.vehicle().model();
        double newPrice = faker.number().numberBetween(1, 1000000);

        CarResponse carResponse = carAdapter.createApiCar(car);
        Integer idCar = carResponse.id;

        CarRequest newCar = CarRequest.builder()
                .id(idCar)
                .engineType(newEngineType)
                .mark(newMark)
                .model(newModel)
                .price(newPrice)
                .build();

        CarResponse newCarResponse = carAdapter.putApiCar(idCar, newCar);

        assertEquals(newCarResponse.engineType, newEngineType);
        assertEquals(newCarResponse.model, newModel);
        assertEquals(newCarResponse.mark, newMark);
        assertEquals(newCarResponse.price, newPrice);

        carAdapter.deleteApiCar(idCar);
    }
}
