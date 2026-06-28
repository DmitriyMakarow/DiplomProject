package ui.pages.users;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ui.locators.BaseLocators.btnStatus;
import static ui.locators.BaseLocators.getInputField;

@Log4j2
public class IssueALoanPage extends BasePage {

    private final String userID = "id_send";
    private final String loanSizeID = "money_send";
    private final SelenideElement requestALoanBtn = $x("//button[contains(@Class, " +
            "'tableButton btn btn-primary')]");

    @Step("Open Issue a loan page verifying")
    public IssueALoanPage verifyOpenIssueALoanPage() {
        log.info("Open Issue a loan page verifying");
        assertTrue(waitVisible(requestALoanBtn, 5), "Страница не загрузилась за отведенное время");
        return this;
    }

    @Step("Заполнение полей 'User ID' значением '{userIDText}' и 'Размер кредита' значением '{loanSizeText}'")
    public IssueALoanPage fillingFieldText(String userIDText, String loanSizeText) {
        new Input(userID).fillField(userIDText);
        new Input(loanSizeID).fillField(loanSizeText);
        return this;
    }

    @Step("Увеличение значения в поле c id: '{elementID}'")
    public IssueALoanPage stepperUp(String elementID) {
        log.info("Increase value in '{}'", elementID);
        getInputField(elementID).click();
        getInputField(elementID).sendKeys(Keys.ARROW_UP);
        return this;
    }

    @Step("Уменьшение значения в поле с id: '{elementID}'")
    public IssueALoanPage stepperDown(String elementID) {
        log.info("Decreasing value in '{}'", elementID);
        getInputField(elementID).click();
        getInputField(elementID).sendKeys(Keys.ARROW_DOWN);
        return this;
    }

    @Step("Нажатие на кнопку 'Запросить кредит'")
    public IssueALoanPage clickRequestALoanbtn() {
        log.info("Clicking request a loan button");
        verifyOpenIssueALoanPage();
        requestALoanBtn.click();
        return this;
    }

    @Step("Проверка сообщения о статусе запроса")
    public IssueALoanPage verifySuccessfulMessage(String expectedStatus) {
        log.info("Waiting for and verifying successful message");
        assertTrue(waitVisible(btnStatus), "Кнопка статуса не отображается");
        assertTrue(waitEqualsTextWithTimeout(expectedStatus, btnStatus, 120),
                """
                        Сообщение о статусе не соответствует
                        Ожидаемый результат: %s
                        Фактический результат: %s""".formatted(expectedStatus, btnStatus.getText()));
        return this;
    }

    @Step("Проверка увеличения значения в поле {elementID} на {increment}")
    private void checkStepperUp(String elementID, BigDecimal initialValue, BigDecimal increment) {
        log.info("Initial value of '{}' is {}", elementID, initialValue);
        BigDecimal expectedValue = initialValue.add(increment);
        stepperUp(elementID);
        assertInputValueIs(elementID, expectedValue);
        log.info("Verified that after stepperUp the value became {}", expectedValue);
    }

    @Step("Проверка уменьшения значения в поле {elementID} на {decrement}")
    private void checkStepperDown(String elementID, BigDecimal initialValue, BigDecimal decrement) {
        log.info("Initial value of '{}' is {}", elementID, initialValue);
        BigDecimal expectedValue = initialValue.subtract(decrement);
        stepperDown(elementID);
        assertInputValueIs(elementID, expectedValue);
        log.info("Verified that after stepperDown the value became {}", expectedValue);
    }

    @Step("Комплексная проверка параметров в полях User ID и Размер кредита")
    public IssueALoanPage checkSteppers(
            String userIDField,
            String userIDValue,
            String loanSizeField,
            String loanSizeValue,
            BigDecimal userIDIncrement,
            BigDecimal loanSizeIncrement) {
        log.info("Starting comprehensive check for UserID and LoanSize steppers");
        fillingFieldText(userIDValue, loanSizeValue);
        checkStepperUp(userIDField, new BigDecimal(userIDValue), userIDIncrement);
        checkStepperDown(userIDField, new BigDecimal(userIDValue).add(userIDIncrement), userIDIncrement);
        checkStepperUp(loanSizeField, new BigDecimal(loanSizeValue), loanSizeIncrement);
        checkStepperDown(loanSizeField, new BigDecimal(loanSizeValue).add(loanSizeIncrement), loanSizeIncrement);
        return this;
    }

    @Step("Извлечение строки из UI, преобразование её в число и сравнение с ожидаемым числом.")
    private void assertInputValueIs(String elementID, BigDecimal expectedValue) {
        String actualValueAsString = $("#%s".formatted(elementID)).getValue();
        BigDecimal actualValueAsNumber = new BigDecimal(actualValueAsString.isEmpty() ? "0" : actualValueAsString);
        assertEquals(
                Double.valueOf(actualValueAsNumber.doubleValue()),
                Double.valueOf(expectedValue.doubleValue()),
                "Значения в поле '" + elementID + "' не совпадают"
        );
    }
}
