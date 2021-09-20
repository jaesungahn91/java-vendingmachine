package domain;

import java.util.Objects;

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

    public int getCount() {
        return count;
    }

    public void minusCount() {
        this.count -= 1;
    }

    public boolean isBuyDrink(int money) {
        return this.count > 0 && this.amount <= money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return amount == drink.amount && Objects.equals(name, drink.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }
}
