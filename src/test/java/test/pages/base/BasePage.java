package test.pages.base;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Condition.*;

public class BasePage {

    /**
     * Ожидает, что элемент присутствует в DOM (существует). Таймаут: 10 секунд.
     */
    public boolean waitExist(SelenideElement selenideElement) {
        return waitExist(selenideElement, 10);
    }

    /**
     * Ожидает, что элемент присутствует в DOM (существует). Таймаут: задаётся параметром.
     */
    public boolean waitExist(SelenideElement selenideElement, int seconds) {
        try {
            return selenideElement.shouldBe(exist, Duration.ofSeconds(seconds)).exists();
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает, что элемент отсутствует в DOM. Таймаут: 10 секунд.
     */
    public boolean waitNoExist(SelenideElement selenideElement) {
        return waitNoExist(selenideElement, 10);
    }

    /**
     * Ожидает, что элемент отсутствует в DOM. Таймаут: задаётся параметром.
     */
    public boolean waitNoExist(SelenideElement selenideElement, int seconds) {
        try {
            return !selenideElement.shouldNotBe(exist, Duration.ofSeconds(seconds)).exists();
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает, что элемент находится в состоянии disabled (заблокирован). Таймаут: 10 секунд.
     */
    public boolean waitDisabled(SelenideElement selenideElement) {
        try {
            selenideElement.shouldBe(disabled, Duration.ofSeconds(10));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает, что элемент видим на странице. Таймаут: 10 секунд.
     */
    public boolean waitVisible(SelenideElement selenideElement) {
        return waitVisible(selenideElement, 10);
    }

    /**
     * Ожидает, что элемент видим на странице. Таймаут: задаётся параметром.
     */
    public boolean waitVisible(SelenideElement selenideElement, int seconds) {
        try {
            return selenideElement.shouldBe(visible, Duration.ofSeconds(seconds)).isDisplayed();
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает, что все переданные элементы видимы. Таймаут: 10 секунд.
     */
    public boolean waitVisible(SelenideElement... selenideElements) {
        try {
            return Arrays.stream(selenideElements).allMatch(el ->
                    el.shouldBe(visible, Duration.ofSeconds(10)).isDisplayed());
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает появления элемента (становится видимым). Таймаут: 5 секунд.
     */
    public boolean waitAppear(SelenideElement selenideElement) {
        return waitAppear(selenideElement, 5);
    }

    /**
     * Ожидает появления элемента (становится видимым). Таймаут: задаётся параметром.
     */
    public boolean waitAppear(SelenideElement selenideElement, int seconds) {
        try {
            return selenideElement.shouldBe(appear, Duration.ofSeconds(seconds)).isDisplayed();
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает исчезновения элемента. Таймаут: 10 секунд.
     */
    public boolean waitDisappear(SelenideElement selenideElement) {
        return waitDisappear(selenideElement, 10);
    }

    /**
     * Ожидает исчезновения элемента. Таймаут: задаётся параметром.
     */
    public boolean waitDisappear(SelenideElement selenideElement, int seconds) {
        try {
            selenideElement.shouldBe(disappear, Duration.ofSeconds(seconds));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Ожидает, что коллекция элементов станет пустой. Таймаут: задаётся параметром.
     */
    public boolean waitDisappear(ElementsCollection elements, int duration) {
        try {
            elements.shouldHave(CollectionCondition.size(0), Duration.ofSeconds(duration));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public boolean isNumeric(List<String> data) {
        return data.stream().allMatch(str -> {
            try {
                new BigDecimal(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public Comparator<String> getStringComparator(boolean ascending) {
        return (a, b) -> {
            if (a.isEmpty() && b.isEmpty()) return 0;
            if (a.isEmpty()) return ascending ? -1 : 1;
            if (b.isEmpty()) return ascending ? 1 : -1;

            boolean aIsNumber = a.matches("-?\\d+(\\.\\d+)?");
            boolean bIsNumber = b.matches("-?\\d+(\\.\\d+)?");

            if (aIsNumber && bIsNumber) {
                try {
                    BigDecimal numA = new BigDecimal(a);
                    BigDecimal numB = new BigDecimal(b);
                    return ascending ? numA.compareTo(numB) : numB.compareTo(numA);
                } catch (NumberFormatException e) {
                    return ascending ? a.compareTo(b) : b.compareTo(a);
                }
            }
            if (aIsNumber) {
                return ascending ? -1 : 1;
            }
            if (bIsNumber) {
                return ascending ? 1 : -1;
            }

            return ascending ? a.compareToIgnoreCase(b) : b.compareToIgnoreCase(a);
        };
    }
}
