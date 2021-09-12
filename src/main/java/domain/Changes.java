package domain;

import java.util.ArrayList;
import java.util.List;

class Changes {
    private int amount;

    public Changes(int amount) {
        this.amount = amount;
    }

    List<Coin> coin() {
        List<Coin> list = new ArrayList<>();
        for (Coin coin : Coin.highestOrder()) {
            final int count = amount / coin.price;
            for (int i = 0; i < count; i++) {
                list.add(coin);
            }
            amount = amount - (count * coin.price);
        }
        return list;
    }
}