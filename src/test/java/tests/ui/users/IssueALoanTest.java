package tests.ui.users;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import static ui.enumUI.Dropdown.USERS;
import static ui.enumUI.TableType.ISSUE_A_LOAN;
import static ui.pages.base.BasePage.faker;

@Epic("Пользователи")
@Story("Запрос кредита")
public class IssueALoanTest extends BaseTest {

    Integer userIDValue = faker.number().randomDigit();
    Integer loanSizeValue = faker.number().randomDigit();
    String userIDField = "#id_send";
    String loanSizeField = "#money_send";

    @Test(testName = "Проверка запроса кредита с валидными данными")
    @Description("Проверка запроса кредита с валидными данными")
    @Owner("Makarov D.A.")
    public void checkRequestALoanWithPositiveData() {
        loginPage
                .authorization();
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, ISSUE_A_LOAN);
        issueALoanPage
                .fillingFieldNumbers(13538, 100000)
                .clickRequestALoanbtn()
                .verifySuccessfulMessage("13538");
    }

    @Test(testName = "Проверка ввода значений в поля с валидными данными")
    @Description("Проверка ввода значений в поля и изменение значений с помощью степперов")
    @Owner("Makarov D.A.")
    public void checkingInputOfValuesIntoField() {
        loginPage
                .authorization();
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, ISSUE_A_LOAN);
        issueALoanPage
                .verifyOpenIssueALoanPage()
                .checkSteppers(userIDValue, loanSizeValue, userIDField, loanSizeField);
    }

    @DataProvider(name = "Тестовые данные для негативных проверок получения кредита")
    public Object[][] loanData() {
        return new Object[][]{
                {"", "", "Status: Incorrect input data"},
                {"13538", "", "Status: Incorrect input data"},
                {"", "10000", "Status: Incorrect input data"},
                {"test", "test", "Status: Incorrect input data"},
                {"test", "10000", "Status: Incorrect input data"},
                {"13538", "test", "Status: Incorrect input data"},
                {"-1", "-1", "Status: Incorrect input data"},
                {"13538", "-1", "Status: Incorrect input data"},
                {"-1", "10000", "Status: Incorrect input data"},
                {"99999", "10000", "Status: AxiosError: Request failed with status code 404"}
        };
    }

    @Test(dataProvider = "Тестовые данные для негативных проверок получения кредита",
            testName = "Проверка запроса кредита с негативными данными")
    @Description("Проверка запроса кредита с негативными данными")
    @Owner("Makarov D.A.")
    public void checkRequestALoanWithNegativeData(String userID, String amount, String errorMessage) {
        loginPage
                .authorization();
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, ISSUE_A_LOAN);
        issueALoanPage
                .verifyOpenIssueALoanPage()
                .fillingFieldText(userID, amount)
                .clickRequestALoanbtn();
        issueALoanPage.verifyErrorMessage(errorMessage);
    }
}
