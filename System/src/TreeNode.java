public class TreeNode {
    private TouristAttraction touristAttraction;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(TouristAttraction touristAttraction, TreeNode left, TreeNode right) {
        this.touristAttraction = touristAttraction;
        this.left = left;
        this.right = right;
    }

    public TreeNode(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
