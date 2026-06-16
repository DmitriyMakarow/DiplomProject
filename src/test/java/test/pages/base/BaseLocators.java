package test.pages.base;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import enumUI.Dropdown;
import enumUI.TableType;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class BaseLocators {

    public static SelenideElement
            rootNavbar = $x("//div[@class='me-auto navbar-nav']");

    public static ElementsCollection
            headerTable = $$x("//table//thead//th"),
            entryTable = $$x("//table//tbody//tr"),
            arrowAsc = $$x("//div[@aria-label='sort']//button[contains(., '↑')]"),
            arrowDesc = $$x("//div[@aria-label='sort']//button[contains(., '↓')]");

    public static SelenideElement getItemDropdown(Dropdown itemDropdown) {
        return rootNavbar.$x(".//a[text()='%s']".formatted(itemDropdown));
    }

    public static SelenideElement getDropdown(Dropdown nameDropdown) {
        return getItemDropdown(nameDropdown).$x("./parent::div[contains(@class, 'dropdown')]");
    }

    public static SelenideElement getDescription(Dropdown nameDropdown, TableType tableName) {
        return getDropdown(nameDropdown).$x(".//a[text()='%s']".formatted(tableName));
    }

    public static SelenideElement getBtnColumnName(String buttonName) {
        return $x("//div[@aria-label='sort']//button[contains(., '%s')]"
                .formatted(buttonName));
    }

    public static SelenideElement getBtnColumnNameDesc(String buttonName) {
        return $x("//div[@aria-label='sort']//button[contains(., '↓') and contains(., '%s')]"
                .formatted(buttonName));
    }

    public static SelenideElement getBtnColumnNameAsc(String buttonName) {
        return $x("//div[@aria-label='sort']//button[contains(., '↑') and contains(., '%s')]"
                .formatted(buttonName));
    }
}
