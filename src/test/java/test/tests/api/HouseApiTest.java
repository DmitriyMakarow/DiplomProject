package test.tests.api.houses;

import api.models.HouseRequest;
import api.models.HouseResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import test.tests.BaseTest;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

@Epic("Дома. API")
@Feature("Создание и редактирование дома")
public class HouseApiTest extends BaseTest {

    private final Integer id = faker.number().numberBetween(1, Integer.MAX_VALUE);
    private final int floorCount = faker.number().numberBetween(1, 50);
    private final double price = faker.number().numberBetween(100000, 10000000);
    private final int parkingCount = faker.number().numberBetween(1, 10);

    HouseRequest.ParkingPlace parkingPlace = HouseRequest.ParkingPlace.builder()
            .id(0)
            .isWarm(true)
            .isCovered(true)
            .placesCount(parkingCount)
            .build();

    HouseRequest house = HouseRequest.builder()
            .id(id)
            .floorCount(floorCount)
            .price(price)
            .parkingPlaces(Collections.singletonList(parkingPlace))
            .lodgers(Collections.emptyList())
            .build();

    @Test(testName = "Проверка создания дома с валидными параметрами")
    void checkCreateHouse() {
        HouseResponse houseResponse = houseAdapter.createApiHouse(house);
        Integer idHouse = houseResponse.id;

        assertEquals(houseResponse.floorCount, floorCount);
        assertEquals(houseResponse.price, price);

        houseAdapter.deleteApiHouse(idHouse);
    }

    @Test(testName = "Проверка редактирования дома валидными параметрами")
    void checkEditHouse() {
        // генерируем новые данные для обновления
        int newFloorCount = faker.number().numberBetween(1, 50);
        double newPrice = faker.number().numberBetween(100000, 10000000);
        int newParkingCount = faker.number().numberBetween(1, 10);

        // создаём исходный дом
        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        Integer idHouse = createdHouse.id;

        // формируем запрос на обновление (используем тот же id, но новые данные)
        HouseRequest.ParkingPlace newParkingPlace = HouseRequest.ParkingPlace.builder()
                .id(0)
                .isWarm(false) // меняем значение для проверки
                .isCovered(true)
                .placesCount(newParkingCount)
                .build();

        HouseRequest updatedHouse = HouseRequest.builder()
                .id(idHouse)
                .floorCount(newFloorCount)
                .price(newPrice)
                .parkingPlaces(Collections.singletonList(newParkingPlace))
                .lodgers(Collections.emptyList())
                .build();

        // отправляем put-запрос
        HouseResponse updatedResponse = houseAdapter.putApiHouse(idHouse, updatedHouse);

        // проверяем, что данные обновились
        assertEquals(updatedResponse.floorCount, newFloorCount);
        assertEquals(updatedResponse.price, newPrice);
        assertEquals(updatedResponse.parkingPlaces.get(0).getPlacesCount(), newParkingCount);

        // удаляем дом после теста
        houseAdapter.deleteApiHouse(idHouse);
    }
}