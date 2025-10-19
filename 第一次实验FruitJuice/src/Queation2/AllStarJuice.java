package Queation2;

public class AllStarJuice extends Juice {
    /**
     * 配方包含三种水果
     */
    public AllStarJuice(double appleWeight, double pearWeight, double orangeWeight) {
        super();
        this.fruits.add(new Apple(appleWeight));
        this.fruits.add(new Pear(pearWeight));
        this.fruits.add(new Orange(orangeWeight));
    }
}
