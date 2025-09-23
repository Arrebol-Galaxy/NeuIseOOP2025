import java.util.concurrent.ThreadLocalRandom;

public class Particle {
    double[] position;
    double[] velocity;
    double[] bestPosition;
    double bestValue;

    public Particle(int dimension, double minRange, double maxRange) {
        this.position = new double[dimension];
        this.velocity = new double[dimension];
        this.bestPosition = new double[dimension];
        this.bestValue = Double.POSITIVE_INFINITY;

        double range = maxRange - minRange;
        for (int i = 0; i < dimension; i++) {
            this.position[i] = minRange + range * ThreadLocalRandom.current().nextDouble();
            this.velocity[i] = range * 0.1 * (ThreadLocalRandom.current().nextDouble() - 0.5);
        }
        System.arraycopy(this.position, 0, this.bestPosition, 0, dimension);
    }
}