package domain;

public class Drink {
   private String name;
   private int count;
   private int amount;

    public Drink(String name, int count, int amount) {
        this.name = name;
        this.count = count;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void minusCount() {
        this.count -= 1;
    }

    public boolean isBuyDrink(int money) {
        return this.count > 0 && this.amount <= money;
    }
}
