package ui.pages.cars;

import org.testng.annotations.DataProvider;
import ui.dto.cars.CarTestData;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

public class CarsPage extends BasePage {

    @DataProvider(name = "Тестовые данные для негативных проверок создания автомобиля с пустыми полями")
    public Object[][] carData() {
        return new Object[][] {
                {CarTestData.builder()
                        .engineType("")
                        .mark(faker.vehicle().manufacturer())
                        .model(faker.vehicle().model())
                        .price(faker.number().digits(7))
                        .build()},
                {CarTestData.builder()
                        .engineType("Electric")
                        .mark("")
                        .model(faker.vehicle().model())
                        .price(faker.number().digits(7))
                        .build()},
                {CarTestData.builder()
                        .engineType("Diesel")
                        .mark(faker.vehicle().manufacturer())
                        .model("")
                        .price(faker.number().digits(7))
                        .build()},
                {CarTestData.builder()
                        .engineType("Hydrogenic")
                        .mark(faker.vehicle().manufacturer())
                        .model(faker.vehicle().model())
                        .price("")
                        .build()},
                {CarTestData.builder()
                        .engineType("")
                        .mark("")
                        .model("")
                        .price("")
                        .build()}
        };
    }

    @DataProvider(name = "Тестовые данные для проверок создания автомобиля с цифровым значением для строкового поля")
    public Object[][] carDataWithNumbers() {
        return new Object[][]{
                {CarTestData.builder()
                        .engineType(faker.number().digits(5))
                        .mark(faker.vehicle().manufacturer())
                        .model(faker.vehicle().model())
                        .price(faker.number().digits(7))
                        .build()},
                {CarTestData.builder()
                        .engineType("Diesel")
                        .mark(faker.number().digits(5))
                        .model(faker.vehicle().model())
                        .price(faker.number().digits(7))
                        .build()},
                {CarTestData.builder()
                        .engineType("Hydrogenic")
                        .mark(faker.vehicle().manufacturer())
                        .model(faker.number().digits(5))
                        .price(faker.number().digits(7))
                        .build()}
        };
    }

    public void addNewCarUI(CarTestData carTestData) {
        new Input("car_engine_type_send").fillField(carTestData.getEngineType());
        new Input("car_mark_send").fillField(carTestData.getMark());
        new Input("car_model_send").fillField(carTestData.getModel());
        new Input("car_price_send").fillField(carTestData.getPrice());
    }
}
