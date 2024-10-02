package functional.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Trader {
    private String name;
    private int initialUnits;
    private int bonusUnits;
    private int totalUnits;

    public void updateBonus(int bonusUnits) {
        this.bonusUnits = bonusUnits;
        totalUnits = initialUnits + bonusUnits;
    }
}
