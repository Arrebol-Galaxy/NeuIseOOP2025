import java.util.Random;
import java.util.Vector;

public class Utils {
    private static final Random random = new Random();

    public static Vector<Double> cloneVector(Vector<Double> vector) {
        Vector<Double> clone = new Vector<>(vector.size());
        for (Double value : vector) {
            clone.add(value);
        }
        return clone;
    }

    // 生成[min, max]范围内的随机数
    public static Double random(Double min, Double max) {
        return min + random.nextDouble() * (max - min);
    }

    // 用[lb, ub]范围内的随机数填充Vector
    public static void fillRandomUniform(Vector<Double> vector, Vector<Double> lb, Vector<Double> ub) {
        for (Integer i = 0; i < vector.size(); i++) {
            vector.set(i, random(lb.get(i), ub.get(i)));
        }
    }

    // 修复Vector中的值到[lb, ub]范围内
    public static void repairBox(Vector<Double> x, Vector<Double> lb, Vector<Double> ub) {
        for (Integer i = 0; i < x.size(); i++) {
            Double value = x.get(i);
            if (value < lb.get(i)) {
                x.set(i, lb.get(i));
            } else if (value > ub.get(i)) {
                x.set(i, ub.get(i));
            }
        }
    }
}
    