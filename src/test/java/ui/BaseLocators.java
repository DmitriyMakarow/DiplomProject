package ui;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BaseLocators {

    public static SelenideElement
    rootNavbar = $x("//div[@class='me-auto navbar-nav']");

    public static SelenideElement getNameDropdown(String nameDropdown) {
        return rootNavbar.$x(".//a[text()='%s']".formatted(nameDropdown));
    }

    public static SelenideElement getDropdown(String nameDropdown) {
        return getNameDropdown(nameDropdown).$x("./parent::div[contains(@class, 'dropdown')]");
    }

    public static SelenideElement getDescription(String nameDropdown, String tableName) {
        return getDropdown(nameDropdown).$x(".//a[text()='%s']".formatted(tableName));
    }
}
