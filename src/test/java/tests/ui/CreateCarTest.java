package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import pages.cars.CarsPage;
import tests.BaseTest;
import wrappers.Input;

@Story("Создание автомобиля")
public class CreateCarTest extends BaseTest {

    public static String
            engineType = "Electric",
            mark = faker.vehicle().manufacturer(),
            model = faker.vehicle().model(),
            price = faker.number().digits(7);

    @Test(testName = "Создание автомобиля с валидными данными")
    @Description("Создание автомобиля с валидными данными")
    void successCreateCar() {
        final String status = "Status: Successfully pushed, code: 201";

        loginPage.authorization();
        baseSteps
                .showDropdown("Cars")
                .openTableFromDropdown("Cars", "Create new");
        new Input("engine_type").fillField(engineType);
        new Input("mark").fillField(mark);
        new Input("model").fillField(model);
        new Input("price").fillField(price);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New car ID:");
    }

    @Test(testName = "Создание автомобиля с пустым полем ",
            dataProvider = "Тестовые данные для негативных проверок создания автомобиля",
            dataProviderClass = CarsPage.class)
    @Description("Создание автомобиля с валидными данными")
    void unsuccessCreateCar(String engineType, String mark, String model, String price) {
        final String status = "Status: Invalid request data";

        loginPage.authorization();
        baseSteps
                .showDropdown("Cars")
                .openTableFromDropdown("Cars", "Create new");
        new Input("engine_type").fillField(engineType);
        new Input("mark").fillField(mark);
        new Input("model").fillField(model);
        new Input("price").fillField(price);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();
    }

    @Issue("")
    @Test(testName = "Создание автомобиля с невалидными данными")
    @Description("Создание автомобиля с невалидными данными")
    void createCarInvalidData() {
        final String status = "Status: AxiosError: Request failed with status code 400";

        loginPage.authorization();
        baseSteps
                .showDropdown("Cars")
                .openTableFromDropdown("Cars", "Create new");
        new Input("engine_type").fillField("PHEV");
        new Input("mark").fillField("Porsche");
        new Input("model").fillField("911");
        new Input("price").fillField(price);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();
    }
}
