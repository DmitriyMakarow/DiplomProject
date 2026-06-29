package ui.pages.allpost;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.pages.base.BasePage;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

@Log4j2
public class AllPostPage extends BasePage {

    // Создание нового пользователя
    private final SelenideElement
            firstNameField = $("#first_name_send"),
            lastNameField = $("#last_name_send"),
            ageField = $("#age_send"),
            sexMaleRadio = $x("//input[@type='radio' and @name='sex_send' and @value='MALE']"),
            sexFemaleRadio = $x("//input[@type='radio' and @name='sex_send' and @value='FEMALE']"),
            moneyField = $("#money_send"),
            createUserBtn = $x("(//div[.//table]//button[contains(@class, 'tableButton')])[1]"),
            createUserStatus = $x("(//div[.//table]//button[contains(@class, 'status')])[1]"),
            createUserNewId = $x("(//div[.//table]//button[contains(@class, 'newId')])[1]");

    // Добавление денег пользователю
    private final SelenideElement
            addMoneyUserIdField = $x("(//input[@id='id_send'])[1]"),
            addMoneyField = $x("(//input[@id='money_send'])[2]"),
            addMoneyBtn = $x("(//div[.//table]//button[contains(@class, 'tableButton')])[2]"),
            addMoneyStatus = $x("(//div[.//table]//button[contains(@class, 'status')])[2]");

    // Заселение/выселение из дома
    private final SelenideElement
            settleUserIdField = $x("(//input[@id='id_send'])[2]"),
            settleHouseIdField = $("#house_send"),
            settleRadio = $x("//input[@type='radio' and @name='settleOrEvict' and @value='settle']"),
            evictRadio = $x("//input[@type='radio' and @name='settleOrEvict' and @value='evict']"),
            settleBtn = $x("(//div[.//table]//button[contains(@class, 'tableButton')])[3]"),
            settleStatus = $x("(//div[.//table]//button[contains(@class, 'status')])[3]");

    // Покупка/продажа автомобиля
    private final SelenideElement
            carUserIdField = $x("(//input[@id='id_send'])[3]"),
            carIdField = $("#car_send"),
            buyRadio = $x("//input[@type='radio' and @name='settleOrEvict' and @value='buyCar']"),
            sellRadio = $x("//input[@type='radio' and @name='settleOrEvict' and @value='sellCar']"),
            carBtn = $x("(//div[.//table]//button[contains(@class, 'tableButton')])[4]"),
            carStatus = $x("(//div[.//table]//button[contains(@class, 'status')])[4]");

    // Создание нового автомобиля
    private final SelenideElement
            engineTypeField = $("#car_engine_type_send"),
            markField = $("#car_mark_send"),
            modelField = $("#car_model_send"),
            priceField = $("#car_price_send"),
            createCarBtn = $x("(//div[.//table]//button[contains(@class, 'tableButton')])[5]"),
            createCarStatus = $x("(//div[.//table]//button[contains(@class, 'status')])[5]"),
            createCarNewId = $x("(//div[.//table]//button[contains(@class, 'newId')])[2]");

    // Создание нового дома
    private final SelenideElement
            floorsField = $("#floor_send"),
            housePriceField = $("#price_send"),
            warmCoveredCountField = $("#parking_first_send"),
            warmNotCoveredCountField = $("#parking_second_send"),
            coldCoveredCountField = $("#parking_third_send"),
            coldNotCoveredCountField = $("#parking_fourth_send"),
            createHouseBtn = $x("(//div[.//table]//button[contains(@class, 'tableButton')])[6]"),
            createHouseStatus = $x("(//div[.//table]//button[contains(@class, 'status')])[6]"),
            createHouseNewId = $x("(//div[.//table]//button[contains(@class, 'newId')])[3]");

    @Step("Открытие страницы ALL POST")
    public AllPostPage openPage() {
        open("http://82.142.167.37:4881/#/create/all");
        return this;
    }

    @Step("Создание нового пользователя")
    public AllPostPage createUser(String firstName, String lastName, int age, String gender, int money) {
        firstNameField.setValue(firstName);
        lastNameField.setValue(lastName);
        ageField.setValue(String.valueOf(age));
        if (gender.equalsIgnoreCase("MALE")) {
            sexMaleRadio.click();
        } else {
            sexFemaleRadio.click();
        }
        moneyField.setValue(String.valueOf(money));
        createUserBtn.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса создания пользователя")
    public AllPostPage verifyCreateUserStatus(String expectedStatus) {
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, createUserStatus, 30),
                "Статус создания пользователя не соответствует ожидаемому");
        return this;
    }

    @Step("Получение ID созданного пользователя")
    public String getCreatedUserId() {
        String fullText = createUserNewId.getText();
        return fullText.replaceAll("\\D+", "");
    }

    @Step("Добавление денег пользователю")
    public AllPostPage addMoney(String userId, int money) {
        addMoneyUserIdField.setValue(userId);
        addMoneyField.setValue(String.valueOf(money));
        addMoneyBtn.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса добавления денег")
    public AllPostPage verifyAddMoneyStatus(String expectedStatus) {
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, addMoneyStatus, 30),
                "Статус добавления денег не соответствует ожидаемому");
        return this;
    }

    @Step("Заселение/выселение пользователя из дома")
    public AllPostPage settleOrEvictUser(String userId, String houseId, String action) {
        settleUserIdField.setValue(userId);
        settleHouseIdField.setValue(houseId);
        if (action.equalsIgnoreCase("settle")) {
            settleRadio.click();
        } else {
            evictRadio.click();
        }
        settleBtn.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса заселения/выселения")
    public AllPostPage verifySettleStatus(String expectedStatus) {
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, settleStatus, 30),
                "Статус заселения/выселения не соответствует ожидаемому");
        return this;
    }

    @Step("Покупка/продажа автомобиля")
    public AllPostPage buyOrSellCar(String userId, String carId, String action) {
        carUserIdField.setValue(userId);
        carIdField.setValue(carId);
        if (action.equalsIgnoreCase("buy")) {
            buyRadio.click();
        } else {
            sellRadio.click();
        }
        carBtn.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса покупки/продажи автомобиля")
    public AllPostPage verifyCarStatus(String expectedStatus) {
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, carStatus, 30),
                "Статус покупки/продажи автомобиля не соответствует ожидаемому");
        return this;
    }

    @Step("Создание нового автомобиля")
    public AllPostPage createCar(String engineType, String mark, String model, double price) {
        engineTypeField.setValue(engineType);
        markField.setValue(mark);
        modelField.setValue(model);
        priceField.setValue(String.valueOf(price));
        createCarBtn.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса создания автомобиля")
    public AllPostPage verifyCreateCarStatus(String expectedStatus) {
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, createCarStatus, 30),
                "Статус создания автомобиля не соответствует ожидаемому");
        return this;
    }

    @Step("Получение ID созданного автомобиля")
    public String getCreatedCarId() {
        String fullText = createCarNewId.getText();
        return fullText.replaceAll("\\D+", "");
    }

    @Step("Создание нового дома")
    public AllPostPage createHouse(int floors, double price, int warmCovered, int warmNotCovered,
                                   int coldCovered, int coldNotCovered) {
        floorsField.setValue(String.valueOf(floors));
        housePriceField.setValue(String.valueOf(price));
        warmCoveredCountField.setValue(String.valueOf(warmCovered));
        warmNotCoveredCountField.setValue(String.valueOf(warmNotCovered));
        coldCoveredCountField.setValue(String.valueOf(coldCovered));
        coldNotCoveredCountField.setValue(String.valueOf(coldNotCovered));
        createHouseBtn.shouldBe(enabled).click();
        return this;
    }

    @Step("Проверка статуса создания дома")
    public AllPostPage verifyCreateHouseStatus(String expectedStatus) {
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, createHouseStatus, 30),
                "Статус создания дома не соответствует ожидаемому");
        return this;
    }

    @Step("Получение ID созданного дома")
    public String getCreatedHouseId() {
        String fullText = createHouseNewId.getText();
        return fullText.replaceAll("\\D+", "");
    }
}