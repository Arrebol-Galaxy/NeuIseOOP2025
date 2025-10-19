package Queation1;

public class Pear {
    private static final double SUGAR_RATE = 0.10;
    private static final double VITAMIN_RATE = 0.003;

    private double weight;

    public Pear(double weight){
        this.weight = weight;
    }

    public double getSugar() {
        return this.weight * SUGAR_RATE;
    }

    public double getVitamins() {
        return this.weight * VITAMIN_RATE;
    }

    public double getWeight() {
        return weight;
    }
}
