package Queation2;

public abstract class Fruit {
    protected double weight;

    public Fruit(double weight) {
        this.weight = weight > 0 ? weight : 0;
    }

    public double getWeight() {
        return weight;
    }

    public abstract double getSugar();
    public abstract double getVitamins();
}