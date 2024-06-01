package assorted;

import java.time.LocalDateTime;

public class FXOrderTest {

    public static void main(String[] args) {
        FXOrder orig = new FXOrder(10, CurrencyPair.INRGBP, Side.BID, 113.45d, LocalDateTime.now(), 24);
        FXOrder copy = new FXOrder(orig.units(), orig.pair(), orig.side(), orig.price(), orig.sentAt(), orig.ttl());
        System.out.println(copy.equals(orig));
    }
}
