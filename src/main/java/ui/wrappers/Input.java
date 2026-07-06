package ui.wrappers;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.steps.BaseSteps;

import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class Input {
    String label;

    public Input(String label) {
        this.label = label;
    }

    @Step("Заполнение поля {label} значением {text}")
    public void fillField(String text) {
        log.info("Writing '{}' in to '{}'", text, label);
        $x("//input[@id='%s']".formatted(label)).setValue(text);
    }

    @Step("Заполнение поля {label} в таблице {formIndex} значением {text}")
    public void fillField(String text, int formIndex) {
        log.info("Writing '{}' in to '{}' таблицы '{}'", text, label, formIndex);
        $x("(//div[./hr and ./table])[%d]//input[@id='%s']".formatted(formIndex, label)).setValue(text);
    }
}