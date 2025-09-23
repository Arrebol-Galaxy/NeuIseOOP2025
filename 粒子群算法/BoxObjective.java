import java.util.Vector;

abstract class BoxObjective implements ObjectiveFunction {
    Integer d;
    Vector<Double> lb;
    Vector<Double> ub;

    public BoxObjective(Integer dimension, Vector<Double> lowerBound, Vector<Double> upperBound){
        this.d = dimension;
        this.lb = lowerBound;
        this.ub = upperBound;
    }

    @Override
    public Integer dimension(){
        return d;
    }

    @Override
    public Double lowerBound(Integer i){
        return lb.get(i);
    }

    @Override
    public Double upperBound(Integer i){
        return ub.get(i);
    }
     
    @Override
    public Vector<Double> getLowerBounds() {
        return lb;
    }

    @Override
    public Vector<Double> getUpperBounds() {
        return ub;
    }

    @Override
    public abstract Double evaluate(Vector<Double> x);
}
