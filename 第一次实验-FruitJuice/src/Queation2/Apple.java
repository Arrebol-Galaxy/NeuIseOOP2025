package Queation2;

public class Apple extends Fruit {
    private static final double SUGAR_RATE = 0.13;   // a1: 含糖率
    private static final double VITAMIN_RATE = 0.005; // a2: 维生素率

    public Apple(double weight) {
        super(weight);
    }

    @Override
    public double getSugar() {
        return this.weight * SUGAR_RATE;
    }

    @Override
    public double getVitamins() {
        return this.weight * VITAMIN_RATE;
    }
}