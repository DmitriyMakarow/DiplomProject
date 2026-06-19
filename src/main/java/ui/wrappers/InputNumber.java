package ui.wrappers;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class InputNumber {

    String elementID;

    public InputNumber(String elementID) {
        this.elementID = elementID;
    }

    public void write(Integer number) {
        log.info("Writing '{}' in to '{}'", number, elementID);
        $(String.format("#%s", elementID)).setValue(String.valueOf(number));
    }

    public void writeNegativeData(String text) {
        log.info("Writing '{}' in to '{}'", text, elementID);
        $(String.format("#%s", elementID)).setValue(String.valueOf(text));
    }
}
