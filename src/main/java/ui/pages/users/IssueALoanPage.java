package ui.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class IssueALoanPage extends BasePage {

    private final String userID = "id_send";
    private final String loanSize = "money_send";
    private final String requestALoanBtn = "//button[contains(@class, 'tableButton btn btn-primary')]";
    private final String message = "//button[contains(@class, 'status btn btn-secondary')]";

    public IssueALoanPage openPage() {
        log.info("Issue a loan page opening");
        open("/update/Issue_A_Loan");
        return this;
    }

    public IssueALoanPage verifyOpenIssueALoanPage() {
        log.info("Open Issue a loan page verifying");
        $x(requestALoanBtn).shouldBe(Condition.visible);
        return this;
    }

    public IssueALoanPage fillingFieldNumbers(Integer userIDValue, Integer loanSizeValue) {
        new Input(userID).fillFieldNumber(userIDValue);
        new Input(loanSize).fillFieldNumber(loanSizeValue);
        return this;
    }

    public IssueALoanPage fillingFieldText(String userIDText, String loanSizeText) {
        new Input(userID).fillField(userIDText);
        new Input(loanSize).fillField(loanSizeText);
        return this;
    }

    public IssueALoanPage stepperUp(String elementID) {
        log.info("Increase value in '{}'", elementID);
        $(String.format("%s", elementID)).click();
        $(String.format("%s", elementID)).sendKeys(Keys.ARROW_UP);
        return this;
    }

    public IssueALoanPage stepperDown(String elementID) {
        log.info("Decreasing value in '{}'", elementID);
        $(String.format("%s", elementID)).click();
        $(String.format("%s", elementID)).sendKeys(Keys.ARROW_DOWN);
        return this;
    }

    public IssueALoanPage clickRequestALoanbtn() {
        log.info("Clicking request a loan button");
        $x(requestALoanBtn).shouldBe(visible).click();
        return this;
    }

    public IssueALoanPage verifySuccessfulMessage(String userID) {
        log.info("Waiting for and verifying successful message");
        String expectedMessage = "Status: Successfully pushed, code: 200";
        SelenideElement successMessage = $(withText(expectedMessage));
        boolean isSuccessMessageVisible = super.waitVisible(successMessage, 120);
        Assert.assertTrue(isSuccessMessageVisible,
                "Ошибка: Сообщение '%s' не появилось за отведенное время.".formatted(expectedMessage));
        return this;
    }


    public IssueALoanPage verifyErrorMessage(String expectedErrorMessage) {
        log.info("Waiting for and verifying error message");
        $x(message).shouldHave(text(expectedErrorMessage));
        return this;
    }

    public IssueALoanPage checkSteppers(Integer userIDValue, Integer loanSizeValue, String userIDField,
                                        String loanSizeField) {
        fillingFieldNumbers(userIDValue, loanSizeValue);
        stepperUp(userIDField);
        stepperUp(loanSizeField);
        $(userIDField).shouldHave(Condition.value(String.valueOf(userIDValue + 1)));
        $(loanSizeField).shouldHave(Condition.value(String.valueOf(loanSizeValue + 0.01)));
        stepperDown(userIDField);
        stepperDown(loanSizeField);
        $(userIDField).shouldHave(Condition.value(String.valueOf(userIDValue)));
        $(loanSizeField).shouldHave(Condition.value(String.valueOf(loanSizeValue)));
        return this;
    }
}
