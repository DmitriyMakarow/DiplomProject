package test.tests.ui.cars;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import test.pages.cars.CarsPage;
import test.tests.BaseTest;
import wrappers.Input;

import static enumUI.Dropdown.CARS;
import static enumUI.TableType.CREATE_NEW_CARS;

@Epic("Автомобили")
@Feature("Создание автомобиля")
public class CreateCarTest extends BaseTest {

    private final String
            mark = faker.vehicle().manufacturer(),
            model = faker.vehicle().model(),
            price = faker.number().digits(7);

    @Test(testName = "Создание автомобиля с валидными данными")
    void successCreateCar() {
        final String status = "Status: Successfully pushed, code: 201";

        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
        new Input("engine_type").fillField("Electric");
        new Input("mark").fillField(mark);
        new Input("model").fillField(model);
        new Input("price").fillField(price);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New car ID:");
    }

    @Story("Создание автомобиля с невалидными данными")
    @Test(testName = "Создание автомобиля с пустым полем ",
            dataProvider = "Тестовые данные для негативных проверок создания автомобиля",
            dataProviderClass = CarsPage.class)
    void unsuccessCreateCar(String engineType, String mark, String model, String price) {
        final String status = "Status: Invalid request data";

        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
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
    @Story("Создание автомобиля с невалидными данными")
    @Test(testName = "Создание автомобиля с несоответствующими данными")
    void createCarInvalidData() {
        final String status = "Status: AxiosError: Request failed with status code 400";

        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
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
