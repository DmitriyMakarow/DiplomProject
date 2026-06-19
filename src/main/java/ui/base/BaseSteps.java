package ui.base;

import io.qameta.allure.Step;

import static org.testng.Assert.assertTrue;
import static ui.base.BaseLocators.*;

public class BaseSteps extends BasePage {

    @Step("Раскрыть выпадающий список {0}")
    public BaseSteps showDropdown(String nameDropdown) {
        getItemDropdown(nameDropdown).click();
        assertTrue(waitVisible(getDropdown(nameDropdown)), "Выпадающий список у наименования \"%s\" не отображается"
                .formatted(nameDropdown));
        return this;
    }

    @Step("Открыть таблицу {1} из выпадающего списка {0}")
    public BaseSteps openTableFromDropdown(String nameDropdown, String tableName) {
        getDescription(nameDropdown, tableName).click();
        return this;
    }
}
