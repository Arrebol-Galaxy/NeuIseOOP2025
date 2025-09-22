import java.util.Vector;

// 这个直接扔给LLM写的莫名其妙法语注释都出来了

public class Schwefel extends BoxObjective {

    /**
     * Constructor para la función objetivo de Schwefel.
     * @param dimension El número de dimensiones del problema.
     */
    public Schwefel(Integer dimension) {
        // Llama al constructor de la clase base para inicializar la dimensión y los vectores de límites.
        super(dimension, new Vector<>(dimension), new Vector<>(dimension));

        // Establece los límites inferior y superior para cada dimensión [-500, 500].
        for (int i = 0; i < dimension; i++) {
            lb.add(-500.0);
            ub.add(500.0);
        }
    }

    /**
     * Evalúa la función de Schwefel para un vector de entrada x.
     * La fórmula es: f(x) = 418.9829*d - sum(x_i * sin(sqrt(|x_i|)))
     * @param x El vector de entrada a evaluar.
     * @return El valor de la función de Schwefel.
     */
    @Override
    public Double evaluate(Vector<Double> x) {
        Double sum = 0.0;
        
        // Calcula la suma de la fórmula.
        for (int i = 0; i < d; i++) {
            Double xi = x.get(i);
            sum += xi * Math.sin(Math.sqrt(Math.abs(xi)));
        }

        // Retorna el valor final de la función.
        return 418.9829 * d - sum;
    }
}