package test.pages.base;

import enumUI.Dropdown;
import enumUI.TableType;
import io.qameta.allure.Step;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;
import static test.pages.base.BaseLocators.*;

public class BaseSteps extends BasePage {

    @Step("Раскрыть выпадающий список {0}")
    public void showDropdown(Dropdown nameDropdown) {
        getItemDropdown(nameDropdown).click();
        assertTrue(waitVisible(getDropdown(nameDropdown)), "Выпадающий список у наименования \"%s\" не отображается"
                .formatted(nameDropdown));
    }

    @Step("Открыть таблицу {1} из выпадающего списка {0}")
    public BaseSteps openTableFromDropdown(Dropdown nameDropdown, TableType tableName) {
        getDescription(nameDropdown, tableName).click();
        waitForPageLoader(tableName);
        return this;
    }

    @Step("Ожидание загрузки таблицы {tableName}")
    public void waitForPageLoader(TableType tableName) {
        List<String> columns = tableName.getColumns();
        columns.forEach(column -> assertTrue(waitVisible(getBtnColumnName(column)),
                "Колонки с именем: \"%s\" не существует".formatted(column)));
        assertTrue(waitVisible(entryTable.first()), "Таблица не загрузилась - первая строка не видима");
    }

    @Step("Кликнуть на кнопку {buttonName}")
    public BaseSteps clickBtn(String buttonName) {
        List<List<String>> dataBefore = getTableData();
        getBtnColumnName(buttonName).click();
        waitForDataChange(dataBefore);
        return this;
    }

    @Step("Проверка отображения сортировки по убыванию у кнопки {buttonName}")
    public BaseSteps verifyColumnNameDesc(String buttonName) {
        assertTrue(waitVisible(getBtnColumnNameDesc(buttonName)), "Должна быть стрелка вниз");
        return this;
    }

    @Step("Проверка отображения сортировки по возрастанию у кнопки {buttonName}")
    public BaseSteps verifyColumnNameAsc(String buttonName) {
        assertTrue(waitVisible(getBtnColumnNameAsc(buttonName)), "Должна быть стрелка вверх");
        return this;
    }

    @Step("Проверка, что сортировка не активна (нет стрелок)")
    public BaseSteps verifyNoSortActive() {
        assertTrue(arrowAsc.isEmpty(), "Найдена стрелка вверх - сортировка по возрастанию активна");
        assertTrue(arrowDesc.isEmpty(), "Найдена стрелка вниз - сортировка по убыванию активна");
        return this;
    }

    @Step("Получение данных таблицы")
    public List<List<String>> getTableData() {
        return entryTable
                .stream()
                .limit(10)
                .map(row -> row.$$("td").texts())
                .toList();
    }

    @Step("Сравнение двух состояний таблицы")
    public boolean isTableDataEquals(List<List<String>> expectedData) {
        List<List<String>> actualData = getTableData();
        return actualData.equals(expectedData);
    }

    @Step("Проверка, что данные восстановились после Reload")
    public void assertDataRestored(List<List<String>> expectedData) {
        assertTrue(isTableDataEquals(expectedData), "Данные не восстановились после Reload");
//        verifyNoSortActive(); TODO: Расскоментировать после исправления бага
    }

    @Step("Проверка, что данные изменились после сортировки")
    public BaseSteps assertDataChangedAfterSort(List<List<String>> initialData) {
        assertFalse(isTableDataEquals(initialData),
                "Данные не изменились после сортировки");
        return this;
    }

    @Step("Проверка сортировки колонки {columnName} по возрастанию")
    public BaseSteps verifySortAscending(String columnName) {
        List<String> headers = headerTable
                .texts()
                .stream()
                .map(text -> text.replace(":", "").trim())
                .map(text -> text.replace("\u00A0", " "))
                .map(text -> text.replaceAll("\\s+", " "))
                .toList();

        int columnIndex = headers.indexOf(columnName);
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Колонка \"%s\" не найдена".formatted(columnName));
        }

        List<String> actualData = entryTable
                .stream()
                .limit(10)
                .map(row -> row.$$("td").get(columnIndex).getText().trim())
                .toList();

        List<String> sortedData = new ArrayList<>(actualData);

        if (isNumeric(actualData)) {
            sortedData.sort((a, b) -> {
                try {
                    BigDecimal numA = new BigDecimal(a);
                    BigDecimal numB = new BigDecimal(b);
                    return numA.compareTo(numB);
                } catch (NumberFormatException e) {
                    return a.compareTo(b);
                }
            });
        } else {
            sortedData.sort(getStringComparator(true));
        }

        assertEquals(sortedData, actualData,
                "Колонка \"%s\" не отсортирована по возрастанию".formatted(columnName));
        return this;
    }

    @Step("Проверка сортировки колонки {columnName} по убыванию")
    public BaseSteps verifySortDescending(String columnName) {
        List<String> headers = headerTable
                .texts()
                .stream()
                .map(text -> text.replace(":", "").trim())
                .map(text -> text.replace("\u00A0", " "))
                .map(text -> text.replaceAll("\\s+", " "))
                .toList();

        int columnIndex = headers.indexOf(columnName);
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Колонка \"%s\" не найдена".formatted(columnName));
        }

        List<String> actualData = entryTable
                .stream()
                .limit(10)
                .map(row -> row.$$("td").get(columnIndex).getText().trim())
                .toList();

        List<String> sortedData = new ArrayList<>(actualData);

        if (isNumeric(actualData)) {
            sortedData.sort((a, b) -> {
                try {
                    BigDecimal numA = new BigDecimal(a);
                    BigDecimal numB = new BigDecimal(b);
                    return numB.compareTo(numA);
                } catch (NumberFormatException e) {
                    return b.compareTo(a);
                }
            });
        } else {
            sortedData.sort(getStringComparator(false));
        }

        assertEquals(sortedData, actualData,
                "Колонка \"%s\" не отсортирована по убыванию".formatted(columnName));
        return this;
    }

    private void waitForDataChange(List<List<String>> dataBefore) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5 * 1000L) {
            List<List<String>> dataAfter = getTableData();
            if (!dataAfter.equals(dataBefore)) {
                return;
            }
        }
    }
}
