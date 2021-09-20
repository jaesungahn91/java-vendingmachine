package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("자판기 테스트")
class VendingMachineTest {

    @Test
    void set_coins(){
        VendingMachine vendingMachine = new VendingMachine(450);
        Map<Coin, Integer> coins = vendingMachine.getCoins();
        assertThat(450).isEqualTo(coins.keySet().stream()
                                        .mapToInt(coin -> coin.price * coins.get(coin)).sum());
    }

    @Test
    void set_drinks(){
        VendingMachine vendingMachine = new VendingMachine(450);
        vendingMachine.addDrinks("[콜라,20,1500];[사이다,10,1000]");
        assertThat(vendingMachine.getDrinks()).contains(new Drink("콜라", 20, 1500));
        assertThat(vendingMachine.getDrinks()).contains(new Drink("사이다", 10, 1000));
    }

    @ValueSource(ints = {111, 80, 0, -1})
    @ParameterizedTest
    void fail_set_drinks_by_amount(int amount) {
        VendingMachine vendingMachine = new VendingMachine(450);
        assertThatThrownBy(() -> vendingMachine.drinkValidate(new Drink("콜라", 10, amount)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(ints = {0, -1})
    @ParameterizedTest
    void fail_set_drinks_by_count(int count) {
        VendingMachine vendingMachine = new VendingMachine(450);
        assertThatThrownBy(() -> vendingMachine.drinkValidate(new Drink("콜라", 10, count)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(strings = {"[콜라,20,1500];[콜라,10,1000]"})
    @ParameterizedTest
    void fail_set_drinks_by_duplicated(final String drinksStr) {
        VendingMachine vendingMachine = new VendingMachine(450);
        assertThatThrownBy(() -> vendingMachine.addDrinks(drinksStr))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void buy_drink() {
        VendingMachine vendingMachine = new VendingMachine(450);
        vendingMachine.addDrinks("[콜라,20,1500];[사이다,10,1000]");
        int changes = vendingMachine.buyDrink(vendingMachine.findDrink("콜라"), 3000);
        Drink drink = vendingMachine.findDrink("콜라");
        assertThat(drink.getCount()).isEqualTo(19);
        assertThat(changes).isEqualTo(3000 - drink.getAmount());
    }

    @ValueSource(ints = {1, 80, 0, 700})
    @ParameterizedTest
    void fail_buy_drink_by_lack_of_money(int money) {
        VendingMachine vendingMachine = new VendingMachine(450);
        vendingMachine.addDrinks("[콜라,20,1500]");
        assertThat(vendingMachine.isBuy(money)).isNotEqualTo(true);
    }

    @Test
    void fail_buy_drink_by_lack_of_count() {
        VendingMachine vendingMachine = new VendingMachine(450);
        vendingMachine.addDrinks("[콜라,1,1500]");
        vendingMachine.findDrink("콜라").minusCount();
        assertThat(vendingMachine.isBuy(3000)).isNotEqualTo(true);
    }

    @Test
    void get_change() {
        VendingMachine vendingMachine = new VendingMachine(800);
        vendingMachine.addDrinks("[콜라,20,1500]");
        Map<Coin, Integer> changesCoins = vendingMachine.returnOfChange(vendingMachine.buyDrink(vendingMachine.findDrink("콜라"), 2000));
        Map<Coin, Integer> remainingCoins = vendingMachine.getCoins();
        assertThat(2000 - 1500).isEqualTo(changesCoins.keySet().stream()
                .mapToInt(coin -> coin.price * changesCoins.get(coin)).sum());
        assertThat(800 - (2000 - 1500)).isEqualTo(remainingCoins.keySet().stream()
                .mapToInt(coin -> coin.price * remainingCoins.get(coin)).sum());
    }

    @Test
    void get_change_amount_lock() {
        VendingMachine vendingMachine = new VendingMachine(3000);
        vendingMachine.addDrinks("[콜라,1,1500]");
        vendingMachine.findDrink("콜라").minusCount();
        if(vendingMachine.isBuy(2000)) {
            vendingMachine.buyDrink(vendingMachine.findDrink("콜라"), 2000);
        };
        Map<Coin, Integer> changesCoins = vendingMachine.returnOfChange(2000);
        Map<Coin, Integer> remainingCoins = vendingMachine.getCoins();
        assertThat(2000).isEqualTo(changesCoins.keySet().stream()
                .mapToInt(coin -> coin.price * changesCoins.get(coin)).sum());
        assertThat(3000 - 2000).isEqualTo(remainingCoins.keySet().stream()
                .mapToInt(coin -> coin.price * remainingCoins.get(coin)).sum());
    }

    @Test
    void get_change_coin_lack() {
        VendingMachine vendingMachine = new VendingMachine(300);
        vendingMachine.addDrinks("[콜라,20,1500]");
        Map<Coin, Integer> changesCoins = vendingMachine.returnOfChange(vendingMachine.buyDrink(vendingMachine.findDrink("콜라"), 2000));
        Map<Coin, Integer> remainingCoins = vendingMachine.getCoins();
        assertThat(300).isEqualTo(changesCoins.keySet().stream()
                .mapToInt(coin -> coin.price * changesCoins.get(coin)).sum());
        assertThat(2000 - 1500).isNotEqualTo(changesCoins.keySet().stream()
                .mapToInt(coin -> coin.price * changesCoins.get(coin)).sum());
        assertThat(0).isEqualTo(remainingCoins.keySet().stream()
                .mapToInt(coin -> coin.price * remainingCoins.get(coin)).sum());
    }
}