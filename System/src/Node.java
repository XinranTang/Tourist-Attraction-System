public class Node {
    private TouristAttraction touristAttraction;
    private int weight;
    private Node nextNode;
    private int cost;

    public Node(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
        this.weight = 0;
        this.nextNode = null;
    }

    public Node(TouristAttraction touristAttraction, int weight) {
        this.touristAttraction = touristAttraction;
        this.weight = weight;
        this.nextNode = null;
    }

    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public boolean hasNext(){
        return this.nextNode!=null;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
