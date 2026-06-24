package enumUI;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RadioLabel {
    MALE("MALE"),
    FEMALE("FEMALE"),
    BUY("buyCar"),
    SELL("sellCar"),
    SETTLE("settle"),
    EVICT("evict");

    private final String radioLabel;

    @Override
    public String toString() {
        return radioLabel;
    }
}
