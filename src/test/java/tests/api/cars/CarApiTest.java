package tests.api.cars;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.cars.CarTestDataFactory;

import static org.testng.Assert.assertEquals;

@Log4j2
@Epic("Автомобили. API")
@Feature("Создание автомобиля")
public class CarApiTest extends BaseTest {

    private CarResponse carResponse;
    private CarRequest carRequest;
    private Integer idCar, idInvalidCar;

    @BeforeMethod(onlyForGroups = {"validCar"})
    public void createDataValidCar() {
        carRequest = CarTestDataFactory.validCarTestDataAPI();
        carResponse = carAdapter.createCar(carRequest, 201, CarResponse.class);
        idCar = carResponse.getId();
    }

    @AfterMethod(onlyForGroups = {"deleteData"})
    void deleteCar() {
        carAdapter.deleteApiCar(idCar);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка создания автомобиля с валидными параметрами",
    groups = {"validCar", "deleteData", "regression"})
    void checkCreateCar() {
        assertEquals(carResponse.getEngineType(), carRequest.getEngineType(), "Тип двигателя не соответствует");
        assertEquals(carResponse.getModel(), carResponse.getModel(), "Модель не соответствует");
        assertEquals(carResponse.getMark(), carResponse.getMark(), "Марка не соответствует");
        assertEquals(carResponse.getPrice(), carResponse.getPrice());
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка создания автомобиля с невалидными параметрами",
            groups = {"invalidCar", "regression"})
    void createInvalidCar() {
        CarRequest invalidCarRequest = CarTestDataFactory.emptyCarTestDataUI();
        carAdapter.createCar(invalidCarRequest, 400, null);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка редактирования автомобиля валидными параметрами",
            groups = {"validCar", "deleteData", "regression"})
    void checkEditCar() {
        CarRequest carNewRequest = CarTestDataFactory.validCarTestDataAPI();
        CarResponse carNewResponse = carAdapter.putCar(idCar, carNewRequest, 202, CarResponse.class);

        assertEquals(carNewResponse.getId(), carResponse.getId(), "Изменился ID машины");
        assertEquals(carNewResponse.getEngineType(), carNewRequest.getEngineType(), "Тип двигателя не соответствует");
        assertEquals(carNewResponse.getModel(), carNewRequest.getModel(), "Модель не соответствует");
        assertEquals(carNewResponse.getMark(), carNewRequest.getMark(), "Марка не соответствует");
        assertEquals(carNewResponse.getPrice(), carNewRequest.getPrice());
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка редактирования автомобиля невалидными параметрами",
            groups = {"validCar", "deleteData", "regression"})
    void checkEditInvalidCar() {
        CarRequest carNewRequest = CarTestDataFactory.emptyCarTestDataUI();
        carAdapter.putCar(idCar, carNewRequest, 400, null);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка удаления автомобиля",
            groups = {"validCar", "regression"})
    void checkDeleteCar() {
        carAdapter.deleteApiCar(idCar);
        carAdapter.getCar(idCar, 204, null);
    }
}
