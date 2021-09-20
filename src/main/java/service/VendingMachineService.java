package service;

import com.woowahan.techcourse.utils.Randoms;
import domain.Coin;
import domain.Drink;

import java.util.*;

public class VendingMachineService {

    /**
     * 보유금액을 랜덤 코인으로 변경
     **/
    public Map<Coin, Integer> getRandomCoin(int amount) {
        Map<Coin, Integer> coins = new HashMap<>();
        while (amount > 0) {
            int pick = Randoms.pick(Coin.getPriceList());
            if (amount - pick >= 0) {
                Coin coin = Coin.priceOf(pick);
                coins.put(coin, coins.getOrDefault(coin, 0)+1);
                amount -= pick;
            }
        }
        return coins;
    }

    /**
     * 드링크 문자열을 드링크 리스트로 변환
     **/
    public List<Drink> stringToDrinks(String drinksStr) {
        List<Drink> drinkList = new ArrayList<>();
        StringTokenizer drinksTokenizer = new StringTokenizer(drinksStr, ";");
        while(drinksTokenizer.hasMoreTokens()) {
            String drinkStr = drinksTokenizer.nextToken().replace("[", "").replace("]", "");
            StringTokenizer drinkTokenizer = new StringTokenizer(drinkStr, ",");
            drinkList.add(new Drink(drinkTokenizer.nextToken(), Integer.parseInt(drinkTokenizer.nextToken()), Integer.parseInt(drinkTokenizer.nextToken())));
        }
        return drinkList;
    }
}
