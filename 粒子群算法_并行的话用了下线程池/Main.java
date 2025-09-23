public class Main {
    public static void main(String[] args) {
        final int dimension = 30;
        final int particleCount = 500;
        final int maxIterations = 10000;
        final double minRange = -500.0;
        final double maxRange = 500.0;
        final double w = 0.7;
        final double c1 = 2.0;
        final double c2 = 2.0;

        System.out.println("维度: " + dimension);
        System.out.println("粒子数量: " + particleCount);
        System.out.println("最大迭代次数: " + maxIterations);
        System.out.println("目标函数: Schwefel 2.26");

        ObjectiveFunction schwefel = new SchwefelFunction(dimension);

        ParallelPSO pso = new ParallelPSO(dimension, particleCount, maxIterations, minRange, maxRange, w, c1, c2, schwefel);
        
        long startTime = System.currentTimeMillis();
        pso.optimize();
        long endTime = System.currentTimeMillis();
        
        pso.printResult();
        
        double durationSeconds = (endTime - startTime) / 1000.0;
        System.out.printf("总执行时间: %.4f 秒%n", durationSeconds);
    }
}