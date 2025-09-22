import java.util.Vector;

public class Rosenbrock extends BoxObjective {

    public Rosenbrock(Integer dimension){
        // super必须放在第一行，之后调用父类字段即可
        super(dimension, new Vector<>(dimension), new Vector<>(dimension));

        for(int i = 0; i < dimension; i++){
            lb.add(-5.0);
            ub.add(10.0);
        }
    }

    @Override
    public Double evaluate(Vector<Double> x){
        Double sum = 0.0;

        for (int i = 0; i < d - 1; i++) {
            double xi = x.get(i);
            double xi1 = x.get(i + 1);
            // 计算当前项并累加
            sum += 100 * Math.pow(xi1 - Math.pow(xi, 2), 2) + Math.pow(1 - xi, 2);
        }

        return 10 * d + sum;
    }
}
