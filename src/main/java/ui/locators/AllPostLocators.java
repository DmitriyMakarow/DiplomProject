package ui.locators;

import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selenide.$x;

@UtilityClass
public class AllPostLocators {

    // Получение конкретной формы по индексу (1-6)
    public static SelenideElement getForm(int formIndex) {
        return $x(String.format("(//div[./hr and ./table])[%d]", formIndex));
    }

    // Получение кнопки PUSH TO API по индексу формы (1-6)
    public static SelenideElement getBtnPushToApi(int formIndex) {
        return getForm(formIndex).$x(".//button[contains(@class, 'tableButton')]");
    }

    // Получение кнопки статуса по индексу формы (1-6)
    public static SelenideElement getBtnStatus(int formIndex) {
        return getForm(formIndex).$x(".//button[contains(@class, 'status')]");
    }

    // Получение кнопки нового ID по индексу формы (1-6)
    public static SelenideElement getBtnNewId(int formIndex) {
        return getForm(formIndex).$x(".//button[contains(@class, 'newId')]");
    }
}