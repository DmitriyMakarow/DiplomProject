package ui.wrappers;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class Input {

    String label;
    Integer number;

    public Input(String label) {
        this.label = label;
    }

    @Step("Заполнение поля {label} значением {text}")
    public void fillField(String text) {
        log.info("Writing '{}' in to '{}'", text, label);
        $x("//input[contains(@id, '%s')]".formatted(label)).setValue(text);
    }

    @Step("Заполнение поля {label} значением {number}")
    public void fillFieldNumber(Integer number) {
        log.info("Writing '{}' in to '{}'", number, label);
        $(String.format("#%s", label)).setValue(String.valueOf(number));
    }
}
