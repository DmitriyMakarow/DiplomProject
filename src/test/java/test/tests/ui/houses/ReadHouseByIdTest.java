package test.tests.ui.houses;

import api.models.HouseRequest;
import api.models.HouseResponse;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.tests.BaseTest;

import java.util.Collections;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static enumUI.Dropdown.HOUSES;

@Epic("Дома")
@Feature("Чтение дома по ID")
@Owner("Твоя фамилия и инициалы")
public class ReadHouseByIdTest extends BaseTest {

    @BeforeMethod
    public void testData() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        baseSteps.showDropdown(HOUSES);
    }

    @Test(testName = "Проверка чтения дома по ID")
    @Story("Получение информации о доме по идентификатору")
    @Description("Тест проверяет корректное отображение дома при поиске по ID")
    public void checkReadHouseById() {
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

        $x("//a[text()='Read one by ID']").click();

        $x("//input[@id='house_input']").setValue(String.valueOf(houseId));

        $x("//button[text()='Read']").click();

        $x("//table").shouldBe(visible, java.time.Duration.ofSeconds(10));

        $x("//button[contains(text(), 'Status: 200')]").shouldBe(
                visible,
                java.time.Duration.ofSeconds(10)
        );

        $x("//table//td[contains(text(), '" + houseId + "')]")
                .shouldBe(visible);

        houseAdapter.deleteApiHouse(houseId);
    }
}
