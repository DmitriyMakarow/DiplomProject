package test.pages.cars;

import org.testng.annotations.DataProvider;
import test.pages.base.BasePage;

import static com.codeborne.selenide.Selenide.open;

public class CarsPage extends BasePage {

    @DataProvider(name = "Тестовые данные для негативных проверок создания автомобиля")
    public Object[][] carData() {
        return new Object[][] {
                {"", faker.vehicle().manufacturer(), faker.vehicle().model(), faker.number().digits(7)},
                {"Electric", "", faker.vehicle().model(), faker.number().digits(7)},
                {"Diesel", faker.vehicle().manufacturer(), "", faker.number().digits(7)},
                {"Hydrogenic", faker.vehicle().manufacturer(), faker.vehicle().model(), ""}
        };
    }
}
