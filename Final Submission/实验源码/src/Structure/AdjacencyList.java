package Structure;

import java.io.*;
import java.util.*;

/**
 * 景点园区图的邻接链表
 */
public class AdjacencyList implements Graph {
    /**
     * 存放景点名信息的哈希图
     */
    private MyHashMap<String, Node> graphHashMap;

    /**
     * 边集，用于不同算法计算最短路径
     */
    private ArrayList<Edge> edges;

    /**
     * 邻接链表构造器
     */
    public AdjacencyList() {
        graphHashMap = new MyHashMap<String, Node>();
    }

    /**
     * @param key 寻找的景点名
     * @return 在邻接链表中存放的景点
     */
    public TouristAttraction find(String key) {
        if (graphHashMap.containsKey(key)) {
            return graphHashMap.get(key).getTouristAttraction();
        } else {
            return null;
        }
    }


    /**
     * 加上某一个景点
     *
     * @param key 景点名称
     * @param x   景点的x坐标，用于界面画图
     * @param y   景点的y坐标，用于界面画图
     */
    public void add(String key, int x, int y) {
        graphHashMap.put(key, new Node(new TouristAttraction(key, x, y)));
    }

    /**
     * 加上某一个景点的邻接结点
     *
     * @param node1 边的前一个结点
     * @param node2 边的后一个结点
     * @return 是否添加成功
     */
    public boolean addNode(Node node1, Node node2) {
        // 得到邻接链表的一个结点
        Node current = graphHashMap.get(node1.getTouristAttraction().getName());
        // node2是否已经在node1的邻接结点中，即是否这条边已经在邻接链表中存在
        boolean exist = false;
        // 遍历node1的所有邻接结点
        while (current != null && current.hasNext()) {
            current = current.getNextNode();
            // 找到node2同名结点，即这条边已经存在
            if (current.getTouristAttraction().getName().equals(node2.getTouristAttraction().getName())) {
                // exist设为true
                exist = true;
            }
        }
        if (!exist) {
            current.setNextNode(node2);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param keyWord 移除的结点名
     */
    public void remove(String keyWord) {
        // keyWord结点的整条链表删除
        graphHashMap.remove(keyWord);
        // 单项链表的删除
        for (Node each : graphHashMap.values) {
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

    /**
     * @return 邻接链表的字符串形式
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : graphHashMap.keySet) {
            // 如果哈希表的key内没有值，搜索下一个
            if (graphHashMap.get(key) == null) continue;
            else {
                // stringBuilder添加结点信息
                stringBuilder.append(key);
                Node current = graphHashMap.get(key);
                if (current != null) {
                    // 添加该结点的邻接结点信息
                    stringBuilder.append(":").append(current.getTouristAttraction().getName());
                    while (current.hasNext()) {
                        current = current.getNextNode();
                        stringBuilder.append("-->").append(current.getTouristAttraction().getName()).append('(').append(current.getWeight()).append(')');
                    }
                    stringBuilder.append("\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * @return 邻接链表的迭代器
     */
    public Iterator<MyHashMap.Node<String, Node>> getIterator() {
        return graphHashMap.entrySet.iterator();
    }

    /**
     * 从邻接链表中移除一条路
     *
     * @param list 路的信息
     * @return 移除的结果，即该路是否存在
     */
    public boolean removeRoad(String[] list) {
        boolean find = false;
        // 删除路的前一个结点后的邻接结点
        Node outNode = graphHashMap.get(list[0]);
        Node current;
        while (outNode != null && outNode.hasNext()) {
            // 第一个结点已经知道了，所以直接求后面的边删掉
            current = outNode;
            outNode = outNode.getNextNode();
            if (outNode.getTouristAttraction().getName().equals(list[1])) {
                current.setNextNode(outNode.getNextNode());
                find = true;
            }
        }
        // 删除路的后一个结点后的邻接结点
        Node inNode = graphHashMap.get(list[1]);
        while (inNode != null && inNode.hasNext()) {
            // 第一个结点已经知道了，所以直接求后面的边删掉
            current = inNode;
            inNode = inNode.getNextNode();
            if (inNode.getTouristAttraction().getName().equals(list[0])) {
                current.setNextNode(inNode.getNextNode());
            }
        }
        // 在边集中删除该边，防止之后算最短路出错
        for (int i = 0; i < edges.size(); i++) {
            if ((edges.get(i).getFrom().getTouristAttraction().getName().equals(list[0]) && edges.get(i).getTo().getTouristAttraction().getName().equals(list[1])) || (edges.get(i).getFrom().getTouristAttraction().getName().equals(list[1]) && edges.get(i).getTo().getTouristAttraction().getName().equals(list[0]))) {
                edges.remove(i);
            }
        }
        // 移除的结果，即该路是否存在
        return find;
    }

    /**
     * 在边集中增加一条边，用于之后的最短路查找和H图查找
     *
     * @param to     边的后一个结点
     * @param from   边的前一个结点
     * @param weight 边的权值
     */
    public void insertEdge(Node to, Node from, int weight) {
        Edge edge1 = new Edge(to, from, weight);
        Edge edge2 = new Edge(from, to, weight);
        edges.add(edge1);
        edges.add(edge2);
    }

    /**
     * 得到一个结点的分枝数
     *
     * @param key 结点的景点名
     * @return 分枝数
     */
    public int getBranches(String key) {
        // 找到当前结点
        Node current = graphHashMap.get(key);
        if (current == null) return 0;
        // 计算结点的分支数，即结点的邻接结点数
        int count = 1;
        while (current.hasNext()) {
            current = current.getNextNode();
            count++;
        }
        return count;
    }

    /**
     * 迪杰斯特拉算法得到最短路
     * 这个算法和其他三个最短路算法分开写了
     *
     * @param start 开始结点
     * @param end   结束结点
     * @return 最短路和最短路径长度
     */
    public MyPair<CarStack<Edge>, Integer> getShortestPathWithDijkstra(String start, String end) {
        HashMap<Node, Edge> edgeTo = new HashMap<>();
        if (!graphHashMap.containsKey(start) || !graphHashMap.containsKey(end)) return null;
        // 开始结点
        Node startNode = graphHashMap.get(start);
        // 结束结点
        Node endNode = graphHashMap.get(end);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
        // 选择起始点
        // 每一个结点的cost初始值为无穷
        // MyHashMap的遍历方式
        for (MyHashMap.Node<String, Node> each : graphHashMap.entrySet) {
            each.getValue().setCost(Integer.MAX_VALUE);
            edgeTo.put(each.getValue(), null);
        }
        // 起点的cost设置为0
        graphHashMap.get(startNode.getTouristAttraction().getName()).setCost(0);
        priorityQueue.add(graphHashMap.get(startNode.getTouristAttraction().getName()));
        // 判断是否能relax
        while (!priorityQueue.isEmpty()) {
            judge(priorityQueue.poll(), edgeTo, priorityQueue);
        }
        // 记录最短路径
        CarStack<Edge> path = new CarStack<>();
        for (Edge e = edgeTo.get(endNode); e != null; e = edgeTo.get(e.from)) {
            path.push(e);
        }
        MyPair<CarStack<Edge>, Integer> pair = new MyPair<>(path, graphHashMap.get(endNode.getTouristAttraction().getName()).getCost());
        return pair;
    }

    /**
     * @param node          当前的顶点
     * @param edgeTo        边集
     * @param priorityQueue 存放结点的优先队列
     */
    private void judge(Node node, HashMap<Node, Edge> edgeTo, PriorityQueue<Node> priorityQueue) {
        Node tempNode = node;
        while (node.getNextNode() != null) {
            // 判断是否有更短的距离
            if ((graphHashMap.get(tempNode.getTouristAttraction().getName()).getCost() + node.getNextNode().getWeight()) < graphHashMap.get(node.getNextNode().getTouristAttraction().getName()).getCost()) {
                graphHashMap.get(node.getNextNode().getTouristAttraction().getName()).setCost(graphHashMap.get(tempNode.getTouristAttraction().getName()).getCost() + node.getNextNode().getWeight());
                edgeTo.replace(graphHashMap.get(node.getNextNode().getTouristAttraction().getName()), new Edge(tempNode, node.getNextNode(), graphHashMap.get(tempNode.getTouristAttraction().getName()).getCost() + node.getNextNode().getWeight()));
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

    /**
     * @return 景点的个数
     */
    public int size() {
        return graphHashMap.size();
    }

    /**
     * @return 返回边集的数组
     */
    public Edge[] getEdges() {
        Edge[] edges1 = new Edge[edges.size()];
        return edges.toArray(edges1);
    }

    /**
     * @param edges 设置边集，用于算最短路
     */
    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public void createGraph(Graph g) {

    }

    public MyHashMap<String, Node> getGraphHashMap() {
        return graphHashMap;
    }
}
