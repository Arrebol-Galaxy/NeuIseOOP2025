public class SchwefelFunction implements ObjectiveFunction {

    private final int dimension;
    private static final double MIN_RANGE = -500.0;
    private static final double MAX_RANGE = 500.0;

    public SchwefelFunction(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public double evaluate(double[] x) {
        double sum = 0.0;
        for (double val : x) {
            double clamped = Math.max(MIN_RANGE, Math.min(MAX_RANGE, val));
            sum += clamped * Math.sin(Math.sqrt(Math.abs(clamped)));
        }
        return 418.9829 * dimension - sum;
    }
}