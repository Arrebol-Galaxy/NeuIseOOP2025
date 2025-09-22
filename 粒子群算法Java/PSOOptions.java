public class PSOOptions {
    Integer swarmSize;    // 种群大小（粒子数量）
    Integer maxIters;     // 最大迭代次数
    Double wStart;    // 惯性权重起始值
    Double wEnd;      // 惯性权重结束值
    Double c1, c2;    // 学习因子（个体/社会）
    Double clampFrac; // 速度限制比例
    Topology topology;// 拓扑结构
    Integer ringK;        // 环形拓扑的“邻居数量K”
    Double tol;       // 收敛容差（函数值变化小于tol则停止）
    Integer patience;     // 耐心值（容差满足patience次则早停）
    long seed;        // 随机种子（用于复现结果）

    public PSOOptions() {
        swarmSize = 30;
        maxIters = 1000;
        wStart = 0.9;
        wEnd = 0.4;
        c1 = 2.0;
        c2 = 2.0;
        clampFrac = 0.1;
        topology = Topology.GLOBAL;
        ringK = 3;
        tol = 1e-6;
        patience = 10;
        seed = System.currentTimeMillis();
    }

    public Integer getSwarmSize() {
        return swarmSize;
    }

    public void setSwarmSize(Integer swarmSize) {
        this.swarmSize = swarmSize;
    }

    public Integer getMaxIters() {
        return maxIters;
    }

    public void setMaxIters(Integer maxIters) {
        this.maxIters = maxIters;
    }

    public Double getWStart() {
        return wStart;
    }

    public Double getWEnd() {
        return wEnd;
    }

    public Double getC1() {
        return c1;
    }

    public Double getC2() {
        return c2;
    }

    public Double getTol() {
        return tol;
    }

    public Integer getPatience() {
        return patience;
    }
}