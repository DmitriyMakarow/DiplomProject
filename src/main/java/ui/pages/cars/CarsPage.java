package ui.pages.cars;

import ui.dto.cars.CarTestData;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

public class CarsPage extends BasePage {

    public void addNewCarUI(CarTestData carTestData) {
        new Input("car_engine_type_send").fillField(carTestData.getEngineType());
        new Input("car_mark_send").fillField(carTestData.getMark());
        new Input("car_model_send").fillField(carTestData.getModel());
        new Input("car_price_send").fillField(carTestData.getPrice());
    }
}
