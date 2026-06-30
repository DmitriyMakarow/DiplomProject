package tests.ui.users;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.users.UserTestData;
import ui.dto.users.UserTestDataFactory;
import ui.pages.users.IssueALoanPage;

import static java.lang.String.valueOf;
import static ui.enumUI.Dropdown.USERS;
import static ui.enumUI.RadioLabel.MALE;
import static ui.enumUI.TableType.CREATE_NEW_USER;
import static ui.enumUI.TableType.ISSUE_A_LOAN;
import static ui.pages.base.BasePage.faker;

@Epic("Пользователи")
@Story("Запрос кредита")
public class IssueALoanTest extends BaseTest {
    UserTestData validUserData = UserTestDataFactory.getUserTestDataUI();

    private final String
            userIDValue = valueOf(faker.number().randomDigit()),
            loanSizeValue = valueOf(faker.number().randomDigit());

    @BeforeMethod(onlyForGroups = {"noUser"})
    public void precondition() {
        loginPage
                .authorization();
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, ISSUE_A_LOAN);
        issueALoanPage.verifyOpenIssueALoanPage();
    }

    @Test(testName = "Проверка запроса кредита с валидными данными", groups = {"needUser"})
    @Description("Проверка запроса кредита с валидными данными")
    @Owner("Makarov D.A.")
    public void checkRequestALoanWithPositiveData() {
        final String status = "Status: Successfully pushed, code: 200";

        loginPage.authorization();
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, CREATE_NEW_USER);
        usersPage.addNewUserUI(validUserData);
        baseSteps
                .verifyUnselectedRadio(MALE)
                .selectRadioLabel(MALE)
                .clickPushToApi()
                .verifyGetIdObject("New user ID:");
        String idUser = baseSteps.getNewObjectId();
        System.out.println("Получен ID пользователя: " + idUser);
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, ISSUE_A_LOAN);
        issueALoanPage.verifyOpenIssueALoanPage();
        issueALoanPage
                .fillingFieldText(idUser, "100000")
                .clickRequestALoanbtn()
                .verifySuccessfulMessage(status);
    }

    @Test(testName = "Проверка ввода значений в поля с валидными данными",
            groups = {"noUser"})
    @Description("Проверка ввода значений в поля и изменение значений с помощью степперов")
    @Owner("Makarov D.A.")
    public void checkingInputOfValuesIntoField() {
        final String
                id = "id_send",
                money = "money_send";
        issueALoanPage.fillingFieldText(userIDValue, loanSizeValue);
        baseSteps
                .stepperUp(id, 5, 5.0)
                .stepperDown(id, 2, 2.0)
                .stepperUp(money, 10, 0.10)
                .stepperDown(money, 7, 0.07);
    }

    @Test(testName = "Проверка запроса кредита с негативными данными",
            groups = {"noUser"},
            dataProvider = "Тестовые данные для негативных проверок получения кредита",
            dataProviderClass = IssueALoanPage.class)
    @Description("Проверка запроса кредита с негативными данными")
    @Owner("Makarov D.A.")
    public void checkRequestALoanWithNegativeData(String userID, String amount, String errorMessage) {
        issueALoanPage
                .verifyOpenIssueALoanPage()
                .fillingFieldText(userID, amount)
                .clickRequestALoanbtn();
        issueALoanPage.verifySuccessfulMessage(errorMessage);
    }
}
