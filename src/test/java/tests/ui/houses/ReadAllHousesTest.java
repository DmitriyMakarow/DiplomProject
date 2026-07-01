package tests.ui.houses;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import static ui.enumUI.Dropdown.HOUSES;
import static ui.enumUI.TableType.READ_ALL_HOUSES;

@Epic("Дома")
@Feature("Чтение всех домов")
@Owner("khvadina a.")
public class ReadAllHousesTest extends BaseTest {

    @BeforeMethod
    public void openPageAllHouses() {
        loginPage.authorization();
    }

    @Test(testName = "Проверка отображения таблицы домов")
    @Story("Отображение списка домов")
    @Description("Тест проверяет, что таблица домов корректно отображается")
    public void checkReadAllHousesTable() {
        baseSteps.openTableFromDropdown(HOUSES, READ_ALL_HOUSES);
    }
}