package data;

import org.testng.annotations.DataProvider;
import ui.pages.base.BasePage;

public class IssueALoanData extends BasePage {

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
