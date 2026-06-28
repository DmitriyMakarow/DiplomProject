package ui.pages.users;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;
import static ui.locators.BaseLocators.btnStatus;

@Log4j2
public class IssueALoanPage extends BasePage {

    private final SelenideElement requestALoanBtn = $x("//button[contains(@Class, " +
            "'tableButton btn btn-primary')]");

    @Step("Проверка отображения страницы \"Issue a loan\"")
    public IssueALoanPage verifyOpenIssueALoanPage() {
        log.info("Open Issue a loan page verifying");
        assertTrue(waitVisible(requestALoanBtn, 10),
                "Страница \"Issue a loan\" не загрузилась за отведенное время");
        return this;
    }

    @Step("Заполнение полей \"User ID\" значением \"{userIDText}\" и \"Размер кредита\" значением \"{loanSizeText}\"")
    public IssueALoanPage fillingFieldText(String userIDText, String loanSizeText) {
        new Input("id_send").fillField(userIDText);
        new Input("money_send").fillField(loanSizeText);
        return this;
    }

    @Step("Нажатие на кнопку \"Запросить кредит\"")
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
}
