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

import java.sql.ResultSet;

import static data.CarDao.*;
import static io.qameta.allure.Allure.step;
import static java.lang.String.valueOf;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Log4j2
@Epic("Автомобили. API")
@Feature("Создание автомобиля")
public class CarApiTest extends BaseTest {

    private CarResponse carResponse;
    private CarRequest carRequest;
    private Integer idCar;

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
        assertEquals(carResponse.getModel(), carRequest.getModel(), "Модель не соответствует");
        assertEquals(carResponse.getMark(), carRequest.getMark(), "Марка не соответствует");
        assertEquals(carResponse.getPrice(), carRequest.getPrice(), "Цена не соответствует");

        step("Проверка записи по созданному авто в БД", () -> {
            connection.connect();
            ResultSet result = carDao.select(getSelectCarByID(valueOf(idCar)));
            while (result.next()) {
                carDao.verifyAttributesCar(carResponse, result);
            }
        });
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка создания автомобиля с невалидными параметрами",
            groups = {"invalidCar", "regression"})
    void createInvalidCar() {
        CarRequest invalidCarRequest = CarTestDataFactory.emptyCarTestDataUI();
        carAdapter.createCar(invalidCarRequest, 400, null);

        step("Проверка отсутствия записи по созданному авто в БД", () -> {
            connection.connect();
            assertTrue(carDao.emptySelect(getSelectCarByModel(invalidCarRequest)));
        });
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
        assertEquals(carNewResponse.getPrice(), carNewRequest.getPrice(), "Цена не соответствует");

        step("Проверка записи по измененному авто в БД", () -> {
            connection.connect();
            ResultSet result = carDao.select(getSelectCarByID(valueOf(idCar)));
            while (result.next()) {
                carDao.verifyAttributesCar(carNewResponse, result);
            }
        });
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка редактирования автомобиля невалидными параметрами",
            groups = {"validCar", "deleteData", "regression"})
    void checkEditInvalidCar() {
        CarRequest carNewRequest = CarTestDataFactory.emptyCarTestDataUI();
        carAdapter.putCar(idCar, carNewRequest, 400, null);

        step("Проверка сохранения изначальной записи по авто в БД", () -> {
            connection.connect();
            ResultSet result = carDao.select(getSelectCarByID(valueOf(idCar)));
            while (result.next()) {
                carDao.verifyAttributesCar(carResponse, result);
            }
        });
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Проверка удаления автомобиля",
            groups = {"validCar", "regression"})
    void checkDeleteCar() {
        carAdapter.deleteApiCar(idCar);
        carAdapter.getCar(idCar, 204, null);

        step("Проверка отсутствия записи по удаленному авто в БД", () -> {
            connection.connect();
            assertTrue(carDao.emptySelect(getSelectCarByModel(carRequest)));
        });
    }
}
