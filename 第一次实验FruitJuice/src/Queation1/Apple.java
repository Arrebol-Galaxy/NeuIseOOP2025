package Queation1;

public class Apple {
    private static final double SUGAR_RATE = 0.13;
    private static final double VITAMIN_RATE = 0.005;

    private double weight;

    public Apple(double weight){
        this.weight = weight;
    }

    public double getSugar() {
        return this.weight * SUGAR_RATE;
    }

    public double getVitamins() {
        return this.weight * VITAMIN_RATE;
    }

    public double getWeight() {
        return this.weight;
    }
}
