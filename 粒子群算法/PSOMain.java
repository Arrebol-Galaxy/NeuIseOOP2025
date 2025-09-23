import java.util.Vector;

public class PSOMain {
    public static void main(String[] args) {
        ObjectiveFunction func = new Rastrigin(30);
        
        PSOOptions options = new PSOOptions();
        options.setSwarmSize(200);
        options.setMaxIters(1000);
        
        PSOResult result = minimize(func, options);
        
        System.out.println("全局最优位置: " + result.getBestX());
        System.out.println("全局最优值: " + result.getBestF());
        System.out.println("迭代次数: " + result.getIters());
    }

    public static PSOResult minimize(ObjectiveFunction func, PSOOptions options) {
        Integer d = func.dimension(); // 问题维度
        Integer swarmSize = options.getSwarmSize();
        Integer maxIters = options.getMaxIters();
        
        Vector<Particle> swarm = new Vector<>(swarmSize);
        Vector<Double> gbest = new Vector<>(d);
        for (Integer i = 0; i < d; i++) {
            gbest.add(Double.POSITIVE_INFINITY);
        }
        Double gbestF = Double.POSITIVE_INFINITY;

        for (Integer i = 0; i < swarmSize; i++) {
            Particle p = new Particle(d);
            
            Utils.fillRandomUniform(p.getX(), func.getLowerBounds(), func.getUpperBounds());
            
            for (Integer j = 0; j < d; j++) {
                Double range = func.upperBound(j) - func.lowerBound(j);
                p.getV().set(j, Utils.random(-range * 0.1, range * 0.1));
            }
            
            p.setFx(func.evaluate(p.getX()));
            p.setPbest(Utils.cloneVector(p.getX()));
            p.setFpbest(p.getFx());

            if (p.getFpbest() < gbestF) {
                gbestF = p.getFpbest();
                gbest = Utils.cloneVector(p.getPbest());
            }
            
            swarm.add(p);
        }

        Integer iters = 0;
        Integer patienceCount = 0;
        Double prevGbestF = Double.POSITIVE_INFINITY;

        while (iters < maxIters) {
            iters++;
            Double w = options.getWStart() - (options.getWStart() - options.getWEnd()) * iters / maxIters;

            for (Particle p : swarm) {
                for (Integer j = 0; j < d; j++) {
                    Double r1 = Math.random();
                    Double r2 = Math.random();
                    Double inertia = w * p.getV().get(j);
                    Double cognitive = options.getC1() * r1 * (p.getPbest().get(j) - p.getX().get(j));
                    Double social = options.getC2() * r2 * (gbest.get(j) - p.getX().get(j));
                    p.getV().set(j, inertia + cognitive + social);
                }

                for (Integer j = 0; j < d; j++) {
                    Double newX = p.getX().get(j) + p.getV().get(j);
                    p.getX().set(j, newX);
                }

                Utils.repairBox(p.getX(), func.getLowerBounds(), func.getUpperBounds());

                p.setFx(func.evaluate(p.getX()));

                if (p.getFx() < p.getFpbest()) {
                    p.setPbest(Utils.cloneVector(p.getX()));
                    p.setFpbest(p.getFx());

                    if (p.getFpbest() < gbestF) {
                        gbestF = p.getFpbest();
                        gbest = Utils.cloneVector(p.getPbest());
                        patienceCount = 0;
                    }
                }
            }

            if (Math.abs(gbestF - prevGbestF) < options.getTol()) {
                patienceCount++;
                if (patienceCount >= options.getPatience()) {
                    break;
                }
            } else {
                patienceCount = 0;
            }
            prevGbestF = gbestF;
        }

        return new PSOResult(gbest, gbestF, iters);
    }
}
    