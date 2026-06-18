package test.pages.base;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BaseLocators {

    public static SelenideElement
            rootNavbar = $x("//div[@class='me-auto navbar-nav']"),
            btnPushToApi = $x("//button[contains(text(), 'PUSH')]"),
            btnStatus = $x("//button[contains(@class, 'status')]"),
            btnNewIdObject = $x("//button[contains(@class, 'newId')]");

    public static SelenideElement getItemDropdown(String itemDropdown) {
        return rootNavbar.$x(".//a[text()='%s']".formatted(itemDropdown));
    }

    public static SelenideElement getDropdown(String nameDropdown) {
        return getItemDropdown(nameDropdown).$x("./parent::div[contains(@class, 'dropdown')]");
    }

    public static SelenideElement getDescription(String nameDropdown, String tableName) {
        return getDropdown(nameDropdown).$x(".//a[text()='%s']".formatted(tableName));
    }

    public static SelenideElement getBtn(String nameButton) {
        return $x("//button[contains(text(), '%s')]".formatted(nameButton));
    }
}
