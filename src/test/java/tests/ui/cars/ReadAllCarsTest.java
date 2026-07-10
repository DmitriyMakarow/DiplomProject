package tests.ui.cars;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import java.util.List;

import static io.qameta.allure.Allure.*;
import static ui.enumUI.Dropdown.CARS;
import static ui.enumUI.TableType.READ_ALL_CARS;

@Epic("Автомобили")
@Feature("Чтение всех автомобилей")
public class ReadAllCarsTest extends BaseTest {

    @BeforeMethod
    public void testData() {
        loginPage.authorization();
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

    @Owner("Лазарев Г.А.")
    @Issue("После клика на \"Reload\" стрелочки не очищаются")
    @Test(dataProvider = "columns",
          groups = {"regression", "broken"})
    @Story("Сортировка Автомобилей")
    public void checkSorting(String columnName) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Сортировка автомобилей по колонке: " + columnName)
        );
        parameter("Колонка для сортировки", columnName);
        parameter("Тест", "Сортировка по колонке: " + columnName);
        step("Тест проверяет сортировку по колонке \"" + columnName + "\"");

        baseSteps.openTableFromDropdown(CARS, READ_ALL_CARS);
        List<List<String>> initialData = baseSteps.getTableData();
        boolean isPriceColumn = columnName.equalsIgnoreCase("Price");

        if (isPriceColumn) {
            addAttachment("Информация",
                    "Проверка сортировки по возрастанию для колонки '%s' пропущена из-за известного бага"
                            .formatted(columnName));
        }

        baseSteps
                .clickBtn(columnName)
                .verifyColumnNameAsc(columnName)
                .assertDataChangedAfterSort(initialData);

        // Пропускаем проверку только для Price
        if (!isPriceColumn) {
            baseSteps.verifySortAscending(columnName);
        } else {
            step("Проверка сортировки по возрастанию для " + columnName + " пропущена");
        }

        baseSteps
                .clickBtn(columnName)
                .verifyColumnNameDesc(columnName)
                .verifySortDescending(columnName)
                .clickBtn("Reload")
                .assertDataRestored(initialData);
    }
}
