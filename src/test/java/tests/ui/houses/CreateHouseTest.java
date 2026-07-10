package tests.ui.houses;

import data.HousesData;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tests.ui.base.BaseTest;
import ui.wrappers.Input;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;
import static ui.enumUI.TableType.CREATE_NEW_HOUSES;
import static ui.enumUI.Dropdown.HOUSES;
import static ui.pages.base.BasePage.faker;

@Epic("Дома")
@Feature("Создание дома")
public class CreateHouseTest extends BaseTest {

    private final String
            floors = String.valueOf(faker.number().numberBetween(1, 50)),
            price = String.valueOf(faker.number().numberBetween(100000, 10000000)),
            parkingFirst = String.valueOf(faker.number().numberBetween(1, 10)),
            parkingSecond = String.valueOf(faker.number().numberBetween(1, 10)),
            parkingThird = String.valueOf(faker.number().numberBetween(1, 10)),
            parkingFourth = String.valueOf(faker.number().numberBetween(1, 10));

    @BeforeMethod
    public void setUp() {
        loginPage.authorization();
        baseSteps.openTableFromDropdown(HOUSES, CREATE_NEW_HOUSES);
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Создание дома с валидными данными",
          groups = {"regression"})
    void successCreateHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание дома с валидными данными")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Создание дома");
        parameter("Ожидаемый результат", "Дом успешно создан");

        final String status = "Status: Successfully pushed, code: 201";

        new Input("floor_send").fillField(floors);
        new Input("price_send").fillField(price);
        new Input("parking_first_send").fillField(parkingFirst);
        new Input("parking_second_send").fillField(parkingSecond);
        new Input("parking_third_send").fillField(parkingThird);
        new Input("parking_fourth_send").fillField(parkingFourth);

        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New house ID:");
    }

    @Owner("Хвадина А.В.")
    @Story("Создание дома с невалидными данными")
    @Test(testName = "Создание дома с пустым полем",
            groups = {"regression"},
            dataProvider = "Тестовые данные для негативных проверок создания дома",
            dataProviderClass = HousesData.class)
    void unsuccessCreateHouse(String floors, String price, String parking) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание дома с пустым полем")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Создание дома с пустыми полями");
        parameter("Ожидаемый результат", "Ошибка валидации, дом не создан");

        final String status = "Status: Invalid input data";

        new Input("floor_send").fillField(floors);
        new Input("price_send").fillField(price);
        new Input("parking_first_send").fillField(parking);

        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();
    }

    @Owner("Хвадина А.В.")
    @Issue("")
    @Story("Создание дома с невалидными данными")
    @Test(testName = "Создание дома с несоответствующими данными",
          groups = {"regression"})
    void createHouseInvalidData() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание дома с несоответствующими данными")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Создание дома с невалидными данными");
        parameter("Ожидаемый результат", "Ошибка валидации, дом не создан");

        final String status = "Status: Invalid input data";

        new Input("floor_send").fillField("invalid_string");
        new Input("price_send").fillField(price);
        new Input("parking_first_send").fillField(parkingFirst);
        new Input("parking_second_send").fillField(parkingSecond);
        new Input("parking_third_send").fillField(parkingThird);
        new Input("parking_fourth_send").fillField(parkingFourth);

        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();
    }
}