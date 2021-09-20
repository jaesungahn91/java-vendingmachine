package domain;

import com.woowahan.techcourse.utils.Randoms;

import java.util.*;
import java.util.stream.Collectors;

public class VendingMachine {
    private final int holdingAmount;
    private final Map<Coin, Integer> coins;
    private final List<Drink> drinks;

    public VendingMachine(int amount) {
        this.holdingAmount = amount;
        this.coins = new HashMap<>();
        this.drinks = new ArrayList<>();
        setRandomCoin(holdingAmount);
    }

    /**
     * 보유금액을 랜덤 코인으로 변경
     **/
    private void setRandomCoin(int amount) {
        while (amount > 0) {
            int pick = Randoms.pick(Coin.getPriceList());
            if (amount - pick >= 0) {
                Coin coin = Coin.priceOf(pick);
                coins.put(coin, coins.getOrDefault(coin, 0)+1);
                amount -= pick;
            }
        }
    }

    public void addDrinks(String drinksStr) {
        for(Drink drink : stringToDrinks(drinksStr)) {
            drinkValidate(drink);
            this.drinks.add(drink);
        }
    }

    /**
     * 드링크 문자열을 드링크 리스트로 변환
     **/
    private List<Drink> stringToDrinks(String drinksStr) {
        List<Drink> drinkList = new ArrayList<>();
        StringTokenizer drinksTokenizer = new StringTokenizer(drinksStr.replace("[", "").replace("]", ""), ";");
        while(drinksTokenizer.hasMoreTokens()) {
            String drinkStr = drinksTokenizer.nextToken();
            StringTokenizer drinkTokenizer = new StringTokenizer(drinkStr, ",");
            String name = drinkTokenizer.nextToken();
            int count = Integer.parseInt(drinkTokenizer.nextToken());
            int amount = Integer.parseInt(drinkTokenizer.nextToken());
            drinkList.add(new Drink(name, count, amount));
        }
        return drinkList;
    }

    public void drinkValidate(Drink drink) {
        if(drink.getAmount() <= 100 || drink.getAmount()%10 != 0) {
            throw new IllegalArgumentException("음료의 최소 금액은 100이며, 10원으로 단위여야 합니다.");
        }
        if(drink.getCount() <= 0) {
            throw new IllegalArgumentException("음료의 개수는 1개 이상이여야 합니다.");
        }
        if(this.drinks.stream().anyMatch(d -> d.getName().equals(drink.getName()))) {
            throw new IllegalArgumentException("음료는 중복될 수 없습니다.");
        }
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
        return 0 < this.holdingAmount && this.drinks.stream().allMatch(drink -> drink.isBuyDrink(money));
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
    public Map<Coin, Integer> returnOfChange(int money) {
        Map<Coin, Integer> changeCoinMap = new HashMap<>();
        for (Coin coin : coins.keySet().stream().sorted((o1, o2) -> Integer.compare(o2.price, o1.price)).collect(Collectors.toList())) {
            if(coins.get(coin) > 0) {
                int count = money / coin.price;
                if(count > coins.get(coin)) {
                    count = coins.get(coin);
                }
                for (int i = 0; i < count; i++) {
                    changeCoinMap.put(coin, changeCoinMap.getOrDefault(coin, 0)+1);
                }
                money = money - (count * coin.price);
                this.coins.put(coin, coins.get(coin) - count);
            }
        }
        return changeCoinMap;
    }

    public Map<Coin, Integer> getCoins() {
        return coins;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }
}
