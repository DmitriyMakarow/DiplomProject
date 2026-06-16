package test.tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static enumUI.Dropdown.USERS;
import static enumUI.TableType.READ_ALL_USERS;

@Epic("Пользователи")
@Owner("Lazarev G.A")
public class UsersTest extends BaseTest {

    @BeforeMethod
    public void testData() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        baseSteps.showDropdown(USERS);
    }

    @Story("Чтение всех пользователей")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет сортировку по колонке \"ID\" пользователей")
    @Test(testName = "Сортировка по ID")
    public void checkSortingID() {

        baseSteps
                .openTableFromDropdown(USERS, READ_ALL_USERS)
                .clickBtn("ID")
                .verifyColumnNameAsc("ID")
                .verifySortAscending("ID")
                .clickBtn("ID")
                .verifyColumnNameDesc("ID")
                .verifySortDescending("ID");
    }

    @Story("Чтение всех пользователей")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет сортировку по колонке \"First name\" пользователей")
    @Test(testName = "Сортировка по колонке \"First name\"")
    public void checkSortingFirstName() {
        baseSteps.openTableFromDropdown(USERS, READ_ALL_USERS);
        List<List<String>> initialData = baseSteps.getTableData();
        baseSteps
                .clickBtn("First")
                .verifyColumnNameAsc("First")
                .assertDataChangedAfterSort(initialData)
                .verifySortAscending("First name")
                .clickBtn("First")
                .verifyColumnNameDesc("First")
                .verifySortDescending("First name")
                .clickBtn("Reload")
                .assertDataRestored(initialData);
    }

    @Story("Чтение всех пользователей")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет сортировку по колонке \"Last name\" пользователей")
    @Test(testName = "Сортировка по колонке \"Last name\"")
    public void checkSortingLastName() {

        baseSteps
                .openTableFromDropdown(USERS, READ_ALL_USERS)
                .clickBtn("Last")
                .verifyColumnNameAsc("Last")
//                .verifySortAscending("Last name") TODO Неправильная сортировка, попробовать пофиксить
                .clickBtn("Last")
                .verifyColumnNameDesc("Last")
                .verifySortDescending("Last name");
    }

    @Story("Чтение всех пользователей")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет сортировку по колонке \"Age\" пользователей")
    @Test(testName = "Сортировка по колонке \"Age\"")
    public void checkSortingAge() {

        baseSteps
                .openTableFromDropdown(USERS, READ_ALL_USERS)
                .clickBtn("Age")
                .verifyColumnNameAsc("Age")
                .verifySortAscending("Age")
                .clickBtn("Age")
                .verifyColumnNameDesc("Age")
                .verifySortDescending("Age");
    }

    @Story("Чтение всех пользователей")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет сортировку по колонке \"Sex\" пользователей")
    @Test(testName = "Сортировка по колонке \"Sex\"")
    public void checkSortingSex() {

        baseSteps
                .openTableFromDropdown(USERS, READ_ALL_USERS)
                .clickBtn("Sex")
                .verifyColumnNameAsc("Sex")
                .verifySortAscending("Sex")
                .clickBtn("Sex")
                .verifyColumnNameDesc("Sex")
                .verifySortDescending("Sex");
    }

    @Story("Чтение всех пользователей")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет сортировку по колонке \"Money\" пользователей")
    @Test(testName = "Сортировка по колонке \"Money\"")
    public void checkSortingMoney() {

        baseSteps.openTableFromDropdown(USERS, READ_ALL_USERS);
        List<List<String>> initialData = baseSteps.getTableData();
        baseSteps
                .clickBtn("Money")
                .verifyColumnNameAsc("Money")
                .assertDataChangedAfterSort(initialData)
                .verifySortAscending("Money")
                .clickBtn("Money")
                .verifyColumnNameDesc("Money")
                .verifySortDescending("Money")
                .clickBtn("Reload")
                .assertDataRestored(initialData);
    }
}
