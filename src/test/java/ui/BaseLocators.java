package ui;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BaseLocators {

    public static SelenideElement getBtnUsers(String nameButton) {
        return $x("//button[contains(text(), '%s')]".formatted(nameButton));
    }
}
