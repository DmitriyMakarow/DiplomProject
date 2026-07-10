package tests.ui.allpost;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.cars.CarTestData;
import ui.dto.cars.CarTestDataFactory;
import ui.dto.users.UserTestData;
import ui.dto.users.UserTestDataFactory;
import ui.enumUI.RadioLabel;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;
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

    @Owner("Кирсанов А.П.")
    @Test(testName = "Создание нового пользователя через ALL POST",
            groups = {"regression"})
    @Description("Проверка создания нового пользователя с валидными данными")
    public void testCreateUser() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание нового пользователя через ALL POST")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Создание пользователя через ALL POST");
        parameter("Ожидаемый результат", "Пользователь успешно создан");

        UserTestData userData = UserTestDataFactory.getUserTestDataUI();

        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushToApi(1)
                .verifyStatus(1, STATUS_201);

        String userId = allPostPage.getCreatedObjectId(1);
        assertNotNull(userId, "ID созданного пользователя не получен");
    }

    @Owner("Кирсанов А.П.")
    @Test(testName = "Добавление денег пользователю через ALL POST",
            groups = {"regression"})
    @Description("Проверка добавления денег существующему пользователю")
    public void testAddMoney() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Добавление денег пользователю через ALL POST")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Добавление денег через ALL POST");
        parameter("Ожидаемый результат", "Деньги успешно добавлены");

        UserTestData userData = UserTestDataFactory.getUserTestDataUI();
        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushToApi(1).verifyStatus(1, STATUS_201);

        String userId = allPostPage.getCreatedObjectId(1);
        assertNotNull(userId, "ID созданного пользователя не получен");

        String money = faker.number().digits(4);
        allPostPage.fillAddMoneyForm(userId, money)
                .pushToApi(2)
                .verifyStatus(2, STATUS_200);
    }

    @Owner("Кирсанов А.П.")
    @Test(testName = "Создание нового автомобиля через ALL POST",
            groups = {"regression"})
    @Description("Проверка создания нового автомобиля с валидными данными")
    public void testCreateCar() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание нового автомобиля через ALL POST")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Создание автомобиля через ALL POST");
        parameter("Ожидаемый результат", "Автомобиль успешно создан");

        CarTestData carData = CarTestDataFactory.validCarTestDataUI();

        allPostPage.fillCreateCarForm(carData)
                .pushToApi(5)
                .verifyStatus(5, STATUS_201);
    }

    @Owner("Кирсанов А.П.")
    @Test(testName = "Создание нового дома через ALL POST",
            groups = {"regression"})
    @Description("Проверка создания нового дома с валидными данными")
    public void testCreateHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание нового дома через ALL POST")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Создание дома через ALL POST");
        parameter("Ожидаемый результат", "Дом успешно создан");

        String floors = String.valueOf(faker.number().numberBetween(1, 50));
        String price = String.valueOf(faker.number().numberBetween(100000, 10000000));
        String parkingFirst = String.valueOf(faker.number().numberBetween(0, 10));
        String parkingSecond = String.valueOf(faker.number().numberBetween(0, 10));
        String parkingThird = String.valueOf(faker.number().numberBetween(0, 10));
        String parkingFourth = String.valueOf(faker.number().numberBetween(0, 10));

        allPostPage.fillCreateHouseForm(floors, price, parkingFirst, parkingSecond, parkingThird, parkingFourth)
                .pushToApi(6)
                .verifyStatus(6, STATUS_201);
    }

    @Owner("Кирсанов А.П.")
    @Test(dataProvider = "settleEvictActions",
            testName = "Заселение/выселение пользователя в/из дом(а) через ALL POST",
            groups = {"regression"})
    @Description("Параметризованный тест: заселение и выселение пользователя в дом")
    public void testSettleEvictUser(String actionName, RadioLabel action) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Заселение/выселение пользователя в/из дом(а) через ALL POST")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Заселение/выселение через ALL POST");
        parameter("Ожидаемый результат", "Операция успешно выполнена");
        parameter("Действие пользователя", actionName);

        UserTestData userData = UserTestDataFactory.getUserTestDataUI();
        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushToApi(1).verifyStatus(1, STATUS_201);
        String userId = allPostPage.getCreatedObjectId(1);
        assertNotNull(userId, "ID созданного пользователя не получен");

        String floors = String.valueOf(faker.number().numberBetween(1, 10));
        String price = String.valueOf(faker.number().numberBetween(100000, 1000000));
        String parkingFirst = String.valueOf(faker.number().numberBetween(1, 5));
        String parkingSecond = String.valueOf(faker.number().numberBetween(1, 5));
        String parkingThird = String.valueOf(faker.number().numberBetween(1, 5));
        String parkingFourth = String.valueOf(faker.number().numberBetween(1, 5));

        allPostPage.fillCreateHouseForm(floors, price, parkingFirst, parkingSecond, parkingThird, parkingFourth)
                .pushToApi(6)
                .verifyStatus(6, STATUS_201);
        String houseId = allPostPage.getCreatedObjectId(6);
        assertNotNull(houseId, "ID созданного дома не получен");

        if (action == RadioLabel.EVICT) {
            allPostPage.fillSettleEvictForm(userId, houseId);
            baseSteps.selectRadioLabel(RadioLabel.SETTLE);
            allPostPage.pushToApi(3).verifyStatus(3, STATUS_200);
        }

        allPostPage.fillSettleEvictForm(userId, houseId);
        baseSteps.selectRadioLabel(action);
        allPostPage.pushToApi(3)
                .verifyStatus(3, STATUS_200);
    }

    @Owner("Кирсанов А.П.")
    @Test(dataProvider = "buySellActions",
            testName = "Покупка/продажа автомобиля пользователем через ALL POST",
            groups = {"regression"})
    @Description("Параметризованный тест: покупка и продажа автомобиля")
    public void testBuySellCar(String actionName, RadioLabel action) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Покупка/продажа автомобиля пользователем через ALL POST")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Покупка/продажа через ALL POST");
        parameter("Ожидаемый результат", "Операция успешно выполнена");
        parameter("Действие пользователя", actionName);

        UserTestData userData = UserTestDataFactory.getUserTestDataUI();
        allPostPage.fillCreateUserForm(userData);
        baseSteps.selectRadioLabel(RadioLabel.MALE);
        allPostPage.pushToApi(1).verifyStatus(1, STATUS_201);
        String userId = allPostPage.getCreatedObjectId(1);
        assertNotNull(userId, "ID созданного пользователя не получен");

        CarTestData carData = CarTestDataFactory.validCarTestDataUI();
        allPostPage.fillCreateCarForm(carData)
                .pushToApi(5)
                .verifyStatus(5, STATUS_201);
        String carId = allPostPage.getCreatedObjectId(5);
        assertNotNull(carId, "ID созданного автомобиля не получен");

        if (action == RadioLabel.SELL) {
            allPostPage.fillBuySellCarForm(userId, carId);
            baseSteps.selectRadioLabel(RadioLabel.BUY);
            allPostPage.pushToApi(4).verifyStatus(4, STATUS_200);
        }

        allPostPage.fillBuySellCarForm(userId, carId);
        baseSteps.selectRadioLabel(action);
        allPostPage.pushToApi(4)
                .verifyStatus(4, STATUS_200);
    }
}