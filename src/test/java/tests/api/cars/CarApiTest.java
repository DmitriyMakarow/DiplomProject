package tests.api.cars;

import api.models.CarRequest;
import api.models.CarResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.CarTestDataFactory;

import static org.testng.Assert.assertEquals;

@Log4j2
@Epic("Автомобили. API")
@Feature("Создание автомобиля")
public class CarApiTest extends BaseTest {

    private CarResponse carResponse;
    private CarRequest carRequest;
    private Integer idCar;

    @BeforeMethod
    public void createCarApi() {
        carRequest = CarTestDataFactory.validCarTestDataUI();
        carResponse = carAdapter.createApiCar(carRequest);
        idCar = carResponse.getId();
    }

    @AfterMethod
    void deleteCar() {
        carAdapter.deleteApiCar(idCar);
    }

    @Test(testName = "Проверка создания автомобиля с валидными параметрами")
    void checkCreateCar() {
        assertEquals(carResponse.getEngineType(), carRequest.getEngineType(), "Тип двигателя не соответствует");
        assertEquals(carResponse.getModel(), carResponse.getModel(), "Модель не соответствует");
        assertEquals(carResponse.getMark(), carResponse.getMark(), "Марка не соответствует");
        assertEquals(carResponse.getPrice(), carResponse.getPrice());
    }

    @Test(testName = "Проверка редактирования автомобиля валидными параметрами")
    void checkEditCar() {
        CarRequest carNewRequest = CarTestDataFactory.validCarTestDataUI();
        CarResponse carNewResponse = carAdapter.putApiCar(idCar, carNewRequest);

        assertEquals(carNewResponse.getId(), carResponse.getId(), "Изменился ID машины");
        assertEquals(carNewResponse.getEngineType(), carNewRequest.getEngineType(), "Тип двигателя не соответствует");
        assertEquals(carNewResponse.getModel(), carNewRequest.getModel(), "Модель не соответствует");
        assertEquals(carNewResponse.getMark(), carNewRequest.getMark(), "Марка не соответствует");
        assertEquals(carNewResponse.getPrice(), carNewRequest.getPrice());
    }
}
