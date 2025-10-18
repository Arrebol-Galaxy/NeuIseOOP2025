package Queation2;

public class Orange extends Fruit {
    private static final double SUGAR_RATE = 0.12;   // c1: 含糖率
    private static final double VITAMIN_RATE = 0.008; // c2: 维生素率

    public Orange(double weight) {
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