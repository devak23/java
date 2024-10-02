package functional;

import functional.model.Trader;
import functional.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Slf4j
public class ConsumerMain {

    public static void main(String[] args) {
        List<Trader> traders = getTraders();
        final Random random = new Random();

        Consumer<Trader> bonusGifter = trader -> trader.updateBonus(random.nextInt(10));

        List<Trader> tradersWithBonus = traders.stream()
                .peek(bonusGifter)
                .peek(t -> log.info(t.toString()))
                .toList();
    }

    private static List<Trader> getTraders() {
        final Random random = new Random();
        return IntStream
                .rangeClosed(1, 10)
                .mapToObj(i -> Trader
                        .builder()
                        .name(CommonUtil.fetchPersonName(i).fullName())
                        .initialUnits(random.nextInt(10))
                        .build()
                )
                .toList();
    }

}
