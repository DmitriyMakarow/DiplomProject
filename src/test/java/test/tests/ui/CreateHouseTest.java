package test.tests.ui.houses;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.tests.BaseTest;
import wrappers.Input;

import static enumUI.Dropdown.HOUSES;
import static enumUI.TableType.CREATE_NEW_HOUSES;

@Epic("Дома")
@Feature("Создание дома")
public class CreateHouseTest extends BaseTest {

    private final String
            floors = String.valueOf(faker.number().numberBetween(1, 50)),
            price = String.valueOf(faker.number().numberBetween(100000, 10000000)),
            parking = String.valueOf(faker.number().numberBetween(1, 10));

    @BeforeMethod
    public void setUp() {
        loginPage.authorization();
        baseSteps
                .showDropdown(HOUSES)
                .openTableFromDropdown(HOUSES, CREATE_NEW_HOUSES);
    }

    @Test(testName = "Создание дома с валидными данными")
    void successCreateHouse() {
        final String status = "Status: Successfully pushed, code: 201";

        new Input("floor_send").fillField(floors);
        new Input("price_send").fillField(price);
        new Input("parking_first_send").fillField(parking);

        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New house ID:");
    }
}