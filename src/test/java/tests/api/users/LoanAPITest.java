package tests.api.users;

import api.models.users.LoanRequest;
import api.models.users.LoanResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;

import static api.adapters.LoanAdapter.RequestALoan;
import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;
import static org.testng.Assert.assertEquals;
import static ui.pages.base.BasePage.faker;

public class LoanAPITest {

    LoanRequest rq = LoanRequest.builder()
            .id(faker.number().randomDigit())
            .amount(faker.number().randomDigit())
            .build();

    @Test(testName = "Проверка запроса кредита с валидными данными", groups = {"regression"})
    @Description("Проверка запроса кредита с валидными данными")
    @Owner("Makarov D.A.")
    public void checkRequestALoanWithPositiveDataAPI() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка запроса кредита с валидными данными")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Запрос кредита через API");
        parameter("Ожидаемый результат", "Кредит успешно выдан");

        LoanResponse rs = RequestALoan("15538", "100000");
        assertEquals(rs.id, 15538);
    }
}
