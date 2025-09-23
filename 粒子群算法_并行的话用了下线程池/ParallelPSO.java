import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelPSO {

    private final int dimension;
    private final int particleCount;
    private final int maxIterations;
    private final double minRange;
    private final double maxRange;
    private final double w;
    private final double c1;
    private final double c2;

    private final Particle[] particles;
    private double[] globalBestPosition;
    private double globalBestValue;
    
    private final ObjectiveFunction objectiveFunction;
    private final ExecutorService executorService;

    public ParallelPSO(int dimension, int particleCount, int maxIterations, double minRange, double maxRange,
                       double w, double c1, double c2, ObjectiveFunction objectiveFunction) {
        this.dimension = dimension;
        this.particleCount = particleCount;
        this.maxIterations = maxIterations;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.w = w;
        this.c1 = c1;
        this.c2 = c2;
        this.objectiveFunction = objectiveFunction;

        this.particles = new Particle[particleCount];
        this.globalBestPosition = new double[dimension];
        this.globalBestValue = Double.POSITIVE_INFINITY;

        int numCores = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(numCores);  // 参考了一下这个：https://javaguide.cn/java/concurrent/java-thread-pool-summary.html#线程池示例代码
    }
    
    public void optimize() {
        initializeSwarm();

        for (int iter = 0; iter < maxIterations; iter++) {
            for (Particle p : particles) {
                updateVelocity(p);
                updatePosition(p);
            }

            evaluateFitnessAndSetBest();

            if (iter > 0 && iter % 100 == 0) {
                System.out.printf("迭代 %4d | 全局最优值: %.6f%n", iter, globalBestValue);
            }
        }
        
        shutdownExecutor();
    }
    
    private void initializeSwarm() {
        for (int i = 0; i < particleCount; i++) {
            particles[i] = new Particle(dimension, minRange, maxRange);
        }
        evaluateFitnessAndSetBest();
        System.out.printf("迭代    0 | 初始全局最优值: %.6f%n", globalBestValue);
    }
    
    private void evaluateFitnessAndSetBest() {
        List<Callable<FitnessResult>> tasks = new ArrayList<>();
        for (int i = 0; i < particleCount; i++) {
            final int particleIndex = i;
            tasks.add(() -> {
                double value = objectiveFunction.evaluate(particles[particleIndex].position);
                return new FitnessResult(particleIndex, value);
            });
        }
        
        try {
            List<Future<FitnessResult>> futures = executorService.invokeAll(tasks);
            Particle iterationBestParticle = null;

            for (Future<FitnessResult> future : futures) {
                FitnessResult result = future.get();
                int index = result.particleIndex;
                
                if (result.value < particles[index].bestValue) {
                    particles[index].bestValue = result.value;
                    System.arraycopy(particles[index].position, 0, particles[index].bestPosition, 0, dimension);
                }

                if (iterationBestParticle == null || particles[index].bestValue < iterationBestParticle.bestValue) {
                    iterationBestParticle = particles[index];
                }
            }
            
            if (iterationBestParticle != null) {
                updateGlobalBest(iterationBestParticle);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("并行计算适应度时出错。");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void updateGlobalBest(Particle particle) {
        if (particle.bestValue < globalBestValue) {
            globalBestValue = particle.bestValue;
            System.arraycopy(particle.bestPosition, 0, globalBestPosition, 0, dimension);
        }
    }
    
    private void updateVelocity(Particle p) {
        double maxVel = 0.2 * (maxRange - minRange);

        for (int i = 0; i < dimension; i++) {
            double r1 = ThreadLocalRandom.current().nextDouble();
            double r2 = ThreadLocalRandom.current().nextDouble();

            double cognitiveComponent = c1 * r1 * (p.bestPosition[i] - p.position[i]);
            double socialComponent = c2 * r2 * (globalBestPosition[i] - p.position[i]);

            p.velocity[i] = w * p.velocity[i] + cognitiveComponent + socialComponent;

            p.velocity[i] = Math.max(-maxVel, Math.min(maxVel, p.velocity[i]));
        }
    }
    
    private void updatePosition(Particle p) {
        for (int i = 0; i < dimension; i++) {
            p.position[i] += p.velocity[i];
        }
        handleBounds(p);
    }
    
    private void handleBounds(Particle p) {
        for (int i = 0; i < dimension; i++) {
            if (p.position[i] < minRange) {
                p.position[i] = minRange;
                p.velocity[i] = 0;
            } else if (p.position[i] > maxRange) {
                p.position[i] = maxRange;
                p.velocity[i] = 0;
            }
        }
    }

    public void printResult() {
        System.out.printf("最终全局最优值: %.6f%n", globalBestValue);
        System.out.print("最终全局最优位置: ");
        for (int i = 0; i < dimension; i++) {
            System.out.printf("%.4f ", globalBestPosition[i]);
        }
        System.out.println();
    }
    
    private static class FitnessResult {
        final int particleIndex;
        final double value;

        FitnessResult(int particleIndex, double value) {
            this.particleIndex = particleIndex;
            this.value = value;
        }
    }

    public void shutdownExecutor() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}