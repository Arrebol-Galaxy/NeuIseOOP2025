import java.util.Vector;

public class Rastrigin extends BoxObjective {
    public Rastrigin(Integer dimension){
        // super必须放在第一行，之后调用父类字段即可
        super(dimension, new Vector<>(dimension), new Vector<>(dimension));

        for(int i = 0; i < dimension; i++){
            lb.add(-5.12);
            ub.add(5.12);
        }
    }

    @Override
    public Double evaluate(Vector<Double> x){
        Double sum = 0.0;

        for (int i = 0; i < d - 1; i++) {
            double xi = x.get(i);
            // 计算当前项并累加
            sum += xi*xi - 10 * Math.cos(2*Math.PI*xi);
        }

        return 10 * d + sum;
    }
}
