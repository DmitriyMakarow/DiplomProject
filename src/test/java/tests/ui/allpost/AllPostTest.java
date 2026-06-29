package tests.ui.allpost;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.UserTestData;
import ui.dto.UserTestDataFactory;
import ui.enumUI.RadioLabel;

import static org.testng.Assert.assertNotNull;
import static ui.pages.base.BasePage.faker;

@Epic("ALL POST")
@Feature("Тестирование всех операций на странице ALL POST")
public class AllPostTest extends BaseTest {

    private static final String STATUS_201 = "Status: Successfully pushed, code: 201";
    private static final String STATUS_200 = "Status: Successfully pushed, code: 200";

    @BeforeMethod
    public void setUp() {
        loginPage.authorization();
        allPostPage.openPage();
    }

    @DataProvider(name = "settleEvictActions")
    public static Object[][] getSettleEvictActions() {
        return new Object[][]{
                {"settle", RadioLabel.SETTLE},
                {"evict", RadioLabel.EVICT}
        };
    }

    @DataProvider(name = "buySellActions")
    public static Object[][] getBuySellActions() {
        return new Object[][]{
                {"buy", RadioLabel.BUY},
                {"sell", RadioLabel.SELL}
        };
    }

    @Test(testName = "Создание нового пользователя через ALL POST")
    @Description("Проверка создания нового пользователя с валидными данными")
    public void testCreateUser() {
        UserTestData userData = UserTestDataFactory.getUserTestDataUI();

        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushCreateUser()
                .verifyCreateUserStatus(STATUS_201);

        String userId = allPostPage.getCreatedUserId();
        assertNotNull(userId, "ID созданного пользователя не получен");
    }

    @Test(testName = "Добавление денег пользователю через ALL POST")
    @Description("Проверка добавления денег существующему пользователю")
    public void testAddMoney() {
        UserTestData userData = UserTestDataFactory.getUserTestDataUI();
        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushCreateUser().verifyCreateUserStatus(STATUS_201);

        String userId = allPostPage.getCreatedUserId();
        assertNotNull(userId, "ID созданного пользователя не получен");

        String money = faker.number().digits(4);
        allPostPage.fillAddMoneyForm(userId, money)
                .pushAddMoney()
                .verifyAddMoneyStatus(STATUS_200);
    }

    @Test(testName = "Создание нового автомобиля через ALL POST")
    @Description("Проверка создания нового автомобиля с валидными данными")
    public void testCreateCar() {
        String engineType = "Electric";
        String mark = faker.vehicle().manufacturer();
        String model = faker.vehicle().model();
        String price = faker.number().digits(7);

        allPostPage.fillCreateCarForm(engineType, mark, model, price)
                .pushCreateCar()
                .verifyCreateCarStatus(STATUS_201);
    }

    @Test(testName = "Создание нового дома через ALL POST")
    @Description("Проверка создания нового дома с валидными данными")
    public void testCreateHouse() {
        String floors = String.valueOf(faker.number().numberBetween(1, 50));
        String price = String.valueOf(faker.number().numberBetween(100000, 10000000));
        String parkingFirst = String.valueOf(faker.number().numberBetween(0, 10));
        String parkingSecond = String.valueOf(faker.number().numberBetween(0, 10));
        String parkingThird = String.valueOf(faker.number().numberBetween(0, 10));
        String parkingFourth = String.valueOf(faker.number().numberBetween(0, 10));

        allPostPage.fillCreateHouseForm(floors, price, parkingFirst, parkingSecond, parkingThird, parkingFourth)
                .pushCreateHouse()
                .verifyCreateHouseStatus(STATUS_201);
    }

    @Test(dataProvider = "settleEvictActions",
            testName = "Заселение/выселение через ALL POST")
    @Description("Параметризованный тест: заселение и выселение пользователя в дом")
    public void testSettleEvictUser(String actionName, RadioLabel action) {
        UserTestData userData = UserTestDataFactory.getUserTestDataUI();
        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushCreateUser().verifyCreateUserStatus(STATUS_201);
        String userId = allPostPage.getCreatedUserId();
        assertNotNull(userId, "ID созданного пользователя не получен");

        String floors = String.valueOf(faker.number().numberBetween(1, 10));
        String price = String.valueOf(faker.number().numberBetween(100000, 1000000));
        String parkingFirst = String.valueOf(faker.number().numberBetween(1, 5));
        String parkingSecond = String.valueOf(faker.number().numberBetween(1, 5));
        String parkingThird = String.valueOf(faker.number().numberBetween(1, 5));
        String parkingFourth = String.valueOf(faker.number().numberBetween(1, 5));

        allPostPage.fillCreateHouseForm(floors, price, parkingFirst, parkingSecond, parkingThird, parkingFourth)
                .pushCreateHouse()
                .verifyCreateHouseStatus(STATUS_201);
        String houseId = allPostPage.getCreatedHouseId();
        assertNotNull(houseId, "ID созданного дома не получен");

        if (action == RadioLabel.EVICT) {
            allPostPage.fillSettleEvictForm(userId, houseId);
            baseSteps.selectRadioLabel(RadioLabel.SETTLE);
            allPostPage.pushSettleEvict().verifySettleEvictStatus(STATUS_200);
        }

        allPostPage.fillSettleEvictForm(userId, houseId);
        baseSteps.selectRadioLabel(action);
        allPostPage.pushSettleEvict()
                .verifySettleEvictStatus(STATUS_200);
    }

    @Test(dataProvider = "buySellActions",
            testName = "Покупка/продажа автомобиля через ALL POST")
    @Description("Параметризованный тест: покупка и продажа автомобиля")
    public void testBuySellCar(String actionName, RadioLabel action) {
        UserTestData userData = UserTestDataFactory.getUserTestDataUI();
        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushCreateUser().verifyCreateUserStatus(STATUS_201);
        String userId = allPostPage.getCreatedUserId();
        assertNotNull(userId, "ID созданного пользователя не получен");

        String engineType = "Electric";
        String mark = faker.vehicle().manufacturer();
        String model = faker.vehicle().model();
        String price = faker.number().digits(5);

        allPostPage.fillCreateCarForm(engineType, mark, model, price)
                .pushCreateCar()
                .verifyCreateCarStatus(STATUS_201);
        String carId = allPostPage.getCreatedCarId();
        assertNotNull(carId, "ID созданного автомобиля не получен");

        if (action == RadioLabel.SELL) {
            allPostPage.fillBuySellCarForm(userId, carId);
            baseSteps.selectRadioLabel(RadioLabel.BUY);
            allPostPage.pushBuySellCar().verifyBuySellCarStatus(STATUS_200);
        }

        allPostPage.fillBuySellCarForm(userId, carId);
        baseSteps.selectRadioLabel(action);
        allPostPage.pushBuySellCar()
                .verifyBuySellCarStatus(STATUS_200);
    }
}