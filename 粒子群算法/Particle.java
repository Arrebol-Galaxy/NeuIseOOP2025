
import java.util.Vector;

public class Particle {
    Vector<Double> x;
    Vector<Double> v;
    Vector<Double> pbest;
    Double fx;
    Double fpbest;

    public Particle(Integer dimension){
        x = new Vector<Double>(dimension);
        for (int i = 0; i < dimension; i++) x.add(0.0);
        v = new Vector<Double>(dimension);
        for (int i = 0; i < dimension; i++) v.add(0.0);
        pbest = new Vector<Double>(dimension);
        for (int i = 0; i < dimension; i++) pbest.add(0.0);
    }

    public Vector<Double> getX() {
        return x;
    }

    public void setX(Vector<Double> x) {
        this.x = x;
    }

    public Vector<Double> getV() {
        return v;
    }

    public void setV(Vector<Double> v) {
        this.v = v;
    }

    public Vector<Double> getPbest() {
        return pbest;
    }

    public void setPbest(Vector<Double> pbest) {
        this.pbest = pbest;
    }

    public Double getFx() {
        return fx;
    }

    public void setFx(Double fx) {
        this.fx = fx;
    }

    public Double getFpbest() {
        return fpbest;
    }

    public void setFpbest(Double fpbest) {
        this.fpbest = fpbest;
    }
}
