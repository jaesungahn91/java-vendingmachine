package ui;

import domain.Coin;
import domain.VendingMachine;

import java.util.Map;
import java.util.Scanner;

public class Console {

    public static VendingMachine initVendingMachine() {
        System.out.println("자판기가 보유하고 있는 금액을 입력해 주세요.");
        int holdingAmount = new Scanner(System.in).nextInt();
        return new VendingMachine(holdingAmount);
    }

    public static void initDrinks(VendingMachine vendingMachine) {
        System.out.println("상품명과 수량, 금액을 입력해 주세요.");
        String drinksStr = new Scanner(System.in).next();
        vendingMachine.addDrinks(drinksStr);
    }

    public static void buyDrinks(VendingMachine vendingMachine) {
        System.out.println("투입 금액을 입력해 주세요.");
        int money = new Scanner(System.in).nextInt();
        while(vendingMachine.isBuy(money)){
            System.out.println("남은 금액: " + money + "원");
            System.out.println("구매할 상품명을 입력해 주세요.");
            String drinkName = new Scanner(System.in).next();
            money = vendingMachine.buyDrink(vendingMachine.findDrink(drinkName), money);
        }
        System.out.println("남은 금액: " + money + "원");
        Map<Coin, Integer> coins = vendingMachine.returnOfChange(money);
        for(Coin coin : coins.keySet()) {
            System.out.println(coin.getPrice() + "원 - " + coins.get(coin) + "개");
        }
    }
}