package tests.ui.allpost;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.pages.allpost.AllPostPage;

import static org.testng.Assert.assertNotNull;
import static ui.pages.base.BasePage.faker;

@Epic("ALL POST")
@Feature("Тестирование всех операций на странице ALL POST")
public class AllPostTest extends BaseTest {

    private AllPostPage allPostPage;
    private String userId;
    private String houseId;
    private String carId;

    @BeforeMethod
    public void setUp() {
        loginPage.authorization();
        allPostPage = new AllPostPage();
        allPostPage.openPage();
    }

    @Test(testName = "Создание нового пользователя через ALL POST")
    @Description("Проверка создания нового пользователя с валидными данными")
    public void testCreateUser() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        int age = faker.number().numberBetween(18, 99);
        String gender = faker.bool().bool() ? "MALE" : "FEMALE";
        int money = faker.number().numberBetween(1000, 100000);

        allPostPage.createUser(firstName, lastName, age, gender, money)
                .verifyCreateUserStatus("Status: Successfully pushed, code: 201");

        userId = allPostPage.getCreatedUserId();
        assertNotNull(userId, "ID созданного пользователя не получен");
    }

    @Test(testName = "Добавление денег пользователю через ALL POST")
    @Description("Проверка добавления денег существующему пользователю")
    public void testAddMoney() {
        if (userId == null) {
            testCreateUser();
        }
        int money = faker.number().numberBetween(100, 10000);

        allPostPage.addMoney(userId, money)
                .verifyAddMoneyStatus("Status: Successfully pushed, code: 200");
    }

    @Test(testName = "Заселение пользователя в дом через ALL POST")
    @Description("Проверка заселения пользователя в дом")
    public void testSettleUser() {
        if (userId == null) {
            testCreateUser();
        }
        if (houseId == null) {
            testCreateHouse();
            houseId = allPostPage.getCreatedHouseId();
        }

        allPostPage.settleOrEvictUser(userId, houseId, "settle")
                .verifySettleStatus("Status: Successfully pushed, code: 200");
    }

    @Test(testName = "Выселение пользователя из дома через ALL POST")
    @Description("Проверка выселения пользователя из дома")
    public void testEvictUser() {
        if (userId == null) {
            testCreateUser();
        }
        if (houseId == null) {
            testCreateHouse();
            houseId = allPostPage.getCreatedHouseId();
        }

        // Сначала заселяем
        allPostPage.settleOrEvictUser(userId, houseId, "settle")
                .verifySettleStatus("Status: Successfully pushed, code: 200");

        // Теперь выселяем
        allPostPage.settleOrEvictUser(userId, houseId, "evict")
                .verifySettleStatus("Status: Successfully pushed, code: 200");
    }

    @Test(testName = "Покупка автомобиля пользователем через ALL POST")
    @Description("Проверка покупки автомобиля пользователем")
    public void testBuyCar() {
        if (userId == null) {
            testCreateUser();
        }
        if (carId == null) {
            testCreateCar();
            carId = allPostPage.getCreatedCarId();
        }

        allPostPage.buyOrSellCar(userId, carId, "buy")
                .verifyCarStatus("Status: Successfully pushed, code: 200");
    }

    @Test(testName = "Продажа автомобиля пользователем через ALL POST")
    @Description("Проверка продажи автомобиля пользователем")
    public void testSellCar() {
        if (userId == null) {
            testCreateUser();
        }
        if (carId == null) {
            testCreateCar();
            carId = allPostPage.getCreatedCarId();
        }

        // Сначала покупаем
        allPostPage.buyOrSellCar(userId, carId, "buy")
                .verifyCarStatus("Status: Successfully pushed, code: 200");

        // Теперь продаем
        allPostPage.buyOrSellCar(userId, carId, "sell")
                .verifyCarStatus("Status: Successfully pushed, code: 200");
    }

    @Test(testName = "Создание нового автомобиля через ALL POST")
    @Description("Проверка создания нового автомобиля с валидными данными")
    public void testCreateCar() {
        String engineType = "Electric";
        String mark = faker.vehicle().manufacturer();
        String model = faker.vehicle().model();
        double price = faker.number().numberBetween(10000, 1000000);

        allPostPage.createCar(engineType, mark, model, price)
                .verifyCreateCarStatus("Status: Successfully pushed, code: 201");
    }

    @Test(testName = "Создание нового дома через ALL POST")
    @Description("Проверка создания нового дома с валидными данными")
    public void testCreateHouse() {
        int floors = faker.number().numberBetween(1, 50);
        double price = faker.number().numberBetween(100000, 10000000);
        int warmCovered = faker.number().numberBetween(0, 10);
        int warmNotCovered = faker.number().numberBetween(0, 10);
        int coldCovered = faker.number().numberBetween(0, 10);
        int coldNotCovered = faker.number().numberBetween(0, 10);

        allPostPage.createHouse(floors, price, warmCovered, warmNotCovered, coldCovered, coldNotCovered)
                .verifyCreateHouseStatus("Status: Successfully pushed, code: 201");
    }
}