package Structure;

/**
 * 结点类
 */
public class Node {
    /**
     * 当前结点存放的旅游景点信息
     */
    private TouristAttraction touristAttraction;
    /**
     * 前一个结点到结点的权重
     */
    private int weight;
    /**
     * 指向下一个结点的指针
     */
    private Node nextNode;
    /**
     * 结点的cost
     */
    private int cost;

    /**
     * 构造器
     *
     * @param touristAttraction 旅游景点信息
     */
    public Node(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
        this.weight = 0;
        this.nextNode = null;
    }

    /**
     * 构造器
     *
     * @param touristAttraction 旅游景点信息
     * @param weight            前一个结点到结点的权重
     */
    public Node(TouristAttraction touristAttraction, int weight) {
        this.touristAttraction = touristAttraction;
        this.weight = weight;
        this.nextNode = null;
    }

    /**
     * @return 旅游景点信息
     */
    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    /**
     * @param touristAttraction 旅游景点信息
     */
    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    /**
     * @return 前一个结点到结点的权重
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight 前一个结点到结点的权重
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return 指向下一个结点的指针
     */
    public Node getNextNode() {
        return nextNode;
    }

    /**
     * @param nextNode 指向下一个结点的指针
     */
    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * @return 是否存在下一个结点
     */
    public boolean hasNext() {
        return this.nextNode != null;
    }

    /**
     * @return 结点的cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost 结点的cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
}
