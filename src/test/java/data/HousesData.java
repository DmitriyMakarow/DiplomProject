package data;

import org.testng.annotations.DataProvider;
import ui.pages.base.BasePage;

public class HousesData extends BasePage {

    @DataProvider(name = "Тестовые данные для негативных проверок создания дома")
    public Object[][] houseData() {
        return new Object[][] {
                {"",
                        faker.number().digits(7),
                        String.valueOf(faker.number().numberBetween(1, 10))},

                {String.valueOf(faker.number().numberBetween(1, 50)),
                        "",
                        String.valueOf(faker.number().numberBetween(1, 10))},

                {String.valueOf(faker.number().numberBetween(1, 50)),
                        "",
                        ""}
        };
    }
}
