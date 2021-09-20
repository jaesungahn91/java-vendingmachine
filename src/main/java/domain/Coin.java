package domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Coin {

    _500(500),
    _100(100),
    _50(50),
    _10(10);

    int price;

    Coin(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static Coin priceOf(final int amount) {
        return Arrays.stream(values())
                .filter(coin -> coin.price == amount)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(amount + "원의 동전은 존재하지 않습니다."));
    }

    public static List<Coin> highestOrder() {
        return Arrays.stream(values())
                .sorted((o1, o2) -> Integer.compare(o2.price, o1.price))
                .collect(Collectors.toList());
    }

    public static List<Integer> getPriceList(){
        return highestOrder().stream().map(Coin::getPrice).collect(Collectors.toList());
    }
}
