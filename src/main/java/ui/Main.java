package ui;

import domain.VendingMachine;

public class Main {
    public static void main(String[] args) {
        VendingMachine vendingMachine = Console.initVendingMachine();
        Console.initDrinks(vendingMachine);
        Console.buyDrinks(vendingMachine);
    }
}
