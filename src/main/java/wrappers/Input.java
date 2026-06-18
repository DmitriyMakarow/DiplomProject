package wrappers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$x;

public class Input {

    String label;

    public Input(String label) {
        this.label = label;
    }

    @Step("Заполнение поля {label} значением {text}")
    public void fillField(String text) {
        $x("//input[contains(@id, '%s')]".formatted(label)).setValue(text);
    }

}
