package test.pages.houses;

import org.testng.annotations.DataProvider;
import test.pages.base.BasePage;

public class HousesPage extends BasePage {

    @DataProvider(name = "Тестовые данные для негативных проверок создания дома")
    public Object[][] houseData() {
        return new Object[][] {
                // 1. Пустое поле Floors
                {"",
                        faker.number().digits(7),
                        String.valueOf(faker.number().numberBetween(1, 10))},

                // 2. Пустое поле Price
                {String.valueOf(faker.number().numberBetween(1, 50)),
                        "",
                        String.valueOf(faker.number().numberBetween(1, 10))},

                // 3. Пустые Price И Parking (гарантированная ошибка!)
                {String.valueOf(faker.number().numberBetween(1, 50)),
                        "",
                        ""}  // ← Теперь два пустых поля!
        };
    }
}