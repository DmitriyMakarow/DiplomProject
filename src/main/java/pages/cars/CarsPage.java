package pages.cars;

import org.testng.annotations.DataProvider;
import pages.base.BasePage;

import static com.codeborne.selenide.Selenide.open;

public class CarsPage extends BasePage {

    public void openCreateCarPage() {
        open("http://82.142.167.37:4881/#/create/cars");
    }

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
