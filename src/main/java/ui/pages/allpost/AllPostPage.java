package ui.pages.allpost;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dto.cars.CarTestData;
import ui.dto.users.UserTestData;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

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

    @Step("Отправка запроса формы {formIndex}")
    public AllPostPage pushToApi(int formIndex) {
        assertTrue(waitAppear(getBtnPushToApi(formIndex)),
                "Кнопка PUSH TO API формы " + formIndex + " не появилась");
        getBtnPushToApi(formIndex).click();
        return this;
    }

    @Step("Проверка статуса формы {formIndex}")
    public AllPostPage verifyStatus(int formIndex, String expectedStatus) {
        String actualStatus = getBtnStatus(formIndex).getText();
        log.info("Form {} Status - Expected: {}, Actual: {}", formIndex, expectedStatus, actualStatus);
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, getBtnStatus(formIndex), 30),
                "Статус запроса не соответствует ожидаемому. Ожидалось: " + expectedStatus + ", Получено: " + actualStatus);
        return this;
    }

    @Step("Получение ID созданного объекта из формы {formIndex}")
    public String getCreatedObjectId(int formIndex) {
        String fullText = getBtnNewId(formIndex).getText();
        String objectId = fullText.replaceAll("\\D+", "");
        log.info("Created Object ID from form {}: {}", formIndex, objectId);
        return objectId;
    }

    // Создание пользователя
    @Step("Заполнение формы создания пользователя")
    public AllPostPage fillCreateUserForm(UserTestData userData) {
        new Input("first_name_send").fillField(userData.getFirstName());
        new Input("last_name_send").fillField(userData.getLastName());
        new Input("age_send").fillField(userData.getAge());
        new Input("money_send").fillField(userData.getMoney(), 1);
        return this;
    }

    // Добавление денег
    @Step("Заполнение формы добавления денег")
    public AllPostPage fillAddMoneyForm(String userId, String money) {
        new Input("id_send").fillField(userId, 2);
        new Input("money_send").fillField(money, 2);
        return this;
    }

    // Заселение/выселение
    @Step("Заполнение формы заселения/выселения")
    public AllPostPage fillSettleEvictForm(String userId, String houseId) {
        new Input("id_send").fillField(userId, 3);
        new Input("house_send").fillField(houseId);
        return this;
    }

    // Покупка/продажа авто
    @Step("Заполнение формы покупки/продажи автомобиля")
    public AllPostPage fillBuySellCarForm(String userId, String carId) {
        new Input("id_send").fillField(userId, 4);
        new Input("car_send").fillField(carId);
        return this;
    }

    // Создание автомобиля
    @Step("Заполнение формы создания автомобиля")
    public AllPostPage fillCreateCarForm(CarTestData carData) {
        new Input("car_engine_type_send").fillField(carData.getEngineType());
        new Input("car_mark_send").fillField(carData.getMark());
        new Input("car_model_send").fillField(carData.getModel());
        new Input("car_price_send").fillField(carData.getPrice());
        return this;
    }

    // Создание дома
    @Step("Заполнение формы создания дома")
    public AllPostPage fillCreateHouseForm(String floors, String price, String parkingFirst,
                                           String parkingSecond, String parkingThird, String parkingFourth) {
        new Input("floor_send").fillField(floors);
        new Input("price_send").fillField(price, 6);
        new Input("parking_first_send").fillField(parkingFirst);
        new Input("parking_second_send").fillField(parkingSecond);
        new Input("parking_third_send").fillField(parkingThird);
        new Input("parking_fourth_send").fillField(parkingFourth);
        return this;
    }
}