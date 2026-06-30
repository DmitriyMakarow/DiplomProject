package ui.pages.houses;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.DataProvider;
import ui.pages.base.BasePage;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

public class HousesPage extends BasePage {

    private final SelenideElement readOneByIdButton = $x("//a[text()='Read one by ID']");
    private final SelenideElement houseInput = $x("//input[@id='house_input']");
    private final SelenideElement readButton = $x("//button[text()='Read']");
    private final SelenideElement statusButton = $x("//button[contains(text(), 'Status: 200')]");
    private final SelenideElement table = $x("//table");

    public void clickReadOneById() {
        readOneByIdButton.click();
    }

    public void enterHouseId(String id) {
        houseInput.setValue(id);
    }

    public void clickReadButton() {
        readButton.click();
    }

    public void waitForTableVisible() {
        assertTrue(waitVisible(table), "Таблица не отобразилась");
    }

    public void verifyStatusOk() {
        assertTrue(waitVisible(statusButton), "Статус 200 не отображён");
    }

    public void verifyHouseIdInTable(Integer houseId) {
        $x("//table//td[contains(text(), '" + houseId + "')]").shouldBe(Condition.visible);
    }

    @DataProvider(name = "Тестовые данные для негативных проверок создания дома")
    public Object[][] houseData() {
        return new Object[][] {
                {"",
                        faker.number().digits(7),
                        String.valueOf(faker.number().numberBetween(1, 10))},

                {String.valueOf(faker.number().numberBetween(1, 50)),
                        "",
                        String.valueOf(faker.number().numberBetween(1, 10))},

                {String.valueOf(faker.number().numberBetween(1, 50)),
                        "",
                        ""}
        };
    }
}