package Queation2;

public class Pear extends Fruit {
    private static final double SUGAR_RATE = 0.10;   // b1: 含糖率
    private static final double VITAMIN_RATE = 0.003; // b2: 维生素率

    public Pear(double weight) {
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