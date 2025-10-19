package Queation2;

public class ApplePearJuice extends Juice {
    /**
     * 配方只有苹果和梨子
     */
    public ApplePearJuice(double appleWeight, double pearWeight) {
        super();
        this.fruits.add(new Apple(appleWeight));
        this.fruits.add(new Pear(pearWeight));
    }
}
