package domain;

import java.util.*;

public class VendingMachine {
    private final int holdingAmount;
    private final Map<Coin, Integer> coins;
    private List<Drink> drinks;

    public VendingMachine(int amount, Map<Coin, Integer> coins) {
        this.holdingAmount = amount;
        this.coins = coins;
        this.drinks = new ArrayList<>();
    }

    public void addDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    /**
     *  드링크 찾기
     **/
    public Drink findDrink(String drinkName) {
        return this.drinks.stream()
                .filter(drink -> drink.getName().equals(drinkName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 음료수 입니다."));
    }
    
    /**
    *  드링크 구매가능 여부
    **/
    public boolean isBuy(int money) {
        return money >= this.holdingAmount && this.drinks.stream().allMatch(drink -> drink.isBuyDrink(money));
    }

    /**
     * 드링크 구매
     **/
    public int buyDrink(Drink drink, int money) {
        drink.minusCount();
        return money - drink.getAmount();
    }

    /**
    * 거스름돈 반환
    * */
    public void returnOfChange(int money) {

    }
}
