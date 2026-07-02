package tests.ui.cars;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import java.util.List;

import static ui.enumUI.Dropdown.CARS;
import static ui.enumUI.TableType.READ_ALL_CARS;

@Epic("Автомобили")
@Feature("Чтение всех автомобилей")
@Owner("Lazarev G.A")
public class ReadAllCarsTest extends BaseTest {

    @BeforeMethod
    public void testData() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        baseSteps.showDropdown(CARS);
    }

    @DataProvider(name = "columns")
    public Object[][] columns() {
        return new Object[][]{
                {"ID"},
                {"Engine"},
                {"Mark"},
                {"Model"},
                {"Price"}
        };
    }

    @Test(dataProvider = "columns",
          groups = {"regression", "broken"})
    @Story("Сортировка Автомобилей")
    @Description("Тест проверяет сортировку по колонке \"{columnName}\"")
    public void checkSorting(String columnName) {
        Allure.parameter("Колонка", columnName);
        baseSteps.openTableFromDropdown(CARS, READ_ALL_CARS);
        List<List<String>> initialData = baseSteps.getTableData();
        baseSteps
                .clickBtn(columnName)
                .verifyColumnNameAsc(columnName)
                .assertDataChangedAfterSort(initialData)
                .verifySortAscending(columnName)
                .clickBtn(columnName)
                .verifyColumnNameDesc(columnName)
                .verifySortDescending(columnName)
                .clickBtn("Reload")
                .assertDataRestored(initialData);
    }
}
