import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class AdjacencyList implements Graph {
    private HashMap<String, Node> graphHashMap;

    public AdjacencyList() {
        graphHashMap = new HashMap<String, Node>();
    }

    @Override
    public void createGraph(Graph g) {

    }

    public TouristAttraction find(String key) {
        if (graphHashMap.containsKey(key)) {
            return graphHashMap.get(key).getTouristAttraction();
        } else {
            return null;
        }
    }

    // 加上某一个景点
    public void add(String key) {
        graphHashMap.put(key, new Node(new TouristAttraction(key)));// TODO：创建景点时加上景点具体信息
    }

    // 加上某一个景点的邻接结点
    public void addNode(Node node1, Node node2) {
        Node current = graphHashMap.get(node1.getTouristAttraction().getName());
        while (current.hasNext()) {
            current = current.getNextNode();
        }
        current.setNextNode(node2);
    }

    public void remove(String keyWord) {
        // keyWord结点的整条链表删除
        graphHashMap.remove(keyWord);
        // 单项链表的删除
        for (Node each : graphHashMap.values()) {
            // 第一个是不可能等于keyWord的
            Node current;
            while (each.hasNext()) {
                current = each;
                each = each.getNextNode();
                if (each.getTouristAttraction().getName().equals(keyWord)) {
                    current.setNextNode(each.getNextNode());

                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : graphHashMap.keySet()) {
            stringBuilder.append(key);
            Node current = graphHashMap.get(key);
            stringBuilder.append(":").append(current.getTouristAttraction().getName());
            while (current.hasNext()) {
                current = current.getNextNode();
                stringBuilder.append("-->").append(current.getTouristAttraction().getName()).append('(').append(current.getWeight()).append(')');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Iterator<Map.Entry<String, Node>> getIterator() {
        return graphHashMap.entrySet().iterator();
    }

    public boolean removeRoad(String[] list) {
        boolean find = false;
        Node outNode = graphHashMap.get(list[0]);
        Node current;
        while (outNode.hasNext()) {
            // 第一个结点已经知道了，所以直接求后面的边删掉
            current = outNode;
            outNode = outNode.getNextNode();
            if (outNode.getTouristAttraction().getName().equals(list[1])) {
                current.setNextNode(outNode.getNextNode());
                find = true;
            }
        }
        Node inNode = graphHashMap.get(list[1]);
        while (inNode.hasNext()) {
            // 第一个结点已经知道了，所以直接求后面的边删掉
            current = inNode;
            inNode = inNode.getNextNode();
            if (inNode.getTouristAttraction().getName().equals(list[0])) {
                current.setNextNode(inNode.getNextNode());
            }
        }
        return find;
    }

    public int getBranches(String key) {
        Node current = graphHashMap.get(key);
        int count = 1;
        while (current.hasNext()) {
            current = current.getNextNode();
            count++;
        }
        return count;
    }

    public Stack<Edge> getShortestPath(String start, String end) {
        HashMap<Node, Edge> edgeTo = new HashMap<>();
        // 开始结点
        Node startNode = graphHashMap.get(start);
        // 结束结点
        Node endNode = graphHashMap.get(end);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
        // 选择起始点
        // 每一个结点的cost初始值为无穷
        graphHashMap.forEach((s, node) -> {
            node.setCost(Integer.MAX_VALUE);
            edgeTo.put(node, null);
        });
        // 起点的cost设置为0
        graphHashMap.get(startNode.getTouristAttraction().getName()).setCost(0);
        priorityQueue.add(graphHashMap.get(startNode.getTouristAttraction().getName()));
        while (!priorityQueue.isEmpty()) {
            judge(priorityQueue.poll(), edgeTo, priorityQueue);
        }
        System.out.println("最短路径长度为：" + graphHashMap.get(endNode.getTouristAttraction().getName()).getCost());
        Stack<Edge> path = new Stack<>();
        for (Edge e = edgeTo.get(endNode); e != null; e = edgeTo.get(e.from)) {
            path.push(e);
        }
        return path;
    }

    private void judge(Node node, HashMap<Node, Edge> edgeTo, PriorityQueue<Node> priorityQueue) {
        Node tempNode = node;
        while (node.getNextNode() != null) {
            if ((graphHashMap.get(tempNode.getTouristAttraction().getName()).getCost() + node.getNextNode().getWeight()) < graphHashMap.get(node.getNextNode().getTouristAttraction().getName()).getCost()) {
                graphHashMap.get(node.getNextNode().getTouristAttraction().getName()).setCost(graphHashMap.get(tempNode.getTouristAttraction().getName()).getCost() + node.getNextNode().getWeight());
                edgeTo.replace(graphHashMap.get(node.getNextNode().getTouristAttraction().getName()), new Edge(tempNode, node.getNextNode()));
//                System.out.println(tempNode.getTouristAttraction().getName()+" "+node.getNextNode().getTouristAttraction().getName()+graphHashMap.get(node.getTouristAttraction().getName()).getCost() + " "+ node.getNextNode().getWeight()+" "+(graphHashMap.get(node.getTouristAttraction().getName()).getCost() + node.getNextNode().getWeight()));
                Iterator<Node> iterator = priorityQueue.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getTouristAttraction().getName().equals(node.getNextNode().getTouristAttraction().getName())) {
                        iterator.remove();
                    }
                }
                priorityQueue.add(graphHashMap.get(node.getNextNode().getTouristAttraction().getName()));
            }
            node = node.getNextNode();
        }
    }

    public void singleTourSortGraph(String in) {
        Node startNode = graphHashMap.get(in);
        HashMap<String, Edge> edges = new HashMap<>();
        HashMap<String, Boolean> marked = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
        // 初始化cost
        graphHashMap.forEach((s, node) -> {
            node.setCost(Integer.MAX_VALUE);
            marked.put(node.getTouristAttraction().getName(), false);
        });
        startNode.setCost(0);
        priorityQueue.add(startNode);
        Node nextNode = null;
        for (Edge each : edges.values()) {
            if (each.from.getTouristAttraction().getName().equals(startNode.getTouristAttraction().getName())) {
                nextNode = each.to;
            }
        }
        if (nextNode == null) {
            System.out.println("未找到生成树");
        } else {
            // 建树
            TreeNode root = new TreeNode(startNode.getTouristAttraction());

        }
    }

    private void minSpan(Node node, HashMap<String, Edge> edges, HashMap<String, Boolean> marked, PriorityQueue<Node> priorityQueue) {
        marked.replace(node.getTouristAttraction().getName(), true);
        Node currentNode = node.getNextNode();
        while (currentNode.getNextNode() != null) {
            if (marked.get(currentNode.getNextNode().getTouristAttraction().getName())) {
                continue;
            }
            if (currentNode.getNextNode().getWeight() < graphHashMap.get(currentNode.getNextNode().getTouristAttraction().getName()).getCost()) {
                edges.replace(currentNode.getNextNode().getTouristAttraction().getName(), new Edge(currentNode, currentNode.getNextNode()));
                currentNode.getNextNode().setCost(currentNode.getNextNode().getWeight());
                priorityQueue.removeIf(node1 -> node1.getTouristAttraction().getName().equals(currentNode.getNextNode().getTouristAttraction().getName()));
                priorityQueue.add(graphHashMap.get(node.getNextNode().getTouristAttraction().getName()));
            }
        }
    }
}
