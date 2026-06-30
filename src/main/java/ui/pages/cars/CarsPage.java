package ui.pages.cars;

import org.testng.annotations.DataProvider;
import ui.dto.cars.CarTestData;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

public class CarsPage extends BasePage {

    @DataProvider(name = "Тестовые данные для негативных проверок создания автомобиля")
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
                        .build()}
        };
    }

    public void addNewCarUI(CarTestData carTestData) {
        new Input("engine_type").fillField(carTestData.getEngineType());
        new Input("mark").fillField(carTestData.getMark());
        new Input("model").fillField(carTestData.getModel());
        new Input("price").fillField(carTestData.getPrice());
    }
}
