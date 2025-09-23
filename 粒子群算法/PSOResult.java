
import java.util.Vector;

public class PSOResult {
    Vector<Double> bestX;
    Double bestF;
    Integer iters;

    public PSOResult(Vector<Double> bestX, Double bestF, Integer iters) {
        this.bestX = bestX;
        this.bestF = bestF;
        this.iters = iters;
    }

    public Vector<Double> getBestX() {
        return bestX;
    }

    public Double getBestF() {
        return bestF;
    }

    public Integer getIters() {
        return iters;
    }
}
