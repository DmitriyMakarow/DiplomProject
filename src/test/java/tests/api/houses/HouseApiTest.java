package tests.api.houses;

import api.models.houses.HouseRequest;
import api.models.houses.HouseResponse;
import api.models.users.UserRequest;
import api.models.users.UserResponse;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.users.UserTestDataFactory;

import java.util.Collections;
import java.util.List;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;
import static org.testng.Assert.*;
import static ui.pages.base.BasePage.faker;

@Epic("Дома. API")
@Feature("Создание и редактирование дома")
public class HouseApiTest extends BaseTest {

    private final int floorCount = faker.number().numberBetween(1, 50);
    private final double price = faker.number().numberBetween(100000, 10000000);
    private final int parkingCount = faker.number().numberBetween(1, 10);

    private Integer createdHouseId;
    private Integer createdUserId;

    HouseRequest.ParkingPlace parkingPlace = HouseRequest.ParkingPlace.builder()
            .isWarm(true)
            .isCovered(true)
            .placesCount(parkingCount)
            .build();

    HouseRequest house = HouseRequest.builder()
            .floorCount(floorCount)
            .price(price)
            .parkingPlaces(Collections.singletonList(parkingPlace))
            .lodgers(Collections.emptyList())
            .build();

    @AfterMethod
    public void tearDown() {
        if (createdHouseId != null) {
            houseAdapter.deleteApiHouse(createdHouseId);
            createdHouseId = null;
        }
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка создания дома с валидными параметрами", groups = {"regression"})
    void checkCreateHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка создания дома с валидными параметрами")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Создание дома");
        parameter("Ожидаемый результат", "Дом успешно создан");

        HouseResponse houseResponse = houseAdapter.createApiHouse(house);
        createdHouseId = houseResponse.getId();

        assertEquals(houseResponse.getFloorCount(), floorCount, "Количество этажей не совпадает");
        assertEquals(houseResponse.getPrice(), price, "Цена не совпадает");
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка редактирования дома валидными параметрами", groups = {"regression"})
    void checkEditHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка редактирования дома валидными параметрами")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Редактирование дома");
        parameter("Ожидаемый результат", "Дом успешно отредактирован");

        int newFloorCount = faker.number().numberBetween(1, 50);
        double newPrice = faker.number().numberBetween(100000, 10000000);
        int newParkingCount = faker.number().numberBetween(1, 10);

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        createdHouseId = createdHouse.getId();

        HouseRequest.ParkingPlace newParkingPlace = HouseRequest.ParkingPlace.builder()
                .isWarm(false)
                .isCovered(true)
                .placesCount(newParkingCount)
                .build();

        HouseRequest updatedHouse = HouseRequest.builder()
                .floorCount(newFloorCount)
                .price(newPrice)
                .parkingPlaces(Collections.singletonList(newParkingPlace))
                .lodgers(Collections.emptyList())
                .build();

        HouseResponse updatedResponse = houseAdapter.putApiHouse(createdHouseId, updatedHouse);

        assertEquals(updatedResponse.getFloorCount(), newFloorCount,
                "Количество этажей не совпадает после обновления");
        assertEquals(updatedResponse.getPrice(), newPrice, "Цена не совпадает после обновления");
        assertEquals(updatedResponse.getParkingPlaces().get(0).getPlacesCount(), newParkingCount,
                "Количество парковочных мест не совпадает после обновления");
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка получения списка домов", groups = {"regression"})
    void checkGetHouses() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка получения списка домов")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Получение списка домов");
        parameter("Ожидаемый результат", "Список домов успешно получен");

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        createdHouseId = createdHouse.getId();

        List<HouseResponse> housesResponse = houseAdapter.getHouses();

        HouseResponse foundHouse = housesResponse.stream()
                .filter(h -> h.getId() == createdHouseId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Созданный дом не найден в списке"));

        assertEquals(foundHouse.getFloorCount(), floorCount, "Количество этажей не совпадает");
        assertEquals(foundHouse.getPrice(), price, "Цена не совпадает");
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка получения дома по ID", groups = {"regression"})
    void checkGetHouseById() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка получения дома по ID")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Получение дома по ID");
        parameter("Ожидаемый результат", "Дом успешно найден");

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        createdHouseId = createdHouse.getId();

        HouseResponse houseResponse = houseAdapter.getHouse(createdHouseId);

        assertEquals(houseResponse.getId(), createdHouseId, "ID дома не совпадает");
        assertEquals(houseResponse.getFloorCount(), floorCount, "Количество этажей не совпадает");
        assertEquals(houseResponse.getPrice(), price, "Цена не совпадает");
        assertEquals(houseResponse.getParkingPlaces().get(0).getPlacesCount(), parkingCount,
                "Количество парковочных мест не совпадает");
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка удаления дома", groups = {"regression"})
    void checkDeleteHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка удаления дома")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Удаление дома");
        parameter("Ожидаемый результат", "Дом успешно удален");

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        Integer houseId = createdHouse.getId();
        createdHouseId = houseId;

        houseAdapter.deleteApiHouse(houseId);
        createdHouseId = null;

        List<HouseResponse> housesAfterDelete = houseAdapter.getHouses();
        boolean houseExists = housesAfterDelete.stream()
                .anyMatch(h -> h.getId() == houseId);

        assertFalse(houseExists, "Дом должен быть удалён из списка");
    }

    @Issue("/house/{houseId}/settle/{userId} возвращает 406 при запросе с Content-Type header")
    @Owner("Кирсанов А.П.")
    @Test(testName = "Проверка заселения пользователя в дом", groups = {"regression"})
    @Description("Проверка успешного заселения пользователя в дом")
    void checkSettleUserToHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка заселения пользователя в дом")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Заселение пользователя в дом");
        parameter("Ожидаемый результат", "Пользователь успешно заселен");

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        createdHouseId = createdHouse.getId();

        UserRequest userRequest = UserTestDataFactory.postUserTestDataApi();
        UserResponse userResponse = userAdapter.createUser(userRequest);
        createdUserId = userResponse.getId();

        usersSteps
                .validateUserId(createdUserId)
                .validateUserData(userResponse, userRequest);

        HouseResponse updatedHouse = houseAdapter.settleUser(createdHouseId, createdUserId);

        assertTrue(updatedHouse.getLodgers().stream()
                        .anyMatch(l -> l.getId() == createdUserId),
                "Пользователь %s не найден в списке lodgers после заселения".formatted(createdUserId));
    }

    @Issue("/house/{houseId}/evict/{userId} возвращает 406 при запросе с Content-Type header")
    @Owner("Кирсанов А.П.")
    @Test(testName = "Проверка выселения пользователя из дома", groups = {"regression"})
    @Description("Проверка успешного выселения пользователя из дома")
    void checkEvictUserFromHouse() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка выселения пользователя из дома")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Выселение пользователя из дома");
        parameter("Ожидаемый результат", "Пользователь успешно выселен");

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        createdHouseId = createdHouse.getId();

        UserRequest userRequest = UserTestDataFactory.postUserTestDataApi();
        UserResponse userResponse = userAdapter.createUser(userRequest);
        createdUserId = userResponse.getId();

        usersSteps
                .validateUserId(createdUserId)
                .validateUserData(userResponse, userRequest);

        houseAdapter.settleUser(createdHouseId, createdUserId);

        HouseResponse updatedHouse = houseAdapter.evictUser(createdHouseId, createdUserId);

        assertTrue(updatedHouse.getLodgers().isEmpty()
                        || updatedHouse.getLodgers().stream().noneMatch(l -> l.getId() == createdUserId),
                "Пользователь %s все еще в списке lodgers после выселения".formatted(createdUserId));
    }
}