package tests.ui.houses;

import api.models.houses.HouseRequest;
import api.models.houses.HouseResponse;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.pages.houses.HousesPage;

import java.util.Collections;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;
import static ui.enumUI.Dropdown.HOUSES;
import static ui.pages.base.BasePage.faker;

@Epic("Дома")
@Feature("Чтение дома по ID")
@Owner("khvadina a.")
public class ReadHouseByIdTest extends BaseTest {

    private HousesPage housesPage;

    @BeforeMethod
    public void openPageReadHouseById() {
        loginPage.authorization();
        baseSteps.showDropdown(HOUSES);
        housesPage = new HousesPage();
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка чтения дома по ID",
          groups = {"regression"})
    @Story("Получение информации о доме по идентификатору")
    @Description("Тест проверяет корректное отображение дома при поиске по ID")
    public void checkReadHouseById() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка чтения дома по ID")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Чтение дома по ID");
        parameter("Ожидаемый результат", "Дом успешно найден и отображен");

        int floorCount = faker.number().numberBetween(1, 50);
        double price = faker.number().numberBetween(100000, 10000000);
        int parkingCount = faker.number().numberBetween(1, 10);

        HouseRequest.ParkingPlace parkingPlace = HouseRequest.ParkingPlace.builder()
                .isWarm(true)
                .isCovered(true)
                .placesCount(parkingCount)
                .build();

        HouseRequest houseRequest = HouseRequest.builder()
                .floorCount(floorCount)
                .price(price)
                .parkingPlaces(Collections.singletonList(parkingPlace))
                .lodgers(Collections.emptyList())
                .build();

        HouseResponse createdHouse = houseAdapter.createApiHouse(houseRequest);
        Integer houseId = createdHouse.getId();

        housesPage.clickReadOneById();
        housesPage.enterHouseId(String.valueOf(houseId));
        housesPage.clickReadButton();
        housesPage.waitForTableVisible();
        housesPage.verifyStatusOk();
        housesPage.verifyHouseIdInTable(houseId);

        houseAdapter.deleteApiHouse(houseId);
    }
}
