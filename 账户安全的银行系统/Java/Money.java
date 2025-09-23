public class Money {
    private int money;

    public Money(int amount) {
        this.money = amount;
    }

    public int getBalance() {
        return money;
    }

    public void add(int modification) {
        this.money += modification;
    }
}