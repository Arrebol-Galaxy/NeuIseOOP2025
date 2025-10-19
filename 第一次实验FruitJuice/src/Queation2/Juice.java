package Queation2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Juice {
    protected List<Fruit> fruits;

    public Juice() {
        this.fruits = new ArrayList<>();
    }

    public final String getReport() {
        double totalWeight = 0, totalSugar = 0, totalVitamins = 0;

        for (Fruit fruit : fruits) {
            totalWeight += fruit.getWeight();
            totalSugar += fruit.getSugar();
            totalVitamins += fruit.getVitamins();
        }

        if (totalWeight == 0) {
            return "没有添加任何水果原料！";
        }

        return String.format(
                "--- %s 营养报告 ---\n\n" +
                        "总重量: %.2f g\n\n" +
                        "总含糖量: %.2f g\n\n" +
                        "总维生素量: %.2f g\n\n" +
                        "---------------------------",
                this.getClass().getSimpleName(), // 显示具体果汁子类的名字
                totalWeight, totalSugar, totalVitamins
        );
    }
}