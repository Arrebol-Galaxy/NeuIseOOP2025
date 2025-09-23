import java.util.Vector;

public interface ObjectiveFunction {
    public Double evaluate(Vector<Double> x);
    public Integer dimension();
    public Double lowerBound(Integer i);
    public Double upperBound(Integer i);
    public Vector<Double> getLowerBounds();
    public Vector<Double> getUpperBounds();
}