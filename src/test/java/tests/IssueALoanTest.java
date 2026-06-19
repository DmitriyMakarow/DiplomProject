package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;

@Story("Заполнение полей")
public class IssueALoanTest extends BaseTest {

    Integer userID = 99;
    Integer loanSize = 10000;
    String userIDField = "#id_send";
    String loanSizeField = "#money_send";

    @Test(testName = "Проверка ввода значений в поля с валидными данными")
    @Description("Проверка ввода значений в поля и изменение значений с помощью степперов")
    @Owner("Makarov D.A.")
    public void checkingInputOfValuesIntoField() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        issueALoanPage.openPage()
                .verifyOpenIssueALoanPage()
                .fillingFieldsWithValues(userID, loanSize)
                .stepperUp(userIDField)
                .stepperUp(loanSizeField);
        $(userIDField).shouldHave(Condition.value(String.valueOf(userID + 1)));
        $(loanSizeField).shouldHave(Condition.value(String.valueOf(loanSize + 0.01)));
        issueALoanPage
                .stepperDown(userIDField)
                .stepperDown(loanSizeField);
        $(userIDField).shouldHave(Condition.value(String.valueOf(userID)));
        $(loanSizeField).shouldHave(Condition.value(String.valueOf(loanSize)));
    }

    @Test(testName = "Проверка ввода в поля невалидных значений")
    @Description("Проверка ввода в поля невалидных значений")
    @Owner("Makarov D.A.")
    public void checkingFillingFieldsWithNegativeData() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        issueALoanPage.openPage()
                .verifyOpenIssueALoanPage()
                .fillingFieldsWithNegativeData("test1", "test2");
        $(userIDField).shouldNotHave(Condition.value("test1"));
        $(loanSizeField).shouldNotHave(Condition.value("test2"));
    }
}
