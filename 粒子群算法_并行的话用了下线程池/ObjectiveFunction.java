@FunctionalInterface
public interface ObjectiveFunction {
    double evaluate(double[] position);
}