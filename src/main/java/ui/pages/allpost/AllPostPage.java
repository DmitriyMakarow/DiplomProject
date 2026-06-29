package ui.pages.allpost;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dto.UserTestData;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertTrue;
import static ui.locators.AllPostLocators.getBtnNewId;
import static ui.locators.AllPostLocators.getBtnPushToApi;
import static ui.locators.AllPostLocators.getBtnStatus;

@Log4j2
public class AllPostPage extends BasePage {

    @Step("Открытие страницы ALL POST")
    public AllPostPage openPage() {
        open("http://82.142.167.37:4881/#/create/all");
        return this;
    }

    // Создание нового пользователя
    @Step("Заполнение формы создания пользователя")
    public AllPostPage fillCreateUserForm(UserTestData userData) {
        new Input("first_name_send").fillField(userData.getFirstName());
        new Input("last_name_send").fillField(userData.getLastName());
        new Input("age_send").fillField(userData.getAge());
        new Input(1, "money_send").fillField(userData.getMoney());
        return this;
    }

    @Step("Отправка запроса на создание пользователя")
    public AllPostPage pushCreateUser() {
        getBtnPushToApi(1).shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса создания пользователя")
    public AllPostPage verifyCreateUserStatus(String expectedStatus) {
        String actualStatus = getBtnStatus(1).getText();
        log.info("Create User Status - Expected: {}, Actual: {}", expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(1), 30),
                "Статус создания пользователя не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    @Step("Получение ID созданного пользователя")
    public String getCreatedUserId() {
        String fullText = getBtnNewId(1).getText();
        String userId = fullText.replaceAll("\\D+", "");
        log.info("Created User ID: {}", userId);
        return userId;
    }

    // Добавление денег пользователю
    @Step("Заполнение формы добавления денег")
    public AllPostPage fillAddMoneyForm(String userId, String money) {
        new Input(2, "id_send").fillField(userId);
        new Input(2, "money_send").fillField(money);
        return this;
    }

    @Step("Отправка запроса на добавление денег")
    public AllPostPage pushAddMoney() {
        getBtnPushToApi(2).shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса добавления денег")
    public AllPostPage verifyAddMoneyStatus(String expectedStatus) {
        String actualStatus = getBtnStatus(2).getText();
        log.info("Add Money Status - Expected: {}, Actual: {}", expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(2), 30),
                "Статус добавления денег не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    // Заселение/выселение из дома
    @Step("Заполнение формы заселения/выселения")
    public AllPostPage fillSettleEvictForm(String userId, String houseId) {
        new Input(3, "id_send").fillField(userId);
        new Input("house_send").fillField(houseId);
        return this;
    }

    @Step("Отправка запроса на заселение/выселение")
    public AllPostPage pushSettleEvict() {
        getBtnPushToApi(3).shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса заселения/выселения")
    public AllPostPage verifySettleEvictStatus(String expectedStatus) {
        String actualStatus = getBtnStatus(3).getText();
        log.info("Settle/Evict Status - Expected: {}, Actual: {}", expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(3), 30),
                "Статус заселения/выселения не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    // Покупка/продажа автомобиля
    @Step("Заполнение формы покупки/продажи автомобиля")
    public AllPostPage fillBuySellCarForm(String userId, String carId) {
        new Input(4, "id_send").fillField(userId);
        new Input("car_send").fillField(carId);
        return this;
    }

    @Step("Отправка запроса на покупку/продажу автомобиля")
    public AllPostPage pushBuySellCar() {
        getBtnPushToApi(4).shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса покупки/продажи автомобиля")
    public AllPostPage verifyBuySellCarStatus(String expectedStatus) {
        String actualStatus = getBtnStatus(4).getText();
        log.info("Buy/Sell Car Status - Expected: {}, Actual: {}", expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(4), 30),
                "Статус покупки/продажи автомобиля не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    // Создание нового автомобиля
    @Step("Заполнение формы создания автомобиля")
    public AllPostPage fillCreateCarForm(String engineType, String mark, String model, String price) {
        new Input("car_engine_type_send").fillField(engineType);
        new Input("car_mark_send").fillField(mark);
        new Input("car_model_send").fillField(model);
        new Input("car_price_send").fillField(price);
        return this;
    }

    @Step("Отправка запроса на создание автомобиля")
    public AllPostPage pushCreateCar() {
        getBtnPushToApi(5).shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса создания автомобиля")
    public AllPostPage verifyCreateCarStatus(String expectedStatus) {
        String actualStatus = getBtnStatus(5).getText();
        log.info("Create Car Status - Expected: {}, Actual: {}", expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(5), 30),
                "Статус создания автомобиля не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    @Step("Получение ID созданного автомобиля")
    public String getCreatedCarId() {
        String fullText = getBtnNewId(5).getText();
        String carId = fullText.replaceAll("\\D+", "");
        log.info("Created Car ID: {}", carId);
        return carId;
    }

    // Создание нового дома
    @Step("Заполнение формы создания дома")
    public AllPostPage fillCreateHouseForm(String floors, String price, String parkingFirst,
                                           String parkingSecond, String parkingThird, String parkingFourth) {
        new Input("floor_send").fillField(floors);
        new Input("price_send").fillField(price);
        new Input("parking_first_send").fillField(parkingFirst);
        new Input("parking_second_send").fillField(parkingSecond);
        new Input("parking_third_send").fillField(parkingThird);
        new Input("parking_fourth_send").fillField(parkingFourth);
        return this;
    }

    @Step("Отправка запроса на создание дома")
    public AllPostPage pushCreateHouse() {
        getBtnPushToApi(6).shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса создания дома")
    public AllPostPage verifyCreateHouseStatus(String expectedStatus) {
        String actualStatus = getBtnStatus(6).getText();
        log.info("Create House Status - Expected: {}, Actual: {}", expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(6), 30),
                "Статус создания дома не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    @Step("Получение ID созданного дома")
    public String getCreatedHouseId() {
        String fullText = getBtnNewId(6).getText();
        String houseId = fullText.replaceAll("\\D+", "");
        log.info("Created House ID: {}", houseId);
        return houseId;
    }
}