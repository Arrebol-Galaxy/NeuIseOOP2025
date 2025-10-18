package Queation1;

import java.util.ArrayList;
import java.util.List;

public class Juice {
    private Apple apple;
    private Pear pear;

    public Juice(Apple apple, Pear pear) {
        this.apple = apple;
        this.pear = pear;
    }

    public String getReport() {
        double totalWeight = 0;
        double totalSugar = 0;
        double totalVitamins = 0;

        // 分别计算，代码无法扩展
        if (apple != null) {
            totalWeight += apple.getWeight();
            totalSugar += apple.getSugar();
            totalVitamins += apple.getVitamins();
        }
        if (pear != null) {
            totalWeight += pear.getWeight();
            totalSugar += pear.getSugar();
            totalVitamins += pear.getVitamins();
        }

        if (totalWeight == 0) {
            return "请添加水果";
        }

        return String.format(
                "--- 非多态设计营养报告 ---\n" +
                        "总重量: %.2f g\n\n" +
                        "总含糖量: %.2f g\n\n" +
                        "总维生素量: %.2f g\n" +
                        "------------------------",
                totalWeight, totalSugar, totalVitamins
        );
    }
}
    