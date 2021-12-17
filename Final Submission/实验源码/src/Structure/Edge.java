package Structure;

/**
 * 边的类，用于算最短路和画图
 */
public class Edge {
    /**
     * 边的前一个结点
     */
    Node from;
    /**
     * 边的后一个结点
     */
    Node to;
    /**
     * 边的权重
     */
    int weight;

    /**
     * 边的构造器
     *
     * @param from   边的前一个结点
     * @param to     边的后一个结点
     * @param weight 边的权重
     */
    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * @return 边的前一个结点
     */
    public Node getFrom() {
        return from;
    }

    /**
     * @return 边的后一个结点
     */
    public Node getTo() {
        return to;
    }

    /**
     * @return 边的权重
     */
    public int getWeight() {
        return weight;
    }
}