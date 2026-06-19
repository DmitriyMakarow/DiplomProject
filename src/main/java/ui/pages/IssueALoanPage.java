package ui.pages;

import com.codeborne.selenide.Condition;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import ui.base.BasePage;
import ui.wrappers.InputNumber;

import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class IssueALoanPage extends BasePage {

    private final String userIDField = "id_send";
    private final String moneySendField = "money_send";
    private final String requestALoanBtn = "//button[contains(@class, 'tableButton btn btn-primary')]";

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

    public IssueALoanPage fillingFieldsWithValues(Integer userIDValue, Integer loanSize) {
        new InputNumber(userIDField).write(userIDValue);
        new InputNumber(moneySendField).write(loanSize);
        return this;
    }

    public IssueALoanPage fillingFieldsWithNegativeData(String userIDText, String loanSizeText) {
        new InputNumber(userIDField).writeNegativeData(userIDText);
        new InputNumber(moneySendField).writeNegativeData(loanSizeText);
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
}
